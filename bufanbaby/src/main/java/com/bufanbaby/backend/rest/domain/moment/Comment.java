package com.bufanbaby.backend.rest.domain.moment;

public class Comment {
	private long id;

	private String content;

	private long dateCreated;

	private long userId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(long dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public String toString() {
		return String.format("Comment [id=%s, content=%s, dateCreated=%s, userId=%s]", id, content,
				dateCreated, userId);
	}
}
