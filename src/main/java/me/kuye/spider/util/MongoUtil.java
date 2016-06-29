package me.kuye.spider.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoUtil {
	private static Logger logger = LoggerFactory.getLogger(MongoUtil.class);

	private MongoUtil(){
		
	}
	
	public static Document objectToDocument(Class<?> clazz, Object obj) {
		if (obj == null) {
			return null;
		}
		Document document = new Document();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String key = propertyDescriptor.getName();
				if (!key.equals("class")) {
					Method getter = propertyDescriptor.getReadMethod();
					Object value = getter.invoke(obj);
					document.append(key, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;

	}

	public static <T> T documentToObj(Class<T> clazz, Document document) {
		try {
			Object obj = clazz.newInstance();
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String key = propertyDescriptor.getName();
				if (document.containsKey(key)) {
					Object value = document.get(key);
					Method setter = propertyDescriptor.getWriteMethod();
					setter.invoke(obj, value);
				}
			}
			return (T) obj;
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}
}
