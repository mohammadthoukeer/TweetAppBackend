package com.tweetapp.exception;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ExceptionDetails> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.CONFLICT,ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(UserEmailAlreadyInUseException.class)
	public ResponseEntity<ExceptionDetails> handleUserEmailAlreadyInUseException(UserEmailAlreadyInUseException ex) {
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.CONFLICT,ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(NoSuchUserException.class)
	public ResponseEntity<ExceptionDetails> handleNoSuchUserPresentException(NoSuchUserException ex) {
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.NOT_FOUND,ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidUsernameOrPasswordException.class)
	public ResponseEntity<ExceptionDetails> handleInvalidUsernameOrPasswordException(InvalidUsernameOrPasswordException ex) {
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.NOT_FOUND,ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidEmailException.class)
	public ResponseEntity<ExceptionDetails> handleInvalidEmailException(InvalidEmailException ex) {
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.NOT_FOUND,ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TweetNotFoundException.class)
	public ResponseEntity<ExceptionDetails> handleTweetNotFoundException(TweetNotFoundException ex) {
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.NOT_FOUND,ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList());
		body.put("errors", errors);
		return new ResponseEntity<>(body, headers, status);
	}
}
