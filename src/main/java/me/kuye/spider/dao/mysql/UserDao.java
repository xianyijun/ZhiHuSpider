package me.kuye.spider.dao.mysql;

import java.sql.Connection;

import me.kuye.spider.entity.User;
import me.kuye.spider.manager.ConnectionManager;

public class UserDao<T> {
	public boolean insert(User user) {
		Connection connection = ConnectionManager.getConnection();
//		DataBaseHelper.update(null, sql, params);
		return false;
	}
}
