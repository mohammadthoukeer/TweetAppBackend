package com.tweetapp.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//@Service
public class ProducerService {
	
	private static final String topic = "TweetMessage";
	private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	public void publishMessage(String message) {
		logger.info("Publishing message : " + message);
		this.kafkaTemplate.send(topic,message);
	}
	
}
