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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;
import com.revature.models.Yawp;
import com.revature.services.YawpService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(value = "/homeyawps")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class YawpUserHomeController {
	private YawpService yawpServ;
	
	@PostMapping()
	public ResponseEntity<String> insertYawp(@RequestBody LinkedHashMap<String, String> uMap) {
		Set<User> sLikes = new HashSet<>();
		Yawp yawp = new Yawp(uMap.get("message"), Integer.parseInt(uMap.get("author_id")), LocalDateTime.now(), sLikes);
		yawp = yawpServ.register(yawp);

		if(!yawp.getMessage().equals(uMap.get("message")) || yawp.getAuthorId()!=(Integer.parseInt(uMap.get("author_id")))) {
			return new ResponseEntity<>("Unable to create Yawp", HttpStatus.NOT_MODIFIED);
		}
		return new ResponseEntity<>("Created Yawp for User", HttpStatus.OK);
	}
	
	
	@PostMapping("/useryawps")
	public ResponseEntity<List<Yawp>> getUserYawps(@RequestBody LinkedHashMap<String, String> yMap) {
		List<Yawp> lYawps =  new ArrayList<>();
		lYawps = yawpServ.getYawpsByUser(Integer.parseInt(yMap.get("user_id")));
		
		if(lYawps.size()<=0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(lYawps, HttpStatus.OK);
	}
	

}
