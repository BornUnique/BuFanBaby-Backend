package com.bufanbaby.backend.rest.resources.auth;

public class SignUpResponse {
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String authToken;

	public SignUpResponse() {
	}

	public SignUpResponse(String username, String firstName,
			String lastName, String email, String authToken) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.authToken = authToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	@Override
	public String toString() {
		return String.format(
				"SignUpResponse [username=%s, firstName=%s, lastName=%s, email=%s, authToken=%s]",
				username, firstName, lastName, email, authToken);
	}
}
