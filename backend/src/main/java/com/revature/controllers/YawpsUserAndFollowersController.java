package com.revature.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Yawp;
import com.revature.services.YawpService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping(value = "/ufyawps")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class YawpsUserAndFollowersController {

	private YawpService yawpServ;

	// GET Http Method, need a user id variable from request path to get a user's yawps with follower yawps
	@GetMapping(value = "/followers/{user_id}")
	public ResponseEntity<List<Yawp>> getUserAndFollowerYawps(@PathVariable("user_id") int user_id) {
		List<Yawp> lYawps = new ArrayList<>();
		// get a list of yawps from a user
		lYawps = yawpServ.getYawpsByUser(user_id);
		// add to the list all the yawps from a user's followers
		lYawps.addAll(yawpServ.getYawpsByFollowers(user_id));
		if (lYawps.size() <= 0) {
			// return null if the list is empty
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		// sort the list in reverse order
		Collections.sort(lYawps, Comparator.reverseOrder());
		// successfully got the list of yawps from user and followers
		return new ResponseEntity<>(lYawps, HttpStatus.OK);
	}

	// GET Http Method, need a user id variable from request path to get a user's yawps with following yawps
	@GetMapping(value = "/following/{user_id}")
	public ResponseEntity<List<Yawp>> getUserAndFollowingYawps(@PathVariable("user_id") int user_id) {
		List<Yawp> lYawps = new ArrayList<>();
		// get a list of yawps from a user
		lYawps = yawpServ.getYawpsByUser(user_id);
		// add to the list all the yawps from a user's following
		lYawps.addAll(yawpServ.getYawpsByFollowing(user_id));
		if (lYawps.size() <= 0) {
			// return null if the list is empty
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		// sort the list in reverse order
		Collections.sort(lYawps,Comparator.reverseOrder());
		// successfully got the list of yawps from user and following
		return new ResponseEntity<>(lYawps, HttpStatus.OK);
	}

	// GET Http Method, need a user id variable from request path to get a user's yawps with followers and following yawps
	@GetMapping(value = "/allff/{user_id}")
	public ResponseEntity<List<Yawp>> getUserBothFFYawps(@PathVariable("user_id") int user_id) {
		List<Yawp> lYawps = new ArrayList<>();
		// get a list of yawps from a user
		lYawps = yawpServ.getYawpsByUser(user_id);
		// add to the list all the yawps from a user's followers
		lYawps.addAll(yawpServ.getYawpsByFollowers(user_id));
		// add to the list all the yawps from a user's following
		lYawps.addAll(yawpServ.getYawpsByFollowing(user_id));
		if (lYawps.size() <= 0) {
			// return null if the list is empty
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		// sort the list in reverse order
		Collections.sort(lYawps,Comparator.reverseOrder());
		// successfully got the list of yawps from user with followers and following
		return new ResponseEntity<>(lYawps, HttpStatus.OK);
	}

	// GET Http Method, get all the yawps
	@GetMapping(value = "/allyawps")
	public ResponseEntity<List<Yawp>> getAllYawps() {
		List<Yawp> lYawps = new ArrayList<>();
		// get a list of all yawps from the database
		lYawps = yawpServ.getAllYawps();
		if (lYawps.size() <= 0) {
			// return null if the list is empty
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		// sort the list in reverse order
		Collections.sort(lYawps, Comparator.reverseOrder());
		// successfully got the list of all yawps
		return new ResponseEntity<>(lYawps, HttpStatus.OK);
	}
	
	// GET Http Method, needs a yawp id variable from request path to get a specific yawp
	@GetMapping(value = "syawp/{yawp_id}")
	public ResponseEntity<Yawp> getYawp(@PathVariable("yawp_id") int yawp_id) {
		// get the yawp by its id from the request
		Yawp yawp =  yawpServ.getYawp(yawp_id);
		if(yawp == null) {
			// return null if the yawp doesn't exist
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); 
		}
		// successfully got the yawp
		return new ResponseEntity<>(yawp, HttpStatus.OK);
	}

	// GET Http Method, needs a user id and yawp id from the request path to like a yawp post
	@RequestMapping(value = "/likeyawp/{user_id}/{yawp_id}", method = RequestMethod.GET)
	public ResponseEntity<String> likePost(@PathVariable("user_id")int user_id, @PathVariable("yawp_id") int yawp_id) {
		// add a like from the user to the yawp specified from request
		yawpServ.like(yawp_id, user_id);
		// successfully added a like to a yawp
		return new ResponseEntity<>("User has liked yawp", HttpStatus.OK);
	}
	
	// GET Http Method, needs a user id and yawp id from the request path to unlike a yawp post
	@RequestMapping(value = "/unlikeyawp/{user_id}/{yawp_id}", method = RequestMethod.GET)
	public ResponseEntity<String> unlikePost(@PathVariable("user_id")int user_id, @PathVariable("yawp_id") int yawp_id) {
		// remove a like from the user to the yawp specified from request
		yawpServ.unlike(yawp_id, user_id);
		// successfully removed a like from a yawp
		return new ResponseEntity<>("User has unliked yawp", HttpStatus.OK);
	}

}
