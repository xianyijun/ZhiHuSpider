package me.kuye.spider.Scheduler;

import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.Scheduler.remover.DuplicateRemover;
import me.kuye.spider.Scheduler.remover.HashDulicateRemover;

public abstract class Duplicatecheduler implements Scheduler {
	private static Logger logger = LoggerFactory.getLogger(Duplicatecheduler.class);

	private DuplicateRemover remover = new HashDulicateRemover();

	@Override
	public void push(HttpRequestBase request) {
		if (!remover.isDuplicate(request)) {
			doPush(request);
		}
	}

	protected void doPush(HttpRequestBase request) {

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
