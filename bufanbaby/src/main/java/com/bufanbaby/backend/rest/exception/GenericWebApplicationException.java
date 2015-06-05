package com.bufanbaby.backend.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@SuppressWarnings("serial")
public class GenericWebApplicationException extends WebApplicationException {

	private final int status;
	private final String errorCode;
	private final String errorMessage;
	private final String developerMessage;

	public GenericWebApplicationException(int httpStatus, String errorCode, String errorMessage,
			String developerMessage) {
		this.status = httpStatus;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.developerMessage = developerMessage;
	}

	@Override
	public Response getResponse() {
		return Response.status(status).type(MediaType.APPLICATION_JSON_TYPE)
				.entity(getErrorResponse()).build();
	}

	@Override
	public String getMessage() {
		return errorMessage;
	}

	public ErrorResponse getErrorResponse() {
		ErrorResponse response = new ErrorResponse();
		response.setErrorCode(errorCode);
		response.setConsumerMessage(errorMessage);
		response.setApplicationMessage(developerMessage);
		return response;
	}
}
