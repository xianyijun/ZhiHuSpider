package me.kuye.spider.Scheduler.remover;

import me.kuye.spider.core.Request;

public interface DuplicateRemover {
	public boolean isDuplicate(Request request);
}
