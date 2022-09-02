package com.tweetapp.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Document(collection = "users")
@ApiModel(value = "Model object that stores the User information")
public class User {

	@ApiModelProperty(notes = "Unique username of the User")
	@Id
	@NotNull(message = "Username should not be null")
	private String username;
	
	@ApiModelProperty(notes = "FirstName of the User")
	@NotNull(message = "FirstName should not be null")
	private String firstName;
	
	@ApiModelProperty(notes = "LastName of the User")
	@NotNull(message = "LastName should not be null")
	private String lastName;
	
	@ApiModelProperty(notes = "Unique email of the User")
	@Email(message = "Provide correct email address")
	@NotNull(message = "Email should not be null")
	private String email;
	
	@ApiModelProperty(notes = "Password of the User")
	@NotNull(message = "Password should not be null")
	private String password;
	
	@ApiModelProperty(notes = "ContactNumber of the User")
	@NotNull(message = "ContactNumber should not be null")
	private String contactNumber;
	
	public User() {}
	
	public User(String username, String firstName, String lastName, String email, String password,
			String contactNumber) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", password=" + password + ", contactNumber=" + contactNumber + "]";
	}
	
}
