package com.tweetapp.service;

import java.util.List;

import com.tweetapp.entity.LoginResponse;
import com.tweetapp.entity.User;
import com.tweetapp.exception.InvalidUsernameOrPasswordException;
import com.tweetapp.exception.NoSuchUserException;
import com.tweetapp.exception.UserAlreadyExistsException;
import com.tweetapp.exception.UserEmailAlreadyInUseException;
import com.tweetapp.pojo.LoginUser;
import com.tweetapp.pojo.ResetPasswordUser;
import com.tweetapp.pojo.TweetResponse;
import com.tweetapp.pojo.UserResponse;

public interface UserService {

	public UserResponse registerUser(User user) throws UserAlreadyExistsException, UserEmailAlreadyInUseException;
	public LoginResponse loginUser(LoginUser loginUser) throws InvalidUsernameOrPasswordException;
	public boolean validateToken(String token);
	public TweetResponse resetPasswordUser(String username,ResetPasswordUser resetPasswordUser) throws Exception;
	public List<UserResponse> findAllUsers() throws NoSuchUserException;
	public List<UserResponse> searchByUsername(String username) throws NoSuchUserException;
	public TweetResponse removeUser(String username) throws NoSuchUserException;
}
