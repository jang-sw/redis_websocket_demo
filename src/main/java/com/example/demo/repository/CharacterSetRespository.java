package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CharacterSet;

@Repository
public interface CharacterSetRespository extends CrudRepository<CharacterSet, String> {

}
	