package com.tweetapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

	User findByEmail(String email);
	List<User> findByUsernameContaining(String username);
}
