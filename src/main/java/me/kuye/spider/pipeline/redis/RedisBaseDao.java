package me.kuye.spider.pipeline.redis;

import me.kuye.spider.manager.RedisManager;

public class RedisBaseDao<T> {
	protected static RedisManager redisManager = new RedisManager();
}
