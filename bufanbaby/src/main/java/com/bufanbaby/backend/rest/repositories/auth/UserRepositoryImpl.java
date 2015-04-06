package com.bufanbaby.backend.rest.repositories.auth;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.data.redis.support.collections.RedisList;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.bufanbaby.backend.rest.domain.auth.Gender;
import com.bufanbaby.backend.rest.domain.auth.User;
import com.bufanbaby.backend.rest.repositories.RedisKeys;

@Component
// @Profile("default")
public class UserRepositoryImpl implements UserRepository {

	private final StringRedisTemplate template;
	private final ValueOperations<String, String> valueOps;
	private final RedisAtomicLong userIdCounter;
	private RedisList<String> users;

	@Autowired
	public UserRepositoryImpl(StringRedisTemplate template) {
		this.template = template;
		this.valueOps = template.opsForValue();

		// users: List of active users
		// users -> {1, 2, 3}
		this.users = new DefaultRedisList<String>(RedisKeys.users(), template);

		// Global user id (uid) counter
		// "global:uid" -> 1
		this.userIdCounter = new RedisAtomicLong(RedisKeys.globalUid(),
				template.getConnectionFactory());
	}

	/**
	 * Add a new user based on the username and password.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the user's hashed password
	 * @return the authentication token
	 */
	@Override
	public String saveUser(User user) {
		// Increment and get global user id: 1, 2, 3...
		String uid = String.valueOf(userIdCounter.incrementAndGet());

		// uid to User Info
		// uid:1 -> {"username": "xinxin", "password": "*****"}
		BoundHashOperations<String, String, String> userOps = template.boundHashOps(RedisKeys
				.uid(uid));
		userOps.put("username", user.getUsername());
		userOps.put("password", user.getPassword());
		userOps.put("email", user.getEmail());
		userOps.put("firstName", user.getFirstName() == null ? "" : user.getFirstName());
		userOps.put("lastName", user.getLastName() == null ? "" : user.getLastName());
		userOps.put("gender", user.getGender().name());
		userOps.put(
				"authority",
				user.getAuthorities().toArray(new GrantedAuthority[user.getAuthorities().size()])[0]
						.getAuthority());

		// username -> uid association
		// user:xinxin:uid -> 1
		valueOps.set(RedisKeys.user(user.getUsername()), uid);

		// email -> uid association
		// email:email@address:uid -> 1
		// valueOps.set(RedisKeys.email(user.getEmail), uid);

		// users -> {1, 2}
		users.addFirst(uid);
		return uid;
	}

	@Override
	public boolean usernameExists(String username) {
		return template.hasKey(RedisKeys.user(username));
	}

	@Override
	public boolean emailExists(String email) {
		return template.hasKey(RedisKeys.email(email));
	}

	@Override
	public void saveAccessToken(String uid, String token) {
		// uid -> oauth access token
		// uid:2:auth -> {3b7b06774554sfsd5af788}
		valueOps.set(RedisKeys.auth(uid), token);

		// auth access token -> uid
		// auth:3b7b06774554sfsd5af788 -> {2}
		valueOps.set(RedisKeys.authKey(token), uid);
	}

	@Override
	public User findUserByUsername(String username) {
		String uid = valueOps.get(RedisKeys.user(username));
		return findUserById(uid);
	}

	@Override
	public User findUserById(String uid) {
		BoundHashOperations<String, String, String> userOps = template.boundHashOps(RedisKeys
				.uid(uid));
		String username = userOps.get("username");
		String hashedPassword = userOps.get("password");
		String email = userOps.get("email");
		String firstName = userOps.get("firstName");
		String lastName = userOps.get("lastName");
		String gender = userOps.get("gender");
		String authority = userOps.get("authority");

		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(hashedPassword);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(Gender.valueOf(gender));
		user.setAuthorities(Collections.singleton(new SimpleGrantedAuthority(authority)));
		return user;
	}
}
