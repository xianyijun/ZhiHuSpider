package me.kuye.spider.processor.helper;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.entity.Question;

public class ZhiQuestionProcessorHelper {
	public  static Logger logger = LoggerFactory.getLogger(ZhiQuestionProcessorHelper.class);

	/**
	 * @Title: processQuestion 
	 * @Description: 解析Question信息 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	public  static void processQuestion(Document doc, Question question) {
		try {
			String urlToken = doc.select("#zh-single-question-page").attr("data-urltoken");
			question.setUrlToken(urlToken);

			String title = doc.select("#zh-question-title h2 span").first().text();
			question.setTitle(title);

			String description = doc.select("#zh-question-detail div").first().text();
			question.setDescription(description);
			/*
			 * 如果问题的个数少于或者等于1个的时候， 不会显示有多少个回答个数
			 * 我们可以通过获取zm-item-answer的个数来设置answerNum
			 */
			try {
				int answerNum = Integer.parseInt(doc.select("#zh-question-answer-num").attr("data-num"));
				question.setAnswerNum(answerNum);
			} catch (NumberFormatException e) {
				if (doc.select(".zm-item-answer").size() == 0) {
					question.setAnswerNum(0);
				} else {
					question.setAnswerNum(doc.select(".zm-item-answer").size());
				}
			}

			// 只有登录才存在
			int visitTimes = Integer.parseInt(doc.select("div.zm-side-section-inner strong").eq(1).text());
			question.setVisitTimes(visitTimes);

			int answerFollowersNum = Integer.parseInt(doc.select("div.zh-question-followers-sidebar strong").text());
			question.setAnswerFollowersNum(answerFollowersNum);

			Elements topicElements = doc.select(".zm-tag-editor-labels a");
			List<String> topics = new LinkedList<>();
			for (int i = 0; i < topicElements.size(); i++) {
				topics.add(topicElements.get(i).text());
			}
			question.setTopics(topics);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}

	}
}
