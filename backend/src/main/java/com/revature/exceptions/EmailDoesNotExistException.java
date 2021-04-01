package com.revature.exceptions;

public class EmailDoesNotExistException extends RuntimeException {
	
	private static final long serialVersionUID = 3886292357489631610L;

	// throw this exception if an email could not be found by its string value
	public EmailDoesNotExistException() {
		super();
	}
}
