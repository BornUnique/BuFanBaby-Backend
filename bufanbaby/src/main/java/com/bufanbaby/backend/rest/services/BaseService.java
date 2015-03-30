package com.bufanbaby.backend.rest.services;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.bufanbaby.backend.rest.exception.ValidationException;

public abstract class BaseService {

	@Autowired
	private Validator validator;

	protected void validate(Object request) {
		Set<? extends ConstraintViolation<?>> constraintViolations = validator.validate(request);
		if (constraintViolations.size() > 0) {
			throw new ValidationException(constraintViolations);
		}
	}
}
