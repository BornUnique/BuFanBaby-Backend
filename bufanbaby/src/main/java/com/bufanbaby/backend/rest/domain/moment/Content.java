package com.bufanbaby.backend.rest.domain.moment;

public class Content {

	/**
	 * The client type of the user when posting this moment. For example:
	 * Android, IOS, Chrome or FireFox etc.
	 */
	private String clientType;

	/**
	 * The geographic location when capturing the moment.
	 */
	private String geographicLocation;

	/**
	 * The feeling when capturing this moment
	 */
	private String feeling;

	/**
	 * The epoch millisecond of the created time
	 */
	private long timeCreated;

	/**
	 * The epoch millisecond of the modified time
	 */
	private long timeModified;

	/**
	 * The share the moment to
	 */
	private ShareWith shareWith = ShareWith.JUST_ME;

	/**
	 * The likes of this moment
	 */
	private long likes;

	/**
	 * The number of the reposts of this moment
	 */
	private long reposts;

	/**
	 * The numbers of the oments of this moment
	 */
	private long comments;

	/**
	 * The attached files amount belong to this moment
	 */
	private int attachedFileSize;

	public Content() {
	}

	public ShareWith getShareWith() {
		return shareWith;
	}

	public Content setShareWith(ShareWith shareWith) {
		this.shareWith = shareWith;
		return this;
	}

	public String getClientType() {
		return clientType;
	}

	public Content setClientType(String clientType) {
		this.clientType = clientType;
		return this;
	}

	public String getFeeling() {
		return feeling;
	}

	public Content setFeeling(String feeling) {
		this.feeling = feeling;
		return this;
	}

	public long getTimeCreated() {
		return timeCreated;
	}

	public Content setTimeCreated(long timeCreated) {
		this.timeCreated = timeCreated;
		return this;
	}

	public long getTimeModified() {
		return timeModified;
	}

	public Content setTimeModified(long timeModified) {
		this.timeModified = timeModified;
		return this;
	}

	public int getAttachedFileSize() {
		return attachedFileSize;
	}

	public Content setAttachedFileSize(int attachedFileSize) {
		this.attachedFileSize = attachedFileSize;
		return this;
	}

	public long getLikes() {
		return likes;
	}

	public Content setLikes(long likes) {
		this.likes = likes;
		return this;
	}

	public long getReposts() {
		return reposts;
	}

	public Content setReposts(long reposts) {
		this.reposts = reposts;
		return this;
	}

	public long getComments() {
		return comments;
	}

	public Content setComments(long comments) {
		this.comments = comments;
		return this;
	}

	public String getGeographicLocation() {
		return geographicLocation;
	}

	public Content setGeographicLocation(String geographicLocation) {
		this.geographicLocation = geographicLocation;
		return this;
	}

	@Override
	public String toString() {
		return String
				.format("Content [clientType=%s, geographicLocation=%s, feeling=%s, timeCreated=%s, timeModified=%s, shareWith=%s, likes=%s, reposts=%s, comments=%s, attachedFileSize=%s]",
						clientType, geographicLocation, feeling, timeCreated, timeModified,
						shareWith, likes, reposts, comments, attachedFileSize);
	}

}
