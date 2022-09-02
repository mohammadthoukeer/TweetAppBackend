package com.tweetapp.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	public static final String TOKEN_SECRET = "secret";

    public String generateToken(String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            String token = JWT.create()
                    .withClaim("username", username)
                    .withClaim("createdAt", new Date(System.currentTimeMillis()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                    .sign(algorithm);
            logger.info("Token created :: "+token);
            return token;
        } catch (UnsupportedEncodingException exception) {
            logger.error("Wrong encoded message");
        } catch (JWTCreationException exception) {
        	logger.error("Token signin failed");
        }
        return null;
    }

    public String getUsernameFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("username").asString();
        } catch (UnsupportedEncodingException exception) {
            logger.error("Wrong encoding message");
        } catch (JWTVerificationException exception) {
            logger.error("Token verification failed");
        }
		return null;
    }

    public boolean isTokenValid(String token) {
    	String username = this.getUsernameFromToken(token);
        return username != null;
    }
	
}