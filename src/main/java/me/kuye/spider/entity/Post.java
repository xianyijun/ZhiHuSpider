package me.kuye.spider.entity;

public class Post implements Entity {
	private static final long serialVersionUID = 7621893832853564110L;
	private long postId;
	private String title;
	private String titleImage;
	private String postUrl;//相对地址
	private String hashId;//作者的HashId
	private String authorName;//信息冗余，文章作者名字
	private int commentsCount;//评论的条数
	private String commetsUrl;//文章评论的url
	private String publishedTime;//文章的发布时间
	private String content;
	
	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public String getHashId() {
		return hashId;
	}

	public void setHashId(String hashId) {
		this.hashId = hashId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}

	public String getCommetsUrl() {
		return commetsUrl;
	}

	public void setCommetsUrl(String commetsUrl) {
		this.commetsUrl = commetsUrl;
	}

	public String getPublishedTime() {
		return publishedTime;
	}

	public void setPublishedTime(String publishedTime) {
		this.publishedTime = publishedTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}

	public String getPostUrl() {
		return postUrl;
	}

	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String getKey() {
		return "post";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorName == null) ? 0 : authorName.hashCode());
		result = prime * result + commentsCount;
		result = prime * result + ((commetsUrl == null) ? 0 : commetsUrl.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((hashId == null) ? 0 : hashId.hashCode());
		result = prime * result + (int) (postId ^ (postId >>> 32));
		result = prime * result + ((postUrl == null) ? 0 : postUrl.hashCode());
		result = prime * result + ((publishedTime == null) ? 0 : publishedTime.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((titleImage == null) ? 0 : titleImage.hashCode());
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
		Post other = (Post) obj;
		if (authorName == null) {
			if (other.authorName != null)
				return false;
		} else if (!authorName.equals(other.authorName))
			return false;
		if (commentsCount != other.commentsCount)
			return false;
		if (commetsUrl == null) {
			if (other.commetsUrl != null)
				return false;
		} else if (!commetsUrl.equals(other.commetsUrl))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (hashId == null) {
			if (other.hashId != null)
				return false;
		} else if (!hashId.equals(other.hashId))
			return false;
		if (postId != other.postId)
			return false;
		if (postUrl == null) {
			if (other.postUrl != null)
				return false;
		} else if (!postUrl.equals(other.postUrl))
			return false;
		if (publishedTime == null) {
			if (other.publishedTime != null)
				return false;
		} else if (!publishedTime.equals(other.publishedTime))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (titleImage == null) {
			if (other.titleImage != null)
				return false;
		} else if (!titleImage.equals(other.titleImage))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Post [hashId=" + hashId + ", authorName=" + authorName + ", content=" + content + ", commentsCount="
				+ commentsCount + ", commetsUrl=" + commetsUrl + ", publishedTime=" + publishedTime + ", title=" + title
				+ ", titleImage=" + titleImage + ", postUrl=" + postUrl + "]";
	}
}
