package com.bufanbaby.backend.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class OAuth2ServerConfiguration {

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.requestMatchers().antMatchers("/me")
				.and()
			        .authorizeRequests()
			        .antMatchers("/me").hasRole("User")
			     .and().logout().disable();
			// @formatter:on
		}
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends
			AuthorizationServerConfigurerAdapter {

		@Autowired
		private TokenStore tokenStore;

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			// @formatter:off
			clients.inMemory()
					.withClient("353b302c44574f565045687e534e7d6a")
						.secret("286924697e615a672a646a493545646c")
				 		.authorizedGrantTypes("password", "refresh_token")
				 		.authorities("ROLE_WEB")
				 		.scopes("read", "write")
				 		.accessTokenValiditySeconds(30000)
				 		.refreshTokenValiditySeconds(300000)
			 		.and()
					.withClient("7b5a38705d7b3562655925406a652e32")
						.secret("655f523128212d6e70634446224c2a48")
				 		.authorizedGrantTypes("password", "refresh_token")
				 		.authorities("ROLE_ANDROID")
				 		.scopes("read", "write")
				 		.accessTokenValiditySeconds(30000)
				 		.refreshTokenValiditySeconds(300000)
			 		.and()
					.withClient("5e572e694e4d61763b567059273a4d3d")
						.secret("316457735c4055642744596b302e2151")
				 		.authorizedGrantTypes("password", "refresh_token")
				 		.authorities("ROLE_IOS")
				 		.scopes("read", "write")
				 		.accessTokenValiditySeconds(30000)
				 		.refreshTokenValiditySeconds(300000);
			// @formatter:on
		}

		@Bean
		public TokenStore tokenStore() {
			return new InMemoryTokenStore();
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.tokenStore(tokenStore);
		}
	}

}
