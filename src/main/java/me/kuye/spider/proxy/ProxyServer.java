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

import me.kuye.spider.util.Constant;

public class ProxyServer {
	public static void main(String[] args) throws IOException {
		HttpHost proxy = new HttpHost("182.89.6.82", 8123, "http");

		Registry<ConnectionSocketFactory> req = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(req);
		connectionManager.setDefaultMaxPerRoute(100);

		RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000)
				.setConnectionRequestTimeout(5000).setCircularRedirectsAllowed(true).build();
		HttpClientBuilder builder = HttpClients.custom().setUserAgent(Constant.DEFAULT_USER_AGENT)
				.setConnectionManager(connectionManager).setDefaultRequestConfig(config);

		CloseableHttpClient client = builder.build();
		HttpGet request = null;
		CloseableHttpResponse response = null;
		try {
			request = new HttpGet("https://www.zhihu.com/");
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
		// System.setProperty("http.maxRedirects", "50");
		// System.getProperties().setProperty("proxySet", "true");
		// String ip = "182.140.132.107";
		// System.getProperties().setProperty("http.proxyHost", ip);
		// System.getProperties().setProperty("http.proxyPort", "8888");
		// Proxy proxy = new Proxy(Proxy.Type.HTTP, new
		// InetSocketAddress("182.140.132.107", 8888));
		// StringBuilder result = new StringBuilder();
		// BufferedInputStream in = null;
		//
		// HttpURLConnection connection = null;
		// try {
		// URL url = new URL("http://www.ip.cn/");
		// connection = (HttpURLConnection) url.openConnection();
		// connection.setReadTimeout(10000);
		// connection.setConnectTimeout(10000);
		// connection.setRequestProperty("User-Agent",
		// "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like
		// Gecko) Ubuntu Chromium/50.0.2661.102 Chrome/50.0.2661.102
		// Safari/537.36");
		// in = new BufferedInputStream(connection.getInputStream());
		// String line = null;
		// byte[] buffer = new byte[1024];
		// while ((in.read(buffer)) != -1) {
		// line = new String(buffer, "UTF-8");
		// result.append(line);
		// }
		// System.out.println(Jsoup.parse(result.toString()));
		// } finally {
		// if (in != null) {
		// in.close();
		// }
		// connection = null;
		// }
	}
}
