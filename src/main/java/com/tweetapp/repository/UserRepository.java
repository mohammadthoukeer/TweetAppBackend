package com.tweetapp.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tweetapp.entity.User;

@Repository
public class UserRepository /*extends MongoRepository<User, String>*/ {

	@Autowired
	private DynamoDBMapper mapper;

	public User save(User user){
		mapper.save(user);
		return user;
	}

	public Optional<User> findById(String username){
		User user = mapper.load(User.class, username);
		return Optional.ofNullable(user);
	}

	public User findByEmail(String email){
		Map<String, AttributeValue> expAttrValues = new HashMap<>();
		expAttrValues.put(":email", new AttributeValue().withS(email));

		DynamoDBScanExpression scanExp = new DynamoDBScanExpression()
				.withFilterExpression("begins_with(email,:email)")
				.withExpressionAttributeValues(expAttrValues);

		List<User> users = mapper.scan(User.class,scanExp);
		if(!users.isEmpty()) return users.get(0);
		return null;
	}


	public User edit(User user){
		mapper.save(user,saveExpression(user));
		return user;
	}

	public DynamoDBSaveExpression saveExpression(User user){
		DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
		Map<String, ExpectedAttributeValue> expectedMap = new HashMap<>();
		expectedMap.put("username", new ExpectedAttributeValue(new AttributeValue().withS(user.getUsername())));
		dynamoDBSaveExpression.setExpected(expectedMap);
		return dynamoDBSaveExpression;
	}

	public List<User> findAll(){
		return mapper.scan(User.class,new DynamoDBScanExpression());
	}

	public List<User> findByUsernameContaining(String user){
		Map<String, AttributeValue> expAttrValues = new HashMap<>();
		expAttrValues.put(":user", new AttributeValue().withS(user));

		DynamoDBScanExpression scanExp = new DynamoDBScanExpression()
				.withFilterExpression("begins_with(username,:user)")
				.withExpressionAttributeValues(expAttrValues);

		return mapper.scan(User.class,scanExp);
	}

	public void deleteById(String username){
		mapper.delete(mapper.load(User.class,username));
	}

//	User findByEmail(String email);
//	List<User> findByUsernameContaining(String username);
}
