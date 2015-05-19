package com.bufanbaby.backend.rest.config;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bufanbaby.backend.rest.domain.MediaTypes;

@Component
public class AppProperties {

	@Value("${bufanbaby.max.files.per.upload}")
	private int maxFilesPerUpload;

	@Value("${bufanbaby.max.bytes.per.uploaded.document}")
	private long maxBytesPerUploadedDocument;

	@Value("${bufanbaby.max.bytes.per.uploaded.image}")
	private long maxBytesPerUploadedImage;

	@Value("${bufanbaby.max.bytes.per.uploaded.audio}")
	private long maxBytesPerUploadedAudio;

	@Value("${bufanbaby.max.bytes.per.uploaded.video}")
	private long maxBytesPerUploadedVideo;

	@Value("${bufanbaby.uploaded.files.location}")
	private String uploadedFilesLocation;

	public int maxFilesPerUpload() {
		return maxFilesPerUpload;
	}

	public long maxBytesPerUploadedDocument() {
		return maxBytesPerUploadedDocument;
	}

	public long maxBytesPerUploadedImage() {
		return maxBytesPerUploadedImage;
	}

	public long maxBytesPerUploadedAudio() {
		return maxBytesPerUploadedAudio;
	}

	public long maxBytesPerUploadedVideo() {
		return maxBytesPerUploadedVideo;
	}

	public String uploadedFilesLocation() {
		return uploadedFilesLocation;
	}

	public long maxBytesPerMediaType(MediaType mediaType) {
		if (mediaType.equals(MediaTypes.TXT.getMediaType())
				|| mediaType.equals(MediaTypes.PDF.getMediaType())) {
			return maxBytesPerUploadedDocument;
		} else if (mediaType.equals(MediaTypes.JPG.getMediaType())
				|| mediaType.equals(MediaTypes.PNG.getMediaType())
				|| mediaType.equals(MediaTypes.GIF.getMediaType())
				|| mediaType.equals(MediaTypes.BMP.getMediaType())) {
			return maxBytesPerUploadedImage;
		} else if (mediaType.equals(MediaTypes.MP3.getMediaType())
				|| mediaType.equals(MediaTypes.WAV.getMediaType())) {
			return maxBytesPerUploadedAudio;
		} else {
			return maxBytesPerUploadedVideo;
		}

	}
}
