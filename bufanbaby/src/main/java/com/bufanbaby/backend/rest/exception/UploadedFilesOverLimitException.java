package com.bufanbaby.backend.rest.exception;

@SuppressWarnings("serial")
public class UploadedFilesOverLimitException extends GenericWebApplicationException {

	public UploadedFilesOverLimitException() {
		super(400, "Over Limit 9", "Too much files uploaded: max=9");
	}
}
