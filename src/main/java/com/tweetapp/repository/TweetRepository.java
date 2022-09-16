package com.tweetapp.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tweetapp.entity.Tweet;

@Repository
public class TweetRepository /*extends MongoRepository<Tweet, String>*/ {

	@Autowired
	private DynamoDBMapper mapper;

	public Tweet save(Tweet tweet) {
		mapper.save(tweet);
		return tweet;
	}

	public Optional<Tweet> findById(String tweetId){
		Tweet tweet = mapper.load(Tweet.class, tweetId);
		return Optional.ofNullable(tweet);
	}

	public void deleteById(String tweetId){
		mapper.delete(mapper.load(Tweet.class,tweetId));
	}

	public Tweet edit(Tweet tweet){
		mapper.save(tweet,saveExpression(tweet));
		return tweet;
	}

	public DynamoDBSaveExpression saveExpression(Tweet tweet){
		DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
		Map<String, ExpectedAttributeValue> expectedMap = new HashMap<>();
		expectedMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(tweet.getId())));
		dynamoDBSaveExpression.setExpected(expectedMap);
		return dynamoDBSaveExpression;
	}

	public List<Tweet> findAll(){
		return mapper.scan(Tweet.class,new DynamoDBScanExpression());
	}



//	List<Tweet> findByUserUsernameOrderByPostedDateDesc(String username);
}
