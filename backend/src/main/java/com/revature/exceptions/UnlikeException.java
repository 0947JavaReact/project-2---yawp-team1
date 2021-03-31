package com.revature.exceptions;

public class UnlikeException extends RuntimeException {
	
	private static final long serialVersionUID = -4629587528759143516L;

	// throw this exception if an error occurs when attempting to unlike a yawp
	public UnlikeException() {
		super();
	}
}
