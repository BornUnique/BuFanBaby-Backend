package com.bufanbaby.backend.rest.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends GenericWebApplicationException {

	public UserNotFoundException(String errorMessage) {
		super(404, "BB4004", errorMessage, "No User could be found for that ID");
	}
}
