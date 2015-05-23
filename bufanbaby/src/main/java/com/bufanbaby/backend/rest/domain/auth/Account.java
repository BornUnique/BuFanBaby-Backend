package com.bufanbaby.backend.rest.domain.auth;

import java.util.List;

public class Account {

	/**
	 * The id of this account
	 */
	private long id;

	/**
	 * Current mood of the user
	 */
	private String mood;

	/**
	 * The url to the user original profile picture
	 */
	private String originalPicture;

	/**
	 * The url to the user thumbnail profile picture
	 */
	private String thumbnailPicture;

	/**
	 * The owner/creator of this acount
	 */
	private User owner;

	/**
	 * The list of family members related to this
	 */
	private List<FamilyMember> familyMembers;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public String getOriginalPicture() {
		return originalPicture;
	}

	public void setOriginalPicture(String originalPicture) {
		this.originalPicture = originalPicture;
	}

	public String getThumbnailPicture() {
		return thumbnailPicture;
	}

	public void setThumbnailPicture(String thumbnailPicture) {
		this.thumbnailPicture = thumbnailPicture;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<FamilyMember> getFamilyMembers() {
		return familyMembers;
	}

	public void setFamilyMembers(List<FamilyMember> familyMembers) {
		this.familyMembers = familyMembers;
	}
}
