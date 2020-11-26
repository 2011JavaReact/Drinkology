package com.gerardoperez.drinkology.exception;

public class IngredientNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public IngredientNotFoundException() {
		super();
	}
	
	public IngredientNotFoundException(String message) {
		super(message);
	}
	
	public IngredientNotFoundException(Throwable cause) {
		super(cause);
	}
	
	public IngredientNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public IngredientNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
