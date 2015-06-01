package com.bufanbaby.backend.rest.resources;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.bufanbaby.backend.rest.domain.auth.User;
import com.bufanbaby.backend.rest.exception.ValidationException;
import com.bufanbaby.backend.rest.services.auth.UserService;

public class AbstractResource {

	@Autowired
	private UserService userService;

	@Autowired
	private Validator validator;

	@Autowired
	private MessageSource messageSource;

	protected void validate(Object request) {
		Set<? extends ConstraintViolation<?>> constraintViolations = validator.validate(request);
		if (constraintViolations.size() > 0) {
			String validationErrorMessage = messageSource.getMessage(
					"bufanbaby.request.validation.error", null, LocaleContextHolder.getLocale());
			throw new ValidationException(validationErrorMessage, constraintViolations);
		}
	}

	/**
	 * Get the current user based on the security context.
	 * 
	 * @param securityContext
	 * @return the user
	 */
	protected User getCurrentUser(SecurityContext securityContext) {
		OAuth2Authentication currentPrincipal = (OAuth2Authentication) securityContext
				.getUserPrincipal();
		Object principal = currentPrincipal.getUserAuthentication().getPrincipal();
		User user = null;
		if (principal instanceof User) {
			user = (User) principal;
		} else {
			user = userService.loadUserById(((String) principal));
		}
		return user;
	}
}
