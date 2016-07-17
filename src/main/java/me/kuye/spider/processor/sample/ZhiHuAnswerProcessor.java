package me.kuye.spider.processor.sample;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import me.kuye.spider.ZhiHuSpider;
import me.kuye.spider.entity.Answer;
import me.kuye.spider.entity.Page;
import me.kuye.spider.entity.Question;
import me.kuye.spider.entity.Request;
import me.kuye.spider.pipeline.ConsolePipeline;
import me.kuye.spider.processor.Processor;
import me.kuye.spider.processor.helper.ZhiHuAnswerProcessorHelper;
import me.kuye.spider.processor.helper.ZhiHuQuestionProcessorHelper;
import me.kuye.spider.util.Constant;
import me.kuye.spider.util.HttpConstant;
import me.kuye.spider.vo.AnswerResult;

public class ZhiHuAnswerProcessor implements Processor {
	private static final Logger logger = LoggerFactory.getLogger(ZhiHuAnswerProcessor.class);

	@Override
	public void process(Page page) {
		Request request = page.getRequest();
		String requestUrl = request.getUrl();
		Document doc = page.getDocument();
		if (requestUrl.equals(Constant.ZHIHU_ANSWER_URL)) {
			String urlToken = (String) request.getExtra(Constant.QUESTION_URL_TOKEN);
			AnswerResult answerResult = null;
			answerResult = JSONObject.parseObject(page.getRawtext(), AnswerResult.class);
			String[] msg = answerResult.getMsg();
			for (int i = 0; i < msg.length; i++) {
				Document answerDoc = Jsoup.parse(msg[i]);
				String relativeUrl = answerDoc.select("div.zm-item-answer link").attr("href");
				Answer answer = new Answer(relativeUrl, Constant.ZHIHU_URL + relativeUrl);
				ZhiHuAnswerProcessorHelper.processAnswerDetail(answerDoc, answer);
				answer.setUrlToken(urlToken);
				page.getResult().add(answer);
			}
		} else {
			//解析问题详情请求
			Question question = new Question(requestUrl);
			ZhiHuQuestionProcessorHelper.processQuestion(page, question);
			page.getResult().add(question);
			String xsrf = doc.select("input[name=_xsrf]").attr("value");
			List<Request> answerList = ZhiHuAnswerProcessorHelper.processAnswerList(question.getUrlToken(), xsrf,
					question.getAnswerNum());
			page.getTargetRequest().addAll(answerList);
		}
	}

	public static void main(String[] args) {
		String url = "https://www.zhihu.com/question/20790679";
		ZhiHuSpider.getInstance(new ZhiHuAnswerProcessor()).setThreadNum(3).setDomain("answer")
				.addPipeline(new ConsolePipeline()).setStartRequest(new Request(HttpConstant.GET, url)).run();
	}
}
