package me.kuye.spider.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtil {
	private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	public static Map<String, Object> getFieldMap(Object obj, boolean ignoreStatic) {
		Map<String, Object> fieldMap = new LinkedHashMap<>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (ignoreStatic && Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			String fieldName = field.getName();
			Object value = null;
			try {
				value = field.get(obj);
				fieldMap.put(fieldName, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				logger.error(e.getLocalizedMessage(), e);
			}
		}

		return fieldMap;
	}
}
