package com.bufanbaby.backend.rest.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class OAuth2ServerConfiguration {

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		@Autowired
		private TokenStore tokenStore;

		@Autowired
		private DefaultTokenServices tokenServices;

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			resources.tokenServices(tokenServices);
			resources.tokenStore(tokenStore);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.requestMatchers().antMatchers("/me", "/v1.0/**")
				.and()
			        .authorizeRequests()
			        .antMatchers("/me", "/v1.0/**").hasRole("User")
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

		@Autowired
		private DefaultTokenServices tokenServices;

		@Autowired
		private ClientDetailsService clientDetailsService;

		@Autowired
		private AuthenticationManager oAuthClientAuthenticationManager;

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.withClientDetails(clientDetailsService);
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.tokenStore(tokenStore);
			endpoints.tokenServices(tokenServices);
			endpoints.authenticationManager(oAuthClientAuthenticationManager);
		}
	}

	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

	// ClientDetails loadClientByClientId(String clientId)
	@Bean
	public ClientDetailsService clientDetailsService() throws Exception {
		InMemoryClientDetailsService clientDetailsService = new InMemoryClientDetailsService();
		Map<String, ClientDetails> clientDetailsStore = new HashMap<>(3);

		// web client
		BaseClientDetails webClient = new BaseClientDetails();
		webClient.setClientId("353b302c44574f565045687e534e7d6a");
		webClient.setClientSecret("286924697e615a672a646a493545646c");

		List<String> webScopes = new ArrayList<String>(2);
		webScopes.add("read");
		webScopes.add("write");
		webClient.setScope(webScopes);

		List<GrantedAuthority> webAuthorities = new ArrayList<>(1);
		webAuthorities.add(new SimpleGrantedAuthority("ROLE_WEB"));
		webClient.setAuthorities(webAuthorities);

		webClient.setAccessTokenValiditySeconds(30000);
		webClient.setRefreshTokenValiditySeconds(300000);

		clientDetailsStore.put("353b302c44574f565045687e534e7d6a", webClient);
		clientDetailsService.setClientDetailsStore(clientDetailsStore);

		// android client
		BaseClientDetails androidClient = new BaseClientDetails();
		androidClient.setClientId("7b5a38705d7b3562655925406a652e32");
		androidClient.setClientSecret("655f523128212d6e70634446224c2a48");

		List<String> androidScopes = new ArrayList<String>(2);
		androidScopes.add("read");
		androidScopes.add("write");
		androidClient.setScope(androidScopes);

		List<GrantedAuthority> androidAuthorities = new ArrayList<>(1);
		androidAuthorities.add(new SimpleGrantedAuthority("ROLE_ANDROID"));
		androidClient.setAuthorities(androidAuthorities);

		androidClient.setAccessTokenValiditySeconds(30000);
		androidClient.setRefreshTokenValiditySeconds(300000);

		clientDetailsStore.put("7b5a38705d7b3562655925406a652e32", androidClient);
		clientDetailsService.setClientDetailsStore(clientDetailsStore);

		// ios client
		BaseClientDetails iosClient = new BaseClientDetails();
		iosClient.setClientId("5e572e694e4d61763b567059273a4d3d");
		iosClient.setClientSecret("316457735c4055642744596b302e2151");

		List<String> iosScopes = new ArrayList<String>(2);
		iosScopes.add("read");
		iosScopes.add("write");
		iosClient.setScope(iosScopes);

		List<GrantedAuthority> iosAuthorities = new ArrayList<>(1);
		iosAuthorities.add(new SimpleGrantedAuthority("ROLE_IOS"));
		iosClient.setAuthorities(iosAuthorities);

		iosClient.setAccessTokenValiditySeconds(30000);
		iosClient.setRefreshTokenValiditySeconds(300000);

		clientDetailsStore.put("5e572e694e4d61763b567059273a4d3d", iosClient);
		clientDetailsService.setClientDetailsStore(clientDetailsStore);

		return clientDetailsService;
	}

	// UserDetails loadUserByUsername(String username)
	@Bean
	public UserDetailsService clientDetailsUserDetailsService() throws Exception {
		ClientDetailsUserDetailsService cduds = new ClientDetailsUserDetailsService(
				clientDetailsService());
		return cduds;
	}

	@Bean
	public AuthenticationManager oAuthClientAuthenticationManager() throws Exception {
		OAuth2AuthenticationManager oaam = new OAuth2AuthenticationManager();
		ClientDetailsService clientDetailsService = clientDetailsService();
		oaam.setClientDetailsService(clientDetailsService);
		DefaultTokenServices tokenServices = tokenServices();
		tokenServices.setClientDetailsService(clientDetailsService);
		oaam.setTokenServices(tokenServices);
		return oaam;
	}

	@Bean
	public DefaultTokenServices tokenServices() throws Exception {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(tokenStore());
		tokenServices.setSupportRefreshToken(true);
		return tokenServices;
	}

	@Bean
	public AuthenticationEntryPoint oAuthAuthenticationEntryPoint() {
		OAuth2AuthenticationEntryPoint ep = new OAuth2AuthenticationEntryPoint();
		ep.setTypeName("Basic");
		return ep;
	}

	@Bean
	public AccessDeniedHandler oAuthAccessDeniedHandler() {
		OAuth2AccessDeniedHandler adh = new OAuth2AccessDeniedHandler();
		return adh;
	}

}
