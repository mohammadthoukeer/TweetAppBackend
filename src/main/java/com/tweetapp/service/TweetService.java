package com.tweetapp.service;

import java.util.List;

import com.tweetapp.entity.Tweet;
import com.tweetapp.exception.NoSuchUserException;
import com.tweetapp.exception.TweetNotFoundException;
import com.tweetapp.pojo.TweetResponse;

public interface TweetService {

	public Tweet postTweetByUser(String username,Tweet tweet) throws NoSuchUserException;
	public Tweet updateTweet(String username,Tweet tweet) throws NoSuchUserException;
	public List<Tweet> getAllTweets();
	public List<Tweet> getAllTweetsByUser(String username) throws NoSuchUserException;
	public Tweet updateTweetByUser(Tweet tweet,String tweetId) throws TweetNotFoundException;
	public TweetResponse deleteTweetById(String tweetId) throws TweetNotFoundException;
	public TweetResponse likeTweetById(String tweetId,String username) throws TweetNotFoundException, NoSuchUserException;
	public Tweet replyTweetById(String parentTweetId, Tweet tweet,String username) throws TweetNotFoundException, NoSuchUserException;
}
