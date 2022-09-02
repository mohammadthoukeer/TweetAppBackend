package com.tweetapp.exception;

public class InvalidUsernameOrPasswordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidUsernameOrPasswordException(String message) {
		super(message);
	}
}
