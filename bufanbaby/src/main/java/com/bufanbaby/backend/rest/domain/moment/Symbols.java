package com.bufanbaby.backend.rest.domain.moment;

public enum Symbols {
	FORWARD_SLASH('/');

	public final char symbol;

	private Symbols(char c) {
		this.symbol = c;
	}
}
