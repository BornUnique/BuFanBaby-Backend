package com.bufanbaby.backend.rest.services.moments;

import java.io.IOException;
import java.io.InputStream;

import com.bufanbaby.backend.rest.domain.Moment;

public interface MomentService {

	void saveUploadedFile(InputStream in, String destPath, long maxBytesPerUploadedFile)
			throws IOException;

	Moment save(Moment moment);
}
