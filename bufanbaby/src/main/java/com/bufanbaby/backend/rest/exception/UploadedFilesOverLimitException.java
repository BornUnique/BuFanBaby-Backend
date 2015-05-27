package com.bufanbaby.backend.rest.exception;

@SuppressWarnings("serial")
public class UploadedFilesOverLimitException extends GenericWebApplicationException {

	public UploadedFilesOverLimitException(String errorMessage) {
		super(400, "BB4003", errorMessage, "Uploaded files are over limit");
	}
}
