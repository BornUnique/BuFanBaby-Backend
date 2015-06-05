package com.bufanbaby.backend.rest.exception;

import com.bufanbaby.backend.rest.exception.ErrorResponse.ErrorCode;

@SuppressWarnings("serial")
public class UnsupportedFileTypeException extends GenericWebApplicationException {

	public UnsupportedFileTypeException(String errorMessage) {
		super(415, ErrorCode.UNSUPPORTED_FILE_TYPE.code, errorMessage,
				ErrorCode.UNSUPPORTED_FILE_TYPE.developerMsg);
	}
}