package me.kuye.spider.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import me.kuye.spider.util.ConfigUtil;

public class ConnectionManager {
	private static DruidDataSource dataSource = null;
	private static ConnectionManager connectionManager = new ConnectionManager();
	private static QueryRunner queryRunner = null;
	static {
		Properties properties = ConfigUtil.getProperties("/jdbc.properties");
		try {
			dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
			if (dataSource != null) {
				queryRunner = new QueryRunner(dataSource);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ConnectionManager() {

	}

	public DruidPooledConnection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static ConnectionManager getInstance() {
		return connectionManager;
	}

	public QueryRunner getQueryRunner() {
		return queryRunner;
	}
}
