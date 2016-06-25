package me.kuye.spider;

import java.io.IOException;
import java.util.Iterator;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.kuye.spider.fetcher.ZhiHuClientGenerator;
import me.kuye.spider.helper.LoginCookiesHelper;

public class QuestionSpider {
	public static void main(String[] args) throws IOException {
		CloseableHttpClient client = ZhiHuClientGenerator.getInstance().getClient();
		HttpClientContext context = ZhiHuClientGenerator.getInstance().getContext();
		context.setCookieStore((CookieStore) LoginCookiesHelper.antiSerializeCookies("/cookies"));
		HttpGet request = new HttpGet("https://www.zhihu.com/question/47706461");
		CloseableHttpResponse response = client.execute(request, context);
		byte[] imgData = null;
		try {
			String body = EntityUtils.toString(response.getEntity());
			Document doc = Jsoup.parse(body);
			// #zh-question-title > h2 > span
			String title = doc.select("#zh-question-title  h2  span").first().text();
			System.out.println(title);
			String description = doc.select("#zh-question-detail div").first().text();
			System.out.println(description);
			// #zh-question-answer-wrap > div:nth-child(1) >
			// div.zm-item-rich-text.expandable.js-collapse-body >
			// div.zm-editable-content.clearfix
			// #zh-question-answer-wrap > div:nth-child(2) >
			// div.zm-item-rich-text.expandable.js-collapse-body >
			// div.zm-editable-content.clearfix
			//#zh-question-answer-wrap > div:nth-child(1) > div.zm-item-rich-text.expandable.js-collapse-body
			Elements elements = doc.select("#zh-question-answer-wrap div.zm-item-rich-text.expandable");
			System.out.println(elements.size());
			Iterator<Element> it = elements.iterator();
			while (it.hasNext()) {
				//#zh-question-answer-wrap > div:nth-child(1) > div.zm-item-rich-text.expandable.js-collapse-body > div.zm-editable-content.clearfix
				Element element = it.next();
				System.out.println(element.select("div.zm-editable-content").outerHtml());
			}
		} catch (Exception e) {
			response.close();
		}
	}
}
