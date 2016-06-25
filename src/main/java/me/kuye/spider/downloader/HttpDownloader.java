package me.kuye.spider.downloader;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;

public class HttpDownloader {
	private final Map<String, CloseableHttpClient> clientMap = new HashMap<>();
	private ZhiHuClientGenerator generator = new ZhiHuClientGenerator();

	public CloseableHttpClient getHttpClient(String domain) {
		if (domain == null) {
			return generator.getClient(null);
		}
		CloseableHttpClient client = clientMap.get(domain);
		if (client == null) {
			synchronized (this) {
				client = clientMap.get(domain);
				if (client == null) {
					client = generator.getClient(domain);
					clientMap.put(domain, client);
				}
			}
		}
		return client;
	}
}
