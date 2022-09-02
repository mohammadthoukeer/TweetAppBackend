package com.tweetapp.pojo;

import javax.validation.constraints.NotNull;

public class LoginUser {

	@NotNull(message = "Username should not be null")
	private String username;
	
	@NotNull(message = "Password should not be null")
	private String password;
	
	public LoginUser() {}

	public LoginUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginUser [username=" + username + ", password=" + password + "]";
	}
	
}
