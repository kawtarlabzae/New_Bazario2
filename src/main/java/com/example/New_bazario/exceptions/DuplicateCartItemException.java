package com.example.New_bazario.exceptions;

public class DuplicateCartItemException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateCartItemException(String message) {
        super(message);
    }
}
