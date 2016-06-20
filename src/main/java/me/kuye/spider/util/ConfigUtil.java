package me.kuye.spider.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigUtil {

	public static Properties getProperties(String path) {
		return null;
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

}
