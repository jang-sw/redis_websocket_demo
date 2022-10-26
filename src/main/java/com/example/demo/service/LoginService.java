package com.example.demo.service;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Login;
import com.example.demo.repository.LoginRepository;

@Service
public class LoginService {
	@Autowired
	LoginRepository loginRepository;
	
	/**
	 * @param Login
	 * @return 입력한 id ,pw가 일치하는 list 
	 * */
	public List<Login> findLoginListByIdAndPw(Login login) {
        try {
			String key = getKey(new File("D:/KEY.pem"));
			String algorithm ="HmacSHA256"; 
			login.setPw(hmac(key, login.getPw(), algorithm));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginRepository.findByIdAndPw(login.getId(), login.getPw());
	}
	/**
	 * 직접쿼리 이용
	 * @param Login
	 * @return 입력한 id ,pw가 일치하는 list 
	 * */
	public List<Login> findByIdAndPwUsingQuery(Login login) {
		try {
			String key = getKey(new File("D:/KEY.pem"));
			String algorithm ="HmacSHA256"; 
			login.setPw(hmac(key, login.getPw(), algorithm));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginRepository.findByIdAndPwUsingQuery(login);
	}
	/**
	 * Hmac 인코딩
	 * @param key, message, algorithm
	 * @return 변환된 문자열 
	 * */
	public String hmac(String key, String message, String algorithm) {
		try {
			 Mac hasher = Mac.getInstance(algorithm); 
		     hasher.init(new SecretKeySpec(key.getBytes("utf-8"), algorithm)); 
		     byte[] hash = hasher.doFinal(message.getBytes()); 
		     return Base64.encodeBase64String(hash);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}     
    }
	/**
	 * 파일로부터 key 받아오기
	 * @param file
	 * @return 키
	 * */
	public String getKey(File file) throws Exception{
		 String KeyPEM = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());
		 String key = KeyPEM
				 .replace("-----BEGIN key-----", "")
				 .replaceAll(System.lineSeparator(), "")
				 .replace("-----END key-----", "");

		 return key;
	}
}
