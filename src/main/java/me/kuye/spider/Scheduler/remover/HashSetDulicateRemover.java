package me.kuye.spider.Scheduler.remover;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.core.Request;
import me.kuye.spider.util.MD5Util;

public class HashSetDulicateRemover implements DuplicateRemover {
	private Set<String> urlSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private static Logger logger = LoggerFactory.getLogger(HashSetDulicateRemover.class);

	@Override
	public boolean isDuplicate(Request request) {
		return !urlSet.add(getMD5Url(request));
	}

	private String getMD5Url(Request request) {
		logger.info(request.toString());
		return MD5Util.MD5Encode(request.getUrl() + request.hashCode());
	}

}
