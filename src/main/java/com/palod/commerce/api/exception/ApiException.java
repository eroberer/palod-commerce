package com.palod.commerce.api.exception;

import lombok.Getter;

@Getter
public class ApiException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;
	private int status;

	public ApiException(String message) {
		this(message, 400);
	}

	public ApiException(String message, int status) {
		super(message);
		this.message = message;
		this.status = status;
	}

	public ApiException(String message, Throwable cause) {
		super(message, cause);
	}
}
