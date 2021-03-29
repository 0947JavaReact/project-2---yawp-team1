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
	
	@AfterReturning("execution(* com.revature.services.UserService.register(..))")
	public void logUserRegister(JoinPoint jp) {
		log.info("User has registered");
	}
	@AfterThrowing("execution(* com.revature.services.UserService.register(..))")
	public void failedUserRegister(JoinPoint jp) {
		log.error("Register failed, username already exists");
	}
	
	@AfterReturning("execution(* com.revature.services.UserService.login(..))")
	public void logUserLogin(JoinPoint jp) {
		log.info("User has logged in");
	}
	@AfterThrowing("execution(* com.revature.services.UserService.login(..))")
	public void failedUserLogin(JoinPoint jp) {
		log.error("Login failed, invalid credentials");
	}
	
	@AfterReturning("execution(* com.revature.services.YawpService.register(..))")
	public void logYawpRegister(JoinPoint jp) {
		log.info("User has created a yawp");
	}
	@AfterThrowing("execution(* com.revature.services.YawpService.register(..))")
	public void faildYawpRegister(JoinPoint jp) {
		log.error("Failed yawp creation: sql exception: " + jp.getSignature());
	}
	
	
}
