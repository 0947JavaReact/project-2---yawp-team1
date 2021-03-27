package com.revature.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.websocket.server.PathParam;

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

	@GetMapping(value = "/followers/{user_id}", params = "user_id")
	public ResponseEntity<List<Yawp>> getUserAndFollowerYawps(@PathParam("user_id") int id) {
		List<Yawp> lYawps = new ArrayList<>();
		lYawps = yawpServ.getYawpsByUser(id);
		lYawps.addAll(yawpServ.getYawpsByFollowers(id));
		if (lYawps.size() <= 0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		Collections.sort(lYawps);
		return new ResponseEntity<>(lYawps, HttpStatus.OK);
	}

	@GetMapping(value = "/following/{user_id}", params = "user_id")
	public ResponseEntity<List<Yawp>> getUserAndFollowingYawps(@PathParam("user_id") int id) {
		List<Yawp> lYawps = new ArrayList<>();
		lYawps = yawpServ.getYawpsByUser(id);
		lYawps.addAll(yawpServ.getYawpsByFollowing(id));
		if (lYawps.size() <= 0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		Collections.sort(lYawps);
		return new ResponseEntity<>(lYawps, HttpStatus.OK);
	}

	@GetMapping(value = "/allff/{user_id}", params = "user_id")
	public ResponseEntity<List<Yawp>> getUserBothFFYawps(@PathParam("user_id") int id) {
		List<Yawp> lYawps = new ArrayList<>();
		lYawps = yawpServ.getYawpsByUser(id);
		lYawps.addAll(yawpServ.getYawpsByFollowers(id));
		lYawps.addAll(yawpServ.getYawpsByFollowing(id));
		if (lYawps.size() <= 0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		Collections.sort(lYawps);
		return new ResponseEntity<>(lYawps, HttpStatus.OK);
	}

	@GetMapping(value = "/allyawps")
	public ResponseEntity<List<Yawp>> getAllYawps() {
		List<Yawp> lYawps = new ArrayList<>();
		lYawps = yawpServ.getAllYawps();

		if (lYawps.size() <= 0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		Collections.sort(lYawps);
		return new ResponseEntity<>(lYawps, HttpStatus.OK);
	}

	@RequestMapping(value = "/likeyawp/{user_id}/{yawp_id}", method = RequestMethod.GET)
	public ResponseEntity<String> likePost(@PathVariable("user_id")int user_id, @PathVariable("yawp_id") int yawp_id) {
		yawpServ.like(yawp_id, user_id);
		return new ResponseEntity<>("User has liked yawp", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/unlikeyawp/{user_id}/{yawp_id}", method = RequestMethod.GET)
	public ResponseEntity<String> unlikePost(@PathVariable("user_id")int user_id, @PathVariable("yawp_id") int yawp_id) {
		yawpServ.unlike(yawp_id, user_id);
		return new ResponseEntity<>("User has unliked yawp", HttpStatus.OK);
	}

}
