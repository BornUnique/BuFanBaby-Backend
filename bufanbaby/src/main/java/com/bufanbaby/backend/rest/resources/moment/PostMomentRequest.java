package com.bufanbaby.backend.rest.resources.moment;

import javax.validation.constraints.NotNull;

import com.bufanbaby.backend.rest.domain.moment.ShareScope;
import com.bufanbaby.backend.rest.domain.moment.Tag;

public class PostMomentRequest {

	@NotNull
	private String feeling;

	private long epochMilliCreated;

	@NotNull
	private ShareScope shareScope;

	private String location;

	@NotNull
	private Tag tag;

	public String getFeeling() {
		return feeling;
	}

	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}

	public long getEpochMilliCreated() {
		return epochMilliCreated;
	}

	public void setEpochMilliCreated(long epochMilliCreated) {
		this.epochMilliCreated = epochMilliCreated;
	}

	public ShareScope getShareScope() {
		return shareScope;
	}

	public void setShareScope(ShareScope shareScope) {
		this.shareScope = shareScope;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
