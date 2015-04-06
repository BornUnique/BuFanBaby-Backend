package com.bufanbaby.backend.rest.resources.auth;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.bufanbaby.backend.rest.domain.auth.User;
import com.bufanbaby.backend.rest.exception.ValidationException;
import com.bufanbaby.backend.rest.services.auth.UserService;

public class AbstractResource {

	@Autowired
	private UserService userService;

	@Autowired
	private Validator validator;

	protected void validate(Object request) {
		Set<? extends ConstraintViolation<?>> constraintViolations = validator.validate(request);
		if (constraintViolations.size() > 0) {
			throw new ValidationException(constraintViolations);
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
