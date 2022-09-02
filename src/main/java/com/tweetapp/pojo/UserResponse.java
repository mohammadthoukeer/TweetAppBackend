package com.tweetapp.pojo;

public class UserResponse {

	private String username;
	private String firstName;
	private String lastName;
	private String email;	
	private String contactNumber;
	
	public UserResponse() {}

	public UserResponse(String username, String firstName, String lastName, String email, String contactNumber) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.contactNumber = contactNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Override
	public String toString() {
		return "UserResponse [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", contactNumber=" + contactNumber + "]";
	}
	
	
}
