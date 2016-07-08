package me.kuye.spider.Scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import me.kuye.spider.Scheduler.remover.DuplicateRemover;
import me.kuye.spider.entity.Request;
import me.kuye.spider.manager.RedisManager;
import me.kuye.spider.util.MD5Util;

public class RedisScheduler extends DuplicateScheduler implements DuplicateRemover {
	private static Logger logger = LoggerFactory.getLogger(RedisScheduler.class);
	private RedisManager redisManager = new RedisManager();
	private static final String SET_KEY = "zhihu_set";
	private static final String QUEUE_KEY = "zhihu_queue";
	private static final String ITEM_KEY = "zhihu_item";

	public RedisScheduler() {
		setRemover(this);
	}

	@Override
	public Request poll() {
		String url = redisManager.lpop(QUEUE_KEY);
		if (url == null) {
			return null;
		}
		String field = MD5Util.MD5Encode(url);
		String value = redisManager.hget(getItemKey(), field);
		if (value != null) {
			Request request = JSON.parseObject(value, Request.class);
			return request;
		}
		return null;
	}

	@Override
	protected void doPush(Request request) {
		redisManager.rpush(getQueueKey(), request.getUrl());
		String field = MD5Util.MD5Encode(request.getUrl());
		String value = JSON.toJSONString(request);
		redisManager.hset(getItemKey(), field, value);
	}

	@Override
	public boolean isDuplicate(Request request) {
		boolean isDuplicate = redisManager.sismember(getSetKey(request), request.getUrl());
		if (!isDuplicate) {
			redisManager.sadd(getSetKey(request), request.getUrl());
		}
		return isDuplicate;
	}

	private String getSetKey(Request request) {
		return SET_KEY;
	}

	private String getQueueKey() {
		return QUEUE_KEY;
	}

	private String getItemKey() {
		return ITEM_KEY;
	}
}
