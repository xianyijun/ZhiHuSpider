package me.kuye.spider.processor.sample;

import java.util.List;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.core.Page;
import me.kuye.spider.core.Request;
import me.kuye.spider.core.SimpleSpider;
import me.kuye.spider.entity.Answer;
import me.kuye.spider.entity.UpVoteUser;
import me.kuye.spider.processor.Processor;
import me.kuye.spider.processor.helper.AnswerProcessorHelper;
import me.kuye.spider.processor.helper.AnswerUpVoteProcessorHelper;
import me.kuye.spider.util.Constant;
import me.kuye.spider.util.HttpConstant;

public class ZhiHuAnswerUpVoteProcessor implements Processor {
	private static final Logger logger = LoggerFactory.getLogger(ZhiHuAnswerUpVoteProcessor.class);

	@Override
	public void process(Page page) {
		Document doc = page.getDocument();
		if (doc.select("title").size() > 0) {
			String url = doc.select("div.zm-item-answer link").attr("href");
			Answer answer = new Answer(url);
			AnswerProcessorHelper.processAnswerDetail(doc, answer);
			String upvoteUserUrl = Constant.ANSWER_UPVOTE_USER_URL.replace("{dataAid}", answer.getDataAid());
			page.getTargetRequest().add(new Request(HttpConstant.GET, upvoteUserUrl));
		} else {
			List<UpVoteUser> upVoteUserList = AnswerUpVoteProcessorHelper.processUpVoteUserList(page);
			page.getResult().addAll(upVoteUserList);
		}
	}

	public static void main(String[] args) {
		String startUrl = "https://www.zhihu.com/question/35407612/answer/62619476";
		SimpleSpider.getInstance(new ZhiHuAnswerUpVoteProcessor()).setDomain("upvote").setThreadNum(5)
				.setStartRequest(new Request(HttpConstant.GET, startUrl)).run();
	}
}
