package com.bufanbaby.backend.rest.config;

import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.stereotype.Component;

import com.bufanbaby.backend.rest.exception.mapper.DataAccessExceptionMapper;
import com.bufanbaby.backend.rest.exception.mapper.ThrowableMapper;

/**
 * This is the bootstrap class for Jersey application. Spring component
 * annotation is used to hook into Spring Boot auto-configuration feature
 * because Boot will look for a bean of ResourceConfig type.
 */
@Component
@ApplicationPath("/v1.0")
public class ResourcesConfiguration extends ResourceConfig {
	private final static Logger logger = Logger.getLogger("JerseyLoggingFilter");

	public ResourcesConfiguration() {

		packages("com.bufanbaby.backend.rest.resources");

		register(RequestContextFilter.class);

		register(new LoggingFilter(logger, true));

		register(JacksonFeature.class);

		// register html escaper of Jackson2
		register(HTMLEscapeObjectMapper.class);

		// register general DataAccessException mapper for Redis
		register(DataAccessExceptionMapper.class);

		// Catch all exception mapper
		register(ThrowableMapper.class);

		register(MultiPartFeature.class);
	}
}
