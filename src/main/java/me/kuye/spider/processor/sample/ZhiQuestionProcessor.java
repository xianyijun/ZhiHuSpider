package me.kuye.spider.processor.sample;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.ZhiHuSpider;
import me.kuye.spider.entity.Page;
import me.kuye.spider.entity.Question;
import me.kuye.spider.entity.Request;
import me.kuye.spider.pipeline.ConsolePipeline;
import me.kuye.spider.pipeline.MongoPipeline;
import me.kuye.spider.processor.Processor;
import me.kuye.spider.util.Constant;
import me.kuye.spider.util.HttpConstant;

/**
 * @author xianyijun
 *批量抓取问题，只抓问题信息不抓问题回答
 */
public class ZhiQuestionProcessor implements Processor {
	private static Logger logger = LoggerFactory.getLogger(ZhiQuestionProcessor.class);

	public static void main(String[] args) {
		//		HttpGet getRequest = new HttpGet("https://www.zhihu.com/question/40924763");
		String url = "https://www.zhihu.com/question/40924763";
		ZhiHuSpider.getInstance(new ZhiQuestionProcessor()).setThreadNum(3).setDomain("question")
				.addPipeline(new MongoPipeline()).addPipeline(new ConsolePipeline())
				.setStartRequest(new Request(HttpConstant.GET, url)).run();
	}

	@Override
	public void process(Page page) {
		Question question = new Question(page.getRequest().getUrl().toString());
		processQuestion(page.getDocument(), question);
		page.getResult().add(question);
		page.getDocument().select("#zh-question-related-questions ul li a").forEach((Element e) -> {
			page.getTargetRequest().add(new Request(HttpConstant.GET, Constant.ZHIHU_URL + e.attr("href")));
		});
	}

	/**
	 * @Title: processQuestion 
	 * @Description: 解析Question信息 
	 * @param 参数 
	 * @return void 返回类型 
	 * @throws
	 */
	private static void processQuestion(Document doc, Question question) {
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
