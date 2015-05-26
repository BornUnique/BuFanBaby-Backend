package com.bufanbaby.backend.rest.config;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class HTMLEscapeObjectMapper implements ContextResolver<ObjectMapper> {

	private final ObjectMapper defaultObjectMapper;

	public HTMLEscapeObjectMapper() {
		defaultObjectMapper = createDefaultMapper();
	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return defaultObjectMapper;
	}

	private static ObjectMapper createDefaultMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.getFactory().setCharacterEscapes(new HTMLEscaper());
		return mapper;
	}
}
