package com.bufanbaby.backend.rest.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

	@Override
	@SuppressWarnings("rawtypes")
	public AccessDecisionManager accessDecisionManager() {
		List<AccessDecisionVoter> decisionVoters = new ArrayList<>();
		decisionVoters.add(roleVoter());
		return new UnanimousBased(decisionVoters);
	}

	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER > ROLE_GUEST");
		return roleHierarchy;
	}

	@Bean
	@SuppressWarnings("rawtypes")
	public AccessDecisionVoter roleVoter() {
		return new HierarchicalJsr250Voter(roleHierarchy());
	}
}