package me.kuye.spider.downloader;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.impl.cookie.RFC6265CookieSpecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.helper.LoginCookiesHelper;
import me.kuye.spider.util.HttpConstant;

public class ZhiHuClientGenerator {
	private static Logger logger = LoggerFactory.getLogger(ZhiHuClientGenerator.class);
	private PoolingHttpClientConnectionManager connectionManager;

	public ZhiHuClientGenerator() {
		Registry<ConnectionSocketFactory> req = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
		connectionManager = new PoolingHttpClientConnectionManager(req);
		connectionManager.setDefaultMaxPerRoute(100);
	}

	public ZhiHuClientGenerator setPoolSize(int poolSize) {
		connectionManager.setMaxTotal(poolSize);
		return this;
	}

	public CloseableHttpClient getClient(String domain) {
		return generateClient(domain);
	}

	private CloseableHttpClient generateClient(String domain) {
		// HttpHost proxy = ProxyManager.getNextProxy();
		HttpClientBuilder builder = HttpClients.custom().setConnectionManager(connectionManager);

		// 暂时设置默认userAgent;
		builder.setUserAgent(HttpConstant.DEFAULT_USER_AGENT);

		SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).setTcpNoDelay(true).build();
		// 设置请求超时，使用代理的话应该配置时间长
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(15000).setSocketTimeout(15000)
				.setConnectionRequestTimeout(15000).setCircularRedirectsAllowed(true).setMaxRedirects(3).build();
		// 设置CookieSpec策略
		Registry<CookieSpecProvider> cookieSpecRegistry = RegistryBuilder.<CookieSpecProvider> create()
				.register(CookieSpecs.DEFAULT, new RFC6265CookieSpecProvider())
				.register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
				.register(CookieSpecs.BROWSER_COMPATIBILITY, new BrowserCompatSpecFactory()).build();

		builder.setDefaultCookieSpecRegistry(cookieSpecRegistry).setDefaultSocketConfig(socketConfig)
				.setDefaultRequestConfig(requestConfig);

		builder.setRetryHandler(new DefaultHttpRequestRetryHandler(HttpConstant.DEFAULT_RETRY_TIMES, true));
		// 读取模拟登录后的cooike
		if (domain != HttpConstant.NO_COOKIE) {
			generateCookie(builder);
		}
		return builder.build();
	}

	private void generateCookie(HttpClientBuilder builder) {
		CookieStore cookieStore = null;
		cookieStore = (CookieStore) LoginCookiesHelper.antiSerializeCookies("/cookies");

		builder.setDefaultCookieStore(cookieStore);
	}
}
