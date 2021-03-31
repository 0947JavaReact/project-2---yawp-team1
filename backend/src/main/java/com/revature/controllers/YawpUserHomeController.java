package com.revature.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

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

import com.revature.exceptions.UsernameDoesNotExistException;
import com.revature.models.User;
import com.revature.models.Yawp;
import com.revature.services.UserService;
import com.revature.services.YawpService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping(value = "/yawps")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class YawpUserHomeController {
	
	private YawpService yawpServ;
	private UserService userServ;
	
	// POST Http Method, needs a JSON from the request to insert a yawp to the database
	@PostMapping("/create")
	public ResponseEntity<String> insertYawp(@RequestBody LinkedHashMap<String, String> uMap) {
		Set<User> sLikes = new HashSet<>();
		User user;
		try {
			// grab the author id of yawp from JSON, convert to int, and get a user by id
			user = userServ.getUserById(Integer.parseInt(uMap.get("author_id")));
		} catch (UsernameDoesNotExistException e) {
			// throw an exception if the user could not be found by id
			return new ResponseEntity<>("Unable to create Yawp, user does not exist", HttpStatus.NOT_MODIFIED);
		}
		// grab the yawp's message from JSON, create a yawp object with user's info
		Yawp yawp = new Yawp(uMap.get("message"), user.getUserId(), user.getUsername(), user.getPicUrl(), LocalDateTime.now(), sLikes);
		// register the yawp in the database
		yawp = yawpServ.register(yawp);
		if(!yawp.getMessage().equals(uMap.get("message")) || user.getUserId()!=(Integer.parseInt(uMap.get("author_id")))) {
			// return error message if the yawp's message didn't match in the database or the author's id didn't match
			return new ResponseEntity<>("Unable to create Yawp", HttpStatus.NOT_MODIFIED);
		}
		// successfully created a yawp as a user
		return new ResponseEntity<>("Created Yawp for User", HttpStatus.OK);
	}
	
	// GET Http Method, need a user id variable from the request path to get that user's yawps
	@GetMapping(value="uyawps/{user_id}")
	public ResponseEntity<List<Yawp>> getUserYawps(@PathVariable("user_id") int user_id) {
		List<Yawp> lYawps =  new ArrayList<>();
		// get a list of yawps from a user by id
		lYawps = yawpServ.getYawpsByUser(user_id);
		if(lYawps.size() <= 0) {
			// return null if the list is empty
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		// successfully got the list of yawps from a user
		return new ResponseEntity<>(lYawps, HttpStatus.OK);
	}
	
	// GET Http Method, needs a yawp id variable from the request path to get a specific yawp
	@GetMapping(value = "yawp/{yawp_id}")
	public ResponseEntity<Yawp> getYawp(@PathVariable("yawp_id") int yawp_id) {
		// get a yawp by its id
		Yawp yawp =  yawpServ.getYawp(yawp_id);
		if(yawp == null) {
			// return null if the yawp wasn't found
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); 
		}
		// successfully got the specific yawp
		return new ResponseEntity<>(yawp, HttpStatus.OK);
	}

}
