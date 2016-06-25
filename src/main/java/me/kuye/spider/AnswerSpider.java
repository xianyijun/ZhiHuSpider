package me.kuye.spider;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;

import me.kuye.spider.downloader.HttpDownloader;
import me.kuye.spider.vo.UpVoteResult;

public class AnswerSpider {
	public static void main(String[] args) {
		/// answer/38441951/voters_profile?total=175&offset=30
		String upvoteUrl = "/answer/38441951/voters_profile?total=175&offset=10";
		HttpDownloader downloader = new HttpDownloader();
		CloseableHttpClient client = downloader.getHttpClient(null);
		HttpGet request = null;
		CloseableHttpResponse response = null;
		UpVoteResult upVoteResult = null;
		String result = "";
		try {
			while (upvoteUrl != null && !upvoteUrl.equals("")) {
				request = new HttpGet("https://www.zhihu.com" + upvoteUrl);
				response = client.execute(request);
				result = EntityUtils.toString(response.getEntity());
				upVoteResult = JSON.parseObject(result, UpVoteResult.class);
				String[] payload = upVoteResult.getPayload();
				for (int i = 0; i < payload.length; i++) {
					Document doc = Jsoup.parse(payload[i]);
					System.out.println(doc.select(".zg-link").text());
				}
				upvoteUrl = upVoteResult.getPaging().getNext();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(result);
		}
	}
}
