package com.gil.couponsys02.exceptions;

public class InvalidDataException extends Exception {

private static final long serialVersionUID = 1L;
	
	public <T> InvalidDataException(ErrorsMessages error, T data) {
		super(String.format(error.getMessage(), data));
	}
}
