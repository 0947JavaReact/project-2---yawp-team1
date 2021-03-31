package com.revature.exceptions;

public class EmailNotSentException extends RuntimeException {

	private static final long serialVersionUID = -3514364118004967L;

	// throw this exception if an error occurred when attempting to send an email
	public EmailNotSentException() {
		super();
	}
}
