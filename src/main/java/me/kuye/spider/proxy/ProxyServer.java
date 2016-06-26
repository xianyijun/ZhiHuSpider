package me.kuye.spider.proxy;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import me.kuye.spider.util.HttpConstant;

public class ProxyServer {
	public static void main(String[] args) throws IOException {
		 HttpHost proxy = new HttpHost("39.78.132.255", 8888, "http");

		Registry<ConnectionSocketFactory> req = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(req);
		connectionManager.setDefaultMaxPerRoute(100);

		RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setProxy(proxy).setSocketTimeout(5000)
				.setConnectionRequestTimeout(5000).setCircularRedirectsAllowed(true).build();
		HttpClientBuilder builder = HttpClients.custom().setUserAgent(HttpConstant.DEFAULT_USER_AGENT)
				.setConnectionManager(connectionManager).setDefaultRequestConfig(config);

		CloseableHttpClient client = builder.build();
		HttpGet request = null;
		CloseableHttpResponse response = null;
		try {
			request = new HttpGet("http://www.ip138.com/");
			response = client.execute(request);
			HttpEntity entity = response.getEntity();
			String str = EntityUtils.toString(entity);
			Document doc = Jsoup.parse(str);
			System.out.println(doc);
		} finally {
			if (response != null && response.getEntity() != null) {
				request.releaseConnection();
				EntityUtils.consumeQuietly(response.getEntity());
			}
			client.close();
		}
	}
}
