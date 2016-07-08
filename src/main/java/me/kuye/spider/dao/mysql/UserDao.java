package me.kuye.spider.dao.mysql;

import java.sql.Connection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.entity.User;
import me.kuye.spider.helper.DataBaseHelper;
import me.kuye.spider.helper.SqlHelper;
import me.kuye.spider.manager.ConnectionManager;
import me.kuye.spider.util.BeanUtil;

public class UserDao {
	private static Logger logger = LoggerFactory.getLogger(UserDao.class);

	public boolean insert(User user) {
		Connection connection = ConnectionManager.getConnection();
		Map<String, Object> fieldMap = BeanUtil.getFieldMap(user);
		String sql = SqlHelper.generateInsertSql(user.getClass(), fieldMap.keySet());
		try {
			int result = DataBaseHelper.update(connection, sql, fieldMap.values().toArray());
			return result != 0;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		} finally {
			ConnectionManager.close(connection);
		}
	}
}
