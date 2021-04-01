package com.revature.exceptions;

public class UsernameDoesNotExistException extends RuntimeException {

	private static final long serialVersionUID = -7605397998079364867L;

	// throw this exception if attempting to find a user with a username that doesn't exist
	public UsernameDoesNotExistException() {
		super();
	}
}
