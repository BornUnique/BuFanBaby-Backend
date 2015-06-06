package com.bufanbaby.backend.rest.services.moment;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.bufanbaby.backend.rest.domain.moment.Moment;

public interface MomentService {

	void saveUploadedFile(InputStream in, String destPath, long maxBytesPerUploadedFile)
			throws IOException;

	Moment save(Moment moment);

	List<Moment> findLatestMoments(long userId, int size);
}
