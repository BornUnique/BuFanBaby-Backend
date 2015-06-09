package com.bufanbaby.backend.rest.services.config;

import javax.ws.rs.core.MediaType;

public interface ConfigService {

	String getParentDirectory(MediaType mediaType, long userId);

	String getRelativeDirectory(MediaType mediaType, String fullPath);

	String getUploadedFileDestPath(MediaType mediaType, String parentDir);

	long getMaxBytesPerMediaType(MediaType mediaType);

	int getMaxFilesPerUpload();

	String getUploadedDocumentsPath();

	String getUploadedAudiosPath();

	String getUploadedImagesPath();

	String getUploadedVideosPath();

	int getMaxMomentsPerRequest();

}
