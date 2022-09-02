package com.tweetapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.entity.LoginResponse;
import com.tweetapp.entity.User;
import com.tweetapp.exception.InvalidEmailException;
import com.tweetapp.exception.InvalidUsernameOrPasswordException;
import com.tweetapp.exception.NoSuchUserException;
import com.tweetapp.exception.UserAlreadyExistsException;
import com.tweetapp.exception.UserEmailAlreadyInUseException;
import com.tweetapp.jwt.JwtUtil;
import com.tweetapp.pojo.LoginUser;
import com.tweetapp.pojo.ResetPasswordUser;
import com.tweetapp.pojo.TweetResponse;
import com.tweetapp.pojo.UserResponse;
import com.tweetapp.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	JwtUtil jwtUtil;

	@Override
	public UserResponse registerUser(User user) throws UserAlreadyExistsException, UserEmailAlreadyInUseException {
		logger.debug("-----Start UserServiceImpl -> registerUser(user)-----");
		Optional<User> userdata = null;
		if(user.getUsername()!=null) {
			userdata = userRepo.findById(user.getUsername());
			if(userdata.isPresent()) {
				logger.error("Username already exists : " + user.getUsername());
				throw new UserAlreadyExistsException("username already exists");
			}
		}
		if(user.getEmail()!=null) {
			userdata = Optional.ofNullable(userRepo.findByEmail(user.getEmail()));
			if(userdata.isPresent()) {
				logger.error("Email already in use : " + userdata.get().getEmail());
				throw new UserEmailAlreadyInUseException("Email is already in use");
			}
		}
		User savedUser = userRepo.save(user);
		UserResponse userResponse = new UserResponse(savedUser.getUsername(), savedUser.getFirstName(), 
				savedUser.getLastName(), savedUser.getEmail(), savedUser.getContactNumber());
		logger.info("User Created : " + userResponse);
		logger.debug("-----End UserServiceImpl -> registerUser(user)-----");
		return userResponse;
	}

	@Override
	public LoginResponse loginUser(LoginUser loginUser) throws InvalidUsernameOrPasswordException {
		logger.debug("-----Start UserServiceImpl -> loginUser(loginUser)-----");
		LoginResponse loginResponse = new LoginResponse();
		if(loginUser.getUsername()!=null) {
			Optional<User> user = userRepo.findById(loginUser.getUsername());
			if(user.isEmpty()) {
				logger.error("No such username exists");
				throw new InvalidUsernameOrPasswordException("No such username exists");
			}
			if(loginUser.getPassword()!=null) {
				if(user.get().getPassword().equals(loginUser.getPassword())) {
					loginResponse.setUsername(loginUser.getUsername());
					loginResponse.setToken(jwtUtil.generateToken(loginUser.getUsername()));
				}else {
					logger.error("Incorrect password");
					throw new InvalidUsernameOrPasswordException("Incorrect password");
				}
			}else {
				logger.error("Invalid username or password");
				throw new InvalidUsernameOrPasswordException("Invalid username or password");
			}
		}else {
			logger.error("Invalid username or password");
			throw new InvalidUsernameOrPasswordException("Invalid username or password");
		}
		logger.info("loginResponse : " + loginResponse);
		logger.debug("-----End UserServiceImpl -> loginUser(loginUser)-----");
		return loginResponse;
	}
	
	public boolean validateToken(String token) {
		logger.debug("-----Start UserServiceImpl -> validateToken(token)-----");
		boolean valid = false;
		if(token!=null) {
			valid = jwtUtil.isTokenValid(token);
		}
		logger.info("Token Valid in user service : " + valid);
		logger.debug("-----End UserServiceImpl -> validateToken(token)-----");
		return valid;
	}
	
	@Override
	public TweetResponse resetPasswordUser(String username,ResetPasswordUser resetPasswordUser) throws Exception {
		logger.debug("-----Start UserServiceImpl -> resetPasswordUser(username,resetPasswordUser)-----");
		if(username!=null) {
			Optional<User> user = userRepo.findById(username);
			if(user.isEmpty()) {
				logger.error("No such username exists");
				throw new NoSuchUserException("No such user exists");
			}
			if(user.get().getEmail().equalsIgnoreCase(resetPasswordUser.getEmail())) {
				User resetUser = new User();
				resetUser.setUsername(username);
				resetUser.setPassword(resetPasswordUser.getPassword());
				resetUser.setEmail(user.get().getEmail());
				resetUser.setFirstName(user.get().getFirstName());
				resetUser.setLastName(user.get().getLastName());
				resetUser.setContactNumber(user.get().getContactNumber());
				userRepo.save(resetUser);
			}else {
				logger.error("Email doesn't match with username");
				throw new InvalidEmailException("Email doesn't match with username");
			}
		}else {
			logger.error("Invalid username or password");
			throw new InvalidUsernameOrPasswordException("Invalid username or password");
		}
		logger.debug("-----End UserServiceImpl -> resetPasswordUser(username,resetPasswordUser)-----");
		return new TweetResponse("Password Reset Successful");
	}

	@Override
	public List<UserResponse> findAllUsers() throws NoSuchUserException {
		logger.debug("-----Start UserServiceImpl -> findAllUsers()-----");
		List<User> users = userRepo.findAll();
		if(users.isEmpty()) {
			logger.error("There are no users");
			throw new NoSuchUserException("There are no users");
		}
		List<UserResponse> userResponses = new ArrayList<>();
		users.forEach(user -> userResponses.add(new UserResponse(user.getUsername(), user.getFirstName(), 
				user.getLastName(), user.getEmail(), user.getContactNumber())));
		logger.debug("-----End UserServiceImpl -> findAllUsers()-----");
		return userResponses;
	}

	@Override
	public List<UserResponse> searchByUsername(String username) throws NoSuchUserException {
		logger.debug("-----Start UserServiceImpl -> searchByUsername(username)-----");
		List<User> users = userRepo.findByUsernameContaining(username);
		if(users.isEmpty()) {
			logger.error("No such username exists");
			throw new NoSuchUserException("No such username exists");
		}
		List<UserResponse> userResponses = new ArrayList<>();
		users.forEach(user -> userResponses.add(new UserResponse(user.getUsername(), user.getFirstName(), 
				user.getLastName(), user.getEmail(), user.getContactNumber())));
		logger.debug("-----End UserServiceImpl -> searchByUsername(username)-----");
		return userResponses;
	}

	@Override
	public TweetResponse removeUser(String username) throws NoSuchUserException {
		logger.debug("-----Start UserServiceImpl -> removeUser(username)-----");
		if(username!=null) {
			Optional<User> user = userRepo.findById(username);
			if(user.isEmpty()) {
				logger.error("No such username exists");
				throw new NoSuchUserException("No such user exists");
			}
			userRepo.deleteById(username);
		}
		logger.debug("-----End UserServiceImpl -> removeUser(username)-----");
		return new TweetResponse("User Deleted Successfully");
	}

}
