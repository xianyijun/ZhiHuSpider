package me.kuye.spider.processor.sample;

import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.ZhiHuSpider;
import me.kuye.spider.entity.Page;
import me.kuye.spider.entity.Question;
import me.kuye.spider.entity.Request;
import me.kuye.spider.pipeline.ConsolePipeline;
import me.kuye.spider.pipeline.MongoPipeline;
import me.kuye.spider.processor.Processor;
import me.kuye.spider.processor.helper.ZhiHuQuestionProcessorHelper;
import me.kuye.spider.util.Constant;
import me.kuye.spider.util.HttpConstant;

/**
 * @author xianyijun
 *批量抓取问题，只抓问题信息不抓问题回答
 */
public class ZhiHuQuestionProcessor implements Processor {
	private static Logger logger = LoggerFactory.getLogger(ZhiHuQuestionProcessor.class);

	@Override
	public void process(Page page) {
		Question question = new Question(page.getRequest().getUrl().toString());
		ZhiHuQuestionProcessorHelper.processQuestion(page, question);
		page.getResult().add(question);
		page.getDocument().select("#zh-question-related-questions ul li a").forEach((Element e) -> {
			page.getTargetRequest().add(new Request(HttpConstant.GET, Constant.ZHIHU_URL + e.attr("href")));
		});
	}

	public static void main(String[] args) {
		String url = "https://www.zhihu.com/question/40924763";
		ZhiHuSpider.getInstance(new ZhiHuQuestionProcessor()).setThreadNum(3).setDomain("question")
				.addPipeline(new MongoPipeline()).addPipeline(new ConsolePipeline())
				.setStartRequest(new Request(HttpConstant.GET, url)).run();
	}


}
