package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Login {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int seq;

	private String id;
	private String pw;
	private String character;
	
}
