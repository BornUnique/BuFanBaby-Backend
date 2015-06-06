package com.bufanbaby.backend.rest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bufanbaby.backend.rest.domain.moment.Symbols;
import com.bufanbaby.backend.rest.services.config.ConfigService;

@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
	private static final Logger LOG = LoggerFactory.getLogger(StaticResourceConfiguration.class);

	private static final String File_Schema_Prefix = "file:///";

	@Value("${spring.resources.cache-period:31556926}")
	private int cachePeriod;

	@Autowired
	private ConfigService configService;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String documentsLocation = File_Schema_Prefix + configService.getUploadedDocumentsPath()
				+ Symbols.FORWARD_SLASH.symbol;
		String imagesLocation = File_Schema_Prefix + configService.getUploadedImagesPath()
				+ Symbols.FORWARD_SLASH.symbol;
		String audiosLocation = File_Schema_Prefix + configService.getUploadedAudiosPath()
				+ Symbols.FORWARD_SLASH.symbol;
		String videosLocation = File_Schema_Prefix + configService.getUploadedVideosPath()
				+ Symbols.FORWARD_SLASH.symbol;

		LOG.info("Serving documents from " + documentsLocation);
		LOG.info("Serving images from " + imagesLocation);
		LOG.info("Serving audios from " + audiosLocation);
		LOG.info("Serving videos from " + videosLocation);

		registry.addResourceHandler("/documents/**").addResourceLocations(documentsLocation)
				.setCachePeriod(cachePeriod);
		registry.addResourceHandler("/images/**").addResourceLocations(imagesLocation)
				.setCachePeriod(cachePeriod);
		registry.addResourceHandler("/audios/**").addResourceLocations(audiosLocation)
				.setCachePeriod(cachePeriod);
		registry.addResourceHandler("/videos/**").addResourceLocations(videosLocation)
				.setCachePeriod(cachePeriod);
	}
}