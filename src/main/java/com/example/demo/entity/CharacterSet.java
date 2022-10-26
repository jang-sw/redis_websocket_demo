package com.example.demo.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@RedisHash("CharacterSet")
@Getter
@Setter
public class CharacterSet implements Serializable{
	@Id
	private String character;	
	
	private String state;
	private int x;
	private int y;
	
	public CharacterSet( String character, String state, int x, int y ) {
		this.character = character;
		this.state = state;
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "CharacterSet [character=" + character + ", state=" + state + ", x=" + x + ", y=" + y + "]";
	}
	
}

