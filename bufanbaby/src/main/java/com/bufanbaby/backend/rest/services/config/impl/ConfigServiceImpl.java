package com.bufanbaby.backend.rest.services.config.impl;

import static com.bufanbaby.backend.rest.domain.moment.Symbols.FORWARD_SLASH;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bufanbaby.backend.rest.domain.moment.Directory;
import com.bufanbaby.backend.rest.domain.moment.MediaTypes;
import com.bufanbaby.backend.rest.services.config.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {
	private static final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

	private int maxFilesPerUpload;

	private int maxMomentsPerRequest;

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
	 * Get the parent directory for the uploaded file using the pattern:
	 * D:/moments/images/{userId}/2015/05/20
	 * 
	 * @param mediaType
	 *            the media type of the uploaded file
	 * @param userId
	 *            the user id used as part of the path
	 * @return the path string
	 */
	@Override
	public String getParentDirectory(MediaType mediaType, long userId) {
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
	 * Get the relative path which returns to the client used to form the url
	 * for the stored files using the pattern:
	 * images/{userId}/2015/05/20/{currentmillis}.gif
	 * 
	 * @param mediaType
	 *            the media type
	 * @param fullPath
	 *            the full path
	 * @return the relative path
	 */
	@Override
	public String getRelativeDirectory(MediaType mediaType, String fullPath) {
		String match = null;
		if (isDocumentType(mediaType)) {
			match = Directory.DOCUMENTS.name;
		} else if (isImageType(mediaType)) {
			match = Directory.IMAGES.name;
		} else if (isAudioType(mediaType)) {
			match = Directory.AUDIOS.name;
		} else {
			match = Directory.VIDEOS.name;
		}

		return fullPath.substring(fullPath.toLowerCase().lastIndexOf(match));
	}

	/**
	 * Generate the destination path for the uploaded file using the pattern:
	 * D:/moments/images/{userId}/2015/05/20/{currentmillis}.gif
	 * 
	 * @param mediaType
	 *            the media type of the uploaded file
	 * @param parentDir
	 *            the parent directory such as: D:/moments/documents
	 * @return the full path for saving the uploaded file
	 */
	@Override
	public String getUploadedFileDestPath(MediaType mediaType, String parentDir) {
		String newFileName = String.valueOf(RandomStringUtils.randomAlphanumeric(12));
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
	@Override
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

	@Override
	public int getMaxFilesPerUpload() {
		return maxFilesPerUpload;
	}

	@Value("${bufanbaby.max.moments.per.request:25}")
	public void setMaxMomentsPerRequest(int maxMomentsPerRequest) {
		this.maxMomentsPerRequest = maxMomentsPerRequest;
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

	@Override
	public String getUploadedDocumentsPath() {
		return uploadedDocumentsPath;
	}

	@Override
	public String getUploadedImagesPath() {
		return uploadedImagesPath;
	}

	@Override
	public String getUploadedAudiosPath() {
		return uploadedAudiosPath;
	}

	@Override
	public String getUploadedVideosPath() {
		return uploadedVideosPath;
	}

	@Override
	public int getMaxMomentsPerRequest() {
		return maxMomentsPerRequest;
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
