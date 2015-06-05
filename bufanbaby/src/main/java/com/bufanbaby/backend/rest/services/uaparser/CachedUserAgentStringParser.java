package com.bufanbaby.backend.rest.services.uaparser;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Service
public class CachedUserAgentStringParser implements UserAgentStringParser {
	private static final Logger logger = LoggerFactory.getLogger(CachedUserAgentStringParser.class);

	private UserAgentStringParser parser;
	private Cache<String, ReadableUserAgent> cache;

	@PostConstruct
	public void init() {
		parser = UADetectorServiceFactory.getResourceModuleParser();
		cache = CacheBuilder.newBuilder()
				.maximumSize(100).expireAfterWrite(24, TimeUnit.HOURS).build();

		logger.info("Initialized User Agent String Parser - Version:", getDataVersion());

	}

	@PreDestroy
	public void destroy() {
		shutdown();
	}

	@Override
	public String getDataVersion() {
		return parser.getDataVersion();
	}

	@Override
	public ReadableUserAgent parse(final String userAgentString) {
		ReadableUserAgent result = cache.getIfPresent(userAgentString);
		if (result == null) {
			result = parser.parse(userAgentString);
			cache.put(userAgentString, result);
		}
		return result;
	}

	@Override
	public void shutdown() {
		parser.shutdown();
	}

}
