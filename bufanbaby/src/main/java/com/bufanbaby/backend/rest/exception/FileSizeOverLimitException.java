package com.bufanbaby.backend.rest.exception;

import com.bufanbaby.backend.rest.exception.ErrorResponse.ErrorCode;

@SuppressWarnings("serial")
public class FileSizeOverLimitException extends GenericWebApplicationException {

	public FileSizeOverLimitException(String errorMessage) {
		super(413, ErrorCode.FILE_SIZE_OVER_LIMIT.code, errorMessage,
				ErrorCode.FILE_SIZE_OVER_LIMIT.developerMsg);
	}
}