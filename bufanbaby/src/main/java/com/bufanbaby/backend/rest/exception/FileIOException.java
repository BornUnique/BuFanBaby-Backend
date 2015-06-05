package com.bufanbaby.backend.rest.exception;

import com.bufanbaby.backend.rest.exception.ErrorResponse.ErrorCode;

@SuppressWarnings("serial")
public class FileIOException extends GenericWebApplicationException {

	public FileIOException(String errorMessage) {
		super(500, ErrorCode.INTERNAL_FILE_IO_OPERATION_FAILURE.code, errorMessage,
				ErrorCode.INTERNAL_FILE_IO_OPERATION_FAILURE.developerMsg);
	}
}
