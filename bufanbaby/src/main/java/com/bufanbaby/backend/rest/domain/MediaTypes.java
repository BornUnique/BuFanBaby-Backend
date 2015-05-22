package com.bufanbaby.backend.rest.domain;

import javax.ws.rs.core.MediaType;

public enum MediaTypes {
	// @formatter:off
    PDF(".pdf", new MediaType("application", "pdf")),
    JPG(".jpg", new MediaType("image", "jpeg")),
    PNG(".png", new MediaType("image", "png")),
    GIF(".gif", new MediaType("image", "gif")),
    BMP(".bmp", new MediaType("image", "pdf")),
    MP3(".mp3", new MediaType("audio", "mpeg")),
    WAV(".wav", new MediaType("audio", "x-wave")),
    AVI(".avi", new MediaType("video", "x-msvideo")),
    MPEG(".mpeg", new MediaType("video", "mpeg"));
    // @formatter:on

	private final String extension;

	private final MediaType mediaType;

	private MediaTypes(final String extension, final MediaType mediaType) {
		if (extension == null || !extension.startsWith(".") || mediaType == null) {
			throw new IllegalArgumentException();
		}
		this.extension = extension;
		this.mediaType = mediaType;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public String getExtension() {
		return extension;
	}

	/**
	 * Gets the file extension.
	 *
	 * @return the file extension.
	 */
	public static String getExtension(MediaType mediaType) {
		for (MediaTypes mt : MediaTypes.values()) {
			if (mt.getMediaType().equals(mediaType)) {
				return mt.getExtension();
			}
		}
		throw new IllegalArgumentException(mediaType.toString());
	}

}