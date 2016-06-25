package me.kuye.spider;

import java.io.IOException;
import java.util.Iterator;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.kuye.spider.downloader.HttpDownloader;

public class QuestionSpider {
	public static void main(String[] args) throws IOException {
		HttpDownloader downloader = new HttpDownloader();
		HttpGet request = new HttpGet("https://www.zhihu.com/question/47706461");
		CloseableHttpResponse response = downloader.getHttpClient(null).execute(request);
		byte[] imgData = null;
		try {
			String body = EntityUtils.toString(response.getEntity());
			Document doc = Jsoup.parse(body);
			String title = doc.select("#zh-question-title  h2  span").first().text();
			System.out.println(title);
			String description = doc.select("#zh-question-detail div").first().text();
			System.out.println(description);
			Elements elements = doc.select("#zh-question-answer-wrap div.zm-item-rich-text.expandable");
			System.out.println(elements.size());
			Iterator<Element> it = elements.iterator();
			while (it.hasNext()) {
				Element element = it.next();
				System.out.println(element.select("div.zm-editable-content").outerHtml());
			}
		} catch (Exception e) {
			response.close();
		}
	}
}
