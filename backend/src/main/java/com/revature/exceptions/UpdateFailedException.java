package com.revature.exceptions;

public class UpdateFailedException extends RuntimeException {
	
	private static final long serialVersionUID = 711334821913254409L;

	// throw this exception if an error occurred when attempting to update a user or yawp
	public UpdateFailedException() {
		super();
	}
}