package com.tweetapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.entity.LoginResponse;
import com.tweetapp.entity.User;
import com.tweetapp.pojo.LoginUser;
import com.tweetapp.pojo.ResetPasswordUser;
import com.tweetapp.pojo.TweetResponse;
import com.tweetapp.pojo.UserResponse;
import com.tweetapp.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v1.0/tweets")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;

	@PostMapping("/register")
	@ApiOperation(notes = "Returns the user details based on valid details of user", value = "Register a new user")
	public ResponseEntity<UserResponse> registerUser(@ApiParam(name = "User", value = "user details input") @Valid @RequestBody User user) throws Exception {
		UserResponse newUser = userService.registerUser(user);
		logger.info("New user registered : " + newUser.getUsername());
		ResponseEntity<UserResponse> response = new ResponseEntity<UserResponse>(newUser, HttpStatus.CREATED);
		return response;
	}
	
	@PostMapping("/login")
	@ApiOperation(notes = "Returns the Token based on valid details of user", value = "Login as a valid user")
	public ResponseEntity<LoginResponse> loginUser(@ApiParam(name = "loginUser", value = "loginUser details input") @Valid @RequestBody LoginUser loginUser) throws Exception {
		LoginResponse loginResponse = userService.loginUser(loginUser);
		logger.info("User login : " + loginResponse.getUsername());
		ResponseEntity<LoginResponse> response = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/validate")
	@ApiOperation(notes = "Returns the boolean response based on valid token", value = "validates token by user")
	public ResponseEntity<Boolean> validateToken(@RequestHeader(name = "Authorization") String token){
		boolean valid = userService.validateToken(token);
		logger.info("Token valid status : " + valid);
		ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(valid, HttpStatus.OK);
		return response;
	}
	
	@PutMapping("/{username}/forgot")
	@ApiOperation(notes = "Returns the response based on valid details of user with email and password", value = "Reset password of valid user")
	public ResponseEntity<TweetResponse> forgotPassword(@ApiParam(name = "username", value = "username input") @PathVariable String username,
			@ApiParam(name = "ResetPasswordUser", value = "resetPasswordUser details input") @Valid @RequestBody ResetPasswordUser resetPassUser) throws Exception {
		TweetResponse resp = userService.resetPasswordUser(username,resetPassUser);
		logger.info("User password reset : " + username);
		ResponseEntity<TweetResponse> response = new ResponseEntity<>(resp, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/users/all")
	@ApiOperation(notes = "Returns all users", value = "Get all users")
	public ResponseEntity<List<UserResponse>> getAllUsers() throws Exception{
		List<UserResponse> AllUsers = userService.findAllUsers();
		logger.info("Getting all users");
		ResponseEntity<List<UserResponse>> response = new ResponseEntity<List<UserResponse>>(AllUsers, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/user/search/{username}")
	@ApiOperation(notes = "Returns all users based on partial or full username", value = "Get all users by partial or full username")
	public ResponseEntity<List<UserResponse>> searchByUsername(@ApiParam(name = "username", value = "username input") @PathVariable String username) throws Exception{
		List<UserResponse> users = userService.searchByUsername(username);
		logger.info("Getting all users by : " + username);
		ResponseEntity<List<UserResponse>> response = new ResponseEntity<List<UserResponse>>(users, HttpStatus.OK);
		return response;
	}
	
	@DeleteMapping("/delete/{username}")
	@ApiOperation(notes = "Returns the response based on username deletion", value = "Delete a user")
	public ResponseEntity<TweetResponse> deleteUser(@ApiParam(name = "username", value = "username input") @PathVariable String username) throws Exception{
		TweetResponse resp = userService.removeUser(username);
		logger.info("User Deleted : " + username);
		ResponseEntity<TweetResponse> response = new ResponseEntity<TweetResponse>(resp, HttpStatus.OK);
		return response;
	}
}
