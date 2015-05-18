package com.bufanbaby.backend.rest.exception;

@SuppressWarnings("serial")
public class MediaTypeNotAllowedException extends GenericWebApplicationException {

	public MediaTypeNotAllowedException() {
		super(400, "Bad Media Type", "The media type of the uploaded file is not allowed");
	}
}