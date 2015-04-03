package com.bufanbaby.backend.rest.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class MultiWebSecurityConfiguration {

	@Autowired
	private UserDetailsService userServiceImpl;

	@Autowired
	UserDetailsService clientDetailsUserDetailsService;

	@Configuration
	@Order(1)
	public static class UsersWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Autowired
		private AuthenticationManager clientAuthenticationManager;

		@Autowired
		private AuthenticationEntryPoint oAuthAuthenticationEntryPoint;

		@Autowired
		private AccessDeniedHandler oAuthAccessDeniedHandler;

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/css/**", "/js/**",
					"/images/**", "/**/favicon.ico");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// "/index.html", "/signup.html":
			// anonymous() (request) -> security headers (response)

			// "/dashboard.html","/forgot_password.html", "/request_email.html",
			// "/reset_password.html","/validate.html"
			// Bearer (request) + hasRole("USER") -> security headers (response)

			// Protected REST APIs: "/me", "/v1.0/**"
			// Bearer (request) + hasRole("USER") -> security headers (response)

			// "/v1.0/users"
			// "Basic: clientId:secretId" + authenticated() -> security headers
			// (response)

			// "/oauth/token"
			// "Basic: clientId:secretId" + hasRole("USER") -> security headers
			// (response)

			// HttpAuthenticationFilter: use OAuthAuthenticationManager to
			// authenticate the request
			http
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/v1.0/users").fullyAuthenticated()
					.antMatchers("/oauth/token").hasRole("USER")
					.and()
					.exceptionHandling().accessDeniedHandler(oAuthAccessDeniedHandler)
					.and()
					.httpBasic().authenticationEntryPoint(oAuthAuthenticationEntryPoint)
					.and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
					.anonymous().disable()
					.csrf().disable()
					.logout().disable()
					.rememberMe().disable()
					.formLogin().disable()
					.logout().disable()
					.x509().disable();
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.parentAuthenticationManager(clientAuthenticationManager);
		}
	}

	@Configuration
	public static class TokenEndpointWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// All other requests are denied by default

			http
					.authorizeRequests()
					.anyRequest().denyAll();
		}
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// other urls default use this authentication manager
	@Bean
	public AuthenticationManager userAuthenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userServiceImpl);
		List<AuthenticationProvider> providers = new ArrayList<AuthenticationProvider>();
		providers.add(provider);
		AuthenticationManager am = new ProviderManager(providers);
		return am;
	}

	// used by /v1.0/users and "/oauth/token"
	@Bean
	public AuthenticationManager clientAuthenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(clientDetailsUserDetailsService);
		List<AuthenticationProvider> providers = new ArrayList<AuthenticationProvider>();
		providers.add(provider);
		AuthenticationManager am = new ProviderManager(providers);
		return am;
	}

}
