package com.greeting.api.core.exception;

import org.springframework.validation.BindingResult;

public class EntityValidationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static String MESSAGE = "Bad Request";
	
	private final BindingResult bindingResult;

	public EntityValidationException(BindingResult bindingResult) {
		super(MESSAGE);
		this.bindingResult = bindingResult;
	}

	public BindingResult getBindingResult() {
		return this.bindingResult;
	}
}
