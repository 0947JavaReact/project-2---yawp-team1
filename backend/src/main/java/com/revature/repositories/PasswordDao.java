package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.StoredPassword;

public interface PasswordDao extends JpaRepository<StoredPassword, Integer>{

	public List<StoredPassword> findAll();
	public StoredPassword findByPlainTextPassword(String password);
	public StoredPassword findByHashedPassword(String hashed);
	
}
