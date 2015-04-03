package com.bufanbaby.backend.rest.config;


//@Configuration
//@EnableWebSecurity
public class WebSecurityConfiguration {

	// // @Autowired
	// private UserDetailsService userDetailsService;
	//
	// @Override
	// public void configure(WebSecurity web) throws Exception {
	// web.ignoring().antMatchers("/css/**", "/js/**",
	// "/images/**", "/**/favicon.ico");
	// }
	//
	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
	//
//		// @formatter:off
//		  http
//		  		.httpBasic().disable()
//		    	.formLogin().disable()
//		    	.logout().disable()
//		    	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//		    .and()
//	        	.csrf().disable()
//	        .authorizeRequests()
//		        .antMatchers("/index.html", "/signup.html", "/about.html", "/terms.html", "/v1.0/users/**").anonymous()
//		        .antMatchers("/dashboard.html","/forgot_password.html", "/request_email.html", "/reset_password.html","/validate.html").permitAll()
//		        .antMatchers("/admin/**").hasRole("ADMIN")
//		        .anyRequest().authenticated();
//	     // @formatter:on
	// }
	//
	// @Override
	// protected void configure(AuthenticationManagerBuilder auth) throws
	// Exception {
	// }
	//
	// // @Bean
	// public PasswordEncoder passwordEncoder() {
	// return new BCryptPasswordEncoder();
	// }
	//
	// // @Bean
	// public AuthenticationManager userAuthenticationManager() {
	// DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	// provider.setPasswordEncoder(passwordEncoder());
	// provider.setUserDetailsService(userDetailsService);
	// List<AuthenticationProvider> providers = new
	// ArrayList<AuthenticationProvider>();
	// providers.add(provider);
	// AuthenticationManager am = new ProviderManager(providers);
	// return am;
	// }
}
