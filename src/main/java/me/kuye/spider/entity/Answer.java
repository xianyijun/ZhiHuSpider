package me.kuye.spider.entity;

import java.util.List;

public class Answer {
	private String url;
	private Question question;
	private String author;
	private long upvote;
	/*
	 * https://www.zhihu.com/answer/38441951/voters_profile?&offset=10
	 * 根据answer的data-aid获取点赞用户列表，然后根据返回的json数据的next是否为空判断 此处存储点赞用户的url地址
	 * 
	 */
	private List<String> upvoteUserList;// 点赞用户列表

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public long getUpvote() {
		return upvote;
	}

	public void setUpvote(long upvote) {
		this.upvote = upvote;
	}

	public List<String> getUpvoteUserList() {
		return upvoteUserList;
	}

	public void setUpvoteUserList(List<String> upvoteUserList) {
		this.upvoteUserList = upvoteUserList;
	}

}
