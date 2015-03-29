package com.bufanbaby.backend.rest.repositories.auth;

import com.bufanbaby.backend.rest.domain.auth.User;

public interface UserRepository {

	boolean usernameExists(String username);

	boolean emailExists(String email);

	String add(User user);

}
