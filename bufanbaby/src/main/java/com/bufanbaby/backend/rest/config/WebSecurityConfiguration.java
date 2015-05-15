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
public class WebSecurityConfiguration {

	@Autowired
	private UserDetailsService userServiceImpl;

	@Autowired
	UserDetailsService clientDetailsUserDetailsService;

	@Configuration
	@Order(1)
	public static class SignUpWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

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
			// "/v1.0/users"
			// "Basic: clientId:secretId" + authenticated() -> security headers
			// (response)
			http
					.requestMatchers().antMatchers(HttpMethod.POST, "/v1.0/users")
					.and()
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/v1.0/users").fullyAuthenticated()
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
	@Order(2)
	public static class WelcomeHtmlWebSecurityConfigurer extends
			WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// "/index.html", "/signup.html":
			// anonymous() (request) -> security headers (response)

			// @formatter:off
			http
			.requestMatchers()
				.antMatchers("/", "/index.html", "/signup.html")
			.and()
			.authorizeRequests()
				.antMatchers("/", "/index.html", "/signup.html").permitAll()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.httpBasic().disable()
				.csrf().disable()
				.logout().disable()
				.rememberMe().disable()
				.formLogin().disable()
				.logout().disable()
				.x509().disable();
			// @formatter:on
		}
	}

	@Configuration
	@Order(4)
	public static class AccountMgmtWebSecurityConfigurer extends
			WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// "/dashboard.html","/forgot_password.html", "/request_email.html",
			// "/reset_password.html","/validate.html"
			// Bearer (request) + hasRole("USER") -> security headers (response)

			// @formatter:off
			http
			.requestMatchers()
				.antMatchers("/dashboard.html","/forgot_password.html", "/request_email.html", 
						"/reset_password.html","/validate.html")
			.and()
			.authorizeRequests()
				.antMatchers("/dashboard.html","/forgot_password.html", "/request_email.html", 
						"/reset_password.html","/validate.html").permitAll()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.httpBasic().disable()
				.csrf().disable()
				.logout().disable()
				.rememberMe().disable()
				.formLogin().disable()
				.logout().disable()
				.x509().disable();
			// @formatter:on
		}
	}

	@Configuration
	@Order(5)
	public static class DenyAllWebSecurityConfigurer extends
			WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests().anyRequest().permitAll()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			    .and()
				.headers().disable()
				.httpBasic().disable()
				.csrf().disable()
				.logout().disable()
				.rememberMe().disable()
				.formLogin().disable()
				.logout().disable()
				.x509().disable();
			// @formatter:on
		}
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Other urls by default use this authentication manager except oauth 2
	// protected urls
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

	// used by /v1.0/users + post http method
	@Bean
	public AuthenticationManager clientAuthenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		// use OAuth2 client details services to load client details
		provider.setUserDetailsService(clientDetailsUserDetailsService);
		List<AuthenticationProvider> providers = new ArrayList<AuthenticationProvider>();
		providers.add(provider);
		AuthenticationManager am = new ProviderManager(providers);
		return am;

	}
}
