package me.kuye.spider.entity;

import java.util.List;

public class Question {
	private String url;// 绝对路径
	private String title;
	private long answerNum;
	private long answerFollowersNum;
	private String[] topics;
	private long visitTimes;
	private Answer topAnswer;
	private List<Answer> topAnswerList;
	private List<Answer> allAnswerList;
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
	public Answer getTopAnswer() {
		return topAnswer;
	}
	public void setTopAnswer(Answer topAnswer) {
		this.topAnswer = topAnswer;
	}
	public List<Answer> getTopAnswerList() {
		return topAnswerList;
	}
	public void setTopAnswerList(List<Answer> topAnswerList) {
		this.topAnswerList = topAnswerList;
	}
	public List<Answer> getAllAnswerList() {
		return allAnswerList;
	}
	public void setAllAnswerList(List<Answer> allAnswerList) {
		this.allAnswerList = allAnswerList;
	}
	
}
