package me.kuye.spider.pipeline;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

import me.kuye.spider.entity.User;

public class ResultItem {
	private Set<String> urlSet = new ConcurrentSkipListSet<String>();
	private Queue<User> userList = new ConcurrentLinkedQueue<User>();

	public ResultItem() {
	}

	public Set<String> getUrlSet() {
		return urlSet;
	}

	public void setUrlSet(Set<String> urlSet) {
		this.urlSet = urlSet;
	}

	public Queue<User> getUserList() {
		return userList;
	}

	public void setUserList(Queue<User> userList) {
		this.userList = userList;
	}

}
