package me.kuye.spider.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.entity.Answer;
import me.kuye.spider.entity.Entity;
import me.kuye.spider.entity.annotation.Exclude;

public class BeanUtil {
	private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	/**
	* @Title: getFieldMap
	* @Description: 使用BeanInfo生成FieldMap
	* @param     参数
	* @return Map<String,Object>    返回类型
	* @throws
	*/
	@Deprecated
	public static Map<String, Object> getFieldMap(Entity obj, boolean ignoreStatic) {
		Map<String, Object> fieldMap = new LinkedHashMap<>();
		Class<?> clazz = obj.getClass();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String key = propertyDescriptor.getName();

				if (!key.equals("class") && !key.equals("key")) {
					Method getter = propertyDescriptor.getReadMethod();
					Object value = getter.invoke(obj);
					fieldMap.put(key, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fieldMap;
	}

	public static Map<String, Object> doGetFieldMap(Entity obj, boolean ignoreStatic) {
		Map<String, Object> fieldMap = new LinkedHashMap<>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (ignoreStatic && Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			if (field.isAnnotationPresent(Exclude.class)) {
				continue;
			}
			try {
				String key = field.getName();
				field.setAccessible(true);
				Object value = field.get(obj);
				fieldMap.put(key, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				logger.error(e.getMessage(), e);
				//报错则跳过该field
				continue;
			}
		}
		return fieldMap;
	}

	public static Map<String, Object> getFieldMap(Entity obj) {
		return doGetFieldMap(obj, true);
	}
}
