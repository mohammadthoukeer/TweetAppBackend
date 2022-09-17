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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.entity.Tweet;
import com.tweetapp.pojo.TweetResponse;
import com.tweetapp.service.TweetService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1.0/tweets")
public class TweetController {
	
	private static final Logger logger = LoggerFactory.getLogger(TweetController.class);
	
	@Autowired
	TweetService tweetService;
	
	@PostMapping("/{username}/add")
	@ApiOperation(notes = "Returns the tweet details based on valid details of Tweet", value = "Post a tweet")
	public ResponseEntity<Tweet> postTweetByUser(@ApiParam(name = "username", value = "username input") @PathVariable String username,
			@ApiParam(name = "Tweet", value = "Tweet details input") @Valid @RequestBody Tweet tweet) throws Exception{
		Tweet savedTweet = tweetService.postTweetByUser(username,tweet);
		logger.info("New tweet posted by : " + username);
		ResponseEntity<Tweet> response = new ResponseEntity<Tweet>(savedTweet, HttpStatus.CREATED);
		return response;
	}
	
	@GetMapping("/all")
	@ApiOperation(notes = "Returns all tweets", value = "Get all tweets")
	public ResponseEntity<List<Tweet>> getAllTweets(){
		List<Tweet> tweets = tweetService.getAllTweets();
		logger.info("Getting all tweets");
		ResponseEntity<List<Tweet>> response = new ResponseEntity<List<Tweet>>(tweets, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/{username}")
	@ApiOperation(notes = "Returns all tweets based on valid details of user", value = "Get all tweets of user")
	public ResponseEntity<List<Tweet>> getAllTweetsByUser(@ApiParam(name = "username", value = "username input") @PathVariable String username) throws Exception{
		List<Tweet> tweets = tweetService.getAllTweetsByUser(username);
		logger.info("Getting all tweets of user : " + username);
		ResponseEntity<List<Tweet>> response = new ResponseEntity<List<Tweet>>(tweets, HttpStatus.OK);
		return response;
	}
	
	@PutMapping("/{username}/update/{tweetId}")
	@ApiOperation(notes = "Returns the tweet details based on valid details of username and tweetId", value = "Update a tweet of user")
	public ResponseEntity<Tweet> updateTweetByUser(@ApiParam(name = "username", value = "username input") @PathVariable String username,
			@ApiParam(name = "tweetId", value = "tweetId input") @PathVariable String tweetId,
			@ApiParam(name = "Tweet", value = "Tweet details input") @Valid @RequestBody Tweet tweet) throws Exception{
		Tweet updatedTweet = tweetService.updateTweetByUser(tweet,tweetId);
		logger.info("Tweet updated by user : " + username + " : " + tweetId);
		ResponseEntity<Tweet> response = new ResponseEntity<Tweet>(updatedTweet, HttpStatus.OK);
		return response;
	}
	
	@DeleteMapping("/{username}/delete/{tweetId}")
	@ApiOperation(notes = "Returns the response based on valid detail of tweetId after deleting", value = "Delete a tweet by Id")
	public ResponseEntity<TweetResponse> deleteTweetById(@ApiParam(name = "username", value = "username input") @PathVariable String username,
			@ApiParam(name = "tweetId", value = "tweetId input") @PathVariable String tweetId) throws Exception{
		TweetResponse resp = tweetService.deleteTweetById(tweetId);
		logger.info("Tweet deleted by user : " + username + " : " + tweetId);
		ResponseEntity<TweetResponse> response = new ResponseEntity<TweetResponse>(resp, HttpStatus.OK);
		return response;
	}
	
	@PutMapping("/{username}/like/{tweetId}")
	@ApiOperation(notes = "Returns the response based on valid details of username and tweetId", value = "Like a tweet by user")
	public ResponseEntity<TweetResponse> likeTweetById(@ApiParam(name = "username", value = "username input") @PathVariable String username,
			@ApiParam(name = "tweetId", value = "tweetId input") @PathVariable String tweetId) throws Exception{
		TweetResponse resp = tweetService.likeTweetById(tweetId,username);
		logger.info("Tweet liked by user : " + username + " : " + tweetId);
		ResponseEntity<TweetResponse> response = new ResponseEntity<TweetResponse>(resp, HttpStatus.OK);
		return response;
	}
	
	@PostMapping("/{username}/reply/{parentTweetId}")
	@ApiOperation(notes = "Returns the tweet details based on valid details of username,parentTweetId and tweet after replying", value = "Reply a parent tweet by user")
	public ResponseEntity<Tweet> replyTweetById(@ApiParam(name = "username", value = "username input") @PathVariable String username,
			@ApiParam(name = "parentTweetId", value = "parentTweetId input") @PathVariable String parentTweetId,
			@ApiParam(name = "Tweet", value = "Tweet details input") @Valid @RequestBody Tweet tweet) throws Exception{
		Tweet replyTweet = tweetService.replyTweetById(parentTweetId,tweet,username);
		logger.info("Tweet replied by user : " + username + " : " + parentTweetId);
		ResponseEntity<Tweet> response = new ResponseEntity<Tweet>(replyTweet, HttpStatus.CREATED);
		return response;
	}
}
