package com.tweetapp.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.tweetapp.jwt.JwtUtil;

class JwtUtilTest {

	@Autowired
	MockMvc mockMvc;
	
	@InjectMocks
	JwtUtil jwtUtil;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	private String token;
	
	@BeforeEach
	void setUp() {
		token = jwtUtil.generateToken("john");
	}

	@Test
	@DisplayName("Test generateToken()")
	void testGenerateToken() {
		assertThat(jwtUtil.generateToken("john")).isNotEmpty();
	}
	
	@Test
	@DisplayName("Test getUsernameFromToken()")
	void testGetUsernameFromToken() {
		assertThat(jwtUtil.getUsernameFromToken(token)).isNotEmpty();
	}
	
	@Test
	@DisplayName("Test getUsernameFromToken() invalid")
	void testGetUsernameFromTokenInvalid() {
		assertThat(jwtUtil.getUsernameFromToken("abc")).isNull();
	}
	
	@Test
	@DisplayName("Test isTokenValid() Valid")
	void testIsTokenValidWithValidToken() {
		assertThat(jwtUtil.isTokenValid(token)).isTrue();
	}
	
	@Test
	@DisplayName("Test isTokenValid() invalid")
	void testIsTokenValidInvalidToken() {
		assertThat(jwtUtil.isTokenValid("abc")).isFalse();
	}

}
