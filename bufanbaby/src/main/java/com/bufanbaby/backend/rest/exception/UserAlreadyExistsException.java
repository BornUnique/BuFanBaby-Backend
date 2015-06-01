package com.bufanbaby.backend.rest.exception;

import com.bufanbaby.backend.rest.exception.ErrorResponse.ErrorCode;

@SuppressWarnings("serial")
public class UserAlreadyExistsException extends GenericWebApplicationException {

	public UserAlreadyExistsException(String errorMessage) {
		super(409, ErrorCode.USER_ALREADY_EXISTS.code, errorMessage,
				ErrorCode.USER_ALREADY_EXISTS.developerMsg);
	}
}