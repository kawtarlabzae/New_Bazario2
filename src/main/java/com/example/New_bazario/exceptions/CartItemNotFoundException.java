package com.example.New_bazario.exceptions;

public class CartItemNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CartItemNotFoundException(String message) {
        super(message);
    }
}