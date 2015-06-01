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
	 * The client type of the user when posting this moment. For example:
	 * Android, IOS, Chrome or FireFox etc.
	 */
	private String clientType;

	/**
	 * The feeling when capturing this moment
	 */
	private String feeling;

	/**
	 * The epoch millisecond of the created time
	 */
	private long epochMilliCreated;

	/**
	 * The epoch millisecond of the modified time
	 */
	private long epochMilliModified;

	/**
	 * The shared scope
	 */
	private ShareScope shareScope;

	/**
	 * The tags of the current comment
	 */
	private Tag tag;

	/**
	 * The attached files belong to this moment
	 */
	private int totalAttachedFiles;

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

	public String getFeeling() {
		return feeling;
	}

	public void setFeeling(String feeling) {
		this.feeling = feeling;
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

	public ShareScope getShareScope() {
		return shareScope;
	}

	public void setShareScope(ShareScope shareScope) {
		this.shareScope = shareScope;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public int getTotalAttachedFiles() {
		return totalAttachedFiles;
	}

	public void setTotalAttachedFiles(int totalAttachedFiles) {
		this.totalAttachedFiles = totalAttachedFiles;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	@Override
	public String toString() {
		return String
				.format("Moment [id=%s, userId=%s, clientType=%s, feeling=%s, epochMilliCreated=%s, epochMilliModified=%s, shareScope=%s, tag=%s, totalAttachedFiles=%s, fileMetadatas=%s, timlines=%s, comments=%s]",
						id, userId, clientType, feeling, epochMilliCreated, epochMilliModified,
						shareScope, tag, totalAttachedFiles, fileMetadatas, timlines, comments);
	}
}
