package com.revature.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.exceptions.UsernameDoesNotExistException;
import com.revature.models.StoredPassword;
import com.revature.models.User;
import com.revature.services.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
@CrossOrigin
public class UserController {
	private UserService userServ;

	@PostMapping("/create")
	public ResponseEntity<String> insertUser(@RequestBody LinkedHashMap<String, String> uMap) {// take out bio		
		String hashed = userServ.hashPassword(uMap.get("password"));
		
		
		StoredPassword sp = new StoredPassword(uMap.get("password"),hashed);
		User user = new User(uMap.get("username"), sp, uMap.get("email"));
		
		try {
		userServ.register(user);
		}catch(UserAlreadyExistsException e) {
			return new ResponseEntity<>("User was not registered", HttpStatus.NOT_ACCEPTABLE);

		}
		return new ResponseEntity<>("User was registered", HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@RequestBody LinkedHashMap<String, String> uMap) {
		User user;
		try {
			user = userServ.login(uMap.get("username"), uMap.get("password"));

		} catch (UsernameDoesNotExistException | InvalidCredentialsException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);

	}

	@PostMapping("/update")
	public ResponseEntity<String> updateUser(@RequestBody LinkedHashMap<String, String> uMap) {
		User user = userServ.getUserById(Integer.parseInt(uMap.get("user_id")));
		user.setBio(uMap.get("bio"));
		user.setEmail(uMap.get("email"));
		user.setPicUrl(uMap.get("pic_url"));
		if (userServ.updateUser(user)) {
			return new ResponseEntity<>("User was updated", HttpStatus.OK);
		}
		return new ResponseEntity<>("User was not updated", HttpStatus.NOT_MODIFIED);
	}

	/*
	 * @PostMapping("/updatepassword") public ResponseEntity<String>
	 * updateUser(@RequestBody LinkedHashMap<String, String> uMap) { User user =
	 * userServ.getUserById(uMap.get("user_id")); java mail
	 * 
	 * }
	 */

	@PostMapping("/startfollowing")
	public ResponseEntity<String> addFollowing(@RequestBody LinkedHashMap<String, String> uMap) {
		
		boolean following = userServ.addFollowing(Integer.parseInt(uMap.get("user_id")),
				Integer.parseInt(uMap.get("following_id")));
		boolean followed = userServ.addFollower(Integer.parseInt(uMap.get("following_id")),
				Integer.parseInt(uMap.get("user_id")));

		if (!following) {
			return new ResponseEntity<>("Unable to follow user", HttpStatus.NOT_MODIFIED);
		}
		if (!followed) {
			return new ResponseEntity<>("Unable to set followed by user", HttpStatus.NOT_MODIFIED);
		}
		return new ResponseEntity<>("User is now following", HttpStatus.OK);

	}

	@PostMapping("/stopfollowing")
	public ResponseEntity<String> removeFollowing(@RequestBody LinkedHashMap<String, String> uMap) {
		boolean stopfollowing = userServ.removeFollowing(Integer.parseInt(uMap.get("user_id")),
				Integer.parseInt(uMap.get("following_id")));
		boolean stopfollowed = userServ.removeFollower(Integer.parseInt(uMap.get("following_id")),
				Integer.parseInt(uMap.get("user_id")));

		if (!stopfollowing) {
			return new ResponseEntity<>("Unable to stop following user", HttpStatus.NOT_MODIFIED);
		}
		if (!stopfollowed) {
			return new ResponseEntity<>("Unable to set unfollowed by user", HttpStatus.NOT_MODIFIED);
		}
		return new ResponseEntity<>("User is now unfollowed", HttpStatus.OK);

	}

	@GetMapping("/username/{user_name}")
	public ResponseEntity<User> getUserByUsername(@PathVariable("user_name") String username) {
		System.out.println("in get user");
		User user = userServ.getUserByUsername(username);
		if (user == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	// insert ID {id}
	@GetMapping("/userid/{user_id}")
	public ResponseEntity<User> getUserByID(@PathVariable("user_id") int id) {
		User user = userServ.getUserById(id);
		if (user == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("/followers")
	public ResponseEntity<List<User>> getUserFollowers(@RequestBody LinkedHashMap<String, String> uMap) {
		System.out.println("in followers");
		List<User> sUsers = userServ.getUserFollowers(Integer.parseInt(uMap.get("user_id")));
		if (sUsers.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(sUsers, HttpStatus.OK);
	}

	@PostMapping("/following")
	public ResponseEntity<List<User>> getUserFollowing(@RequestBody LinkedHashMap<String, String> uMap) {

		List<User> sUsers = userServ.getUserFollowing(Integer.parseInt(uMap.get("user_id")));
		System.out.println(sUsers);
		if (sUsers.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(sUsers, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<List<User>> searchForUsername(@RequestBody LinkedHashMap<String, String> sMap) {
		String search = new String(sMap.get("search"));
		List<User> uList = userServ.searchByUsername(search);

		if (uList.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(uList, HttpStatus.OK);
	}
}
