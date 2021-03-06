package com.greeting.api.core.exception;

public class BadRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static String MESSAGE = "A required parameter is missing";

	public BadRequestException() {
		super(MESSAGE);
	}

	public BadRequestException(String message) {
		super(message);
	}
}
