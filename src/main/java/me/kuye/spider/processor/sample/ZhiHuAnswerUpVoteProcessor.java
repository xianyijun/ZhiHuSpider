package me.kuye.spider.processor.sample;

import java.util.List;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.ZhiHuSpider;
import me.kuye.spider.entity.Answer;
import me.kuye.spider.entity.Page;
import me.kuye.spider.entity.Request;
import me.kuye.spider.entity.UpVoteUser;
import me.kuye.spider.processor.Processor;
import me.kuye.spider.processor.helper.ZhiHuAnswerUpVoteProcessorHelper;
import me.kuye.spider.util.Constant;
import me.kuye.spider.util.HttpConstant;

public class ZhiHuAnswerUpVoteProcessor implements Processor {
	private static final Logger logger = LoggerFactory.getLogger(ZhiHuAnswerUpVoteProcessor.class);

	@Override
	public void process(Page page) {
		Document doc = page.getDocument();
		if (doc.select("title").size() > 0) {
			String relativeUrl = doc.select("div.zm-item-answer link").attr("href");
			Answer answer = new Answer(relativeUrl, Constant.ZHIHU_URL + relativeUrl);
			ZhiHuAnswerUpVoteProcessorHelper.processAnswerDetail(page, doc, answer);
		} else {
			List<UpVoteUser> upVoteUserList = ZhiHuAnswerUpVoteProcessorHelper.processUpVoteUserList(page);
			page.getResult().addAll(upVoteUserList);
		}
	}

	public static void main(String[] args) {
		String startUrl = "https://www.zhihu.com/question/35407612/answer/62619476";
		ZhiHuSpider.getInstance(new ZhiHuAnswerUpVoteProcessor()).setDomain("upvote").setThreadNum(5)
				.setStartRequest(new Request(HttpConstant.GET, startUrl)).run();
	}
}
