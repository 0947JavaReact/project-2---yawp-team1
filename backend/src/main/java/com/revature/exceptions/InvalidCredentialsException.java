package com.revature.exceptions;

public class InvalidCredentialsException extends RuntimeException{

	private static final long serialVersionUID = -6573307333524845568L;

	// throw this exception if a user attempting to login inputs the wrong username or password
	public InvalidCredentialsException() {
		super();
	}
}
