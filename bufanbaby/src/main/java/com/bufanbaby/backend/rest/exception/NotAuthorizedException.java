package com.bufanbaby.backend.rest.exception;

@SuppressWarnings("serial")
public class NotAuthorizedException extends GenericWebApplicationException {

	public NotAuthorizedException(String applicationMessage) {
		super(403, "Not authorized", applicationMessage);
	}

}
