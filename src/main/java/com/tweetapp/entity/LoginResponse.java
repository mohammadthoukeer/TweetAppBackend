package com.tweetapp.entity;

public class LoginResponse {

	private String username;
	private String token;
	
	public LoginResponse() {}

	public LoginResponse(String username, String token) {
		super();
		this.username = username;
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginResponse [username=" + username + ", token=" + token + "]";
	}
}
