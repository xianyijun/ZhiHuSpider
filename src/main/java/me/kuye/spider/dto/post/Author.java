package me.kuye.spider.dto.post;

import me.kuye.spider.dto.column.Avatar;

public class Author {
	private Avatar avatar;
	private String bio;
	private String description;
	private String hash;
	private boolean isOrg;
	private String profileUrl;
	private String name;
	private String slug;
	public Avatar getAvatar() {
		return avatar;
	}
	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public boolean isOrg() {
		return isOrg;
	}
	public void setOrg(boolean isOrg) {
		this.isOrg = isOrg;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	
}
