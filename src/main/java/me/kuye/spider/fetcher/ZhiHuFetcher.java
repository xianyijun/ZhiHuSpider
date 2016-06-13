package me.kuye.spider.fetcher;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.util.Constant;

public class ZhiHuFetcher {
	private static Logger logger = LoggerFactory.getLogger(ZhiHuFetcher.class);
	private static volatile ZhiHuFetcher ZHIHU_FETCHER;
	private PoolingHttpClientConnectionManager connectionManager;

	private ZhiHuFetcher() {
	}

	public static ZhiHuFetcher getInstance() {
		if (ZHIHU_FETCHER == null) {
			synchronized (ZhiHuFetcher.class) {
				if (ZHIHU_FETCHER == null) {
					ZhiHuFetcher zhiHuFetcher = new ZhiHuFetcher();
					zhiHuFetcher.init();
					ZHIHU_FETCHER = zhiHuFetcher;
				}
			}
		}
		return ZHIHU_FETCHER;
	}

	private void init() {
		Registry<ConnectionSocketFactory> req = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
		connectionManager = new PoolingHttpClientConnectionManager(req);
		connectionManager.setDefaultMaxPerRoute(100);

	}

	public CloseableHttpClient getClient() {
		return generateClient();
	}

	private CloseableHttpClient generateClient() {
		HttpClientBuilder builder = HttpClients.custom().setConnectionManager(connectionManager);
		builder.setUserAgent(Constant.DEFAULT_USER_AGENT);
		SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).setTcpNoDelay(true).build();
		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
		builder.setDefaultSocketConfig(socketConfig).setDefaultRequestConfig(requestConfig);
		return builder.build();
	}

	public HttpClientContext getContext() {
		HttpClientContext context = null;
		context = HttpClientContext.create();
		Registry<CookieSpecProvider> registry = RegistryBuilder.<CookieSpecProvider> create()
				.register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
				.register(CookieSpecs.BROWSER_COMPATIBILITY, new BrowserCompatSpecFactory()).build();
		context.setCookieSpecRegistry(registry);
		return context;
	}
}
