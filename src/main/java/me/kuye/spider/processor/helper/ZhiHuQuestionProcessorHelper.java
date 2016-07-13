package me.kuye.spider.processor.helper;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.entity.Page;
import me.kuye.spider.entity.Question;

public class ZhiHuQuestionProcessorHelper {
	public static Logger logger = LoggerFactory.getLogger(ZhiHuQuestionProcessorHelper.class);

	/**
	* @Title: processQuestion
	* @Description: 解析问题详情
	* @param     参数
	* @return void    返回类型
	* @throws
	*/
	public static void processQuestion(Page page, Question question) {
		Document doc = page.getDocument();
		String urlToken = doc.select("#zh-single-question-page").attr("data-urltoken");
		question.setUrlToken(urlToken);

		String title = doc.select("#zh-question-title  h2  span").first().text();
		question.setTitle(title);

		String description = doc.select("#zh-question-detail div").first().text();
		question.setDescription(description);

		int answerNum = 0;
		try {
			answerNum = Integer.parseInt(doc.select("#zh-question-answer-num").attr("data-num"));
		} catch (NumberFormatException e) {
			//当问题回答数目小于1的时候，#zh-question-answer-num元素不存在。
			answerNum = doc.select(".zm-item-answer").size();
		}
		question.setAnswerNum(answerNum);

		// 只有登录才存在
		int visitTimes = Integer.parseInt(doc.select("div.zg-gray-normal strong").eq(1).text());
		question.setVisitTimes(visitTimes);

		int answerFollowersNum = Integer.parseInt(doc.select("div.zh-question-followers-sidebar strong").text());
		question.setAnswerFollowersNum(answerFollowersNum);

		Elements topicElements = doc.select(".zm-tag-editor-labels a");
		List<String> topics = new LinkedList<>();
		for (int i = 0; i < topicElements.size(); i++) {
			topics.add(topicElements.get(i).text());
		}
		question.setTopics(topics);
	}
}
