package com.bufanbaby.backend.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**",
				"/images/**", "/**/favicon.ico");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
//		  http
//		    .formLogin().disable()
//		    .logout().disable()
//		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//		    .and()
//	        .csrf().disable()
//	        .authorizeRequests()
//	        .antMatchers("/index.html", "/signup.html", "/about.html", "/terms.html").permitAll()
//	        .antMatchers("/dashboard.html","/forgot_password.html", "/request_email.html", "/reset_password.html","/validate.html" ).hasRole("USER")
//	        .antMatchers("/admin/**").hasRole("ADMIN");
//	        .anyRequest().authenticated();
	     // @formatter:on
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
