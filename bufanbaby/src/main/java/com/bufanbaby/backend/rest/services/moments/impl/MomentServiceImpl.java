package com.bufanbaby.backend.rest.services.moments.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bufanbaby.backend.rest.config.AppProperties;
import com.bufanbaby.backend.rest.services.moments.MomentService;

@Service
public class MomentServiceImpl implements MomentService {
	private static final Logger logger = LoggerFactory.getLogger(MomentService.class);

	private AppProperties appProperties;

	@Autowired
	public MomentServiceImpl(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	@Override
	public void saveUploadedFile(InputStream is, Path destPath, long maxBytesPerUploadedFile)
			throws IOException {
		SizeLimitedInputStream slis = new SizeLimitedInputStream(is, maxBytesPerUploadedFile);
		try {
			Files.copy(slis, destPath, StandardCopyOption.REPLACE_EXISTING);
		} finally {
			try {
				slis.close();
			} catch (IOException e) {
				logger.info("Cannot close InputStream of file: " + destPath, e);
			}
		}
	}

	private class SizeLimitedInputStream extends InputStream {
		private long totalBytesRead = 0;
		private long maxBytesPerUploadedFile = 0;

		private InputStream inputStream;

		public SizeLimitedInputStream(InputStream is, long maxBytesPerUploadedFile) {
			this.inputStream = is;
			this.maxBytesPerUploadedFile = maxBytesPerUploadedFile;
		}

		@Override
		public int read() throws IOException {
			int b = inputStream.read();

			if ((++totalBytesRead) > maxBytesPerUploadedFile) {
				throw new IOException("File size is too big");
			}

			return b;
		}

		@Override
		public int read(byte[] b) throws IOException {
			return inputStream.read(b);
		}

		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			return inputStream.read(b, off, len);
		}

		@Override
		public long skip(long n) throws IOException {
			return inputStream.skip(n);
		}

		@Override
		public int available() throws IOException {
			return inputStream.available();
		}

		@Override
		public void close() throws IOException {
			inputStream.close();
		}

		@Override
		public synchronized void mark(int readlimit) {
			inputStream.mark(readlimit);
		}

		@Override
		public synchronized void reset() throws IOException {
			inputStream.reset();
		}

		@Override
		public boolean markSupported() {
			return inputStream.markSupported();
		}
	}
}
