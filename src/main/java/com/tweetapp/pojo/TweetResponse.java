package com.tweetapp.pojo;

public class TweetResponse {

	private String message;
	
	public TweetResponse() {}

	public TweetResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "TweetResponse [message=" + message + "]";
	}
	
}
