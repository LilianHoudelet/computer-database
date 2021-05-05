package com.excilys.cdb.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Computer")
public class ComputerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ComputerNotFoundException() {
		super();
	}
	
	public ComputerNotFoundException(final Throwable cause) {
		super(cause);
	}
	
	public ComputerNotFoundException(final String message) {
		super(message);
	}
	
	public ComputerNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
