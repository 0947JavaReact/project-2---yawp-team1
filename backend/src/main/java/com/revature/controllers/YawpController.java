package com.revature.controllers;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Yawp;
import com.revature.services.YawpService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(value = "/yawps")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class YawpController {
	private YawpService yawpServ;
	
	@PostMapping()
	public ResponseEntity<String> insertYawp(@RequestBody LinkedHashMap<String, String> uMap) {
		Yawp yawp = new Yawp(uMap.get("message"), Integer.parseInt(uMap.get("author_id")), LocalDateTime.now(),0);
		yawp = yawpServ.register(yawp);

		if(!yawp.getMessage().equals(uMap.get("message")) || yawp.getAuthorId()!=(Integer.parseInt(uMap.get("author_id")))) {
			return new ResponseEntity<>("Unable to create Yawp", HttpStatus.NOT_MODIFIED);
		}
		return new ResponseEntity<>("Created Yawp for User", HttpStatus.OK);
	}
	
	

}
