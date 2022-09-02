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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
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
import com.tweetapp.entity.Tweet;
import com.tweetapp.entity.User;
import com.tweetapp.pojo.TweetResponse;
import com.tweetapp.service.TweetServiceImpl;

@WebMvcTest(value = TweetController.class)
class TweetControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	TweetServiceImpl tweetService;
	
	private User user;
	private Tweet tweet;
	
	@BeforeEach
	void setUp() {
		user = new User("john", "John", "Wick", "john@yahoo.com", "john12", "1234567890");
		tweet = new Tweet("100", "Hi All", LocalDateTime.now(), 0, new HashSet<>(), new ArrayList<>(),null , user);
	}
	
	@Test
	@DisplayName("Test Mock MVC ")
	void testMockMvcNotNull() {
		assertThat(mockMvc).isNotNull();
	}

	/******************************************************  postTweetByUser()  @throws Exception  ********************************************************/
	
	@Test
	@DisplayName("Test postTweetByUser()")
	public void testPostTweetByUser() throws Exception {
		when(tweetService.postTweetByUser("john", tweet)).thenReturn(tweet);
		this.mockMvc.perform(post("/api/v1.0/tweets/{username}/add","john")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(tweet)))
				.andExpect(status().isCreated());
		verify(tweetService).postTweetByUser(any(), any());
	}
	
	
	/******************************************************  getAllTweets()  @throws Exception  ***********************************************************/
	
	@Test
	@DisplayName("Test getAllTweets()")
	public void testGetAllTweets() throws Exception {
		when(tweetService.getAllTweets()).thenReturn(List.of(tweet));
		this.mockMvc.perform(get("/api/v1.0/tweets/all")).andExpect(status().isOk());
		verify(tweetService).getAllTweets();
	}
	
	
	/******************************************************  getAllTweetsByUser()  @throws Exception  ***********************************************************/
	
	@Test
	@DisplayName("Test getAllTweetsByUser()")
	public void testGetAllTweetsByUser() throws Exception {
		when(tweetService.getAllTweetsByUser("john")).thenReturn(List.of(tweet));
		this.mockMvc.perform(get("/api/v1.0/tweets/{username}","john")).andExpect(status().isOk());
		verify(tweetService).getAllTweetsByUser(any());
	}
	
	
	/******************************************************  updateTweetByUser()  @throws Exception  ****************************************************************/
	
	@Test
	@DisplayName("Test updateTweetByUser()")
	public void testUpdateTweetByUser() throws Exception {
		when(tweetService.updateTweetByUser(tweet,"100")).thenReturn(tweet);
		this.mockMvc.perform(put("/api/v1.0/tweets/{username}/update/{id}","john","100")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(tweet)))
				.andExpect(status().isOk());
		verify(tweetService).updateTweetByUser(any(), any());
	}

	
	/******************************************************  deleteTweetById()  @throws Exception  ***********************************************************/
	
	@Test
	@DisplayName("Test deleteTweetById()")
	public void testDeleteTweetById() throws Exception {
		when(tweetService.deleteTweetById("100")).thenReturn(new TweetResponse("Tweet Deleted"));
		this.mockMvc.perform(delete("/api/v1.0/tweets/{username}/delete/{id}","john","100")).andExpect(status().isOk());
		verify(tweetService).deleteTweetById(any());
	}

	
	/******************************************************  likeTweetById()  @throws Exception  ****************************************************************/
	
	@Test
	@DisplayName("Test likeTweetById()")
	public void testLikeTweetById() throws Exception {
		when(tweetService.likeTweetById("100","john")).thenReturn(new TweetResponse("Tweet Liked"));
		this.mockMvc.perform(put("/api/v1.0/tweets/{username}/like/{id}","john","100"))
				.andExpect(status().isOk());
		verify(tweetService).likeTweetById(any(), any());
	}
	
	
	/******************************************************  replyTweetById()  @throws Exception  ****************************************************************/
	
	@Test
	@DisplayName("Test replyTweetById()")
	public void testReplyTweetById() throws Exception {
		when(tweetService.replyTweetById("100",tweet,"john")).thenReturn(tweet);
		this.mockMvc.perform(post("/api/v1.0/tweets/{username}/reply/{id}","john","100")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(tweet)))
				.andExpect(status().isCreated());
		verify(tweetService).replyTweetById(any(), any(),any());
	}
}
