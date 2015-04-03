package com.bufanbaby.backend.rest.exception;

@SuppressWarnings("serial")
public class NotAuthorizedException extends AbstractWebApplicationException {

	public NotAuthorizedException(String applicationMessage) {
		super(403, "Not authorized", applicationMessage);
	}

}
