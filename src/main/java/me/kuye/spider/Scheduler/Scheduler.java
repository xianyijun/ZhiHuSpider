package me.kuye.spider.Scheduler;

import me.kuye.spider.entity.Request;

public interface Scheduler {
	public void push(Request request);
	
	public Request poll();
	
}
