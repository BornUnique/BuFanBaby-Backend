package com.bufanbaby.backend.rest.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@SuppressWarnings("serial")
public class ValidationException extends WebApplicationException {

	private final static int status = 400;
	private final String errorMessage;
	private String developerMessage;
	private final List<Violation> violations = new ArrayList<Violation>();

	public ValidationException() {
		errorMessage = "Validation Error";
		developerMessage = "The data passed in the request was invalid. Please check and resubmit";
	}

	public ValidationException(String message) {
		super();
		errorMessage = message;
	}

	public ValidationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
		this();
		for (ConstraintViolation<?> constraintViolation : constraintViolations) {
			Violation violation = new Violation();
			violation.setMessage(constraintViolation.getMessage());
			violation.setPropertyName(constraintViolation.getPropertyPath().toString());
			violation
					.setPropertyValue(constraintViolation.getInvalidValue() != null ? constraintViolation
							.getInvalidValue().toString()
							: null);
			violations.add(violation);
		}
	}

	@Override
	public Response getResponse() {
		return Response.status(status).type(MediaType.APPLICATION_JSON_TYPE)
				.entity(getViolationResponse()).build();
	}

	public ErrorResponse getViolationResponse() {
		ErrorResponse response = new ErrorResponse();
		response.setApplicationMessage(developerMessage);
		response.setConsumerMessage(errorMessage);
		response.setViolations(violations);
		return response;
	}

}
