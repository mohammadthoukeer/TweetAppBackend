package com.tweetapp.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Document(collection =  "tweets")
@ApiModel(value = "Model object that stores the Tweet information")
public class Tweet {

	@ApiModelProperty(notes = "Id of the Tweet") 
	@Id private String id;
	
	@ApiModelProperty(notes = "Tweet message of the Tweet")
	@NotEmpty(message = "Tweet message cannot be empty")
	@Size(max = 144,message = "Tweet message should not be greater than 144 characters")
	private String tweet;
	
	@ApiModelProperty(notes = "Tweet creation Date") 
	private LocalDateTime postedDate;
	
	@ApiModelProperty(notes = "Likes of the Tweet") 
	private long likes;
	
	@ApiModelProperty(notes = "Liked users of the Tweet")
	private Set<String> likedBy;
	
	@ApiModelProperty(notes = "Replies of the Tweet")
	private List<Tweet> replies;
	
	@ApiModelProperty(notes = "Tag of the Tweet")
	@Size(max = 50,message = "TweetTag should not be greater than 50 characters")
	private String tweetTag;
	
	@ApiModelProperty(notes = "User of the Tweet")
	private User user;
	
	public Tweet() {}

	public Tweet(String id, String tweet, LocalDateTime postedDate, long likes, Set<String> likedBy,
			List<Tweet> replies, String tweetTag, User user) {
		super();
		this.id = id;
		this.tweet = tweet;
		this.postedDate = postedDate;
		this.likes = likes;
		this.likedBy = likedBy;
		this.replies = replies;
		this.tweetTag = tweetTag;
		this.user = user;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	public LocalDateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(LocalDateTime postedDate) {
		this.postedDate = postedDate;
	}

	public long getLikes() {
		return likes;
	}

	public void setLikes(long likes) {
		this.likes = likes;
	}

	public List<Tweet> getReplies() {
		return replies;
	}

	public void setReplies(List<Tweet> replies) {
		this.replies = replies;
	}

	public String getTweetTag() {
		return tweetTag;
	}

	public void setTweetTag(String tweetTag) {
		this.tweetTag = tweetTag;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<String> getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(Set<String> likedBy) {
		this.likedBy = likedBy;
	}

	@Override
	public String toString() {
		return "Tweet [id=" + id + ", tweet=" + tweet + ", postedDate=" + postedDate + ", likes=" + likes + ", replies="
				+ replies + ", tweetTag=" + tweetTag + ", user=" + user + "]";
	}
	
}
