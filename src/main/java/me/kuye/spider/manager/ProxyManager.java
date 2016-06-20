package me.kuye.spider.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;

import me.kuye.spider.util.ConfigUtil;

public class ProxyManager {
	private static final List<HttpHost> proxyList = new ArrayList<>();
	static {
		List<String> list = ConfigUtil.getConfigList("/proxy");
		for (String str : list) {
			String host = str.split("#")[0];
			int port = Integer.parseInt(str.split("#")[1]);
			proxyList.add(new HttpHost(host, port,"HTTP"));
		}
	}

	public static HttpHost getNextProxy() {
		int index = (int) Math.round((Math.random() * proxyList.size())) - 1;
		index = index < 0 ? 0 : index;
		return proxyList.get(index);
	}
}
