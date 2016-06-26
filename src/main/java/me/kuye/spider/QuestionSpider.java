package me.kuye.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import me.kuye.spider.downloader.HttpDownloader;
import me.kuye.spider.entity.Answer;
import me.kuye.spider.entity.Question;
import me.kuye.spider.manager.MongoManager;
import me.kuye.spider.util.Constant;
import me.kuye.spider.util.MongoUtil;
import me.kuye.spider.vo.UpVoteResult;
import me.kuye.spider.vo.UpVoteUser;

public class QuestionSpider {
	private static Logger logger = LoggerFactory.getLogger(QuestionSpider.class);
	private static final HttpDownloader downloader = new HttpDownloader();
	private static final CloseableHttpClient client = downloader.getHttpClient("question");

	public static void main(String[] args) throws IOException {
		String url = "https://www.zhihu.com/question/47706461";
		HttpGet request = new HttpGet(url);
		CloseableHttpResponse response = client.execute(request);
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
			// String[] topics = new String[topicElements.size()];
			List<String> topics = new LinkedList<>();
			for (int i = 0; i < topicElements.size(); i++) {
				topics.add(topicElements.get(i).text());
			}
			question.setTopics(topics);
			List<Answer> answerList = new ArrayList<>();
			Object[] elementList = doc.select(".zm-item-answer link").toArray();
			for (int i = 0; i < elementList.length; i++) {
				Element element = (Element) elementList[i];
				Answer answer = new Answer(element.attr("href"), element.baseUri() + element.attr("href"));
				// answer.setQuestion(question);
				processAnswerDetail(answer);
				answerList.add(answer);
			}
			question.setAllAnswerList(answerList);
			MongoManager.getInstance().insertOne("question", MongoUtil.objectToDocument(Question.class, question));
		} catch (Exception e) {
			e.printStackTrace();
			response.close();
		}
	}

	private static void processAnswerDetail(Answer answer) throws IOException {
		logger.info(answer.getAbsUrl());
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

		String dataAid = answerDoc.select(".zm-item-answer").attr("data-aid");
		answer.setDataAid(dataAid);

		String upvoteUserUrl = "/answer/" + dataAid + "/voters_profile?&offset=0";
		List<UpVoteUser> userList = processUpVoteUserList(upvoteUserUrl);
		answer.setUpvoteUserList(userList);
	}

	private static List<UpVoteUser> processUpVoteUserList(String upvoteUserUrl) {
		HttpGet request = null;
		CloseableHttpResponse response = null;
		UpVoteResult upVoteResult = null;
		String result = "";
		List<UpVoteUser> userList = new LinkedList<>();
		try {
			while (upvoteUserUrl != null && !upvoteUserUrl.equals("")) {
				request = new HttpGet(Constant.ZHIHU_URL + upvoteUserUrl);
				logger.info(request.getURI().toString());
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
				upvoteUserUrl = upVoteResult.getPaging().getNext();
			}
		} catch (IOException e) {
			e.printStackTrace();
			userList = Collections.emptyList();
			logger.info("processAnswerDetail failure");
		}
		return userList;
	}
}
