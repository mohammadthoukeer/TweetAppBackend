package com.tweetapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

import com.tweetapp.entity.Tweet;
import com.tweetapp.entity.User;
import com.tweetapp.exception.NoSuchUserException;
import com.tweetapp.exception.TweetNotFoundException;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.repository.UserRepository;

class TweetServiceImplTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@InjectMocks
	TweetServiceImpl tweetService;
	
	@Mock
	UserRepository userRepo;
	
	@Mock
	TweetRepository tweetRepo;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	private Tweet tweet;
	private Tweet tweet2;
	private Tweet tweet3;
	private User user;

	@BeforeEach
	void setUp() {
		tweet = new Tweet();
		tweet.setId("1");
		tweet.setTweet("Hi All");
		tweet.setLikedBy(new HashSet<>());
		List<Tweet> replies = new ArrayList<Tweet>();
		tweet2 = new Tweet();
		tweet2.setId("100");
		tweet2.setTweet("Hi");
		tweet2.setLikedBy(new HashSet<>());
		replies.add(tweet);
		tweet2.setReplies(replies);
		user = new User("john", "John", "Wick", "john@yahoo.com", "john12", "1234567890");
		tweet.setUser(user);
		tweet.setReplies(new ArrayList<Tweet>());
		tweet2.setUser(user);
		tweet3 = new Tweet();
		tweet3.setId("200");
		tweet3.setTweet("Good");
		tweet3.setLikedBy(new HashSet<String>());
		tweet3.setReplies(new ArrayList<Tweet>());
		tweet3.setUser(user);
	}

	
	/***************************************************  postTweetByUser() @throws Exception ********************************************/
	
	@Test
	@DisplayName("Test postTweetByUser() with valid username")
	public void testPostTweetByUserValid() throws Exception {
		when(userRepo.findById("john")).thenReturn(Optional.of(user));
		when(tweetRepo.save(tweet)).thenReturn(tweet);
		assertThat(tweetService.postTweetByUser("john", tweet).getTweet()).isEqualTo(tweet.getTweet());
		verify(userRepo).findById(any());
		verify(tweetRepo).save(any());
	}
	
	@Test
	@DisplayName("Test postTweetByUser() with invalid username")
	public void testPostTweetByUserInvalidUser() {
		when(userRepo.findById("john")).thenReturn(Optional.empty());
		assertThrows(NoSuchUserException.class,() -> { tweetService.postTweetByUser("john", tweet); });
		verify(userRepo).findById(any());
	}
	
	
	/********************************************************  getAllTweets()  *********************************************************/
	
	
	@Test
	@DisplayName("Test getAllTweets()")
	public void testGetAllTweets() {
		when(tweetRepo.findAll(Sort.by(Sort.Direction.DESC,"postedDate"))).thenReturn(Arrays.asList(tweet));
		assertThat(tweetService.getAllTweets()).hasSize(1);
		verify(tweetRepo).findAll(Sort.by(Sort.Direction.DESC,"postedDate"));
	}
	
	/*********************************************************  getAllTweetsByUser()  @throws Exception ******************************************************/
	
	
	@Test
	@DisplayName("Test getAllTweetsByUser() valid username")
	public void testGetAllTweetsByUser() throws Exception {
		when(userRepo.findById("john")).thenReturn(Optional.of(user));
		when(tweetRepo.findByUserUsernameOrderByPostedDateDesc("john")).thenReturn(List.of(tweet));
		assertThat(tweetService.getAllTweetsByUser("john")).hasSize(1);
		verify(userRepo).findById(any());
		verify(tweetRepo).findByUserUsernameOrderByPostedDateDesc(any());
	}
	
	@Test
	@DisplayName("Test getAllTweetsByUser() with invalid username")
	public void testGetAllTweetsByUserInvalidUser() {
		when(userRepo.findById("john")).thenReturn(Optional.empty());
		assertThrows(NoSuchUserException.class,() -> { tweetService.getAllTweetsByUser("john"); });
		verify(userRepo).findById(any());
	}
	
	/************************************************************  updateTweet()  @throws Exception ********************************************************************/
	
	@Test
	@DisplayName("Test updateTweet() with valid username")
	public void testUpdateTweetValid() throws Exception {
		when(userRepo.findById("john")).thenReturn(Optional.of(user));
		when(tweetRepo.save(tweet)).thenReturn(tweet);
		assertThat(tweetService.updateTweet("john", tweet).getTweet()).isEqualTo(tweet.getTweet());
		verify(userRepo).findById(any());
		verify(tweetRepo).save(any());
	}
	
	@Test
	@DisplayName("Test updateTweet() with invalid username")
	public void testUpdateTweetInvalidUser() {
		when(userRepo.findById("john")).thenReturn(Optional.empty());
		assertThrows(NoSuchUserException.class,() -> { tweetService.updateTweet("john", tweet); });
		verify(userRepo).findById(any());
	}
	
	/********************************************************  updateTweetByUser()  @throws Exception ************************************************************/
	
	@Test
	@DisplayName("Test updateTweetByUser() with valid tweetId")
	public void testUpdateTweetByUserValid() throws Exception {
		when(tweetRepo.findById("1")).thenReturn(Optional.of(tweet));
		when(tweetRepo.save(tweet)).thenReturn(tweet);
		assertThat(tweetService.updateTweetByUser(tweet, "1").getTweet()).isEqualTo(tweet.getTweet());
		verify(tweetRepo).findById(any());
		verify(tweetRepo).save(any());
	}
	
	@Test
	@DisplayName("Test updateTweetByUser() with invalid tweetId")
	public void testUpdateTweetByUserInvalidTweetId() {
		when(tweetRepo.findById("1")).thenReturn(Optional.empty());
		assertThrows(TweetNotFoundException.class,() -> { tweetService.updateTweetByUser(tweet, "1"); });
		verify(tweetRepo).findById(any());
	}
	
	
	/********************************************************  deleteTweetById()  @throws Exception ************************************************************/
	
	@Test
	@DisplayName("Test deleteTweetById() with valid tweetId")
	public void testDeleteTweetByIdValid() throws Exception {
		when(tweetRepo.findById("1")).thenReturn(Optional.of(tweet));
		assertThat(tweetService.deleteTweetById("1").getMessage()).isEqualTo("Tweet Deleted Successfully");
		verify(tweetRepo).findById(any());
	}
	
	@Test
	@DisplayName("Test deleteTweetById() with invalid tweetId")
	public void testDeleteTweetByIdInvalidTweetId() {
		when(tweetRepo.findById("1")).thenReturn(Optional.empty());
		assertThrows(TweetNotFoundException.class,() -> { tweetService.deleteTweetById("1"); });
		verify(tweetRepo).findById(any());
	}
	
	/**********************************************************  likeTweetById()  @throws Exception  ***********************************************************/
	
	@Test
	@DisplayName("Test likeTweetById() with valid tweetId")
	public void testLikeTweetByIdValid() throws Exception {
		when(tweetRepo.findById("1")).thenReturn(Optional.of(tweet));
		when(userRepo.findById("john")).thenReturn(Optional.of(user));
		assertThat(tweetService.likeTweetById("1","john").getMessage()).isEqualTo("Tweet liked");
		verify(tweetRepo).findById(any());
		verify(userRepo).findById(any());
	}
	
	@Test
	@DisplayName("Test likeTweetById() with invalid tweetId")
	public void testLikeTweetByIdInvalidTweetId() {
		when(tweetRepo.findById("1")).thenReturn(Optional.empty());
		assertThrows(TweetNotFoundException.class,() -> { tweetService.likeTweetById("1","john"); });
		verify(tweetRepo).findById(any());
	}
	
	@Test
	@DisplayName("Test likeTweetById() with invalid username")
	public void testLikeTweetByIdInvalidUser() {
		when(tweetRepo.findById("1")).thenReturn(Optional.of(tweet));
		when(userRepo.findById("john")).thenReturn(Optional.empty());
		assertThrows(NoSuchUserException.class,() -> { tweetService.likeTweetById("1", "john"); });
		verify(tweetRepo).findById(any());
		verify(userRepo).findById(any());
	}
	
	/****************************************************  replyTweetById()  ***********************************************************/
	
	@Test
	@DisplayName("Test replyTweetById() with valid tweetId")
	public void testReplyTweetByIdValid() throws Exception {
		when(tweetRepo.findById("200")).thenReturn(Optional.of(tweet3));
		when(userRepo.findById("john")).thenReturn(Optional.of(user));
		when(tweetRepo.save(tweet3)).thenReturn(tweet3);
		assertThat(tweetService.replyTweetById("200",tweet,"john").getTweet()).isEqualTo(tweet3.getTweet());
		verify(tweetRepo).findById(any());
		verify(userRepo).findById(any());
		verify(tweetRepo).save(any());
	}
	
	@Test
	@DisplayName("Test replyTweetById() with invalid tweetId")
	public void testReplyTweetByIdInvalidTweetId() {
		when(tweetRepo.findById("1")).thenReturn(Optional.empty());
		assertThrows(TweetNotFoundException.class,() -> { tweetService.replyTweetById("1",tweet,"john"); });
		verify(tweetRepo).findById(any());
	}
	
	@Test
	@DisplayName("Test replyTweetById() with invalid username")
	public void testReplyTweetByIdInvalidUser() {
		when(tweetRepo.findById("100")).thenReturn(Optional.of(tweet2));
		when(userRepo.findById("john")).thenReturn(Optional.empty());
		assertThrows(NoSuchUserException.class,() -> { tweetService.replyTweetById("100",tweet2,"john"); });
		verify(userRepo).findById(any());
		verify(tweetRepo).findById(any());
	}

}
