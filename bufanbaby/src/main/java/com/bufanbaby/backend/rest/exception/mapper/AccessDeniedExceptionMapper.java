package com.bufanbaby.backend.rest.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;

import com.bufanbaby.backend.rest.exception.ErrorResponse;
import com.bufanbaby.backend.rest.exception.ErrorResponse.ErrorCode;

public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {
	private static final Logger logger = LoggerFactory.getLogger(AccessDeniedExceptionMapper.class);

	@Override
	public Response toResponse(AccessDeniedException ex) {
		logger.error("Access Denied", ex);

		ErrorResponse response = new ErrorResponse();
		response.setErrorCode(ErrorCode.UNAUTHORIZED.code);
		response.setApplicationMessage(ErrorCode.UNAUTHORIZED.developerMsg);
		response.setConsumerMessage(Response.Status.UNAUTHORIZED.getReasonPhrase());

		return Response.status(Response.Status.UNAUTHORIZED)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.entity(response)
				.build();
	}

}
