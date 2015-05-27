package com.bufanbaby.backend.rest.resources.auth;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.bufanbaby.backend.rest.domain.auth.User;
import com.bufanbaby.backend.rest.exception.UserNotFoundException;
import com.bufanbaby.backend.rest.resources.AbstractResource;

@Path("/me")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class MeResource extends AbstractResource {

	@Autowired
	private MessageSource messageSource;

	@GET
	public User getUser(final @Context SecurityContext securityContext) {
		User currentUser = getCurrentUser(securityContext);
		if (currentUser == null) {
			throw new UserNotFoundException(messageSource.getMessage("bufanbaby.user.not.found",
					null, LocaleContextHolder.getLocale()));
		}
		return currentUser;
	}
}
