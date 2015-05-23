package com.bufanbaby.backend.rest.services.moment.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bufanbaby.backend.rest.domain.moment.Moment;
import com.bufanbaby.backend.rest.repositories.MomentRepository;
import com.bufanbaby.backend.rest.services.moment.MomentService;

@Service
public class MomentServiceImpl implements MomentService {
	private static final Logger logger = LoggerFactory.getLogger(MomentService.class);

	private MomentRepository momentRepository;

	@Autowired
	public MomentServiceImpl(MomentRepository momentRepository) {
		this.momentRepository = momentRepository;
	}

	/**
	 * Save the uploaded file into the given path.
	 */
	@Override
	public void saveUploadedFile(InputStream is, String destPath, long maxBytesPerUploadedFile)
			throws IOException {
		SizeLimitedInputStream slis = new SizeLimitedInputStream(is, maxBytesPerUploadedFile);
		try {
			Files.copy(slis, Paths.get(destPath));
		} catch (IOException e) {
			logger.error("Error when saving file: " + destPath, e);
			throw e;
		} finally {
			try {
				slis.close();
			} catch (IOException e) {
				logger.info("Cannot close InputStream of file: " + destPath, e);
			}
		}
	}

	@Override
	public Moment save(Moment moment) {
		long momentId = momentRepository.create(moment);
		moment.setId(momentId);
		return moment;
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
