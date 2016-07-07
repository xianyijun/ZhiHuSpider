package me.kuye.spider.helper;

import java.util.Collection;

public class SqlHelper {
	public static String generateInsertSql(Class<?> entityClass, Collection<String> fieldNames) {
		String tableName = entityClass.getSimpleName().toLowerCase();
		StringBuilder sql = new StringBuilder("insert into ").append(tableName);
		if (fieldNames != null && !fieldNames.isEmpty()) {
			int i = 0;
			StringBuilder columns = new StringBuilder(" ");
			StringBuilder values = new StringBuilder(" values ");
			for (String fieldName : fieldNames) {
				if (i == 0) {
					columns.append("(").append(fieldName);
					values.append("(?");
				} else {
					columns.append(", ").append(fieldName);
					values.append(", ?");
				}
				if (i == fieldNames.size() - 1) {
					columns.append(")");
					values.append(")");
				}
				i++;
			}
			sql.append(columns).append(values);
		}
		return sql.toString();
	}

	public static void main(String[] args) {
		//		System.out.println(User.class.getSimpleName());
	}
}
