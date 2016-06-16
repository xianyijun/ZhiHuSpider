package me.kuye.spider.dao;

import me.kuye.spider.entity.User;
import me.kuye.spider.util.RedisUtil;

public class UserDao extends RedisBaseDao<User> {
	public boolean save(User user) {
		String key = "user:" + user.getHashId();
		String value = RedisUtil.objectToString(user);
		try {
			if (!redisManager.set(key, value).equals("0")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
