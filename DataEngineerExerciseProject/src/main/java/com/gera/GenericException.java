package com.gera;

public class GenericException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GenericException(String message, Throwable cause) {
        super(message, cause);
    }
}