package com.gil.couponsys02.exceptions;

public class AlreadyInUseException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public<E, T> AlreadyInUseException(ErrorsMessages error, E firstValue  , T SecondValue) {
		super(String.format(error.getMessage(), firstValue, SecondValue));
	}
}
