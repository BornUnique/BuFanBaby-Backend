package com.bufanbaby.backend.rest.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bufanbaby.backend.rest.exception.ErrorResponse;

@Provider
public class ThrowableMapper implements ExceptionMapper<Throwable> {
	private static final Logger logger = LoggerFactory.getLogger(ThrowableMapper.class);

	@Override
	public Response toResponse(Throwable t) {
		logger.error("Server Failure", t);

		ErrorResponse response = new ErrorResponse();
		response.setErrorCode("BB5001");
		response.setApplicationMessage("Server Failure: please check if server is working");
		response.setConsumerMessage("Server Failure: try it again or contact Administrator");

		return Response.status(500)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.entity(response)
				.build();
	}
}
