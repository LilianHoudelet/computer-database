package com.excilys.cdb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Company")
public class CompanyNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CompanyNotFoundException() {
		super();
	}
	
	public CompanyNotFoundException(final Throwable cause) {
		super(cause);
	}
	
	public CompanyNotFoundException(final String message) {
		super(message);
	}
	
	public CompanyNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

}