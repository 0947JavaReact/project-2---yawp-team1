package com.revature.repositories;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.models.User;

@Primary
public interface UserDao extends JpaRepository<User, Integer>{

	// methods Spring handles for the User
	public List<User> findAll();
	public User findByUsername(String username);
	public User findByEmail(String email);
	public List<User> findByUsernameContaining(String name);
	
}
