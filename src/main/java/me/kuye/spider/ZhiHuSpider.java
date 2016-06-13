package me.kuye.spider;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import me.kuye.spider.fetcher.ZhiHuFetcher;
import me.kuye.spider.helper.LoginCookiesHelper;

public class ZhiHuSpider {

	public static void main(String[] args) {
		CloseableHttpClient client = ZhiHuFetcher.getInstance().getClient();
		HttpClientContext context = ZhiHuFetcher.getInstance().getContext();
		context.setCookieStore((CookieStore) LoginCookiesHelper.antiSerializeCookies("/cookies"));
		HttpGet getMethod = new HttpGet("http://www.zhihu.com/question/following");
		try {
			CloseableHttpResponse response = client.execute(getMethod, context);
			String content = EntityUtils.toString(response.getEntity(), "UTF-8");
			System.out.println(content);
			response.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}