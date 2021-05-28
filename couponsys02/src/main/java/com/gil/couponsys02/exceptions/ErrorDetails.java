package com.gil.couponsys02.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ErrorDetails {

	private String message;
	private HttpStatus error;
	private int status;
	
	
	public ErrorDetails(String message, HttpStatus error) {
		this.message = message;
		this.error = error;
		this.status =  error.value();
	}
	
	
}
