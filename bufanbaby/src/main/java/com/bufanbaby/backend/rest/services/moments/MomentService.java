package com.bufanbaby.backend.rest.services.moments;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface MomentService {

	void saveUploadedFile(InputStream in, Path destPath, long maxBytesPerUploadedFile)
			throws IOException;
}
