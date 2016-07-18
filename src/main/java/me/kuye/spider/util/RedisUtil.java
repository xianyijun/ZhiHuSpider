package me.kuye.spider.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisUtil {
	private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	
	private RedisUtil() {
	}

	/**
	 * @Title: objectToString
	 * @Description: 将object对象序列化为String，默认为utf-8编码
	 * @param 参数
	 * @return String
	 * @throws TODO
	 */
	public static String objectToString(Object obj) {
		String str = "";
		ByteArrayOutputStream byteArrayOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(obj);
			str = byteArrayOutputStream.toString("Utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(" UnsupportedEncodingException ", e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(" IOException ", e);
			e.printStackTrace();
		} finally {
			try {
				if (objectOutputStream != null) {
					objectOutputStream.close();
				}
				if (byteArrayOutputStream != null) {
					byteArrayOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	public static Object stringToObject(String str) {
		Object obj = null;
		ByteArrayInputStream byteArrayInputStream = null;
		ObjectInputStream objectInputStream = null;

		try {
			byteArrayInputStream = new ByteArrayInputStream(str.getBytes("Utf-8"));
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			obj = objectInputStream.readObject();

		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException", e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException", e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IOException", e);
			e.printStackTrace();
		} finally {
			try {
				if (objectInputStream != null) {
					objectInputStream.close();
				}
				if (byteArrayInputStream != null) {
					byteArrayInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
}
