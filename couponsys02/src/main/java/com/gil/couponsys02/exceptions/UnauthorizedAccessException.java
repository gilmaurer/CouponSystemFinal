package com.gil.couponsys02.exceptions;

public class UnauthorizedAccessException extends Exception {

	private static final long serialVersionUID = 1L;

	public <E, T> UnauthorizedAccessException(ErrorsMessages error, E firstVlaue,T secondValue) {
		super(String.format(error.getMessage(), firstVlaue, secondValue));
	}
	
	

}
