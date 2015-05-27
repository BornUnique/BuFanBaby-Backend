package com.bufanbaby.backend.rest.exception;

@SuppressWarnings("serial")
public class UnsupportedFileTypeException extends GenericWebApplicationException {

	public UnsupportedFileTypeException(String errorMessage) {
		super(400, "BB4001", errorMessage,
				"The media type of the uploaded file is not supported");
	}
}