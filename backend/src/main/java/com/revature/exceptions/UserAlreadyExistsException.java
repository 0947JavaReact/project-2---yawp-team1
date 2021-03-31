package com.revature.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 2088007957381932980L;

	// throw this exception if attempting to add a user that already exists in the database
	public UserAlreadyExistsException() {
		super();
	}
}
