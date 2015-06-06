package com.bufanbaby.backend.rest.services.config.impl;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import com.bufanbaby.backend.rest.domain.moment.MediaTypes;
import com.bufanbaby.backend.rest.domain.moment.Symbols;

public class ConfigServiceImplTest {

	private static final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

	private ConfigServiceImpl configService;
	private String documentsPath = "D:/moments/documents";
	private String imagesPath = "D:/moments/images";
	private String audiosPath = "D:/moments/audios";
	private String videosPath = "D:/moments/videos";

	private long maxBytesDocument = 10000;
	private long maxBytesImage = 20000;
	private long maxBytesAudio = 30000;
	private long maxBytesVideo = 40000;

	private long userId = 1234;

	@Before
	public void setUp() throws Exception {
		configService = new ConfigServiceImpl();

		configService.setUploadedDocumentsPath(documentsPath);
		configService.setUploadedImagesPath(imagesPath);
		configService.setUploadedAudiosPath(audiosPath);
		configService.setUploadedVideosPath(videosPath);

		configService.setMaxBytesPerUploadedDocument(maxBytesDocument);
		configService.setMaxBytesPerUploadedImage(maxBytesImage);
		configService.setMaxBytesPerUploadedAudio(maxBytesAudio);
		configService.setMaxBytesPerUploadedVideo(maxBytesVideo);
	}

	@Test
	public void testGetMaxBytesPerMediaType() {
		assertThat(configService.getMaxBytesPerMediaType(MediaTypes.PDF.getMediaType()),
				equalTo(maxBytesDocument));

		assertThat(configService.getMaxBytesPerMediaType(MediaTypes.GIF.getMediaType()),
				equalTo(maxBytesImage));
		assertThat(configService.getMaxBytesPerMediaType(MediaTypes.PNG.getMediaType()),
				equalTo(maxBytesImage));
		assertThat(configService.getMaxBytesPerMediaType(MediaTypes.BMP.getMediaType()),
				equalTo(maxBytesImage));
		assertThat(configService.getMaxBytesPerMediaType(MediaTypes.JPG.getMediaType()),
				equalTo(maxBytesImage));

		assertThat(configService.getMaxBytesPerMediaType(MediaTypes.MP3.getMediaType()),
				equalTo(maxBytesAudio));
		assertThat(configService.getMaxBytesPerMediaType(MediaTypes.WAV.getMediaType()),
				equalTo(maxBytesAudio));

		assertThat(configService.getMaxBytesPerMediaType(MediaTypes.MPEG.getMediaType()),
				equalTo(maxBytesVideo));
		assertThat(configService.getMaxBytesPerMediaType(MediaTypes.AVI.getMediaType()),
				equalTo(maxBytesVideo));
	}

	@Test
	public void testGetParentDirectory() {
		assertThat(configService.getParentDirectory(MediaTypes.GIF.getMediaType(), userId),
				equalTo(getFileParentPath(imagesPath)));

		assertThat(configService.getParentDirectory(MediaTypes.MP3.getMediaType(), userId),
				equalTo(getFileParentPath(audiosPath)));

		assertThat(configService.getParentDirectory(MediaTypes.MPEG.getMediaType(), userId),
				equalTo(getFileParentPath(videosPath)));

	}

	private String getFileParentPath(String path) {
		return path + Symbols.FORWARD_SLASH.symbol + userId
				+ Symbols.FORWARD_SLASH.symbol + LocalDateTime.now().format(dtFormatter);
	}

	@Test
	public void testGetUploadedFileDestPath() {
		assertThat(configService.getUploadedFileDestPath(MediaTypes.PDF.getMediaType(),
				getFileParentPath(documentsPath)), containsString("pdf"));
		assertThat(configService.getUploadedFileDestPath(MediaTypes.PNG.getMediaType(),
				getFileParentPath(imagesPath)), containsString("png"));
		assertThat(configService.getUploadedFileDestPath(MediaTypes.MP3.getMediaType(),
				getFileParentPath(audiosPath)), containsString("mp3"));
		assertThat(configService.getUploadedFileDestPath(MediaTypes.MPEG.getMediaType(),
				getFileParentPath(videosPath)), containsString("mpeg"));
	}

	@Test
	public void testGetRelativeDirectory() {
		String fullPath = "D:/moments/images/1234/2015/05/30/1433032168506.jpg";
		assertThat(configService.getRelativeDirectory(MediaTypes.JPG.getMediaType(), fullPath),
				equalTo("images/1234/2015/05/30/1433032168506.jpg"));
	}

}
