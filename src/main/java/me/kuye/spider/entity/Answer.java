package me.kuye.spider.entity;

import java.util.List;

public class Answer implements Entity {

	private static final long serialVersionUID = -3316280175987809356L;
	private String absUrl;
	private String relativeUrl;
	private String author;
	private String upvote;
	private String content;
	private String dataAid;
	private String urlToken;//回答对应问题的urlToken
	private String startUpvoteUserUrl;
	/*
	 * https://www.zhihu.com/answer/38441951/voters_profile?&offset=10
	 * 根据answer的data-aid获取点赞用户列表，然后根据返回的json数据的next是否为空判断 此处存储点赞用户的url地址
	 * 
	 */
	private List<UpVoteUser> upvoteUserList;// 点赞用户列表

	public Answer(String relativeUrl, String absUrl) {
		this.relativeUrl = relativeUrl;
		this.absUrl = absUrl;
	}

	public String getAbsUrl() {
		return absUrl;
	}

	public void setAbsUrl(String absUrl) {
		this.absUrl = absUrl;
	}

	public String getRelativeUrl() {
		return relativeUrl;
	}

	public void setRelativeUrl(String relativeUrl) {
		this.relativeUrl = relativeUrl;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUpvote() {
		return upvote;
	}

	public void setUpvote(String upvote) {
		this.upvote = upvote;
	}

	public List<UpVoteUser> getUpvoteUserList() {
		return upvoteUserList;
	}

	public void setUpvoteUserList(List<UpVoteUser> upvoteUserList) {
		this.upvoteUserList = upvoteUserList;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDataAid() {
		return dataAid;
	}

	public void setDataAid(String dataAid) {
		this.dataAid = dataAid;
	}

	public String getStartUpvoteUserUrl() {
		return startUpvoteUserUrl;
	}

	public void setStartUpvoteUserUrl(String startUpvoteUserUrl) {
		this.startUpvoteUserUrl = startUpvoteUserUrl;
	}

	public String getUrlToken() {
		return urlToken;
	}

	public void setUrlToken(String urlToken) {
		this.urlToken = urlToken;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((absUrl == null) ? 0 : absUrl.hashCode());
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((dataAid == null) ? 0 : dataAid.hashCode());
		result = prime * result + ((relativeUrl == null) ? 0 : relativeUrl.hashCode());
		result = prime * result + ((startUpvoteUserUrl == null) ? 0 : startUpvoteUserUrl.hashCode());
		result = prime * result + ((upvote == null) ? 0 : upvote.hashCode());
		result = prime * result + ((upvoteUserList == null) ? 0 : upvoteUserList.hashCode());
		result = prime * result + ((urlToken == null) ? 0 : urlToken.hashCode());
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
		Answer other = (Answer) obj;
		if (absUrl == null) {
			if (other.absUrl != null)
				return false;
		} else if (!absUrl.equals(other.absUrl))
			return false;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (dataAid == null) {
			if (other.dataAid != null)
				return false;
		} else if (!dataAid.equals(other.dataAid))
			return false;
		if (relativeUrl == null) {
			if (other.relativeUrl != null)
				return false;
		} else if (!relativeUrl.equals(other.relativeUrl))
			return false;
		if (startUpvoteUserUrl == null) {
			if (other.startUpvoteUserUrl != null)
				return false;
		} else if (!startUpvoteUserUrl.equals(other.startUpvoteUserUrl))
			return false;
		if (upvote == null) {
			if (other.upvote != null)
				return false;
		} else if (!upvote.equals(other.upvote))
			return false;
		if (upvoteUserList == null) {
			if (other.upvoteUserList != null)
				return false;
		} else if (!upvoteUserList.equals(other.upvoteUserList))
			return false;
		if (urlToken == null) {
			if (other.urlToken != null)
				return false;
		} else if (!urlToken.equals(other.urlToken))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Answer [absUrl=" + absUrl + ", relativeUrl=" + relativeUrl + ", author=" + author + ", upvote=" + upvote
				+ ", content=" + content + ", dataAid=" + dataAid + ", urlToken=" + urlToken + ", startUpvoteUserUrl="
				+ startUpvoteUserUrl + ", upvoteUserList=" + upvoteUserList + "]";
	}

	@Override
	public String getKey() {
		return "answer";
	}

}
