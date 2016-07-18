package me.kuye.spider.Scheduler;

import me.kuye.spider.core.Request;

public interface Scheduler {
	public void push(Request request);
	
	public Request poll();
	
}
