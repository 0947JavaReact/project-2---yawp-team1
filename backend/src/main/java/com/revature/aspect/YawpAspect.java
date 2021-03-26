package com.revature.aspect;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component("myAspect")
@Aspect
public class YawpAspect {
	public final static Logger logger = Logger.getLogger(YawpAspect.class);
	public YawpAspect() {
		logger.setLevel(Level.ALL);
	}
	
	@AfterReturning("execution(* com.revature.services.UserService.register(..))")
	public void logUserRegister(JoinPoint jp) {
		logger.info("User has registered");
	}
	@AfterThrowing("execution(* com.revature.services.UserService.register(..))")
	public void failedUserRegister(JoinPoint jp) {
		logger.error("Register failed, username already exists");
	}
	
	@AfterReturning("execution(* com.revature.services.UserService.login(..))")
	public void logUserLogin(JoinPoint jp) {
		logger.info("User has logged in");
	}
	@AfterThrowing("execution(* com.revature.services.UserService.login(..))")
	public void failedUserLogin(JoinPoint jp) {
		logger.error("Login failed, invalid credentials");
	}
	
	@AfterReturning("execution(* com.revature.services.YawpService.register(..))")
	public void logYawpRegister(JoinPoint jp) {
		logger.info("User has created a yawp");
	}
	@AfterThrowing("execution(* com.revature.services.YawpService.register(..))")
	public void faildYawpRegister(JoinPoint jp) {
		logger.error("Failed yawp creation: sql exception: " + jp.getSignature());
	}
	
	
}
