package com.bufanbaby.backend.integration.moment;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.junit.Before;
import org.junit.Test;

import com.bufanbaby.backend.rest.domain.moment.FileMetadata;
import com.bufanbaby.backend.rest.domain.moment.ShareWith;
import com.bufanbaby.backend.rest.exception.ErrorResponse;
import com.bufanbaby.backend.rest.exception.ValidationErrorResponse;
import com.bufanbaby.backend.rest.resources.moment.PostMomentRequest;
import com.bufanbaby.backend.rest.resources.moment.PostMomentResponse;

public class MomentRestApiTest {
	private final static Logger logger = Logger.getLogger(MomentRestApiTest.class.getName());

	private final static String Footprint_Image_Name = "footprint-150x150.jpg";
	private final static String Smile_Image_Name = "smile.jpg";

	private WebTarget rootTarget;
	private WebTarget momentTarget;
	private URI rootUri;
	private int userId;

	@Before
	public void setUp() throws Exception {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);
		clientConfig.register(MultiPartFeature.class);
		clientConfig.register(new LoggingFilter(logger, true));

		Client client = ClientBuilder.newClient(clientConfig);

		rootUri = UriBuilder.fromUri("http://{host}:{port}/{path}")
				.resolveTemplate("host", "localhost")
				.resolveTemplate("port", 8080)
				.resolveTemplate("path", "v1.0")
				.build();

		rootTarget = client.target(rootUri);

		userId = new Random().nextInt(31);

		momentTarget = rootTarget
				.path("{userId}/moments")
				.resolveTemplate("userId", userId);
	}

	@Test
	public void testPostMoment() throws URISyntaxException {
		FormDataMultiPart multipart = setupFormDataMultipart(false, false);

		Response response = momentTarget.request()
				// .header("Accept-Language",
				// "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4")
				.post(Entity.entity(multipart, multipart.getMediaType()));

		// check http status code
		assertThat(response.getStatusInfo().getFamily(), equalTo(Response.Status.Family.SUCCESSFUL));
		assertThat(response.getStatus(), equalTo(Response.Status.CREATED.getStatusCode()));
		assertThat(response.getStatusInfo().getReasonPhrase(),
				equalTo(Response.Status.CREATED.getReasonPhrase()));
		assertThat(response.getLocation(), is(notNullValue()));

		PostMomentResponse momentResponse = response.readEntity(PostMomentResponse.class);

		assertThat(momentResponse.getSelf(), equalTo(response.getLocation()));

		List<FileMetadata> fileMetadatas = momentResponse.getFileMetadatas();
		for (FileMetadata fileMetadata : fileMetadatas) {
			assertThat(fileMetadata.getOriginalName(),
					is(anyOf(equalTo(Footprint_Image_Name), equalTo(Smile_Image_Name))));
		}
	}

	@Test
	public void testPostMomentShouldFailIfUploadedFilesOverLimit() throws URISyntaxException {
		// generate 10 files which is over limit
		FormDataMultiPart multipart = setupFormDataMultipart(true, false);

		Response response = momentTarget.request()
				// .header("Accept-Language",
				// "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4")
				.post(Entity.entity(multipart, multipart.getMediaType()));

		// check http status code
		assertThat(response.getStatusInfo().getFamily(),
				equalTo(Response.Status.Family.CLIENT_ERROR));
		assertThat(response.getStatus(),
				equalTo(Response.Status.BAD_REQUEST.getStatusCode()));
		assertThat(response.getStatusInfo().getReasonPhrase(),
				equalTo(Response.Status.BAD_REQUEST.getReasonPhrase()));

		// check customized error response
		ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
		assertThat(errorResponse.getErrorCode(),
				equalTo(ErrorResponse.ErrorCode.UPLOADED_FILES_OVER_LIMIT.code));
	}

	@Test
	public void testPostMomentShouldFailIfValidationErrorExists() throws URISyntaxException {
		// generate 10 files which is over limit
		FormDataMultiPart multipart = setupFormDataMultipart(true, true);

		Response response = momentTarget.request()
				.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4")
				.post(Entity.entity(multipart, multipart.getMediaType()));

		// check http status code
		assertThat(response.getStatusInfo().getFamily(),
				equalTo(Response.Status.Family.CLIENT_ERROR));
		assertThat(response.getStatus(),
				equalTo(Response.Status.BAD_REQUEST.getStatusCode()));
		assertThat(response.getStatusInfo().getReasonPhrase(),
				equalTo(Response.Status.BAD_REQUEST.getReasonPhrase()));

		// check customized error response
		ValidationErrorResponse errorResponse = response.readEntity(ValidationErrorResponse.class);
		assertThat(errorResponse.getErrorCode(),
				equalTo(ErrorResponse.ErrorCode.REQUEST_VALIDATION_ERROR.code));

		// missing feeling, tag, shared scope
		assertThat(errorResponse.getViolations().size(), equalTo(2));
	}

	/**
	 * Set up FormDataMultipart for testing.
	 * 
	 * @return FormDataMultiPart
	 * @throws URISyntaxException
	 *             throws an error
	 */
	@SuppressWarnings("resource")
	private FormDataMultiPart setupFormDataMultipart(boolean uploadedFilsOverLimit,
			boolean triggerValidationError) throws URISyntaxException {

		PostMomentRequest momentRequest = new PostMomentRequest();
		momentRequest.setTimeCreated(Instant.now().toEpochMilli());
		if (!triggerValidationError) {
			momentRequest.setFeeling("This is the first unique moment which makes excited!!!"
					+ " 这是第一个不凡时刻， 它让我无比兴奋！");
			momentRequest.setShareWith(ShareWith.JUST_ME);

			Set<String> tags = new HashSet<>();
			tags.add("Kaiqin");
			tags.add("Laiyan");
			tags.add("Angie");
			tags.add("Sydney");
			momentRequest.setTags(tags);
		}

		FormDataMultiPart multipart = new FormDataMultiPart()
				.field("moment", momentRequest, MediaType.APPLICATION_JSON_TYPE);

		int max = 1;
		if (uploadedFilsOverLimit) {
			max = 6;
		}

		for (int i = 0; i < max; i++) {
			URL url1 = this.getClass().getResource(Footprint_Image_Name);
			FileDataBodyPart filePart1 = new FileDataBodyPart("files", new File(url1.toURI()));

			URL url2 = this.getClass().getResource(Smile_Image_Name);
			FileDataBodyPart filePart2 = new FileDataBodyPart("files", new File(url2.toURI()));

			multipart.bodyPart(filePart1);
			multipart.bodyPart(filePart2);
		}

		return multipart;
	}
}
