package com.tweetapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.entity.Tweet;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, String>{

	List<Tweet> findByUserUsernameOrderByPostedDateDesc(String username);
}
