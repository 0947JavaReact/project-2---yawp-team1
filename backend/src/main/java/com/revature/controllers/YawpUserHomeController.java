package com.revature.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

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
	
	@PostMapping("/create")
	public ResponseEntity<String> insertYawp(@RequestBody LinkedHashMap<String, String> uMap) {
		Set<User> sLikes = new HashSet<>();
		User user;
		try {
		 user = userServ.getUserById(Integer.parseInt(uMap.get("author_id")));
		} catch (UsernameDoesNotExistException e) {
			return new ResponseEntity<>("Unable to create Yawp, user does not exist", HttpStatus.NOT_MODIFIED);
		}
		
		Yawp yawp = new Yawp(uMap.get("message"), user.getUserId(),user.getUsername(), user.getPicUrl(),      LocalDateTime.now(), sLikes);
		yawp = yawpServ.register(yawp);

		if(!yawp.getMessage().equals(uMap.get("message")) || user.getUserId()!=(Integer.parseInt(uMap.get("author_id")))) {
			return new ResponseEntity<>("Unable to create Yawp", HttpStatus.NOT_MODIFIED);
		}
		return new ResponseEntity<>("Created Yawp for User", HttpStatus.OK);
	}
	
	
	@GetMapping(value="uyawps/{user_id}")
	public ResponseEntity<List<Yawp>> getUserYawps(@PathVariable("user_id") int user_id) {
		List<Yawp> lYawps =  new ArrayList<>();
		lYawps = yawpServ.getYawpsByUser(user_id);
		
		if(lYawps.size()<=0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(lYawps, HttpStatus.OK);
	}
	
	@GetMapping(value = "yawp/{yawp_id}")
	public ResponseEntity<Yawp> getYawp(@PathVariable("yawp_id") int yawp_id) {
		Yawp yawp =  yawpServ.getYawp(yawp_id);
		
		if(yawp==null) {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<>(yawp, HttpStatus.OK);
	}
	
	

}
