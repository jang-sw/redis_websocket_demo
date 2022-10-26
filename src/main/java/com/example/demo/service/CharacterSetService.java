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
	 * 로그인 한 캐릭터를 redis에 추가
	 * @param CharacterSet
	 * @return CharacterSet, 저장한 CharacterSet
	 * */
	public CharacterSet addCharacter(CharacterSet characterSet) {
		return characterSetRespository.save(characterSet);
    }
	
	/**
	 * redis에 저장된 모든 캐릭터 프린트
	 * */
	public void printAllCharacter() {	
		List<CharacterSet> list = (List<CharacterSet>) characterSetRespository.findAll();
		list.stream().forEach(characterSet -> log.info(characterSet.toString()));		
	}
	
	/**
	 * @return redis에 저장된 모든 캐릭터
	 * */
	public List<CharacterSet> findAllCharacter(){
		return (List<CharacterSet>) characterSetRespository.findAll();
	}
	
	/**
	 * redis에 저장된 캐릭터 제거
	 * @param id(character)
	 * */
	public void deleteCharacterById(String id){
		characterSetRespository.deleteById(id);
	}
}
