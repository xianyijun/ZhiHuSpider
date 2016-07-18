package me.kuye.spider.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtil {
	private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	public static Map<String, Object> getFieldMap(Object obj, boolean ignoreStatic) {
		Map<String, Object> fieldMap = new LinkedHashMap<>();
		Class<?> clazz = obj.getClass();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String key = propertyDescriptor.getName();
				if (!key.equals("class")&&!key.equals("key")) {
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

	public static Map<String, Object> getFieldMap(Object obj) {
		return getFieldMap(obj, true);
	}
}
