package com.revature.controllers;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;
import com.revature.services.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class UserController {
	private UserService userServ;
	
	@PostMapping()
	public ResponseEntity<String> insertUser(@RequestBody LinkedHashMap<String, String> uMap) {
		User user =  new User(uMap.get("username"), uMap.get("password"), uMap.get("email"), uMap.get("bio"));
		userServ.register(user);
		return new ResponseEntity<>("User was registered",HttpStatus.CREATED);
	}
	
	@GetMapping("/{user_name}")
	public ResponseEntity<User> getUserByUsername(@PathVariable("user_name") String username) {
		System.out.println("in get user");
		User user =  userServ.getUserByUsername(username);
		if(user == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<User>> searchForUsername(@RequestBody LinkedHashMap<String, String> sMap) {
		String search = new String(sMap.get("search"));
		List<User> uList = userServ.searchByUsername(search);
		
		if(uList.isEmpty()) {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(uList,HttpStatus.OK);
	}
}
