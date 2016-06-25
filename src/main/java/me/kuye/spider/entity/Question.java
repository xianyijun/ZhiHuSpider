package me.kuye.spider.entity;

import java.util.List;

public class Question {
	private String url;// 绝对路径
	private String title;
	private String description;
	private long answerNum;
	private long answerFollowersNum;
	private long visitTimes;
	private String[] topics;
	private List<Answer> allAnswerList;
	private String createTime;

	public Question() {

	}

	public Question(String url) {
		this.url = url;
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

	public String[] getTopics() {
		return topics;
	}

	public void setTopics(String[] topics) {
		this.topics = topics;
	}

	public long getVisitTimes() {
		return visitTimes;
	}

	public void setVisitTimes(long visitTimes) {
		this.visitTimes = visitTimes;
	}

	public List<Answer> getAllAnswerList() {
		return allAnswerList;
	}

	public void setAllAnswerList(List<Answer> allAnswerList) {
		this.allAnswerList = allAnswerList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
