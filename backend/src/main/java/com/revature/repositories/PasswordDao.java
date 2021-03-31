package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.StoredPassword;

public interface PasswordDao extends JpaRepository<StoredPassword, Integer>{

	// methods Spring handles for the Password
	public List<StoredPassword> findAll();
	public StoredPassword findByHashedPassword(String hashed);
	
}
