package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.User;
import com.revature.repositories.UserRepository;

@Service("userServ")
public class UserService {

	private UserRepository urepo;

	public UserService() {
		super();
	}

	@Autowired
	public UserService(UserRepository urepo) {
		super();
		this.urepo = urepo;
	}

	public List<User> getAllUsers() {
		return urepo.selectAll();
	}
}
