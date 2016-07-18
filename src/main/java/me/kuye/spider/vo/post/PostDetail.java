package me.kuye.spider.vo.post;

public class PostDetail {
	private Author author;
	private boolean canComment;
	private String commentPermission;
	private int commentsCount;
	private String content;
	private String href;
	private boolean isTitleImageFullScreen;
	private int likesCount;
	private Link links;
	private Meta meta;
	private String publishedTime;
	private String rating;
	private int slug;
	private String snapshotUrl;
	private String sourceUrl;
	private String state;
	private String summary;
	private String title;
	private String titleImage;
	private String url;
	
	public Author getAuthor() {
		return author;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}

	public boolean isCanComment() {
		return canComment;
	}

	public void setCanComment(boolean canComment) {
		this.canComment = canComment;
	}
	
	public String getCommentPermission() {
		return commentPermission;
	}

	public void setCommentPermission(String commentPermission) {
		this.commentPermission = commentPermission;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public boolean isTitleImageFullScreen() {
		return isTitleImageFullScreen;
	}

	public void setTitleImageFullScreen(boolean isTitleImageFullScreen) {
		this.isTitleImageFullScreen = isTitleImageFullScreen;
	}

	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	public Link getLinks() {
		return links;
	}

	public void setLinks(Link links) {
		this.links = links;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public String getPublishedTime() {
		return publishedTime;
	}

	public void setPublishedTime(String publishedTime) {
		this.publishedTime = publishedTime;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public int getSlug() {
		return slug;
	}

	public void setSlug(int slug) {
		this.slug = slug;
	}

	public String getSnapshotUrl() {
		return snapshotUrl;
	}

	public void setSnapshotUrl(String snapshotUrl) {
		this.snapshotUrl = snapshotUrl;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + (canComment ? 1231 : 1237);
		result = prime * result + ((commentPermission == null) ? 0 : commentPermission.hashCode());
		result = prime * result + commentsCount;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((href == null) ? 0 : href.hashCode());
		result = prime * result + (isTitleImageFullScreen ? 1231 : 1237);
		result = prime * result + likesCount;
		result = prime * result + ((links == null) ? 0 : links.hashCode());
		result = prime * result + ((meta == null) ? 0 : meta.hashCode());
		result = prime * result + ((publishedTime == null) ? 0 : publishedTime.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + slug;
		result = prime * result + ((snapshotUrl == null) ? 0 : snapshotUrl.hashCode());
		result = prime * result + ((sourceUrl == null) ? 0 : sourceUrl.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((summary == null) ? 0 : summary.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((titleImage == null) ? 0 : titleImage.hashCode());
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
		PostDetail other = (PostDetail) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (canComment != other.canComment)
			return false;
		if (commentPermission == null) {
			if (other.commentPermission != null)
				return false;
		} else if (!commentPermission.equals(other.commentPermission))
			return false;
		if (commentsCount != other.commentsCount)
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (href == null) {
			if (other.href != null)
				return false;
		} else if (!href.equals(other.href))
			return false;
		if (isTitleImageFullScreen != other.isTitleImageFullScreen)
			return false;
		if (likesCount != other.likesCount)
			return false;
		if (links == null) {
			if (other.links != null)
				return false;
		} else if (!links.equals(other.links))
			return false;
		if (meta == null) {
			if (other.meta != null)
				return false;
		} else if (!meta.equals(other.meta))
			return false;
		if (publishedTime == null) {
			if (other.publishedTime != null)
				return false;
		} else if (!publishedTime.equals(other.publishedTime))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		if (slug != other.slug)
			return false;
		if (snapshotUrl == null) {
			if (other.snapshotUrl != null)
				return false;
		} else if (!snapshotUrl.equals(other.snapshotUrl))
			return false;
		if (sourceUrl == null) {
			if (other.sourceUrl != null)
				return false;
		} else if (!sourceUrl.equals(other.sourceUrl))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (summary == null) {
			if (other.summary != null)
				return false;
		} else if (!summary.equals(other.summary))
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
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "PostDetail [author=" + author + ", canComment=" + canComment + ", commentPermission="
				+ commentPermission + ", commentsCount=" + commentsCount + ", content=" + content + ", href=" + href
				+ ", isTitleImageFullScreen=" + isTitleImageFullScreen + ", likesCount=" + likesCount + ", links="
				+ links + ", meta=" + meta + ", publishedTime=" + publishedTime + ", rating=" + rating + ", slug="
				+ slug + ", snapshotUrl=" + snapshotUrl + ", sourceUrl=" + sourceUrl + ", state=" + state + ", summary="
				+ summary + ", title=" + title + ", titleImage=" + titleImage + ", url=" + url + "]";
	}

}
