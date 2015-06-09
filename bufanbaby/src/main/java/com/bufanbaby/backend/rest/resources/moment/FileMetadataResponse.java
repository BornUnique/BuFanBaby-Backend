package com.bufanbaby.backend.rest.resources.moment;

import java.net.URI;

public class FileMetadataResponse {

	private String fileName;

	private URI fileUri;

	private URI thumbnailUrl;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public URI getFileUri() {
		return fileUri;
	}

	public void setFileUri(URI fileUri) {
		this.fileUri = fileUri;
	}

	public URI getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(URI thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	@Override
	public String toString() {
		return String.format("FileMetadataResponse [fileName=%s, fileUri=%s, thumbnailUrl=%s]",
				fileName, fileUri, thumbnailUrl);
	}

}
