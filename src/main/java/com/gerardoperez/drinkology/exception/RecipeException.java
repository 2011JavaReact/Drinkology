package com.gerardoperez.drinkology.exception;

public class RecipeException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public RecipeException() {
		super();
	}
	
	public RecipeException(String message) {
		super(message);
	}
	
	public RecipeException(Throwable cause) {
		super(cause);
	}
	
	public RecipeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RecipeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
