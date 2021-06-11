package com.negd.umang.coir.exceptionhandler;

public class BasicValidationException extends Exception {

	
	private static final long serialVersionUID = 1L;
	
	public BasicValidationException(String message) {
		super(message);
	}
	
	public BasicValidationException(Throwable cause) {
		super(cause);
	}

}
