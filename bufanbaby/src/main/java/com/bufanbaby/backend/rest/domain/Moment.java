package com.bufanbaby.backend.rest.domain;

import java.util.Map;
import java.util.Set;

public class Moment {

	private String id;

	private String userId;

	private String comment;

	private long epochMilliCreated;

	private long epochMilliModified;

	private Set<Tag> tags;

	private Map<String, String> files;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Map<String, String> getFiles() {
		return files;
	}

	public void setFiles(Map<String, String> files) {
		this.files = files;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getEpochMilliCreated() {
		return epochMilliCreated;
	}

	public void setEpochMilliCreated(long epochMilliCreated) {
		this.epochMilliCreated = epochMilliCreated;
	}

	public long getEpochMilliModified() {
		return epochMilliModified;
	}

	public void setEpochMilliModified(long epochMilliModified) {
		this.epochMilliModified = epochMilliModified;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
}
