package com.tweetapp.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
public class ConsumerService {
	
	private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

	@KafkaListener(topics = "TweetMessage",groupId = "mytweetgroup")
	public void consumeMessage(String message) {
		logger.info("Consumed Message : " + message);
	}
}
