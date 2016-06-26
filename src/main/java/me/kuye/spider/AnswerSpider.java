package me.kuye.spider;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;

import me.kuye.spider.downloader.HttpDownloader;
import me.kuye.spider.vo.UpVoteResult;
import me.kuye.spider.vo.UpVoteUser;

public class AnswerSpider {
	public static void main(String[] args) {
		/// answer/38441951/voters_profile?total=175&offset=30
		String upvoteUrl = "/answer/38441951/voters_profile?total=175&offset=0";
		HttpDownloader downloader = new HttpDownloader();
		CloseableHttpClient client = downloader.getHttpClient(null);
		HttpGet request = null;
		CloseableHttpResponse response = null;
		UpVoteResult upVoteResult = null;
		String result = "";
		List<UpVoteUser> userList = new LinkedList<>();
		try {
			while (upvoteUrl != null && !upvoteUrl.equals("")) {
				request = new HttpGet("https://www.zhihu.com" + upvoteUrl);
				response = client.execute(request);
				result = EntityUtils.toString(response.getEntity());
				upVoteResult = JSON.parseObject(result, UpVoteResult.class);
				String[] payload = upVoteResult.getPayload();
				for (int i = 0; i < payload.length; i++) {
					Document doc = Jsoup.parse(payload[i]);
					UpVoteUser upVoteUser = new UpVoteUser();
					upVoteUser.setName(doc.select(".zg-link").attr("title"));
					upVoteUser.setAvatar(doc.select("img.zm-item-img-avatar").attr("src"));
					upVoteUser.setBio(doc.select(".bio").text());
					upVoteUser.setAgree(doc.select(".status").first().child(0).text());
					upVoteUser.setThanks(doc.select(".status").first().child(1).text());
					upVoteUser.setAnswers(doc.select(".status").first().child(3).text());
					upVoteUser.setAsks(doc.select(".status").first().child(2).text());
					userList.add(upVoteUser);
				}
				upvoteUrl = upVoteResult.getPaging().getNext();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
