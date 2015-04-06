package com.bufanbaby.backend.rest.services.auth;

import javax.ws.rs.core.SecurityContext;

import com.bufanbaby.backend.rest.domain.auth.User;
import com.bufanbaby.backend.rest.resources.auth.SignUpRequest;

public interface UserService {

	String create(SignUpRequest signUpRequest, SecurityContext sc);

	User loadUserById(String userId);

	boolean emailExists(String email);

	boolean usernameExists(String username);

}
