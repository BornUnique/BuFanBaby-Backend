package com.bufanbaby.backend.rest.exception;

import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;

@SuppressWarnings("serial")
public class BadClientCredentialsException extends ClientAuthenticationException {

	public BadClientCredentialsException() {
		super("Bad client credentials"); // Don't reveal source of error
	}

	@Override
	public int getHttpErrorCode() {
		return 401;
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "invalid_client";
	}
}
