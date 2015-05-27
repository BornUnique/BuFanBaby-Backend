package com.bufanbaby.backend.rest.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.bufanbaby.backend.rest.exception.ErrorResponse;

@Provider
public class DataAccessExceptionMapper implements ExceptionMapper<DataAccessException> {
	private static final Logger logger = LoggerFactory.getLogger(DataAccessExceptionMapper.class);

	@Override
	public Response toResponse(DataAccessException ex) {
		logger.error("Caught Redis Server Failure", ex);

		ErrorResponse response = new ErrorResponse();
		response.setErrorCode("BB5002");
		response.setApplicationMessage("Redis Server Failure: please check if Redis is working");
		response.setConsumerMessage("Server Failure: try it again or contact Administrator");

		return Response.status(500)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.entity(response)
				.build();
	}
}
