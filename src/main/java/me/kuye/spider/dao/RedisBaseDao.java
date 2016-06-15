package me.kuye.spider.dao;

import me.kuye.spider.manager.RedisManager;

public class RedisBaseDao<T> {
	protected static RedisManager redisManager = new RedisManager();
}
