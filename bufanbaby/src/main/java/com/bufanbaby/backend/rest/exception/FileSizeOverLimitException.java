package com.bufanbaby.backend.rest.exception;

@SuppressWarnings("serial")
public class FileSizeOverLimitException extends GenericWebApplicationException {

	public FileSizeOverLimitException(String errorMessage) {
		super(400, "BB4002", errorMessage, "The uploaded file size is over limit");
	}
}