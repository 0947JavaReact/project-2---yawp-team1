package com.revature.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
	
	private static final long serialVersionUID = -6384238840094678157L;

	// throw this exception if an email is required to be unique
	public EmailAlreadyExistsException() {
		super();
	}
}
