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
import me.kuye.spider.entity.Question;

public class QuestionSpider {
	public static void main(String[] args) throws IOException {
		HttpDownloader downloader = new HttpDownloader();
		String url = "https://www.zhihu.com/question/47706461";
		HttpGet request = new HttpGet(url);
		CloseableHttpResponse response = downloader.getHttpClient(null).execute(request);
		Question question = new Question(url);
		try {
			String body = EntityUtils.toString(response.getEntity());
			Document doc = Jsoup.parse(body);
			
			String title = doc.select("#zh-question-title  h2  span").first().text();
			question.setTitle(title);
			
			String description = doc.select("#zh-question-detail div").first().text();
			question.setDescription(description);
			
			int answerNum = Integer.parseInt(doc.select("#zh-question-answer-num").attr("data-num"));
			question.setAnswerNum(answerNum);
			
			int visitTimes = Integer.parseInt(doc.select("div.zg-gray-normal strong").eq(1).text());
			question.setVisitTimes(visitTimes);
			
			int answerFollowersNum = Integer.parseInt(doc.select("div.zh-question-followers-sidebar strong").text());
			question.setAnswerFollowersNum(answerFollowersNum);
			
			Elements topicElements = doc.select(".zm-tag-editor-labels a");
			String[] topics = new String[topicElements.size()];
			for (int i = 0; i < topicElements.size(); i++) {
				topics[i] = topicElements.get(i).text();
			}
			question.setTopics(topics);
			//===============================================
		} catch (Exception e) {
			e.printStackTrace();
			response.close();
		}
	}
}
