package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.SearchReturnedZeroResultsException;
import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.exceptions.UsernameDoesNotExistException;
import com.revature.models.User;
import com.revature.repositories.UserDao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service("userServ")
public class UserService {
	private UserDao userDao;

	public List<User> getAllUsers() {
		return userDao.findAll();
	}
	
	public void register(User u) {
		if (userDao.findByUsername(u.getUsername()) !=null) {
			throw new UserAlreadyExistsException();
		}
		
		userDao.save(u);
	}

	
	public User getUserById(int id) {
		Optional<User> oUser = userDao.findById(id);
		
		
		return oUser.get();
	}
	
	public User getUserByUsername(String username) {
		if (userDao.findByUsername(username) ==null) {
			throw new UsernameDoesNotExistException();
		}
		return userDao.findByUsername(username);
	}
	
	public List<User> searchByUsername(String str) {
		List<User> uList = new ArrayList<>();
		uList = userDao.findByUsernameContaining(str);
		if (uList.isEmpty()) {
			throw new SearchReturnedZeroResultsException();
		}
		return uList;
	}
	
	
	public User login(String username, String password) {
		User user = new User();
		user = userDao.findByUsername(username);
		
		if (userDao.findByUsername(username) ==null) {
			throw new UsernameDoesNotExistException();
		}
		
		if(!user.getPassword().equals(password)) {
			 throw new InvalidCredentialsException();
		}
		return user;
	}
}
