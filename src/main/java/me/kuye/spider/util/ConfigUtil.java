package me.kuye.spider.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigUtil {
	private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
	private static Map<String, Properties> propertiesMap = new HashMap<>();

	private ConfigUtil() {

	}

	public static List<String> getConfigList(String sourceName) {
		InputStream in = null;
		List<String> list = null;
		try {
			list = new ArrayList<>();
			in = ConfigUtil.class.getResourceAsStream(sourceName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String result = null;
			while ((result = reader.readLine()) != null) {
				list.add(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static Properties getProperties(String name) {
		if (name == null) {
			logger.info(" the properties name can not be null");
			throw new NullPointerException("the properties name can not be null");
		}
		Properties properties = propertiesMap.get(name);
		if (properties == null) {
			properties = new Properties();
			InputStream input = ConfigUtil.class.getResourceAsStream(name);
			try {
				properties.load(input);
				propertiesMap.put(name, properties);
			} catch (IOException e) {
				logger.info(" load the " + name + " properties failure");
				e.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return properties;
	}

}
