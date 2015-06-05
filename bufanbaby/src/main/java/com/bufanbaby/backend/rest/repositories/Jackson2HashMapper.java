package com.bufanbaby.backend.rest.repositories;

import java.util.Map;

import org.springframework.data.redis.hash.HashMapper;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class Jackson2HashMapper<T> implements HashMapper<T, String, Object> {

	private final ObjectMapper mapper;
	private final JavaType userType;
	private final JavaType mapType = TypeFactory.defaultInstance()
			.constructMapType(Map.class, String.class, Object.class);

	public Jackson2HashMapper(Class<T> type) {
		this(type, new ObjectMapper());
	}

	public Jackson2HashMapper(Class<T> type, ObjectMapper mapper) {
		this.mapper = mapper;
		this.userType = TypeFactory.defaultInstance().constructType(type);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T fromHash(Map<String, Object> hash) {
		return (T) mapper.convertValue(hash, userType);
	}

	@Override
	public Map<String, Object> toHash(T object) {
		return mapper.convertValue(object, mapType);
	}
}
