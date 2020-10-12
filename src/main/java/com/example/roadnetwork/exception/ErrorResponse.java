package com.example.roadnetwork.exception;

/**
 * Error Response class to encapsulate error code and error message.
 *
 */
public class ErrorResponse {

	private int errorCode;

	private String message;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
