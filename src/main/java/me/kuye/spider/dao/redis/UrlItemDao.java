package me.kuye.spider.dao.redis;

public class UrlItemDao extends RedisBaseDao{
	public boolean exist(String url) {
		return redisManager.sismember("url", url);
	}

	public boolean add(String url) {
		return redisManager.sadd("url", url) != 0;
	}
}
