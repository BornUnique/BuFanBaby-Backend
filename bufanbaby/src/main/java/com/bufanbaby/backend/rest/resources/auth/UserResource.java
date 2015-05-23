package com.bufanbaby.backend.rest.resources.auth;

import java.net.URI;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bufanbaby.backend.rest.resources.AbstractResource;
import com.bufanbaby.backend.rest.services.auth.UserService;

/**
 * This class represents User Resources
 */
@Path("/users")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UserResource extends AbstractResource {
	private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

	@Autowired
	private UserService userService;

	@POST
	public Response signUp(@NotNull SignUpRequest request, @Context SecurityContext sc,
			@Context UriInfo uriInfo) {
		logger.info("Validating user sign up request");
		validate(request);

		String accessToken = userService.create(request, sc);

		SignUpResponse response = new SignUpResponse();
		response.setUsername(request.getUsername());
		response.setEmail(request.getEmail());
		response.setFirstName(request.getFirstName());
		response.setLastName(request.getLastName());
		response.setAuthToken(accessToken);
		URI location = uriInfo.getAbsolutePathBuilder()
				.path(request.getUsername()).build();

		return Response.created(location).entity(response).build();
	}

	@GET
	public Response checkEmail(@QueryParam("email") @Email String email,
			@Context SecurityContext sc, @Context UriInfo uriInfo) {
		boolean exists = userService.emailExists(email);
		return Response.ok().entity(new ExistenceResponse(exists)).build();
	}

	@GET
	@Path("{username}")
	public Response checkUsername(@PathParam("username") String username,
			@Context SecurityContext sc, @Context UriInfo uriInfo) {
		boolean exists = userService.emailExists(username);
		return Response.ok().entity(new ExistenceResponse(exists)).build();
	}
}
