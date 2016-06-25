package me.kuye.spider;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.kuye.spider.downloader.HttpDownloader;
import me.kuye.spider.entity.Answer;
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
			Document doc = Jsoup.parse(body, "https://www.zhihu.com");

			String title = doc.select("#zh-question-title  h2  span").first().text();
			question.setTitle(title);

			String description = doc.select("#zh-question-detail div").first().text();
			question.setDescription(description);

			int answerNum = Integer.parseInt(doc.select("#zh-question-answer-num").attr("data-num"));
			question.setAnswerNum(answerNum);
			
			// 只有登录才存在
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
			// ===============================================
			// System.out.println(doc.select(".zm-item-answer link").toArray());
			Object[] elementList = doc.select(".zm-item-answer link").toArray();
			for (int i = 0; i < elementList.length; i++) {
				Element element = (Element) elementList[i];
				Answer answer = new Answer(element.attr("href"), element.baseUri() + element.attr("href"));
				answer.setQuestion(question);
				processAnswerDetail(answer);
				System.out.println(answer);
				System.out.println("///=======================================================");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.close();
		}
	}

	private static void processAnswerDetail(Answer answer) throws IOException {
		Document answerDoc = Jsoup.connect(answer.getAbsUrl()).get();
		answer.setContent(answerDoc.select("div[data-entry-url=" + answer.getRelativeUrl() + "]  .zm-editable-content")
				.html().replaceAll("<br>", ""));
		answer.setUpvote(Long.parseLong(answerDoc.select(".zm-votebar span.count").first().text()));
		/*
		 * 不可以直接用a.author-link来获取，如果是知乎用户的话，不存在该标签 #zh-question-answer-wrap >
		 * div > div.answer-head > div.zm-item-answer-author-info >
		 * a.author-link 普通用户 2 #zh-question-answer-wrap > div > div.answer-head
		 * > div.zm-item-answer-author-info > span 知乎用户 2
		 * #zh-question-answer-wrap > div > div.answer-head >
		 * div.zm-item-answer-author-info > span 匿名用户 1
		 * 我们可以先通过获取zm-item-answer-author-info来获取用户名，因为是否为知乎用户的话
		 */
		Element authorInfo = answerDoc.select(".zm-item-answer-author-info").first();
		String author = authorInfo.select(".name").size() == 0 ? authorInfo.select("a.author-link").text()
				: authorInfo.select(".name").text();
		answer.setAuthor(author);

//		System.out.println(answerDoc.select(".zm-item-answer").attr("data-aid"));
	}
}
