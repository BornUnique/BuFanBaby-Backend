package com.bufanbaby.backend.rest.resources.multipart;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/moments")
public class MomentsResource {

	private static final String SERVER_UPLOAD_LOCATION_FOLDER = "D://moments";

	private void saveFile(InputStream in, String serverLocation) {

		try {
			Files.copy(in, Paths.get(serverLocation), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response postMoments(@FormDataParam("file") List<FormDataBodyPart> files) {

		for (FormDataBodyPart formDataBodyPart : files) {
			String filePath = SERVER_UPLOAD_LOCATION_FOLDER + File.separator
					+ formDataBodyPart.getContentDisposition().getFileName();
			saveFile(formDataBodyPart.getValueAs(InputStream.class), filePath);
		}
		return Response.status(200).build();
	}

}
