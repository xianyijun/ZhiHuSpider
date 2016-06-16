package me.kuye.spider.dao;

import com.alibaba.fastjson.JSON;

import me.kuye.spider.entity.User;

public class UserDao extends RedisBaseDao<User> {
	private static final String USER_KEY = "user";
	private static final String USER_INC_KEY = "user:inc";
	private static final String NAMESPACE_SPERATOR = ":";

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
		String key = "user:" + hashId;
		return redisManager.exists(key);
	}

	private Long generateNextId(String key) {
		return redisManager.incr(key);
	}
}
