package com.bufanbaby.backend.rest.exception;

import com.bufanbaby.backend.rest.exception.ErrorResponse.ErrorCode;

@SuppressWarnings("serial")
public class UserNotFoundException extends GenericWebApplicationException {

	public UserNotFoundException(String errorMessage) {
		super(404, ErrorCode.USER_NOT_FOUND.code, errorMessage,
				ErrorCode.USER_NOT_FOUND.developerMsg);
	}
}
