package com.bufanbaby.backend.rest.exception;

import java.util.List;

public class ValidationErrorResponse extends ErrorResponse {

	private List<Violation> violations;

	public List<Violation> getViolations() {
		return violations;
	}

	public void setViolations(List<Violation> violations) {
		this.violations = violations;
	}
}
