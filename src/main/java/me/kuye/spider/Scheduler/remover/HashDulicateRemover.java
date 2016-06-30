package me.kuye.spider.Scheduler.remover;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashDulicateRemover implements DuplicateRemover {
	private Set<String> urlSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private static Logger logger = LoggerFactory.getLogger(HashDulicateRemover.class);

	@Override
	public boolean isDuplicate(HttpRequestBase request) {
		return !urlSet.add(getUrl(request));
	}

	private String getUrl(HttpRequestBase request) {
		logger.info(request.getURI().toString());
		return request.getURI().toString();
	}

}
