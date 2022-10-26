package com.example.demo.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.demo.entity.CharacterSet;
import com.example.demo.service.CharacterSetService;

import lombok.extern.log4j.Log4j2;

@Component
public class CharacterSetWebSocketHandler extends TextWebSocketHandler {
	private static List<WebSocketSession> webSocketSessionlist = new ArrayList<>();
	private static CharacterSetService characterSetService;
	private static Map<WebSocketSession, String> loginCharacter = new HashMap<>();
	@Autowired
	public void setCharacterSetService(CharacterSetService characterSetService) {
		CharacterSetWebSocketHandler.characterSetService = characterSetService;
	}
	
	/**
	 * �ż��� �ڵ鸵
	 * ���� �޼����� characterset���� ������ redis�� �߰�
	 * */
    @Override
    protected void handleTextMessage(WebSocketSession webSocketSession, TextMessage textMessage) throws Exception {
        String payload = textMessage.getPayload();
        String[] info = payload.split("&");
        loginCharacter.put(webSocketSession, info[3]);
        CharacterSet characterSet = new CharacterSet( info[3], info[2], Integer.parseInt(info[0]), Integer.parseInt(info[1]));
        characterSetService.addCharacter(characterSet);
//        characterSetService.printAllCharacter();
        for(WebSocketSession sess: webSocketSessionlist) {
            sess.sendMessage(textMessage);
        }
    }
    /**
	 * ����
	 * */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
    	webSocketSessionlist.add(webSocketSession);    
    }
    
    /**
	 * ���� ����
	 * loginCharacter���� webSocketSession�� �����Ǵ� character�� ã�� redis���� delete
	 * */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
    	Optional.ofNullable(loginCharacter.get(webSocketSession))
    		.ifPresent(character ->{
	    		characterSetService.deleteCharacterById(character);
	    		loginCharacter.remove(webSocketSession);
    		});
        webSocketSessionlist.remove(webSocketSession);
    }
}
