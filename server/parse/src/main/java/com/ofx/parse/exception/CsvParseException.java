package com.ofx.parse.exception;

public class CsvParseException extends IllegalArgumentException {

	public CsvParseException (String message, Throwable cause) {
		super(message, cause);
	}

}
