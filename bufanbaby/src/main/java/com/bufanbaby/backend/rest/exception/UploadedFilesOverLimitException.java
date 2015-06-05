package com.bufanbaby.backend.rest.exception;

import com.bufanbaby.backend.rest.exception.ErrorResponse.ErrorCode;

@SuppressWarnings("serial")
public class UploadedFilesOverLimitException extends GenericWebApplicationException {

	public UploadedFilesOverLimitException(String errorMessage) {
		super(400, ErrorCode.UPLOADED_FILES_OVER_LIMIT.code, errorMessage,
				ErrorCode.UPLOADED_FILES_OVER_LIMIT.developerMsg);
	}
}
