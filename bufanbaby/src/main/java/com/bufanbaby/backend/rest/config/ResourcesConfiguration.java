package com.bufanbaby.backend.rest.config;

import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.stereotype.Component;

/**
 * This is the bootstrap class for Jersey application. Spring component
 * annotation is used to hook into Spring Boot auto-configuration feature.
 * 
 * See: spring-boot-starter-jersey
 * http://docs.spring.io/spring-boot/docs/1.2.2.RELEASE/reference/htmlsingle
 * /#boot-features-jersey
 */
@Component
@ApplicationPath("/v1.0")
public class ResourcesConfiguration extends ResourceConfig {
	private final static Logger logger = Logger.getLogger(ResourcesConfiguration.class.getName());

	public ResourcesConfiguration() {

		packages("com.bufanbaby.backend.rest.resources");

		register(RequestContextFilter.class);

		register(new LoggingFilter(logger, true));

		register(JacksonFeature.class);
	}
}
