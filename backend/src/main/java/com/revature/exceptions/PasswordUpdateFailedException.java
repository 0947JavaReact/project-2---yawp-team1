package com.revature.exceptions;

public class PasswordUpdateFailedException extends RuntimeException {

	private static final long serialVersionUID = 3096892139608950039L;

	// throw this exception if a user trying to reset their password does not pass the string validation
	public PasswordUpdateFailedException() {
		super();
	}
}
