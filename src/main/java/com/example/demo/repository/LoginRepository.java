package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long>  {

	List<Login> findByIdAndPw(String id, String pw);

	@Query(value=
			  "SELECT * "
			+ "FROM "
			+ 	"login "
			+ "WHERE "
			+ 	"id=:#{#login.id} "
			+ "AND "
			+ 	"pw=:#{#login.pw}", 
			nativeQuery=true)
	public List<Login> findByIdAndPwUsingQuery(@Param(value="login")Login login);
}