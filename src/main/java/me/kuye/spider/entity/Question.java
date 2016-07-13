package me.kuye.spider.entity;

import java.util.List;

public class Question implements Entity {

	private static final long serialVersionUID = -109150094039975443L;
	private long questionId;
	private String urlToken;// 用来获取回答
	private String url;// 绝对路径
	private String title;
	private String description;
	private long answerNum;
	private long answerFollowersNum;
	private long visitTimes;
	private List<String> topics;

	public Question() {

	}

	public Question(String url) {
		this.url = url;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(long answerNum) {
		this.answerNum = answerNum;
	}

	public long getAnswerFollowersNum() {
		return answerFollowersNum;
	}

	public void setAnswerFollowersNum(long answerFollowersNum) {
		this.answerFollowersNum = answerFollowersNum;
	}

	public long getVisitTimes() {
		return visitTimes;
	}

	public void setVisitTimes(long visitTimes) {
		this.visitTimes = visitTimes;
	}

	public List<String> getTopics() {
		return topics;
	}

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (answerFollowersNum ^ (answerFollowersNum >>> 32));
		result = prime * result + (int) (answerNum ^ (answerNum >>> 32));
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (questionId ^ (questionId >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((topics == null) ? 0 : topics.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((urlToken == null) ? 0 : urlToken.hashCode());
		result = prime * result + (int) (visitTimes ^ (visitTimes >>> 32));
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
		Question other = (Question) obj;
		if (answerFollowersNum != other.answerFollowersNum)
			return false;
		if (answerNum != other.answerNum)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (questionId != other.questionId)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
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
		if (urlToken == null) {
			if (other.urlToken != null)
				return false;
		} else if (!urlToken.equals(other.urlToken))
			return false;
		if (visitTimes != other.visitTimes)
			return false;
		return true;
	}

	public String getUrlToken() {
		return urlToken;
	}

	public void setUrlToken(String urlToken) {
		this.urlToken = urlToken;
	}

	@Override
	public String toString() {
		return "Question [urlToken=" + urlToken + ", url=" + url + ", title=" + title + ", description=" + description
				+ ", answerNum=" + answerNum + ", answerFollowersNum=" + answerFollowersNum + ", visitTimes="
				+ visitTimes + ", topics=" + topics + "]";
	}

	@Override
	public String getKey() {
		return "question";
	}

}
