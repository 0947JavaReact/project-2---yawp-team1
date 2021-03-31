package com.revature.controllers;

import java.util.LinkedHashMap;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.EmailDoesNotExistException;
import com.revature.exceptions.EmailNotSentException;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.exceptions.UsernameDoesNotExistException;
import com.revature.models.StoredPassword;
import com.revature.models.User;
import com.revature.services.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class UserController {
	
	private UserService userServ;

	// POST Http Method, need a JSON from request to register a user
	@PostMapping("/create")
	public ResponseEntity<String> insertUser(@RequestBody LinkedHashMap<String, String> uMap) {
		// convert the password string from JSON to a hashed value
		String hashed = userServ.hashPassword(uMap.get("password"));
		// store the hashed value into the password object of user
		StoredPassword sp = new StoredPassword(hashed);
		// from JSON grab username and email, set password object, empty bio, and default pic to user object
		User user = new User(uMap.get("username"), sp, uMap.get("email"),"","https://robertsrevbucket.s3-us-west-1.amazonaws.com/default-profile-picture.jpeg");

		try {
			// attempt to add user to the database
			userServ.register(user);
		} catch (UserAlreadyExistsException e) {	// throw exception if username is not unique
			return new ResponseEntity<>("User was not registered, username already exists", HttpStatus.NOT_ACCEPTABLE);

		} catch (EmailAlreadyExistsException e) {	// throw exception if email is not unique
			return new ResponseEntity<>("User was not registered, email already exists", HttpStatus.NOT_MODIFIED);
		}
		// attempt to add user to the database was successful
		return new ResponseEntity<>("User was registered", HttpStatus.CREATED);
	}

	// POST Http Method, need a JSON from request to login a user
	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@RequestBody LinkedHashMap<String, String> uMap) {
		User user;
		try {
			// attempt to get a user from the database by username and password grabbed from JSON
			user = userServ.login(uMap.get("username"), uMap.get("password"));

		} catch (UsernameDoesNotExistException | InvalidCredentialsException e) {	// throw exception if no user has that username
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);				//		or if username and password do not match
		}
		// attempt to get a user form the database was successful
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	// POST Http Method, need a JSON from request to update a user
	@PostMapping("/update")
	public ResponseEntity<String> updateUser(@RequestBody LinkedHashMap<String, String> uMap) {
		// get user from database by grabbing user id from JSON, converting the string to an int
		User user = userServ.getUserById(Integer.parseInt(uMap.get("user_id")));
		// grab the new values from JSON to update the values in the user
		user.setBio(uMap.get("bio"));
		user.setEmail(uMap.get("email"));
		user.setPicUrl(uMap.get("pic_url"));
		// update was successful if the user with new values was updated in the database
		if (userServ.updateUser(user)) {
			return new ResponseEntity<>("User was updated", HttpStatus.OK);
		}
		// update was not successful
		return new ResponseEntity<>("User was not updated", HttpStatus.NOT_MODIFIED);
	}

	// POST Http Method, need a JSON from request to establish follower relationship between users
	@PostMapping("/startfollowing")
	public ResponseEntity<String> addFollowing(@RequestBody LinkedHashMap<String, String> uMap) {
		// grab id values from JSON, convert to integers, add relationship to following table
		boolean following = userServ.addFollowing(Integer.parseInt(uMap.get("user_id")),
				Integer.parseInt(uMap.get("following_id")));
		// grab id values from JSON, convert to integers, add relationship to followers table
		boolean followed = userServ.addFollower(Integer.parseInt(uMap.get("following_id")),
				Integer.parseInt(uMap.get("user_id")));
		// if following relationship failed to be added
		if (!following) {
			return new ResponseEntity<>("Unable to follow user", HttpStatus.NOT_MODIFIED);
		}
		// if follower relationship failed to be added
		if (!followed) {
			return new ResponseEntity<>("Unable to set followed by user", HttpStatus.NOT_MODIFIED);
		}
		// following relationship was successfully created
		return new ResponseEntity<>("User is now following", HttpStatus.OK);
	}

	// POST Http Method, need a JSON from request to delete follower relationship between users
	@PostMapping("/stopfollowing")
	public ResponseEntity<String> removeFollowing(@RequestBody LinkedHashMap<String, String> uMap) {
		// grab id values from JSON, convert to integers, remove relationship from following table
		boolean stopfollowing = userServ.removeFollowing(Integer.parseInt(uMap.get("user_id")),
				Integer.parseInt(uMap.get("following_id")));
		// grab id values from JSON, convert to integers, remove relationship from followers table
		boolean stopfollowed = userServ.removeFollower(Integer.parseInt(uMap.get("following_id")),
				Integer.parseInt(uMap.get("user_id")));
		// if following relationship failed to be removed
		if (!stopfollowing) {
			return new ResponseEntity<>("Unable to stop following user", HttpStatus.NOT_MODIFIED);
		}
		// if follower relationship failed to be removed
		if (!stopfollowed) {
			return new ResponseEntity<>("Unable to set unfollowed by user", HttpStatus.NOT_MODIFIED);
		}
		// following relationship was successfully removed
		return new ResponseEntity<>("User is now unfollowed", HttpStatus.OK);
	}

	// GET Http Method, need a username variable from request path to get a user
	@GetMapping("/username/{username}")
	public ResponseEntity<User> getUser(@PathVariable("username") String username) {
		User user;
		try {
			// get a user from database by username, assign to user object
			user = userServ.getUserByUsername(username);
		} catch (UsernameDoesNotExistException e) {		// throw exception if no user has that username
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		// successfully got a user from the database
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	// GET Http Method, need a user id variable from request path to get a user
	@GetMapping("/userid/{user_id}")
	public ResponseEntity<User> getUser(@PathVariable("user_id") int user_id) {
		User user;
		try {
			// get a user from database by user id, assign to user object
			user = userServ.getUserById(user_id);
		} catch (UsernameDoesNotExistException e) {		// throw exception if no user has that id value
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		// successfully got a user from the database
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	// POST Http Method, need a JSON from request to get a user's followers
	@PostMapping("/followers")
	public ResponseEntity<List<User>> getUserFollowers(@RequestBody LinkedHashMap<String, String> uMap) {
		// grab id value from JSON, convert to int, get that user's follower list
		List<User> sUsers = userServ.getUserFollowers(Integer.parseInt(uMap.get("user_id")));
		if (sUsers.isEmpty()) {
			// return null if list of followers is empty
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		// successfully grabbed list of followers
		return new ResponseEntity<>(sUsers, HttpStatus.OK);
	}

	// POST Http Method, need a JSON from request to get a user's following
	@PostMapping("/following")
	public ResponseEntity<List<User>> getUserFollowing(@RequestBody LinkedHashMap<String, String> uMap) {
		// grab id value from JSON, convert to int, get that user's following list
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
	
	@PostMapping("/sendreset")
	public ResponseEntity<String> sendResetEmail(@RequestBody LinkedHashMap<String, String> sMap) {
		String email = new String(sMap.get("email"));
		
		try {
			userServ.sendResetEmail(email);
		} catch (EmailDoesNotExistException|EmailNotSentException e) {
			return new ResponseEntity<>("Reset email was not sent", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Reset email was sent", HttpStatus.OK);
	}
	
	 @PostMapping("/resetpass") 
	 public ResponseEntity<String> resetPassword(@RequestBody LinkedHashMap<String, String> uMap) { 
		 try {
		 userServ.resetPassword(uMap.get("email"), uMap.get("temp_password"),uMap.get("new_password"));
		 } catch (InvalidCredentialsException e) {
			 return new ResponseEntity<>("Password reset unsuccessfull, temp password wrong",HttpStatus.UNAUTHORIZED);
		 }
		 
		 return new ResponseEntity<>("Password reset success",HttpStatus.OK);

		 
	 }
}
