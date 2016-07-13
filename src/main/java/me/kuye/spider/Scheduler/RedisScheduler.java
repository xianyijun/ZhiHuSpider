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
	protected RedisManager redisManager = new RedisManager();
	private static final String SET_KEY = "zhihu_set";
	private static final String QUEUE_KEY = "zhihu_queue";
	private static final String ITEM_KEY = "zhihu_item";

	public RedisScheduler() {
		setRemover(this);
	}

	@Override
	public Request poll() {
		String url = getNextUrl();
		return doPoll(url);
	}

	/**
	* @Title: getNextUrl
	* @Description: 返回下个请求对应的url
	* @param     参数
	* @return String    返回类型
	* @throws
	*/
	protected String getNextUrl() {
		return redisManager.lpop(QUEUE_KEY);
	}

	protected Request doPoll(String url) {
		if (url == null) {
			return null;
		}
		String field = MD5Util.MD5Encode(url);
		String value = redisManager.hget(getItemKey(), field);
		if (value != null) {
			Request request = JSON.parseObject(value, Request.class);
			logger.info(request.getUrl() + "从请求队列中弹出");
			return request;
		}
		return null;
	}

	@Override
	protected void doPush(Request request) {
		logger.info(request.getUrl() + "添加到请求队列中");
		if (doQueuePush(request)) {
			String field = MD5Util.MD5Encode(request.getUrl());
			String value = JSON.toJSONString(request);
			redisManager.hset(getItemKey(), field, value);

		}
	}

	private boolean doQueuePush(Request request) {
		String key = null;
		try {
			key = getQueueKey(request);
			redisManager.rpush(key, request.getUrl());
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
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

	/**
	* @Title: getQueueKey
	* @Description: 获取url在redis中对应的key值
	* @param     参数
	* @return String    返回类型
	* @throws
	*/
	protected String getQueueKey(Request request) {
		return QUEUE_KEY;
	}

	private String getItemKey() {
		return ITEM_KEY;
	}
}
