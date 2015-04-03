package com.bufanbaby.backend.rest.services.auth.impl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bufanbaby.backend.rest.domain.auth.Role;
import com.bufanbaby.backend.rest.domain.auth.User;
import com.bufanbaby.backend.rest.exception.DuplicateUserException;
import com.bufanbaby.backend.rest.repositories.auth.UserRepository;
import com.bufanbaby.backend.rest.resources.auth.SignUpRequest;
import com.bufanbaby.backend.rest.services.auth.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(final UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User create(SignUpRequest signUpRequest) {
		String email = signUpRequest.getEmail();
		String username = signUpRequest.getUsername();

		if (!userRepository.usernameExists(username)) {
			logger.info(
					"User does not already exist in the data store - creating a new user [{}].",
					signUpRequest);

			String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword());

			User user = new User();
			user.setEmail(email);
			user.setUsername(username);
			user.setFirstName(signUpRequest.getFirstName());
			user.setLastName(signUpRequest.getLastName());
			user.setPassword(hashedPassword);

			GrantedAuthority authority = new SimpleGrantedAuthority(Role.USER.name());
			Set<GrantedAuthority> authorities = new HashSet<>();
			authorities.add(authority);
			user.setAuthorities(authorities);

			// TODO: what should be returned from redis repository
			String userId = userRepository.add(user);
			logger.info("Created new user [{}].", userId);
			return user;
		} else {
			logger.info("Duplicate user located, exception raised with appropriate HTTP response code.");
			throw new DuplicateUserException();
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}
}
