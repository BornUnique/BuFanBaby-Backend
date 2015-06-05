package com.bufanbaby.backend.rest.domain.moment;

import java.util.List;
import java.util.Set;

public class Moment {

	/**
	 * The moment identifier
	 */
	private long id;

	/**
	 * The user's identifier
	 */
	private long userId;

	/**
	 * The details of the moment
	 */
	private Content content;

	/**
	 * The tags belongs to this moment
	 */
	private Set<String> tags;

	/**
	 * The list of uploaded file metadata
	 */
	private List<FileMetadata> fileMetadatas;

	/**
	 * Comments belong to this moment
	 */
	private List<Comment> comments;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public List<FileMetadata> getFileMetadatas() {
		return fileMetadatas;
	}

	public void setFileMetadatas(List<FileMetadata> fileMetadatas) {
		this.fileMetadatas = fileMetadatas;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return String.format(
				"Moment [id=%s, userId=%s, content=%s, tags=%s, fileMetadatas=%s, comments=%s]",
				id, userId, content, tags, fileMetadatas, comments);
	}

}
