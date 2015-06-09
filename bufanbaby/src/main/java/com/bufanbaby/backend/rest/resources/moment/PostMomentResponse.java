package com.bufanbaby.backend.rest.resources.moment;

import java.net.URI;
import java.util.List;

public class PostMomentResponse {

	private long momentId;

	private URI self;

	private List<FileMetadataResponse> fileMetadatas;

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

	public List<FileMetadataResponse> getFileMetadatas() {
		return fileMetadatas;
	}

	public void setFileMetadatas(List<FileMetadataResponse> fileMetadatas) {
		this.fileMetadatas = fileMetadatas;
	}

	@Override
	public String toString() {
		return String.format("PostMomentResponse [momentId=%s, self=%s, fileMetadatas=%s]",
				momentId, self, fileMetadatas);
	}

}
