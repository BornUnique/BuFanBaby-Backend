package com.bufanbaby.backend.rest.services.validation.impl;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.bufanbaby.backend.rest.exception.ValidationException;
import com.bufanbaby.backend.rest.services.validation.RequestBeanValidator;

@Service
public class RequestBeanValidatorImpl implements RequestBeanValidator {

	@Autowired
	private Validator validator;

	@Autowired
	private MessageSource messageSource;

	@Override
	public void validate(Object request) {
		Set<? extends ConstraintViolation<?>> constraintViolations = validator.validate(request);
		if (constraintViolations.size() > 0) {
			String validationErrorMessage = messageSource.getMessage(
					"bufanbaby.request.validation.error", null, LocaleContextHolder.getLocale());
			throw new ValidationException(validationErrorMessage, constraintViolations);
		}
	}
}
