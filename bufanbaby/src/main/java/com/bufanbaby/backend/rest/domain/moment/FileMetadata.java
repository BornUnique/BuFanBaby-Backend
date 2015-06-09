package com.bufanbaby.backend.rest.domain.moment;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.bufanbaby.backend.rest.resources.moment.FileMetadataResponse;

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

	public FileMetadataResponse toFileMetadataResponse(UriInfo uriInfo) {
		URI uri = uriInfo.getBaseUri();
		String host = uri.getHost();
		String schema = uri.getScheme();
		int port = uri.getPort();

		URI root = null;
		if (port == -1) {
			root = URI.create(schema + "://" + host);
		} else {
			root = URI.create(schema + "://" + host + ":" + port);
		}

		UriBuilder builder = UriBuilder.fromUri(root);

		FileMetadataResponse response = new FileMetadataResponse();
		response.setFileName(originalName);
		response.setFileUri(builder.path(originalFilePath).build());
		response.setThumbnailUrl(builder.path(thumbnailFilePath).build());
		return response;

	}

	@Override
	public String toString() {
		return String.format(
				"FileMetadata [originalName=%s, originalFilePath=%s, thumbnailFilePath=%s]",
				originalName, originalFilePath, thumbnailFilePath);
	}

}
