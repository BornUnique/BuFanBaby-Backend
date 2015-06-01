package com.bufanbaby.backend.rest.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bufanbaby.backend.rest.exception.ErrorResponse.ErrorCode;

@SuppressWarnings("serial")
public class ValidationException extends WebApplicationException {

	private final static int status = 400;
	private final String errorMessage;
	private final List<Violation> violations = new ArrayList<Violation>();

	public ValidationException(String errorMessage,
			Set<? extends ConstraintViolation<?>> constraintViolations) {
		this.errorMessage = errorMessage;

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

	public ValidationErrorResponse getViolationResponse() {
		ValidationErrorResponse response = new ValidationErrorResponse();
		response.setErrorCode(ErrorCode.REQUEST_VALIDATION_ERROR.code);
		response.setApplicationMessage(ErrorCode.REQUEST_VALIDATION_ERROR.developerMsg);
		response.setConsumerMessage(errorMessage);
		response.setViolations(violations);
		return response;
	}

	public void addViolation(Violation violation) {
		this.violations.add(violation);
	}
}
