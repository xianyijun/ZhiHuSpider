package me.kuye.spider.vo;

import java.util.Arrays;

public class ZhuanLanDetail {
	private long followersCount;
	private Creator creator;
	private Topic[] topics;
	private String activateState;
	private String href;
	private boolean acceptSubmission;
	private boolean firstTime;
	private PostTopic[] postTopics;
	private String pendingName;
	private Avatar avatar;
	private boolean canManage;
	private String description;
	private PendingTopic[] pendingTopics;
	private int nameCanEditUntil;
	private String reason;
	private int banUntil;
	private String slug;
	private String name;
	private String url;
	private String intro;
	private int topicsCanEditUntil;
	private String activateAuthorRequested;
	private String commentPermission;
	private boolean following;
	private int postsCount;
	private boolean canPost;

	public class PendingTopic {

	}

	public long getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(long followersCount) {
		this.followersCount = followersCount;
	}

	public Creator getCreator() {
		return creator;
	}

	public void setCreator(Creator creator) {
		this.creator = creator;
	}

	public Topic[] getTopics() {
		return topics;
	}

	public void setTopics(Topic[] topics) {
		this.topics = topics;
	}

	public String getActivateState() {
		return activateState;
	}

	public void setActivateState(String activateState) {
		this.activateState = activateState;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public boolean isAcceptSubmission() {
		return acceptSubmission;
	}

	public void setAcceptSubmission(boolean acceptSubmission) {
		this.acceptSubmission = acceptSubmission;
	}

	public boolean isFirstTime() {
		return firstTime;
	}

	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}

	public PostTopic[] getPostTopics() {
		return postTopics;
	}

	public void setPostTopics(PostTopic[] postTopics) {
		this.postTopics = postTopics;
	}

	public String getPendingName() {
		return pendingName;
	}

	public void setPendingName(String pendingName) {
		this.pendingName = pendingName;
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	public boolean isCanManage() {
		return canManage;
	}

	public void setCanManage(boolean canManage) {
		this.canManage = canManage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PendingTopic[] getPendingTopics() {
		return pendingTopics;
	}

	public void setPendingTopics(PendingTopic[] pendingTopics) {
		this.pendingTopics = pendingTopics;
	}

	public int getNameCanEditUntil() {
		return nameCanEditUntil;
	}

	public void setNameCanEditUntil(int nameCanEditUntil) {
		this.nameCanEditUntil = nameCanEditUntil;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getBanUntil() {
		return banUntil;
	}

	public void setBanUntil(int banUntil) {
		this.banUntil = banUntil;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getTopicsCanEditUntil() {
		return topicsCanEditUntil;
	}

	public void setTopicsCanEditUntil(int topicsCanEditUntil) {
		this.topicsCanEditUntil = topicsCanEditUntil;
	}

	public String getActivateAuthorRequested() {
		return activateAuthorRequested;
	}

	public void setActivateAuthorRequested(String activateAuthorRequested) {
		this.activateAuthorRequested = activateAuthorRequested;
	}

	public String getCommentPermission() {
		return commentPermission;
	}

	public void setCommentPermission(String commentPermission) {
		this.commentPermission = commentPermission;
	}

	public boolean isFollowing() {
		return following;
	}

	public void setFollowing(boolean following) {
		this.following = following;
	}

	public int getPostsCount() {
		return postsCount;
	}

	public void setPostsCount(int postsCount) {
		this.postsCount = postsCount;
	}

	public boolean isCanPost() {
		return canPost;
	}

	public void setCanPost(boolean canPost) {
		this.canPost = canPost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (acceptSubmission ? 1231 : 1237);
		result = prime * result + ((activateAuthorRequested == null) ? 0 : activateAuthorRequested.hashCode());
		result = prime * result + ((activateState == null) ? 0 : activateState.hashCode());
		result = prime * result + ((avatar == null) ? 0 : avatar.hashCode());
		result = prime * result + banUntil;
		result = prime * result + (canManage ? 1231 : 1237);
		result = prime * result + (canPost ? 1231 : 1237);
		result = prime * result + ((commentPermission == null) ? 0 : commentPermission.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (firstTime ? 1231 : 1237);
		result = prime * result + (int) (followersCount ^ (followersCount >>> 32));
		result = prime * result + (following ? 1231 : 1237);
		result = prime * result + ((href == null) ? 0 : href.hashCode());
		result = prime * result + ((intro == null) ? 0 : intro.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + nameCanEditUntil;
		result = prime * result + ((pendingName == null) ? 0 : pendingName.hashCode());
		result = prime * result + ((pendingTopics == null) ? 0 : pendingTopics.hashCode());
		result = prime * result + Arrays.hashCode(postTopics);
		result = prime * result + postsCount;
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((slug == null) ? 0 : slug.hashCode());
		result = prime * result + Arrays.hashCode(topics);
		result = prime * result + topicsCanEditUntil;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZhuanLanDetail other = (ZhuanLanDetail) obj;
		if (acceptSubmission != other.acceptSubmission)
			return false;
		if (activateAuthorRequested == null) {
			if (other.activateAuthorRequested != null)
				return false;
		} else if (!activateAuthorRequested.equals(other.activateAuthorRequested))
			return false;
		if (activateState == null) {
			if (other.activateState != null)
				return false;
		} else if (!activateState.equals(other.activateState))
			return false;
		if (avatar == null) {
			if (other.avatar != null)
				return false;
		} else if (!avatar.equals(other.avatar))
			return false;
		if (banUntil != other.banUntil)
			return false;
		if (canManage != other.canManage)
			return false;
		if (canPost != other.canPost)
			return false;
		if (commentPermission == null) {
			if (other.commentPermission != null)
				return false;
		} else if (!commentPermission.equals(other.commentPermission))
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (firstTime != other.firstTime)
			return false;
		if (followersCount != other.followersCount)
			return false;
		if (following != other.following)
			return false;
		if (href == null) {
			if (other.href != null)
				return false;
		} else if (!href.equals(other.href))
			return false;
		if (intro == null) {
			if (other.intro != null)
				return false;
		} else if (!intro.equals(other.intro))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nameCanEditUntil != other.nameCanEditUntil)
			return false;
		if (pendingName == null) {
			if (other.pendingName != null)
				return false;
		} else if (!pendingName.equals(other.pendingName))
			return false;
		if (pendingTopics == null) {
			if (other.pendingTopics != null)
				return false;
		} else if (!pendingTopics.equals(other.pendingTopics))
			return false;
		if (!Arrays.equals(postTopics, other.postTopics))
			return false;
		if (postsCount != other.postsCount)
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (slug == null) {
			if (other.slug != null)
				return false;
		} else if (!slug.equals(other.slug))
			return false;
		if (!Arrays.equals(topics, other.topics))
			return false;
		if (topicsCanEditUntil != other.topicsCanEditUntil)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ZhuanLanDetail [followersCount=" + followersCount + ", creator=" + creator + ", topics="
				+ Arrays.toString(topics) + ", activateState=" + activateState + ", href=" + href
				+ ", acceptSubmission=" + acceptSubmission + ", firstTime=" + firstTime + ", postTopics="
				+ Arrays.toString(postTopics) + ", pendingName=" + pendingName + ", avatar=" + avatar + ", canManage="
				+ canManage + ", description=" + description + ", pendingTopics=" + pendingTopics
				+ ", nameCanEditUntil=" + nameCanEditUntil + ", reason=" + reason + ", banUntil=" + banUntil + ", slug="
				+ slug + ", name=" + name + ", url=" + url + ", intro=" + intro + ", topicsCanEditUntil="
				+ topicsCanEditUntil + ", activateAuthorRequested=" + activateAuthorRequested + ", commentPermission="
				+ commentPermission + ", following=" + following + ", postsCount=" + postsCount + ", canPost=" + canPost
				+ ", getFollowersCount()=" + getFollowersCount() + ", getCreator()=" + getCreator() + ", getTopics()="
				+ Arrays.toString(getTopics()) + ", getActivateState()=" + getActivateState() + ", getHref()="
				+ getHref() + ", isAcceptSubmission()=" + isAcceptSubmission() + ", isFirstTime()=" + isFirstTime()
				+ ", getPostTopics()=" + Arrays.toString(getPostTopics()) + ", getPendingName()=" + getPendingName()
				+ ", getAvatar()=" + getAvatar() + ", isCanManage()=" + isCanManage() + ", getDescription()="
				+ getDescription() + ", getPendingTopics()=" + getPendingTopics() + ", getNameCanEditUntil()="
				+ getNameCanEditUntil() + ", getReason()=" + getReason() + ", getBanUntil()=" + getBanUntil()
				+ ", getSlug()=" + getSlug() + ", getName()=" + getName() + ", getUrl()=" + getUrl() + ", getIntro()="
				+ getIntro() + ", getTopicsCanEditUntil()=" + getTopicsCanEditUntil()
				+ ", getActivateAuthorRequested()=" + getActivateAuthorRequested() + ", getCommentPermission()="
				+ getCommentPermission() + ", isFollowing()=" + isFollowing() + ", getPostsCount()=" + getPostsCount()
				+ ", isCanPost()=" + isCanPost() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass()
				+ ", toString()=" + super.toString() + "]";
	}
}
