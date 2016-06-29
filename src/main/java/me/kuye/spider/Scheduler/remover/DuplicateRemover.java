package me.kuye.spider.Scheduler.remover;

import org.apache.http.client.methods.HttpRequestBase;

public interface DuplicateRemover {
	public boolean isDuplicate(HttpRequestBase request);
}
