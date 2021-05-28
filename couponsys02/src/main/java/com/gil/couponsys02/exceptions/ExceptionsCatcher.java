package com.gil.couponsys02.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsCatcher {
	
	@ExceptionHandler(AlreadyInUseException.class)
	public ResponseEntity<?>  badRequestHandler(Exception e) {
		return errorResponseSender(new ErrorDetails(e.getMessage(), HttpStatus.BAD_REQUEST));
	}
	
	@ExceptionHandler(UnauthorizedAccessException.class)
	public ResponseEntity<?> unauthorizedHandler(Exception e) {
		return errorResponseSender(new ErrorDetails(e.getMessage(), HttpStatus.UNAUTHORIZED));
	}
	
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<?> notFoundHandler(Exception e) {
		return errorResponseSender(new ErrorDetails(e.getMessage(), HttpStatus.NOT_FOUND));
	}
	
	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<?> notAllowedHandler(Exception e) {
		return errorResponseSender(new ErrorDetails(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED));
	}
	
	
	private ResponseEntity<?> errorResponseSender(ErrorDetails errorDetails) {
		return new ResponseEntity<>(errorDetails, errorDetails.getError());
	}
}
