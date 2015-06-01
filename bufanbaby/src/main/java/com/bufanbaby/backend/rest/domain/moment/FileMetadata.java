package com.bufanbaby.backend.rest.domain.moment;

public class FileMetadata {

	/**
	 * The original file name
	 */
	private String originalName;

	/**
	 * The relative path to the file stored in the file system and the path
	 * separator is forward slash
	 */
	private String relativePath;

	/**
	 * The media type of the file
	 */
	private String mediaType;

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
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
		return String.format("FileMetadata [originalName=%s, relativePath=%s, mediaType=%s]",
				originalName, relativePath, mediaType);
	}

}
