package me.kuye.spider.dao.redis;

import me.kuye.spider.manager.RedisManager;

public class RedisBaseDao<T> {
	protected static RedisManager redisManager = new RedisManager();
}
