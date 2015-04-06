package com.bufanbaby.backend.rest.resources.auth;

public class ExistenceResponse {

	private boolean exist;

	public ExistenceResponse(boolean exist) {
		this.setExist(exist);
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}
}
