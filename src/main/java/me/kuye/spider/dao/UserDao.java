package me.kuye.spider.dao;

import me.kuye.spider.entity.User;

public class UserDao extends RedisBaseDao<User> {
	public boolean save(User user) {

		return false;
	}
}
