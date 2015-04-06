package com.bufanbaby.backend.rest.repositories.auth;

import com.bufanbaby.backend.rest.domain.auth.User;

public interface UserRepository {

	boolean usernameExists(String username);

	boolean emailExists(String email);

	void saveAccessToken(String uid, String token);

	String saveUser(User user);

	User findUserByUsername(String username);

	User findUserById(String uid);

}
