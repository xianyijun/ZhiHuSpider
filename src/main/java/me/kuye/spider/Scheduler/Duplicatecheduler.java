package me.kuye.spider.Scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.Scheduler.remover.DuplicateRemover;
import me.kuye.spider.Scheduler.remover.RedisDuplicateRemover;
import me.kuye.spider.entity.Request;

public abstract class Duplicatecheduler implements Scheduler {
	private static Logger logger = LoggerFactory.getLogger(Duplicatecheduler.class);

	private DuplicateRemover remover = new RedisDuplicateRemover();

	@Override
	public void push(Request request) {
		if (!remover.isDuplicate(request)) {
			doPush(request);
		} else {
			logger.info(request.getUrl() + " 已经抓取过了");
		}
	}

	protected void doPush(Request request) {

	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		Duplicatecheduler.logger = logger;
	}

	public DuplicateRemover getRemover() {
		return remover;
	}

	public Duplicatecheduler setRemover(DuplicateRemover remover) {
		this.remover = remover;
		return this;
	}

}
