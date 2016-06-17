package me.kuye.spider.proxy;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ClientConnectionManager;
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

public class Proxy {
	public static void main(String[] args) throws IOException {
		// System.getProperties().setProperty("proxySet", "true");
		// System.getProperties().setProperty("http.proxyHost",
		// "175.152.210.39");
		// System.getProperties().setProperty("http.proxyPort", "8090");
		// BufferedReader in = null;
		// try {
		// String result = "";
		// URLConnection connection = new
		// URL("http://www.baidu.com/").openConnection();
		// connection.setConnectTimeout(6000); // 6s
		// connection.setReadTimeout(6000);
		// connection.setUseCaches(false);
		// connection.connect();
		// in = new BufferedReader(new
		// InputStreamReader(connection.getInputStream()));
		// String line;
		// while ((line = in.readLine()) != null) {
		// result += line;
		// }
		// Document doc = Jsoup.parse(result);
		// System.out.println(doc);
		// } catch (IOException e) {
		// e.printStackTrace();
		// } finally {
		//
		// }
		HttpHost proxy = new HttpHost("112.122.219.96", 8118,"http");

		Registry<ConnectionSocketFactory> req = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(req);
		connectionManager.setDefaultMaxPerRoute(100);

		RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(5000).setSocketTimeout(5000)
				.setConnectionRequestTimeout(5000).build();
		HttpClientBuilder builder = HttpClients.custom().setConnectionManager(connectionManager)
				.setDefaultRequestConfig(config);

		CloseableHttpClient client = builder.build();
		try {

			HttpGet request = new HttpGet("http://www.baidu.com");
			CloseableHttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			String str = EntityUtils.toString(entity);
			Document doc = Jsoup.parse(str);
			System.out.println(doc);
		} finally {
			client.close();
		}
	}
}
