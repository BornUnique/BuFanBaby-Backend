package com.bufanbaby.backend.rest.exception;

@SuppressWarnings("serial")
public class DuplicateUserException extends GenericWebApplicationException {

	public DuplicateUserException(String errorMessage) {
		super(409, "BB4009", errorMessage,
				"An attempt was made to create a user that already exists");
	}
}