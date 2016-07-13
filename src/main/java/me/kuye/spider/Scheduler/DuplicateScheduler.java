package me.kuye.spider.Scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.Scheduler.remover.DuplicateRemover;
import me.kuye.spider.Scheduler.remover.HashSetDulicateRemover;
import me.kuye.spider.entity.Request;

public abstract class DuplicateScheduler implements Scheduler {
	private static Logger logger = LoggerFactory.getLogger(DuplicateScheduler.class);

	private DuplicateRemover remover = new HashSetDulicateRemover();

	@Override
	public void push(Request request) {
		if (!remover.isDuplicate(request)) {
			doPush(request);
		} else {
			logger.info(request.getUrl() + " 已经添加到请求队列中");
		}
	}

	protected void doPush(Request request) {

	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		DuplicateScheduler.logger = logger;
	}

	public DuplicateRemover getRemover() {
		return remover;
	}

	public DuplicateScheduler setRemover(DuplicateRemover remover) {
		this.remover = remover;
		return this;
	}

}
