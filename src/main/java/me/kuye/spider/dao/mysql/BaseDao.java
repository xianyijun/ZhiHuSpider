package me.kuye.spider.dao.mysql;

import java.sql.Connection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.entity.Answer;
import me.kuye.spider.entity.Entity;
import me.kuye.spider.helper.DataBaseHelper;
import me.kuye.spider.helper.SqlHelper;
import me.kuye.spider.manager.ConnectionManager;
import me.kuye.spider.util.BeanUtil;

public class BaseDao<T extends Entity> {
	private static Logger logger = LoggerFactory.getLogger(BaseDao.class);

	public boolean insert(T t) {
		Connection connection = ConnectionManager.getConnection();
		Map<String, Object> fieldMap = BeanUtil.getFieldMap(t);
		String sql = SqlHelper.generateInsertSql(t.getClass(), fieldMap.keySet());
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
