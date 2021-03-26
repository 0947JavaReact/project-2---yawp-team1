package com.revature.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

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

import com.revature.models.Yawp;
import com.revature.services.YawpService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(value = "/userandfollowersyawps")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
@CrossOrigin(origins = "*")
public class YawpsUserAndFollowersController {

	private YawpService yawpServ;

	
	@GetMapping("/{user_id}")
	public ResponseEntity<List<Yawp>> getUserandFollowerYawps(@PathVariable("user_id")int id) {
		List<Yawp> lYawps =  new ArrayList<>();
		lYawps = yawpServ.getYawpsByUser(id);
		lYawps.addAll(yawpServ.getYawpsByFollowers(id));
		if(lYawps.size()<=0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		Collections.sort(lYawps);
		return new ResponseEntity<>(lYawps, HttpStatus.OK);
	}
}
