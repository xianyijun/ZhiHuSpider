package me.kuye.spider.Scheduler.remover;

import me.kuye.spider.entity.Request;

public interface DuplicateRemover {
	public boolean isDuplicate(Request request);
}
