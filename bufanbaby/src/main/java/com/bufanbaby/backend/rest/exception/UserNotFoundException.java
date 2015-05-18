package com.bufanbaby.backend.rest.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends GenericWebApplicationException {

	public UserNotFoundException() {
		super(404, "User Not Found", "No User could be found for that Id");
	}
}
