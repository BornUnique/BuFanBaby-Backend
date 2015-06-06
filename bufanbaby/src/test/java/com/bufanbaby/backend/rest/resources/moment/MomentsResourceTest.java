package com.bufanbaby.backend.rest.resources.moment;

import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import net.sf.uadetector.UserAgentStringParser;

import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.bufanbaby.backend.rest.domain.moment.ShareWith;
import com.bufanbaby.backend.rest.exception.UnsupportedFileTypeException;
import com.bufanbaby.backend.rest.exception.UploadedFilesOverLimitException;
import com.bufanbaby.backend.rest.services.config.ConfigService;
import com.bufanbaby.backend.rest.services.moment.MomentService;
import com.bufanbaby.backend.rest.services.validation.RequestBeanValidator;

@RunWith(MockitoJUnitRunner.class)
public class MomentsResourceTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private MomentsResource momentResource;

	@Mock
	private ConfigService configService;

	@Mock
	private MessageSource messageSource;

	@Mock
	private RequestBeanValidator requestBeanValidator;

	@Mock
	private UserAgentStringParser userAgentStringParser;

	@Mock
	private UriInfo uriInfo;

	@Mock
	private List<String> childrenTags;

	@Mock
	private List<FormDataBodyPart> files;

	@Mock
	private FormDataBodyPart formDataBodyPart;

	@Mock
	private MomentService momentService;

	@Mock
	private ContentDisposition contentDisposition;

	@Before
	public void setUp() throws Exception {
		momentResource = new MomentsResource(configService, momentService, messageSource,
				requestBeanValidator, userAgentStringParser);
	}

	@Test
	public void testPostMomentsShouldThrowExceptionIfUploadedFilesOverLimit() {
		when(configService.getMaxFilesPerUpload()).thenReturn(9);
		when(files.size()).thenReturn(10);
		when(messageSource.getMessage("bufanbaby.uploaded.files.over.limit",
				new Integer[] { 9 }, Locale.getDefault())).thenReturn("");

		PostMomentRequest momentRequest = setupPostMomentRequest();
		int userId = new Random().nextInt(31);

		thrown.expect(UploadedFilesOverLimitException.class);
		momentResource.postMoments(uriInfo, null, userId, momentRequest, files);
	}

	@Test
	public void testPostMomentsShouldThrowExceptionIfMediaTypeNotAllowed() {
		when(configService.getMaxFilesPerUpload()).thenReturn(9);
		when(files.size()).thenReturn(8);
		when(contentDisposition.getFileName()).thenReturn("file.xml");
		when(formDataBodyPart.getContentDisposition()).thenReturn(contentDisposition);
		when(formDataBodyPart.getMediaType()).thenReturn(MediaType.APPLICATION_XML_TYPE);
		when(messageSource.getMessage("bufanbaby.unsupported.file.type",
				null, Locale.getDefault())).thenReturn("");

		List<FormDataBodyPart> files = Arrays.asList(formDataBodyPart);
		PostMomentRequest momentRequest = setupPostMomentRequest();
		int userId = new Random().nextInt(31);

		thrown.expect(UnsupportedFileTypeException.class);
		momentResource.postMoments(uriInfo, null, userId, momentRequest, files);
	}

	private PostMomentRequest setupPostMomentRequest() {
		PostMomentRequest momentRequest = new PostMomentRequest();
		momentRequest.setTimeCreated(Instant.now().toEpochMilli());
		momentRequest
				.setFeeling("This is the first unique moment which makes excited!!! 这是第一个不凡时刻， 它让我无比兴奋！");
		momentRequest.setShareWith(ShareWith.JUST_ME);
		Set<String> tags = new HashSet<>();
		tags.add("Kaiqin");
		tags.add("Laiyan");
		tags.add("Angie");
		tags.add("Sydney");
		momentRequest.setTags(tags);
		return momentRequest;
	}
}
