package com.bufanbaby.backend.rest.exception.mapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bufanbaby.backend.rest.exception.ErrorResponse;
import com.bufanbaby.backend.rest.exception.ErrorResponse.ErrorCode;

@Provider
public class ThrowableMapper implements ExceptionMapper<Throwable> {
	private static final Logger logger = LoggerFactory.getLogger(ThrowableMapper.class);

	@Override
	public Response toResponse(Throwable t) {
		if (t instanceof WebApplicationException) {
			throw (WebApplicationException) t;
		}

		ErrorResponse response = new ErrorResponse();
		response.setErrorCode(ErrorCode.INTERNAL_SERVER_FAILURE.code);
		response.setApplicationMessage(ErrorCode.INTERNAL_SERVER_FAILURE.developerMsg);
		response.setConsumerMessage("Internal Server Failure: try it again or contact Administrator");

		logger.error(response.toString(), t);

		return Response.status(500)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.entity(response)
				.build();
	}
}
