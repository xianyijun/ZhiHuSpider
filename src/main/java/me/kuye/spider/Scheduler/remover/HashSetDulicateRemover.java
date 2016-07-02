package me.kuye.spider.Scheduler.remover;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.entity.Request;

public class HashSetDulicateRemover implements DuplicateRemover {
	private Set<String> urlSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private static Logger logger = LoggerFactory.getLogger(HashSetDulicateRemover.class);

	@Override
	public boolean isDuplicate(Request request) {
		return !urlSet.add(getUrl(request));
	}

	private String getUrl(Request request) {
		logger.info(request.getUrl());
		return request.getUrl();
	}

}
