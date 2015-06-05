package com.bufanbaby.backend.rest.resources.moment;

import java.time.Instant;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

import com.bufanbaby.backend.rest.domain.moment.ShareWith;

public class PostMomentRequest {

	private long timeCreated;

	private String geographicLocation;

	private ShareWith shareWith;

	@NotEmpty
	private String feeling;

	@NotEmpty
	private Set<String> tags;

	public long getTimeCreated() {
		if (timeCreated == 0) {
			timeCreated = Instant.now().toEpochMilli();
		}
		return timeCreated;
	}

	public void setTimeCreated(long timeCreated) {
		this.timeCreated = timeCreated;
	}

	public String getGeographicLocation() {
		return geographicLocation;
	}

	public void setGeographicLocation(String geographicLocation) {
		this.geographicLocation = geographicLocation;
	}

	public ShareWith getShareWith() {
		if (shareWith == null) {
			return ShareWith.JUST_ME;
		}
		return shareWith;
	}

	public void setShareWith(ShareWith shareWith) {
		this.shareWith = shareWith;
	}

	public String getFeeling() {
		return feeling;
	}

	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return String
				.format("PostMomentRequest [timeCreated=%s, geographicLocation=%s, shareWith=%s, feeling=%s, tags=%s]",
						timeCreated, geographicLocation, shareWith, feeling, tags);
	}

}
