package me.kuye.spider.entity;

public class Column implements Entity {
	private long columnId;
	private String name;
	private String creatorName;
	private String hashId;//creator HashId
	private String topics;
	private String postTopics;//topic:postCount,topic:postCount
	private String intro;
	private String description;
	private String url;
	private long postsCount;
	private long followersCount;
	private String reason;
	
	public long getColumnId() {
		return columnId;
	}

	public void setColumnId(long columnId) {
		this.columnId = columnId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getHashId() {
		return hashId;
	}

	public void setHashId(String hashId) {
		this.hashId = hashId;
	}

	public String getTopics() {
		return topics;
	}

	public void setTopics(String topics) {
		this.topics = topics;
	}

	public String getPostTopics() {
		return postTopics;
	}

	public void setPostTopics(String postTopics) {
		this.postTopics = postTopics;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getPostsCount() {
		return postsCount;
	}

	public void setPostsCount(long postsCount) {
		this.postsCount = postsCount;
	}

	public long getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(long followersCount) {
		this.followersCount = followersCount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String getKey() {
		return "column";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (columnId ^ (columnId >>> 32));
		result = prime * result + ((creatorName == null) ? 0 : creatorName.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (followersCount ^ (followersCount >>> 32));
		result = prime * result + ((hashId == null) ? 0 : hashId.hashCode());
		result = prime * result + ((intro == null) ? 0 : intro.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((postTopics == null) ? 0 : postTopics.hashCode());
		result = prime * result + (int) (postsCount ^ (postsCount >>> 32));
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((topics == null) ? 0 : topics.hashCode());
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
		Column other = (Column) obj;
		if (columnId != other.columnId)
			return false;
		if (creatorName == null) {
			if (other.creatorName != null)
				return false;
		} else if (!creatorName.equals(other.creatorName))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (followersCount != other.followersCount)
			return false;
		if (hashId == null) {
			if (other.hashId != null)
				return false;
		} else if (!hashId.equals(other.hashId))
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
		if (postTopics == null) {
			if (other.postTopics != null)
				return false;
		} else if (!postTopics.equals(other.postTopics))
			return false;
		if (postsCount != other.postsCount)
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (topics == null) {
			if (other.topics != null)
				return false;
		} else if (!topics.equals(other.topics))
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
		return "Column [columnId=" + columnId + ", name=" + name + ", creatorName=" + creatorName + ", hashId=" + hashId
				+ ", topics=" + topics + ", postTopics=" + postTopics + ", intro=" + intro + ", description="
				+ description + ", url=" + url + ", postsCount=" + postsCount + ", followersCount=" + followersCount
				+ ", reason=" + reason + "]";
	}
	
}
