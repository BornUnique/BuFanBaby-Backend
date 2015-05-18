package com.bufanbaby.backend.rest.exception;

@SuppressWarnings("serial")
public class DuplicateUserException extends GenericWebApplicationException {

	public DuplicateUserException() {
		super(409, "User already exists",
				"An attempt was made to create a user that already exists");
	}
}