package com.bufanbaby.backend.rest.exception;

public class ErrorResponse {

	private String errorCode;
	private String consumerMessage;
	private String applicationMessage;

	public ErrorResponse() {
	}

	public ErrorResponse(String errorCode, String consumerMessage, String applicationMessage) {
		this.errorCode = errorCode;
		this.consumerMessage = consumerMessage;
		this.applicationMessage = applicationMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getConsumerMessage() {
		return consumerMessage;
	}

	public void setConsumerMessage(String consumerMessage) {
		this.consumerMessage = consumerMessage;
	}

	public String getApplicationMessage() {
		return applicationMessage;
	}

	public void setApplicationMessage(String applicationMessage) {
		this.applicationMessage = applicationMessage;
	}

	public static enum ErrorCode {
		REQUEST_VALIDATION_ERROR("BB400",
				"The data passed in the request was invalid. Please check and resubmit."),

		UNSUPPORTED_FILE_TYPE("BB4015",
				"The uploaded file media type is not supported. Please check and resubmit."),

		FILE_SIZE_OVER_LIMIT("BB4013",
				"The uploaded file size is too large. Please check and resubmit."),

		UPLOADED_FILES_OVER_LIMIT("BB4003",
				"The total uploaded Files is too much. Please check and resubmit."),

		USER_NOT_FOUND("BB4004", "No user can be found for that ID."),

		USER_ALREADY_EXISTS("BB4009", "An attempt was made to create a user that already exists."),

		INTERNAL_SERVER_FAILURE("BB5001", "Internal Server Failure"),

		INTERNAL_REDIS_SERVER_FAILURE("BB5002", "Internal Redis Server Failure"),

		INTERNAL_FILE_IO_OPERATION_FAILURE("BB5003", "Internal File I/O Operation Failure");

		public String code;
		public String developerMsg;

		private ErrorCode(String code, String developerMsg) {
			this.code = code;
			this.developerMsg = developerMsg;
		}
	}

	@Override
	public String toString() {
		return String.format(
				"ErrorResponse [errorCode=%s, consumerMessage=%s, applicationMessage=%s]",
				errorCode, consumerMessage, applicationMessage);
	}

}