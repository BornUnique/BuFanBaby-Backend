package com.bufanbaby.backend.rest.exception;

@SuppressWarnings("serial")
public class FileOverSizeException extends GenericWebApplicationException {

	public FileOverSizeException() {
		super(400, "File too big", "The uploaded file is too big: max=15M");
	}
}