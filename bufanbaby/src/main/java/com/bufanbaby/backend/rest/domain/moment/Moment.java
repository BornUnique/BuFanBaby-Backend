package com.bufanbaby.backend.rest.domain.moment;

import java.util.List;

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
	 * The message content
	 */
	private String message;

	/**
	 * The epoch millisecond of the created time
	 */
	private long epochMilliCreated;

	/**
	 * The epoch millisecond of the modified time
	 */
	private long epochMilliModified;

	/**
	 * Keep it private
	 */
	private boolean privateScope = true;

	/**
	 * Share it to friend
	 */
	private boolean friendScope = false;

	/**
	 * Share it to world
	 */
	private boolean worldScope = false;

	/**
	 * The tags of the current comment
	 */
	private Tag tag;

	/**
	 * The list of uploaded file metadata
	 */
	private List<FileMetadata> fileMetadatas;

	/**
	 * 1 moment belongs to multiple timelines which matches the tag names. In
	 * other word, each tag name has its own timeline. But the owner has all of
	 * the moments.
	 */
	private List<Timeline> timlines;

	private List<Comment> comments;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<FileMetadata> getFileMetadatas() {
		return fileMetadatas;
	}

	public void setFileMetadatas(List<FileMetadata> fileMetadatas) {
		this.fileMetadatas = fileMetadatas;
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

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<Timeline> getTimlines() {
		return timlines;
	}

	public void setTimlines(List<Timeline> timlines) {
		this.timlines = timlines;
	}

	public boolean isPrivateScope() {
		return privateScope;
	}

	public void setPrivateScope(boolean privateScope) {
		this.privateScope = privateScope;
	}

	public boolean isFriendScope() {
		return friendScope;
	}

	public void setFriendScope(boolean friendScope) {
		this.friendScope = friendScope;
	}

	public boolean isWorldScope() {
		return worldScope;
	}

	public void setWorldScope(boolean worldScope) {
		this.worldScope = worldScope;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
