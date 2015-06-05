package com.bufanbaby.backend.rest.domain.moment;

public class Comment {

	/**
	 * The moment id that the comment belongs to
	 */
	private long momentId;

	/**
	 * The user id of the commentor
	 */
	private long userId;

	/**
	 * The creator of this comment
	 */
	private String commentor;

	/**
	 * The user id that the comment is replied to
	 */
	private long replyToUserId;

	/**
	 * The username that the comment is replied to
	 */
	private String replyToUsername;

	/**
	 * The message of this comment
	 */
	private String message;

	/**
	 * The time that the comment is created
	 */
	private long timeCreated;

	public Comment() {
	}

	public Comment(long momentId, long userId, String commentor, long replyToUserId,
			String replyToUsername, String message, long timeCreated) {
		this.momentId = momentId;
		this.userId = userId;
		this.commentor = commentor;
		this.replyToUserId = replyToUserId;
		this.replyToUsername = replyToUsername;
		this.message = message;
		this.timeCreated = timeCreated;
	}

	public long getMomentId() {
		return momentId;
	}

	public Comment setMomentId(long momentId) {
		this.momentId = momentId;
		return this;
	}

	public long getUserId() {
		return userId;
	}

	public Comment setUserId(long userId) {
		this.userId = userId;
		return this;
	}

	public String getCommentor() {
		return commentor;
	}

	public Comment setCommentor(String commentor) {
		this.commentor = commentor;
		return this;
	}

	public long getReplyToUserId() {
		return replyToUserId;
	}

	public Comment setReplyToUserId(long replyToUserId) {
		this.replyToUserId = replyToUserId;
		return this;
	}

	public String getReplyToUsername() {
		return replyToUsername;
	}

	public Comment setReplyToUsername(String replyToUsername) {
		this.replyToUsername = replyToUsername;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Comment setMessage(String message) {
		this.message = message;
		return this;
	}

	public long getTimeCreated() {
		return timeCreated;
	}

	public Comment setTimeCreated(long timeCreated) {
		this.timeCreated = timeCreated;
		return this;
	}

	@Override
	public String toString() {
		return String
				.format("Comment [momentId=%s, userId=%s, commentor=%s, replyToUserId=%s, replyToUsername=%s, message=%s, timeCreated=%s]",
						momentId, userId, commentor, replyToUserId, replyToUsername, message,
						timeCreated);
	}

}
