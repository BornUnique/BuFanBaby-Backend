package com.bufanbaby.backend.rest.domain.auth;

import java.time.LocalDate;

public class FamilyMember {

	private String name;

	private String nickname;

	private String picture;

	private Relationship relationshipToOwner;

	private long dateCreated;

	private long dateModified;

	private int age;

	private LocalDate birthday;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Relationship getRelationshipToOwner() {
		return relationshipToOwner;
	}

	public void setRelationshipToOwner(Relationship relationshipToOwner) {
		this.relationshipToOwner = relationshipToOwner;
	}

	public long getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(long dateCreated) {
		this.dateCreated = dateCreated;
	}

	public long getDateModified() {
		return dateModified;
	}

	public void setDateModified(long dateModified) {
		this.dateModified = dateModified;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

}
