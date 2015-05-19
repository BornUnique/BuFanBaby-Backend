package com.bufanbaby.backend.rest.resources.multipart;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bufanbaby.backend.rest.config.AppProperties;
import com.bufanbaby.backend.rest.domain.MediaTypes;
import com.bufanbaby.backend.rest.exception.GenericWebApplicationException;
import com.bufanbaby.backend.rest.exception.MediaTypeNotAllowedException;
import com.bufanbaby.backend.rest.exception.UploadedFilesOverLimitException;
import com.bufanbaby.backend.rest.services.moments.MomentService;

@Component
@Path("{userId}/moments")
public class MomentsResource {
	private static final Logger logger = LoggerFactory.getLogger(MomentService.class);

	private static final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private MomentService momentService;

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response postMoments(@PathParam("userId") String userId,
			@FormDataParam("comment") String comment,
			@FormDataParam("file") List<FormDataBodyPart> files) {

		// Check total files if over limit
		int size = files.size();
		if (size > appProperties.maxFilesPerUpload()) {
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

		// Create directory based on pattern: {userId}/{yyyy-mm-dd}/
		String creationDatePath = LocalDateTime.now().format(dtFormatter);
		java.nio.file.Path parentPath = Paths.get(appProperties.uploadedFilesLocation(), userId,
				creationDatePath);
		try {
			Files.createDirectories(parentPath);
		} catch (IOException e) {
			logger.error("Error when creating directory: " + parentPath, e);
			throw new GenericWebApplicationException(500, "Server Failure",
					"Failed to create directory");
		}

		// Save uploaded file as:
		// {userId}/{yyyy-mm-dd}/{currentmillis}.{extension}
		for (FormDataBodyPart formDataBodyPart : files) {

			// Get max bytes for each uploaded file based on media type
			ContentDisposition contentDisposition = formDataBodyPart.getContentDisposition();
			MediaType mediaType = formDataBodyPart.getMediaType();
			long maxBytesPerUploadedFile = appProperties.maxBytesPerMediaType(mediaType);

			// Get current millis to create new file name
			String newFileName = String.valueOf(Instant.now().toEpochMilli());
			java.nio.file.Path destPath = Paths.get(parentPath.toString(), newFileName + "."
					+ mediaType.getSubtype());

			try {
				// Save uploaded file and report error if over allowed file size
				momentService.saveUploadedFile(formDataBodyPart.getValueAs(InputStream.class),
						destPath, maxBytesPerUploadedFile);
			} catch (IOException e) {
				logger.error("Error when saving file: " + destPath, e);
				throw new GenericWebApplicationException(500, "Server Failure",
						"Failed to create file");
			}
		}

		return Response.status(200).build();
	}

	private boolean isMediaTypeAllowed(MediaType mediaType) {
		if (mediaType.equals(MediaTypes.JPG.getMediaType())
				|| mediaType.equals(MediaTypes.TXT.getMediaType())
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

}
