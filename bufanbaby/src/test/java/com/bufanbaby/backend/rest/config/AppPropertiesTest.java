package com.bufanbaby.backend.rest.config;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import com.bufanbaby.backend.rest.domain.moment.MediaTypes;
import com.bufanbaby.backend.rest.domain.moment.Symbols;

public class AppPropertiesTest {
	private static final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

	private AppProperties properties;
	private String documentsPath = "D:/moments/documents";
	private String imagesPath = "D:/moments/images";
	private String audiosPath = "D:/moments/audios";
	private String videosPath = "D:/moments/videos";

	private long maxBytesDocument = 10000;
	private long maxBytesImage = 20000;
	private long maxBytesAudio = 30000;
	private long maxBytesVideo = 40000;

	private String userId = "1234";

	@Before
	public void setUp() throws Exception {
		properties = new AppProperties();

		properties.setUploadedDocumentsPath(documentsPath);
		properties.setUploadedImagesPath(imagesPath);
		properties.setUploadedAudiosPath(audiosPath);
		properties.setUploadedVideosPath(videosPath);

		properties.setMaxBytesPerUploadedDocument(maxBytesDocument);
		properties.setMaxBytesPerUploadedImage(maxBytesImage);
		properties.setMaxBytesPerUploadedAudio(maxBytesAudio);
		properties.setMaxBytesPerUploadedVideo(maxBytesVideo);
	}

	@Test
	public void testGetMaxBytesPerMediaType() {
		assertThat(properties.getMaxBytesPerMediaType(MediaTypes.PDF.getMediaType()),
				equalTo(maxBytesDocument));

		assertThat(properties.getMaxBytesPerMediaType(MediaTypes.GIF.getMediaType()),
				equalTo(maxBytesImage));
		assertThat(properties.getMaxBytesPerMediaType(MediaTypes.PNG.getMediaType()),
				equalTo(maxBytesImage));
		assertThat(properties.getMaxBytesPerMediaType(MediaTypes.BMP.getMediaType()),
				equalTo(maxBytesImage));
		assertThat(properties.getMaxBytesPerMediaType(MediaTypes.JPG.getMediaType()),
				equalTo(maxBytesImage));

		assertThat(properties.getMaxBytesPerMediaType(MediaTypes.MP3.getMediaType()),
				equalTo(maxBytesAudio));
		assertThat(properties.getMaxBytesPerMediaType(MediaTypes.WAV.getMediaType()),
				equalTo(maxBytesAudio));

		assertThat(properties.getMaxBytesPerMediaType(MediaTypes.MPEG.getMediaType()),
				equalTo(maxBytesVideo));
		assertThat(properties.getMaxBytesPerMediaType(MediaTypes.AVI.getMediaType()),
				equalTo(maxBytesVideo));
	}

	@Test
	public void testGetParentDirectory() {
		assertThat(properties.getParentDirectory(MediaTypes.GIF.getMediaType(), userId),
				equalTo(getFileParentPath(imagesPath)));

		assertThat(properties.getParentDirectory(MediaTypes.MP3.getMediaType(), userId),
				equalTo(getFileParentPath(audiosPath)));

		assertThat(properties.getParentDirectory(MediaTypes.MPEG.getMediaType(), userId),
				equalTo(getFileParentPath(videosPath)));

	}

	private String getFileParentPath(String path) {
		return path + Symbols.FORWARD_SLASH.symbol + userId
				+ Symbols.FORWARD_SLASH.symbol + LocalDateTime.now().format(dtFormatter);
	}

	@Test
	public void testGetUploadedFileDestPath() {
		assertThat(properties.getUploadedFileDestPath(MediaTypes.PDF.getMediaType(),
				getFileParentPath(documentsPath)), containsString("pdf"));
		assertThat(properties.getUploadedFileDestPath(MediaTypes.PNG.getMediaType(),
				getFileParentPath(imagesPath)), containsString("png"));
		assertThat(properties.getUploadedFileDestPath(MediaTypes.MP3.getMediaType(),
				getFileParentPath(audiosPath)), containsString("mp3"));
		assertThat(properties.getUploadedFileDestPath(MediaTypes.MPEG.getMediaType(),
				getFileParentPath(videosPath)), containsString("mpeg"));
	}

}
