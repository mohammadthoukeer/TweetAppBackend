package com.tweetapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.entity.LoginResponse;
import com.tweetapp.entity.User;
import com.tweetapp.pojo.LoginUser;
import com.tweetapp.pojo.ResetPasswordUser;
import com.tweetapp.pojo.TweetResponse;
import com.tweetapp.pojo.UserResponse;
import com.tweetapp.service.UserServiceImpl;

@WebMvcTest(value = UserController.class)
class UserControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	UserServiceImpl userService;
	
	private User user;
	private UserResponse userResponse;
	private LoginUser loginUser;
	private LoginResponse loginResponse;
	private ResetPasswordUser resetPasswordUser;
	
	@BeforeEach
	void setUp() {
		user = new User("john", "John", "Wick", "john@yahoo.com", "john12", "1234567890");
		userResponse = new UserResponse("john", "John", "Wick", "john@yahoo.com", "1234567890");
		loginUser = new LoginUser("john", "john12");
		loginResponse = new LoginResponse("john", "abcdefg");
		resetPasswordUser = new ResetPasswordUser("john@yahoo.com", "john12");
	}

	@Test
	@DisplayName("Test Mock MVC ")
	void testMockMvcNotNull() {
		assertThat(mockMvc).isNotNull();
	}
	
	/******************************************************  registerUser()  @throws Exception  ********************************************************/
	
	@Test
	@DisplayName("Test registerUser()")
	public void testRegisterUser() throws Exception {
		when(userService.registerUser(any())).thenReturn(userResponse);
		this.mockMvc.perform(post("/api/v1.0/tweets/register")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isCreated());
		verify(userService).registerUser(any());
	}
	
	/******************************************************  loginUser()  @throws Exception  ****************************************************************/
	
	@Test
	@DisplayName("Test loginUser()")
	public void testLoginUser() throws Exception {
		when(userService.loginUser(any())).thenReturn(loginResponse);
		this.mockMvc.perform(post("/api/v1.0/tweets/login")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(loginUser))).andExpect(status().isOk());
		verify(userService).loginUser(any());
	}
	
	
	/******************************************************  validateToken()  @throws Exception  ***********************************************************/
	
	@Test
	@DisplayName("Test validateToken()")
	public void testValidateToken() throws Exception {
		when(userService.validateToken(any())).thenReturn(true);
		this.mockMvc.perform(get("/api/v1.0/tweets/validate").header("Authorization", "abcdefg")).andExpect(status().isOk());
		verify(userService).validateToken(any());
	}
	
	/******************************************************  forgotPassword()  @throws Exception  ****************************************************************/
	
	@Test
	@DisplayName("Test forgotPassword()")
	public void testForgotPassword() throws Exception {
		when(userService.resetPasswordUser(any(),any())).thenReturn(new TweetResponse("Reset Successful"));
		this.mockMvc.perform(put("/api/v1.0/tweets/{username}/forgot","john")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(resetPasswordUser)))
				.andExpect(status().isOk());
		verify(userService).resetPasswordUser(any(), any());
	}
	
	
	/******************************************************  getAllUsers()  @throws Exception  ***********************************************************/
	
	@Test
	@DisplayName("Test getAllUsers()")
	public void testGetAllUsers() throws Exception {
		when(userService.findAllUsers()).thenReturn(List.of(userResponse));
		this.mockMvc.perform(get("/api/v1.0/tweets/users/all")).andExpect(status().isOk());
		verify(userService).findAllUsers();
	}
	
	
	/******************************************************  searchByUsername()  @throws Exception  ***********************************************************/
	
	@Test
	@DisplayName("Test searchByUsername()")
	public void testSearchByUsername() throws Exception {
		when(userService.searchByUsername("john")).thenReturn(List.of(userResponse));
		this.mockMvc.perform(get("/api/v1.0/tweets/user/search/{username}","john")).andExpect(status().isOk());
		verify(userService).searchByUsername(any());
	}
	
	
	/******************************************************  deleteUser()  @throws Exception  ***********************************************************/
	
	@Test
	@DisplayName("Test deleteUser()")
	public void testDeleteUser() throws Exception {
		when(userService.removeUser("john")).thenReturn(new TweetResponse("User Removed"));
		this.mockMvc.perform(delete("/api/v1.0/tweets/delete/{username}","john")).andExpect(status().isOk());
		verify(userService).removeUser(any());
	}

}
