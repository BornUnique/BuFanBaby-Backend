package com.bufanbaby.backend.rest.domain.moment;

import java.util.List;

public class Tag {

	/**
	 * Owner's username as the base or primary tag name.
	 * <p>
	 * Its style is @laiyan :-)
	 */
	private String ownerTag;

	/**
	 * Spouse's name is used as the secondary tag name
	 * <p>
	 * Its style is @laiyan#kaiqin
	 */
	private String spouseTag;

	/**
	 * Child's name is used as the secondary tag name.
	 * <p>
	 * Its style is @laiyan##angie or @laiyan##sydney
	 */
	private List<String> childrenTags;

	public String getSpouseTag() {
		return spouseTag;
	}

	public void setSpouseTag(String spouseTag) {
		this.spouseTag = spouseTag;
	}

	public List<String> getChildrenTags() {
		return childrenTags;
	}

	public void setChildrenTags(List<String> childrenTags) {
		this.childrenTags = childrenTags;
	}

	public String getOwnerTag() {
		return ownerTag;
	}

	public void setOwnerTag(String ownerTag) {
		this.ownerTag = ownerTag;
	}

	@Override
	public String toString() {
		return String.format("Tag [ownerTag=%s, spouseTag=%s, childrenTags=%s]", ownerTag,
				spouseTag, childrenTags);
	}
}
