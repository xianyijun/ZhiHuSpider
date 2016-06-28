package me.kuye.spider.Scheduler;

import org.apache.http.client.methods.HttpRequestBase;

public interface Scheduler {
	public void push(HttpRequestBase request);
	
	public HttpRequestBase poll();
}
