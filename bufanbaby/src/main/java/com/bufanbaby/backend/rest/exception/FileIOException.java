package com.bufanbaby.backend.rest.exception;

@SuppressWarnings("serial")
public class FileIOException extends GenericWebApplicationException {

	public FileIOException(String errorMessage) {
		super(500, "BB5003", errorMessage,
				"Server Failure: An attempt was made to save the file failed");
	}
}
