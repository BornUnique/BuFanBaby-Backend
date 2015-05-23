package com.bufanbaby.backend.rest.domain.moment;

public class FileMetadata {

	/**
	 * The original created time of this file and it is interesting to keep the
	 * file (photo, audio and video) taken-time.
	 */
	private long originalCreatedTime;

	/**
	 * The original file name
	 */
	private String originalName;

	/**
	 * The system generated file name: {epochmillis}.extentsion
	 */
	private String generatedName;

	/**
	 * The relative path to the file stored in the file system and the path
	 * separator is forward slash
	 */
	private String relativePath;

	/**
	 * The media type of the file
	 */
	private String mediaType;

	public long getOriginalCreatedTime() {
		return originalCreatedTime;
	}

	public void setOriginalCreatedTime(long originalCreatedTime) {
		this.originalCreatedTime = originalCreatedTime;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getGeneratedName() {
		return generatedName;
	}

	public void setGeneratedName(String generatedName) {
		this.generatedName = generatedName;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	@Override
	public String toString() {
		return String
				.format("FileMetadata [originalCreatedTime=%s, originalName=%s, generatedName=%s, relativePath=%s, mediaType=%s]",
						originalCreatedTime, originalName, generatedName, relativePath, mediaType);
	}
}
