package com.example.New_bazario.exceptions;

public class UserNotAuthenticatedException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotAuthenticatedException(String message) {
        super(message);
    }
}
