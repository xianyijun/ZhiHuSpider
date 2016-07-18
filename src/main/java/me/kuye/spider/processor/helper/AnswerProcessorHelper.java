package me.kuye.spider.processor.helper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import me.kuye.spider.core.Page;
import me.kuye.spider.core.Request;
import me.kuye.spider.dto.answer.UpVoteResult;
import me.kuye.spider.entity.Answer;
import me.kuye.spider.entity.UpVoteUser;
import me.kuye.spider.util.Constant;
import me.kuye.spider.util.HttpConstant;

public class AnswerProcessorHelper {
	private static Logger logger = LoggerFactory.getLogger(QuestionProcessorHelper.class);

	/**
	* @Title: processAnswerList
	* @Description: 获取问题回答请求列表
	* @param     参数
	* @return List<Request>    返回类型
	* @throws
	*/
	public static List<Request> processAnswerList(String urlToken, String xsrf, long answerNum) {
		List<Request> answerRequestList = new ArrayList<>();

		for (int i = 0; i < answerNum / 10 + 1; i++) {
		
		/*	List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
			valuePairs.add(new BasicNameValuePair("method", "next"));
			valuePairs.add(new BasicNameValuePair("xsrf", xsrf));
			JSONObject obj = new JSONObject();
			obj.put("url_token", urlToken);
			// 并没有什么用，服务器端固定为10
			obj.put("pagesize", 10);
			obj.put("offset", 10 * i);
			valuePairs.add(new BasicNameValuePair("params", obj.toJSONString()));
			answerRequestList.add(new Request(HttpConstant.POST, Constant.ZHIHU_ANSWER_URL)
					.addExtra(HttpConstant.NAMEVALUEPAIR, valuePairs)
					.addExtra(HttpConstant.NO_COOKIE, HttpConstant.NO_COOKIE)
					.addExtra(Constant.QUESTION_URL_TOKEN, urlToken));*/
			
			String url = Constant.ZHIHU_ANSWER_POST_URL.replace("{url_token}", urlToken).replace("{pagesize}", "10")
					.replace("{_xsrf}", xsrf).replace("{offset}", 10 * i + "");
			answerRequestList
					.add(new Request(HttpConstant.POST, url).addExtra("HttpConstant.NO_COOKIE", HttpConstant.NO_COOKIE)
							.addExtra(Constant.QUESTION_URL_TOKEN, urlToken));

		}
		return answerRequestList;
	}

	/**
	* @Title: processAnswerDetail
	* @Description: 解析回答详情，并添加点赞用户详情
	* @param     参数
	* @return void    返回类型
	* @throws
	*/
	public static void processAnswerDetail(Document answerDoc, Answer answer) {
		answer.setContent(answerDoc.select("div[data-entry-url=" + answer.getUrl() + "]  .zm-editable-content").html()
				.replaceAll("<br>", ""));
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
	}
}
