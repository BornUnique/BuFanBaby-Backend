package com.bufanbaby.backend.rest.resources.auth;

import java.net.URI;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bufanbaby.backend.rest.domain.auth.User;
import com.bufanbaby.backend.rest.services.auth.UserService;

/**
 * This class represents User Resources
 */
@Path("/users")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UserResource extends AbstractResource {

	private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

	@Context
	private UriInfo uriInfo;

	@Autowired
	private UserService userService;

	@POST
	public Response signUp(@NotNull SignUpRequest signUpRequest) {

		logger.info("Validating user sign up request");
		validate(signUpRequest);
		User user = userService.create(signUpRequest);

		SignUpResponse response = new SignUpResponse();
		response.setUsername(user.getUsername());
		response.setEmail(user.getEmail());
		response.setFirstName(user.getFirstName());
		response.setLastName(user.getLastName());

		// TODO: currently bypass OAuth token generation
		response.setAuthToken(UUID.randomUUID().toString());

		URI location = uriInfo.getAbsolutePathBuilder()
				.path(user.getUsername()).build();
		return Response.created(location).entity(response).build();
	}

}
