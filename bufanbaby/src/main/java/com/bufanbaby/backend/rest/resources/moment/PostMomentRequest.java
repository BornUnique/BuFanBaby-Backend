package com.bufanbaby.backend.rest.resources.moment;

import javax.validation.constraints.NotNull;

import com.bufanbaby.backend.rest.domain.moment.ShareScope;
import com.bufanbaby.backend.rest.domain.moment.Tag;

public class PostMomentRequest {

	private String feeling;

	private long epochMilliCreated;

	@NotNull
	private ShareScope shareScope;

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

	@Override
	public String toString() {
		return String.format(
				"PostMomentRequest [feeling=%s, epochMilliCreated=%s, shareScope=%s, tag=%s]",
				feeling, epochMilliCreated, shareScope, tag);
	}

}
