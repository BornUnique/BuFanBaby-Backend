package com.bufanbaby.backend.rest.config;

import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

//@Configuration
public class OAuth2Configuration {

	// @Bean
	// @Profile("dev")
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}
}
