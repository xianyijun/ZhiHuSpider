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

public class ZhiHuClientGenerator {
	private static Logger logger = LoggerFactory.getLogger(ZhiHuClientGenerator.class);
	private static volatile ZhiHuClientGenerator ZHIHU_FETCHER;
	private PoolingHttpClientConnectionManager connectionManager;
	
	private ZhiHuClientGenerator() {
	}

	public static ZhiHuClientGenerator getInstance() {
		if (ZHIHU_FETCHER == null) {
			synchronized (ZhiHuClientGenerator.class) {
				if (ZHIHU_FETCHER == null) {
					ZhiHuClientGenerator zhiHuFetcher = new ZhiHuClientGenerator();
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
		connectionManager.setMaxTotal(15);
	}

	public CloseableHttpClient getClient() {
		return generateClient();
	}

	// TODO
	private CloseableHttpClient generateClient() {
		// HttpHost proxy = new HttpHost("183.230.80.79", 8080, "http");
//		HttpHost proxy = ProxyManager.getNextProxy();
		HttpClientBuilder builder = HttpClients.custom().setConnectionManager(connectionManager);
		builder.setUserAgent(Constant.DEFAULT_USER_AGENT);
		SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).setTcpNoDelay(true).build();
		// 设置请求超时，使用代理的话应该配置时间长
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(15000)
				.setSocketTimeout(15000).setConnectionRequestTimeout(15000).setCircularRedirectsAllowed(true)
				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
		builder.setDefaultSocketConfig(socketConfig).setDefaultRequestConfig(requestConfig);
		return builder.build();
	}

	public HttpClientContext getContext() {
		return generateContext();
	}

	@SuppressWarnings("deprecation")
	private HttpClientContext generateContext() {
		HttpClientContext context = null;
		context = HttpClientContext.create();
		Registry<CookieSpecProvider> registry = RegistryBuilder.<CookieSpecProvider> create()
				.register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
				.register(CookieSpecs.BROWSER_COMPATIBILITY, new BrowserCompatSpecFactory()).build();
		context.setCookieSpecRegistry(registry);
		return context;
	}
}
