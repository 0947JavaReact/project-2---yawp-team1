package com.revature.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component("myAspect")
@Aspect
public class YawpAspect {
	
	public YawpAspect() {
	}
	
	// append to log that a new user has registered
	@AfterReturning("execution(* com.revature.services.UserService.register(..))")
	public void logUserRegister(JoinPoint jp) {
		log.info("User has registered");
	}
	
	// append to log that when registering, the username must be unique
	@AfterThrowing("execution(* com.revature.services.UserService.register(..))")
	public void failedUserRegister(JoinPoint jp) {
		log.error("Register failed, username already exists");
	}
	
	// append to log that a user has successfully logged in
	@AfterReturning("execution(* com.revature.services.UserService.login(..))")
	public void logUserLogin(JoinPoint jp) {
		log.info("User has logged in");
	}
	
	// append to log that when logging in, the username or password was incorrect
	@AfterThrowing("execution(* com.revature.services.UserService.login(..))")
	public void failedUserLogin(JoinPoint jp) {
		log.error("Login failed, invalid credentials");
	}
	
	// append to log that the logged in user has posted a new yawp
	@AfterReturning("execution(* com.revature.services.YawpService.register(..))")
	public void logYawpRegister(JoinPoint jp) {
		log.info("User has created a yawp");
	}
	
	// append to log that when attempting to create a new yawp post, a SQL exception was thrown
	@AfterThrowing("execution(* com.revature.services.YawpService.register(..))")
	public void faildYawpRegister(JoinPoint jp) {
		log.error("Failed yawp creation: sql exception: " + jp.getSignature());
	}
	
}
