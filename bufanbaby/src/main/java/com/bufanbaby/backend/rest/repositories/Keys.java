package com.bufanbaby.backend.rest.repositories;

/**
 * The keys are used to manipulate the data in Redis.
 */
public abstract class Keys {

	public static String nextUserId() {
		return "global:next_user_id";
	}

	public static String nextMomentId() {
		return "global:next_moment_id";
	}

	// Set: all users ids
	// users -> {1234, 5678, 90}
	public static String users() {
		return "users";
	}

	// Hash: store user info
	// user:{1234} -> {name: laiyan, age: 20}
	public static String userId(String id) {
		return "user:" + id;
	}

	// user:{laiyan} ->
	public static String username(String username) {
		return "user:" + username + ":id";
	}

}
