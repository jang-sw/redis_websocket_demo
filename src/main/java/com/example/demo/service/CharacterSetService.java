package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.CharacterSet;
import com.example.demo.repository.CharacterSetRespository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CharacterSetService {

	private final CharacterSetRespository characterSetRespository;
	
	/**
	 * �α��� �� ĳ���͸� redis�� �߰�
	 * @param CharacterSet
	 * @return CharacterSet, ������ CharacterSet
	 * */
	public CharacterSet addCharacter(CharacterSet characterSet) {
		return characterSetRespository.save(characterSet);
    }
	
	/**
	 * redis�� ����� ��� ĳ���� ����Ʈ
	 * */
	public void printAllCharacter() {	
		List<CharacterSet> list = (List<CharacterSet>) characterSetRespository.findAll();
		list.stream().forEach(characterSet -> log.info(characterSet.toString()));		
	}
	
	/**
	 * @return redis�� ����� ��� ĳ����
	 * */
	public List<CharacterSet> findAllCharacter(){
		return (List<CharacterSet>) characterSetRespository.findAll();
	}
	
	/**
	 * redis�� ����� ĳ���� ����
	 * @param id(character)
	 * */
	public void deleteCharacterById(String id){
		characterSetRespository.deleteById(id);
	}
}
