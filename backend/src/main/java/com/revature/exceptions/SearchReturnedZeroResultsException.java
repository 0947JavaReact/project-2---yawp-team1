package com.revature.exceptions;

public class SearchReturnedZeroResultsException extends RuntimeException {
	
	private static final long serialVersionUID = 7609896802842619726L;

	// throw this exception if the results of a search return no matches 
	public SearchReturnedZeroResultsException() {
		super();
	}
}
