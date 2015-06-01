package com.bufanbaby.backend.rest.resources.moment;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.bufanbaby.backend.rest.config.AppProperties;
import com.bufanbaby.backend.rest.domain.moment.FileMetadata;
import com.bufanbaby.backend.rest.domain.moment.MediaTypes;
import com.bufanbaby.backend.rest.domain.moment.Moment;
import com.bufanbaby.backend.rest.exception.FileIOException;
import com.bufanbaby.backend.rest.exception.UnsupportedFileTypeException;
import com.bufanbaby.backend.rest.exception.UploadedFilesOverLimitException;
import com.bufanbaby.backend.rest.services.moment.MomentService;
import com.bufanbaby.backend.rest.services.validation.RequestBeanValidator;

@Path("{userId}/moments")
public class MomentsResource {
	private static final Logger logger = LoggerFactory.getLogger(MomentsResource.class);

	private final AppProperties appProperties;
	private final MomentService momentService;
	private final MessageSource messageSource;
	private final RequestBeanValidator requestBeanValidator;

	@Autowired
	public MomentsResource(AppProperties appProperties, MomentService momentService,
			MessageSource messageSource, RequestBeanValidator requestBeanValidator) {
		this.appProperties = appProperties;
		this.momentService = momentService;
		this.messageSource = messageSource;
		this.requestBeanValidator = requestBeanValidator;
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postMoments(
			@Context UriInfo uriInfo,
			@PathParam("userId") String userId,
			@FormDataParam("moment") PostMomentRequest momentRequest,
			@FormDataParam("files") List<FormDataBodyPart> files) {

		requestBeanValidator.validate(momentRequest);

		Moment moment = new Moment();
		moment.setEpochMilliCreated(momentRequest.getEpochMilliCreated());
		moment.setFeeling(momentRequest.getFeeling());
		moment.setShareScope(momentRequest.getShareScope());
		moment.setTag(momentRequest.getTag());
		moment.setUserId(Long.parseLong(userId));

		// Check total files if over limit
		if (files != null) {
			int size = files.size();
			if (size > appProperties.getMaxFilesPerUpload()) {
				logger.warn("Detected uploaded files: {} over limit, user id: {}", size, userId);
				throw new UploadedFilesOverLimitException(messageSource.getMessage(
						"bufanbaby.uploaded.files.over.limit",
						new Integer[] { appProperties.getMaxFilesPerUpload() },
						LocaleContextHolder.getLocale()));
			}

			// Check media type to avoid attack
			for (FormDataBodyPart formDataBodyPart : files) {
				ContentDisposition contentDisposition = formDataBodyPart.getContentDisposition();
				MediaType mediaType = formDataBodyPart.getMediaType();

				if (!isMediaTypeAllowed(mediaType)) {
					logger.warn("Detected unsupported media type: {}, user id: {}, file name: {}",
							mediaType, userId, contentDisposition.getFileName());
					throw new UnsupportedFileTypeException(messageSource.getMessage(
							"bufanbaby.unsupported.file.type",
							null, LocaleContextHolder.getLocale()));
				}
			}

			// Save uploaded file
			List<FileMetadata> fileMetadatas = new ArrayList<FileMetadata>(size);
			for (FormDataBodyPart formDataBodyPart : files) {
				ContentDisposition disposition = formDataBodyPart.getContentDisposition();
				MediaType mediaType = formDataBodyPart.getMediaType();
				long maxBytesPerUploadedFile = appProperties.getMaxBytesPerMediaType(mediaType);

				// save the file
				// D:/moments/images/{userId}/2015/05/20
				String parentDir = appProperties.getParentDirectory(mediaType, userId);
				createParentDirectories(parentDir);

				// D:/moments/images/{userId}/2015/05/20/{currentmillis}.gif
				String destPath = appProperties.getUploadedFileDestPath(mediaType, parentDir);
				saveUploadedFile(formDataBodyPart, maxBytesPerUploadedFile, destPath);

				String relativePath = appProperties.getRelativeDirectory(mediaType, destPath);

				// create file metadata
				FileMetadata fileMetadata = new FileMetadata();
				fileMetadata.setOriginalName(disposition.getFileName());
				fileMetadata.setRelativePath(relativePath);
				fileMetadata.setMediaType(mediaType.toString());

				fileMetadatas.add(fileMetadata);
			}

			moment.setTotalAttachedFiles(size);
			moment.setFileMetadatas(fileMetadatas);
		}

		// Save the moment
		momentService.save(moment);

		String momentId = "9999";

		// Return response based on Post semantic
		URI location = uriInfo.getAbsolutePathBuilder().path(momentId).build();
		return Response.created(location).entity(moment).build();
	}

	/**
	 * Check if the uploaded file media type is allowed.
	 * 
	 * @param mediaType
	 *            the media type of the uploaded file
	 * @return true if allowed, false otherwise
	 */
	private boolean isMediaTypeAllowed(MediaType mediaType) {
		if (mediaType.equals(MediaTypes.JPG.getMediaType())
				|| mediaType.equals(MediaTypes.PDF.getMediaType())
				|| mediaType.equals(MediaTypes.PNG.getMediaType())
				|| mediaType.equals(MediaTypes.GIF.getMediaType())
				|| mediaType.equals(MediaTypes.BMP.getMediaType())
				|| mediaType.equals(MediaTypes.MP3.getMediaType())
				|| mediaType.equals(MediaTypes.WAV.getMediaType())
				|| mediaType.equals(MediaTypes.AVI.getMediaType())
				|| mediaType.equals(MediaTypes.MPEG.getMediaType())) {
			return true;
		}
		return false;
	}

	/**
	 * Create the parent directories for the uploaded file before creating the
	 * file itself.
	 * <p>
	 * Pattern: D:/moments/documents/{userId}/2015/05/20/
	 */
	private void createParentDirectories(String parentDir) {
		try {
			Files.createDirectories(Paths.get(parentDir));
		} catch (IOException e) {
			logger.error("Error when creating directory: " + parentDir, e);
			throw new FileIOException(messageSource.getMessage("bufanbaby.file.save.failure",
					null, LocaleContextHolder.getLocale()));
		}
	}

	/**
	 * Save the uploaded to the given file name.
	 * <p>
	 * Pattern:Pattern:
	 * D:/moments/documents/{userId}/2015/05/20/{currentmillis}.pdf
	 * 
	 * @param formDataBodyPart
	 *            the form data body part
	 * @param maxBytesPerUploadedFile
	 *            the max allowed bytes per file
	 * @param destPath
	 *            the full path
	 */
	private void saveUploadedFile(FormDataBodyPart formDataBodyPart, long maxBytesPerUploadedFile,
			String destPath) {
		try {
			momentService.saveUploadedFile(formDataBodyPart.getValueAs(InputStream.class),
					destPath, maxBytesPerUploadedFile);
		} catch (IOException e) {
			logger.error("Error when saving file: " + destPath, e);
			throw new FileIOException(messageSource.getMessage("bufanbaby.file.save.failure",
					null, LocaleContextHolder.getLocale()));
		}
	}
}
