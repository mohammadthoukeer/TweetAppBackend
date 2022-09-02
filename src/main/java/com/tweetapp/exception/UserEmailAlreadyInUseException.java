package com.tweetapp.exception;

public class UserEmailAlreadyInUseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserEmailAlreadyInUseException(String message) {
		super(message);
	}
}
