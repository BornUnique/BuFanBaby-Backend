package com.bufanbaby.backend.rest.config;

import static com.bufanbaby.backend.rest.domain.Symbols.FORWARD_SLASH;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bufanbaby.backend.rest.domain.MediaTypes;

@Component
public class AppProperties {
	private static final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

	private int maxFilesPerUpload;

	private long maxBytesPerUploadedDocument;

	private long maxBytesPerUploadedImage;

	private long maxBytesPerUploadedAudio;

	private long maxBytesPerUploadedVideo;

	private String uploadedDocumentsPath;

	private String uploadedImagesPath;

	private String uploadedAudiosPath;

	private String uploadedVideosPath;

	@PostConstruct
	public void init() throws IOException {
		Objects.requireNonNull(uploadedDocumentsPath,
				"Error missing uploaded documents path property in application.properties");
		Objects.requireNonNull(uploadedImagesPath,
				"Error missing uploaded images path property in application.properties");
		Objects.requireNonNull(uploadedAudiosPath,
				"Error missing uploaded audios path property in application.properties");
		Objects.requireNonNull(uploadedVideosPath,
				"Error missing uploaded videos path property in application.properties");

		Files.createDirectories(Paths.get(uploadedDocumentsPath));
		Files.createDirectories(Paths.get(uploadedImagesPath));
		Files.createDirectories(Paths.get(uploadedAudiosPath));
		Files.createDirectories(Paths.get(uploadedVideosPath));
	}

	/**
	 * Get the parent directory for the uploaded file.
	 * 
	 * @param mediaType
	 *            the media type of the uploaded file
	 * @param userId
	 *            the user id used as part of the path
	 * @return the path string
	 */
	public String getParentDirectory(MediaType mediaType, String userId) {
		StringBuilder sb = new StringBuilder();

		if (isDocumentType(mediaType)) {
			sb.append(uploadedDocumentsPath);
		} else if (isImageType(mediaType)) {
			sb.append(uploadedImagesPath);
		} else if (isAudioType(mediaType)) {
			sb.append(uploadedAudiosPath);
		} else {
			sb.append(uploadedVideosPath);
		}

		String parentDir = sb.append(FORWARD_SLASH.symbol)
				.append(userId)
				.append(FORWARD_SLASH.symbol)
				.append(LocalDateTime.now().format(dtFormatter))
				.toString();
		return parentDir;
	}

	/**
	 * Generate the destination path for the uploaded file using the pattern:
	 * D:/moments/documents/{userId}/2015/05/20/{currentmillis}.gif
	 * 
	 * @param mediaType
	 *            the media type of the uploaded file
	 * @param parentDir
	 *            the parent directory such as: D:/moments/documents
	 * @return the full path for saving the uploaded file
	 */
	public String getUploadedFileDestPath(MediaType mediaType, String parentDir) {
		String newFileName = String.valueOf(Instant.now().toEpochMilli());
		return new StringBuilder()
				.append(parentDir)
				.append(FORWARD_SLASH.symbol)
				.append(newFileName)
				.append(MediaTypes.getExtension(mediaType))
				.toString();
	}

	/**
	 * Get the max bytes for different uploaded file media type
	 * 
	 * @param mediaType
	 *            the file media type
	 * @return the max bytes allowed
	 */
	public long getMaxBytesPerMediaType(MediaType mediaType) {
		if (isDocumentType(mediaType)) {
			return maxBytesPerUploadedDocument;
		} else if (isImageType(mediaType)) {
			return maxBytesPerUploadedImage;
		} else if (isAudioType(mediaType)) {
			return maxBytesPerUploadedAudio;
		} else {
			return maxBytesPerUploadedVideo;
		}
	}

	public int getMaxFilesPerUpload() {
		return maxFilesPerUpload;
	}

	@Value("${bufanbaby.max.files.per.upload:9}")
	public void setMaxFilesPerUpload(int maxFilesPerUpload) {
		this.maxFilesPerUpload = maxFilesPerUpload;
	}

	@Value("${bufanbaby.max.bytes.per.uploaded.document:15728640}")
	public void setMaxBytesPerUploadedDocument(long maxBytesPerUploadedDocument) {
		this.maxBytesPerUploadedDocument = maxBytesPerUploadedDocument;
	}

	@Value("${bufanbaby.max.bytes.per.uploaded.image:15728640}")
	public void setMaxBytesPerUploadedImage(long maxBytesPerUploadedImage) {
		this.maxBytesPerUploadedImage = maxBytesPerUploadedImage;
	}

	@Value("${bufanbaby.max.bytes.per.uploaded.audio:15728640}")
	public void setMaxBytesPerUploadedAudio(long maxBytesPerUploadedAudio) {
		this.maxBytesPerUploadedAudio = maxBytesPerUploadedAudio;
	}

	@Value("${bufanbaby.max.bytes.per.uploaded.video:31457280}")
	public void setMaxBytesPerUploadedVideo(long maxBytesPerUploadedVideo) {
		this.maxBytesPerUploadedVideo = maxBytesPerUploadedVideo;
	}

	@Value("${bufanbaby.uploaded.files.document.path}")
	public void setUploadedDocumentsPath(String uploadedDocumentsPath) {
		this.uploadedDocumentsPath = uploadedDocumentsPath;
	}

	@Value("${bufanbaby.uploaded.files.image.path}")
	public void setUploadedImagesPath(String uploadedImagesPath) {
		this.uploadedImagesPath = uploadedImagesPath;
	}

	@Value("${bufanbaby.uploaded.files.audio.path}")
	public void setUploadedAudiosPath(String uploadedAudiosPath) {
		this.uploadedAudiosPath = uploadedAudiosPath;
	}

	@Value("${bufanbaby.uploaded.files.video.path}")
	public void setUploadedVideosPath(String uploadedVideosPath) {
		this.uploadedVideosPath = uploadedVideosPath;
	}

	private boolean isAudioType(MediaType mediaType) {
		return mediaType.equals(MediaTypes.MP3.getMediaType())
				|| mediaType.equals(MediaTypes.WAV.getMediaType());
	}

	private boolean isDocumentType(MediaType mediaType) {
		return mediaType.equals(MediaTypes.PDF.getMediaType());
	}

	private boolean isImageType(MediaType mediaType) {
		return mediaType.equals(MediaTypes.JPG.getMediaType())
				|| mediaType.equals(MediaTypes.PNG.getMediaType())
				|| mediaType.equals(MediaTypes.GIF.getMediaType())
				|| mediaType.equals(MediaTypes.BMP.getMediaType());
	}
}
