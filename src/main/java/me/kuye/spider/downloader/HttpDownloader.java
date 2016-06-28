package me.kuye.spider.downloader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.entity.Page;

public class HttpDownloader {
	private static Logger logger = LoggerFactory.getLogger(HttpDownloader.class);

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

	public void setThreaNum(int threadNum) {
		this.generator.setPoolSize(threadNum);
	}

	public Page download(HttpRequestBase request, String domain) {
		CloseableHttpResponse response = null;
		CloseableHttpClient client = clientMap.get(domain);
		try {
			try {
				response = client.execute(request);

			} catch (IOException e) {
				logger.info("IOException", e);
				return null;
			}

			int statusCode = response.getStatusLine().getStatusCode();
			while (statusCode == 429) {
				Thread.sleep(1000);
				response = client.execute(request);
				statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != 429) {
					break;
				}
			}
			if (statusCode == HttpStatus.SC_OK) {
				String content = EntityUtils.toString(response.getEntity());
				Page page = handlePage(content, request);
				return page;
			} else if (statusCode == 500 || statusCode == 502 || statusCode == 504) {
				Thread.sleep(1000);
				return null;
			}
		} catch (ClientProtocolException e) {
			logger.info(" clientProtocolException ", e);
		} catch (IOException e) {
			logger.info(" IOException ", e);
		} catch (InterruptedException e) {
			logger.info(" InterruptedException ", e);
		} finally {
			if (response != null && response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				request.abort();
				if (response.getEntity() != null) {
					try {
						response.getEntity().writeTo(System.out);
						request.releaseConnection();
						EntityUtils.consumeQuietly(response.getEntity());
						response.close();
					} catch (UnsupportedOperationException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	private Page handlePage(String content, HttpRequestBase request) {
		Page page = new Page();
		page.setRequest(request);
		page.setDocument(Jsoup.parse(content));
		return page;
	}
}
