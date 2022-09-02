package com.tweetapp.pojo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class ResetPasswordUser {

	@Email(message = "Provide correct email address")
	@NotNull(message = "Email should not be null")
	private String email;
	@NotNull(message = "Password should not be null")
	private String password;
	
	public ResetPasswordUser() {}

	public ResetPasswordUser(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "ResetPasswordUser [email=" + email + ", password=" + password + "]";
	}
	
}
