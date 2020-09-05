package com.greeting.api.core.exception;

public class UserAgentRequiredException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String MESSAGE = "User-Agent header is required";

	public UserAgentRequiredException() {
		super(MESSAGE);
	}
}
