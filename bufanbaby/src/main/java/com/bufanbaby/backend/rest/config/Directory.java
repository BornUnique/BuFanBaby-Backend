package com.bufanbaby.backend.rest.config;

public enum Directory {

	DOCUMENTS("documents"), IMAGES("images"), AUDIOS("audios"), VIDEOS("videos");

	public String name;

	private Directory(String name) {
		this.name = name;
	}
}
