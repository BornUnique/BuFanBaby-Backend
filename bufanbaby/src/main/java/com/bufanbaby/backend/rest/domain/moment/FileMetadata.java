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
	private String originalFilePath;

	/**
	 * The path of thumbnail
	 */
	private String thumbnailFilePath;

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getOriginalFilePath() {
		return originalFilePath;
	}

	public void setOriginalFilePath(String originalFilePath) {
		this.originalFilePath = originalFilePath;
	}

	public String getThumbnailFilePath() {
		return thumbnailFilePath;
	}

	public void setThumbnailFilePath(String thumbnailFilePath) {
		this.thumbnailFilePath = thumbnailFilePath;
	}

	@Override
	public String toString() {
		return String.format(
				"FileMetadata [originalName=%s, originalFilePath=%s, thumbnailFilePath=%s]",
				originalName, originalFilePath, thumbnailFilePath);
	}

}
