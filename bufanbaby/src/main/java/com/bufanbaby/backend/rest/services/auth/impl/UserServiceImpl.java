package com.bufanbaby.backend.rest.services.auth.impl;

import java.util.Collections;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
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
	private final DefaultTokenServices tokenServices;
	private final ClientDetailsService clientDetailsService;

	@Autowired
	public UserServiceImpl(final UserRepository userRepository, PasswordEncoder passwordEncoder,
			DefaultTokenServices tokenServices,
			ClientDetailsService clientDetailsService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenServices = tokenServices;
		this.clientDetailsService = clientDetailsService;
	}

	@Override
	@PermitAll
	public String create(SignUpRequest signUpRequest, SecurityContext sc) {
		String email = signUpRequest.getEmail();
		String username = signUpRequest.getUsername();

		// TODO: normalize username and email
		// String emailAddress =
		// createUserRequest.getUser().getEmailAddress().toLowerCase();

		if (!userRepository.usernameExists(username)) {
			logger.info(
					"User does not already exist in the data store - creating a new user [{}].",
					signUpRequest);

			// Save user into Redis
			String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword());

			User user = new User();
			user.setEmail(email);
			user.setUsername(username);
			user.setFirstName(signUpRequest.getFirstName());
			user.setLastName(signUpRequest.getLastName());
			user.setPassword(hashedPassword);

			Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(
					Role.USER.name()));
			user.setAuthorities(authorities);

			String userId = userRepository.saveUser(user);
			logger.info("Saved new user [{}].", user);

			// Create OAuth2 access token
			OAuth2AccessToken accessToken = createAccessToken(sc, hashedPassword, authorities,
					userId);

			// Save OAuth access token into Redis
			saveAccessToken(userId, accessToken);

			// TODO: Send Email verification

			// return user to create a response
			return accessToken.getValue();
		} else {
			logger.info("Duplicate user found, exception raised with appropriate HTTP response code.");
			throw new DuplicateUserException();
		}
	}

	private OAuth2AccessToken createAccessToken(SecurityContext sc, String hashedPassword,
			Set<GrantedAuthority> authorities, String userId) {
		String clientId = sc.getUserPrincipal().getName();
		UsernamePasswordAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken(
				userId, hashedPassword, authorities);
		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
		OAuth2Request oAuth2Request = new OAuth2Request(null, clientId, authorities, true,
				clientDetails.getScope(), null, null, null, null);

		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,
				usernamePasswordToken);
		return tokenServices.createAccessToken(oAuth2Authentication);
	}

	private void saveAccessToken(String uid, OAuth2AccessToken accessToken) {
		String token = accessToken.getValue();
		userRepository.saveAccessToken(uid, token);
	}

	@Override
	@RolesAllowed("USER")
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		return userRepository.findUserByUsername(username);
	}

	@Override
	@RolesAllowed("USER")
	public User loadUserById(String userId) {
		return userRepository.findUserById(userId);
	}

	@Override
	@PermitAll
	public boolean emailExists(String email) {
		return userRepository.emailExists(email);
	}

	@Override
	@PermitAll
	public boolean usernameExists(String username) {
		return userRepository.usernameExists(username);
	}
}
