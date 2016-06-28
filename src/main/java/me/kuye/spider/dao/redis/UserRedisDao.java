package me.kuye.spider.dao.redis;

import com.alibaba.fastjson.JSON;

import me.kuye.spider.entity.User;

public class UserRedisDao extends RedisBaseDao<User> {
	private static final String USER_KEY = "user";
	private static final String NAMESPACE_SPERATOR = ":";
	private static final String USER_INC_KEY = USER_KEY + NAMESPACE_SPERATOR + "incr";
	private static final String USER_HASH_ID_KEY = USER_KEY + NAMESPACE_SPERATOR + "hashId";

	public boolean save(User user) {
		String key = USER_KEY + NAMESPACE_SPERATOR + generateNextId(USER_INC_KEY);
		String jsonObject = JSON.toJSONString(user);
		try {
			if (redisManager.setnx(key, jsonObject) != 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean exist(String hashId) {
		return redisManager.sismember(USER_HASH_ID_KEY, hashId);
	}

	private Long generateNextId(String key) {
		return redisManager.incr(key);
	}
}
