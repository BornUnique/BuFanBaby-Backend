package com.bufanbaby.backend.rest.resources.moment;

import java.net.URI;
import java.util.List;

import com.bufanbaby.backend.rest.domain.moment.FileMetadata;

public class PostMomentResponse {

	private long momentId;

	private URI self;

	private List<FileMetadata> fileMetadatas;

	public long getMomentId() {
		return momentId;
	}

	public void setMomentId(long momentId) {
		this.momentId = momentId;
	}

	public URI getSelf() {
		return self;
	}

	public void setSelf(URI self) {
		this.self = self;
	}

	public List<FileMetadata> getFileMetadatas() {
		return fileMetadatas;
	}

	public void setFileMetadatas(List<FileMetadata> fileMetadatas) {
		this.fileMetadatas = fileMetadatas;
	}

	@Override
	public String toString() {
		return String.format("PostMomentResponse [momentId=%s, self=%s, fileMetadatas=%s]",
				momentId, self, fileMetadatas);
	}

}
