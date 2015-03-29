package com.bufanbaby.backend.rest.repositories.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.data.redis.support.collections.RedisList;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.bufanbaby.backend.rest.domain.auth.User;
import com.bufanbaby.backend.rest.repositories.RedisKeys;

@Component
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
	public String add(User user) {
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

		// TODO: support multiple roles currently only one authority
		userOps.put(
				"authority",
				user.getAuthorities().toArray(new GrantedAuthority[user.getAuthorities().size()])[0]
						.getAuthority());

		// username -> uid association
		// user:xinxin:uid -> 1
		valueOps.set(RedisKeys.user(user.getUsername()), uid);

		users.addFirst(user.getUsername());
		return uid;
	}

	@Override
	public boolean usernameExists(String username) {
		return template.hasKey(RedisKeys.user(username));
	}

	@Override
	public boolean emailExists(String email) {
		return template.hasKey(RedisKeys.user(email));
	}
}
