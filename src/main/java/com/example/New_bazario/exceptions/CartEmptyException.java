package com.example.New_bazario.exceptions;

public class CartEmptyException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CartEmptyException(String message) {
        super(message);
    }
}
