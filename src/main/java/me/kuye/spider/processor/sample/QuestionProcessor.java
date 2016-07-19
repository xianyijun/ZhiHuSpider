package me.kuye.spider.processor.sample;

import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.Scheduler.impl.QuestionRedisScheduler;
import me.kuye.spider.core.Page;
import me.kuye.spider.core.Request;
import me.kuye.spider.core.SimpleSpider;
import me.kuye.spider.entity.Question;
import me.kuye.spider.pipeline.impl.ConsolePipeline;
import me.kuye.spider.processor.Processor;
import me.kuye.spider.processor.helper.QuestionProcessorHelper;
import me.kuye.spider.util.Constant;
import me.kuye.spider.util.HttpConstant;

/**
 * @author xianyijun
 *批量抓取问题，只抓问题信息不抓问题回答
 */
public class QuestionProcessor implements Processor {
	private static final Logger logger = LoggerFactory.getLogger(QuestionProcessor.class);

	@Override
	public void process(Page page) {
		Question question = new Question(page.getRequest().getUrl().toString());
		QuestionProcessorHelper.processQuestion(page, question);
		page.getResult().add(question);
		page.getDocument().select("#zh-question-related-questions ul li a").forEach((Element e) -> {
			page.getTargetRequest().add(new Request(HttpConstant.GET, Constant.ZHIHU_URL + e.attr("href")));
		});
	}

	public static void main(String[] args) {
		String url = "https://www.zhihu.com/question/40924763";
		SimpleSpider.getInstance(new QuestionProcessor()).setThreadNum(3).setDomain("question")
				.setScheduler(new QuestionRedisScheduler()).addPipeline(new ConsolePipeline())
				.setStartRequest(new Request(HttpConstant.GET, url)).run();
	}

}
