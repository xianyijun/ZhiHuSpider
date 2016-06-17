package me.kuye.spider.pipeline.redis;

import me.kuye.spider.entity.UrlItem;

public class UrlItemDao extends RedisBaseDao<UrlItem> {
	public boolean exist(String url) {
		return redisManager.sismember("user:url", url);
	}

	public boolean add(String url) {
		return redisManager.sadd("user:url", url) != 0;
	}
}
