package com.bufanbaby.backend.rest.domain;

import java.util.List;

/**
 * moments timeline
 * 
 * TODO: Dashborad (Home) Timeline displaying all the posts: from followed, from
 * business users
 * 
 * 
 * @author lchi
 *
 */
public class Timeline {

	/**
	 * The timeline id
	 */
	private long id;

	/**
	 * The tag name which this timeline belongs to
	 */
	private String tagName;

	/**
	 * The moments which this timeline has
	 */
	private List<Moment> moment;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public List<Moment> getMoment() {
		return moment;
	}

	public void setMoment(List<Moment> moment) {
		this.moment = moment;
	}

	@Override
	public String toString() {
		return String.format("Timeline [id=%s, tagName=%s, moment=%s]", id, tagName, moment);
	}

}
