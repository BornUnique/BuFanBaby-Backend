package com.bufanbaby.backend.rest.resources.moment;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
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
import org.springframework.stereotype.Component;

import com.bufanbaby.backend.rest.config.AppProperties;
import com.bufanbaby.backend.rest.domain.FileMetadata;
import com.bufanbaby.backend.rest.domain.MediaTypes;
import com.bufanbaby.backend.rest.domain.Moment;
import com.bufanbaby.backend.rest.domain.Tag;
import com.bufanbaby.backend.rest.exception.GenericWebApplicationException;
import com.bufanbaby.backend.rest.exception.MediaTypeNotAllowedException;
import com.bufanbaby.backend.rest.exception.UploadedFilesOverLimitException;
import com.bufanbaby.backend.rest.services.moment.MomentService;

@Component
@Path("{userId}/moments")
public class MomentsResource {
	private static final Logger logger = LoggerFactory.getLogger(MomentsResource.class);

	private AppProperties appProperties;
	private MomentService momentService;

	@Autowired
	public MomentsResource(AppProperties appProperties, MomentService momentService) {
		this.appProperties = appProperties;
		this.momentService = momentService;
	}

	// TODO: need pass JSON from client side
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postMoments(
			@Context UriInfo uriInfo,
			@PathParam("userId") String userId,
			@FormDataParam("comment") String comment,
			@FormDataParam("ownerTag") String ownerTag,
			@FormDataParam("spouseTag") String spouseTag,
			@FormDataParam("childrenTag") List<String> childrenTags,
			@FormDataParam("file") List<FormDataBodyPart> files) {

		// Check total files if over limit
		int size = files.size();
		if (size > appProperties.getMaxFilesPerUpload()) {
			throw new UploadedFilesOverLimitException();
		}

		// Check media type to avoid attack
		for (FormDataBodyPart formDataBodyPart : files) {
			ContentDisposition contentDisposition = formDataBodyPart.getContentDisposition();
			MediaType mediaType = formDataBodyPart.getMediaType();

			if (!isMediaTypeAllowed(mediaType)) {
				logger.warn("Detected not allowed media type: {}, user id: {}, file name: {}",
						mediaType, userId, contentDisposition.getFileName());
				throw new MediaTypeNotAllowedException();
			}
		}

		// Save uploaded file
		List<FileMetadata> fileMetadatas = new ArrayList<FileMetadata>(size);
		for (FormDataBodyPart formDataBodyPart : files) {
			ContentDisposition disposition = formDataBodyPart.getContentDisposition();
			MediaType mediaType = formDataBodyPart.getMediaType();
			long maxBytesPerUploadedFile = appProperties.getMaxBytesPerMediaType(mediaType);

			// save the file
			String parentDir = appProperties.getParentDirectory(mediaType, userId);
			createParentDirectories(parentDir);
			String destPath = appProperties.getUploadedFileDestPath(mediaType, parentDir);
			saveUploadedFile(formDataBodyPart, maxBytesPerUploadedFile, destPath);

			// create file metadata
			FileMetadata fileMetadata = new FileMetadata();
			String originalName = disposition.getFileName();
			fileMetadata.setOriginalName(originalName);
			fileMetadata.setOriginalCreatedTime(0);
			fileMetadata.setGeneratedName(null);
			String relativePath = null;
			fileMetadata.setRelativePath(relativePath);
			fileMetadata.setMediaType(mediaType.toString());

			fileMetadatas.add(fileMetadata);
		}

		// Create the Moment
		Tag tag = new Tag();
		tag.setOwnerTag(ownerTag);
		tag.setSpouseTag(spouseTag);
		tag.setChildrenTags(childrenTags);

		Moment moment = new Moment();
		moment.setMessage(comment);
		moment.setEpochMilliCreated(Instant.now().toEpochMilli());
		moment.setUserId(Long.parseLong(userId));
		moment.setFileMetadatas(fileMetadatas);
		moment.setTag(tag);
		//
		// // Save the moment
		// momentService.save(moment);

		// TODO: after saved in Redis then get an id
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
			throw new GenericWebApplicationException(500, "Server Failure",
					"Failed to create directory");
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
			throw new GenericWebApplicationException(500, "Server Failure",
					"Failed to create file");
		}
	}
}
