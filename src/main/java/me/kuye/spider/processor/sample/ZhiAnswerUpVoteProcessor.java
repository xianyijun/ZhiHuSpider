package me.kuye.spider.processor.sample;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import me.kuye.spider.ZhiHuSpider;
import me.kuye.spider.entity.Answer;
import me.kuye.spider.entity.Page;
import me.kuye.spider.entity.Request;
import me.kuye.spider.entity.UpVoteUser;
import me.kuye.spider.processor.Processor;
import me.kuye.spider.util.Constant;
import me.kuye.spider.vo.UpVoteResult;

public class ZhiAnswerUpVoteProcessor implements Processor {
	private static Logger logger = LoggerFactory.getLogger(ZhiAnswerUpVoteProcessor.class);

	@Override
	public void process(Page page) {
		Document doc = page.getDocument();
		if (doc.select("title").size() > 0) {
			String relativeUrl = doc.select("div.zm-item-answer link").attr("href");
			Answer answer = new Answer(relativeUrl, Constant.ZHIHU_URL + relativeUrl);
			processAnswerDetail(page, doc, answer);
		} else {
			List<UpVoteUser> upVoteUserList = processUpVoteUserList(page);
			page.getResult().addAll(upVoteUserList);
		}
	}

	private static List<UpVoteUser> processUpVoteUserList(Page page) {
		UpVoteResult upVoteResult = null;
		List<UpVoteUser> userList = new LinkedList<>();
		upVoteResult = JSON.parseObject(page.getRawtext(), UpVoteResult.class);
		String[] payload = upVoteResult.getPayload();
		for (int i = 0; i < payload.length; i++) {
			Document doc = Jsoup.parse(payload[i]);
			UpVoteUser upVoteUser = new UpVoteUser();
			Element avatar = doc.select("img.zm-item-img-avatar").first();
			upVoteUser.setAvatar(avatar.attr("src"));
			//点赞用户不为匿名用户
			if (!"匿名用户".equals(avatar.attr("title"))) {
				upVoteUser.setName(doc.select(".zg-link").attr("title"));
				upVoteUser.setBio(doc.select(".bio").text());
				upVoteUser.setAgree(doc.select(".status").first().child(0).text());
				upVoteUser.setThanks(doc.select(".status").first().child(1).text());
				upVoteUser.setAnswers(doc.select(".status").first().child(3).text());
				upVoteUser.setAsks(doc.select(".status").first().child(2).text());
			} else {
				upVoteUser.setName(avatar.attr("title"));
			}
			userList.add(upVoteUser);
		}
		String nextUrl = upVoteResult.getPaging().getNext();
		if (!nextUrl.equals("") && nextUrl.trim().length() > 0) {
			HttpGet getMethod = new HttpGet(Constant.ZHIHU_URL + nextUrl);
			page.getTargetRequest().add(new Request(getMethod.getMethod(), getMethod.getURI().toString(), getMethod));
		}
		return userList;
	}

	private static void processAnswerDetail(Page page, Document answerDoc, Answer answer) {
		answer.setContent(answerDoc.select("div[data-entry-url=" + answer.getRelativeUrl() + "]  .zm-editable-content")
				.html().replaceAll("<br>", ""));
		answer.setUpvote(answerDoc.select(".zm-votebar span.count").first().text());

		/*
		 * 不可以直接用a.author-link来获取，如果是知乎用户的话，不存在该标签 
		 * #zh-question-answer-wrap >div > div.answer-head > div.zm-item-answer-author-info >a.author-link 普通用户 2 
		 * #zh-question-answer-wrap > div > div.answer-head> div.zm-item-answer-author-info > span 知乎用户 2
		 * #zh-question-answer-wrap > div > div.answer-head >div.zm-item-answer-author-info > span 匿名用户 1
		 * 我们可以先通过获取zm-item-answer-author-info来获取用户名，因为是否为知乎用户的话
		 */
		Element authorInfo = answerDoc.select(".zm-item-answer-author-info").first();

		String author = authorInfo.select(".name").size() == 0 ? authorInfo.select("a.author-link").text()
				: authorInfo.select(".name").text();
		answer.setAuthor(author);

		String dataAid = answerDoc.select(".zm-item-answer").attr("data-aid");
		answer.setDataAid(dataAid);

		String upvoteUserUrl = Constant.ZHIHU_URL + "/answer/" + dataAid + "/voters_profile?&offset=0";
		answer.setStartUpvoteUserUrl(upvoteUserUrl);
		HttpGet upVoteUserRequest = new HttpGet(upvoteUserUrl);
		page.getTargetRequest().add(
				new Request(upVoteUserRequest.getMethod(), upVoteUserRequest.getURI().toString(), upVoteUserRequest));
	}

	public static void main(String[] args) {
		HttpGet answerRequest = new HttpGet("https://www.zhihu.com/question/35407612/answer/62619476");
		ZhiHuSpider.getInstance(new ZhiAnswerUpVoteProcessor()).setDomain("upvote").setThreadNum(5).setStartRequest(
				new Request(answerRequest.getMethod(), answerRequest.getURI().toString(), answerRequest)).run();
	}
}
