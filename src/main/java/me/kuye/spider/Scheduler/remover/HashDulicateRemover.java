package me.kuye.spider.Scheduler.remover;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.client.methods.HttpRequestBase;

public class HashDulicateRemover implements DuplicateRemover {
	private Set<String> urlSet = Collections.newSetFromMap(new ConcurrentHashMap<>());

	@Override
	public boolean isDuplicate(HttpRequestBase request) {
		return !urlSet.add(getUrl(request));
	}

	private String getUrl(HttpRequestBase request) {
		return request.getURI().toString();
	}

}
