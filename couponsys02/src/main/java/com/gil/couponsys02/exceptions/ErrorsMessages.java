package com.gil.couponsys02.exceptions;

public enum ErrorsMessages {
	LOGIN_FAILED("Email (%s) or password is incorrect for %s."),
	NAME_OR_EMAIL_ALREADY_IN_USE("Name (%s) or email (%s) already in use"),
	EMAIL_ALREADY_IN_USE("%s email %s already in use"),
	NOT_FOUND_BY_EMAIL("No %s with an email %s was found"),
	NOT_FOUND_BY_ID("No %s with an id %d was found"),
	NAME_REPLACED("Replacing Name of company %d is not allowed"),
	COMPANY_REPLACED("Replacing company of a coupon %d is not allowed"),
	COUPON_ALREADY_PURCHASED("Customer %d already purchased coupon %d"),
	COUPON_TITLE_ALREADY_IN_USE_BY_COMPANY("Company %d, already have a coupon with title '%s'"),
	COUPON_NOT_FOUND("No Coupon named %s belong to comapny with id %d was found"),
	COUPON_NOT_FOUND_BY_ID("No coupon belong to company %d was found with coupon id %d"),
	NO_COUPONS_LEFT("Coupon %d is out of stock"),
	COUPON_EXPIRED("Coupon %d is expired"),
	WORNG_COMPANY_ID("Adding or Update Coupon of other Company (%d) is NOT allowed");
	
	private String message;

	private ErrorsMessages(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
