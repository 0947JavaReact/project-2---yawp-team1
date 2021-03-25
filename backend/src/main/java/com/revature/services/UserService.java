package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.UserAlreadyExistsException;
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
	
	public void register(User u) {
		this.urepo.insert(u);
	}
	
	public void register(String username, String password, String email, String bio) {
		User u = new User(username, password, email, bio);
		if (this.urepo.selectByName(username) !=null) {
			throw new UserAlreadyExistsException();
		}
		
		this.urepo.insert(u);
	}
	
	public User getUserById(int id) {
		return urepo.selectById(id);
	}
	
	public User login(String username, String password) {
		User user = new User();
		user = urepo.selectByName(username);
		
		if(!user.getPassword().equals(password)) {
			 throw new InvalidCredentialsException();
		}
		return user;
	}
}
