package me.kuye.spider.processor.helper;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import me.kuye.spider.core.Page;
import me.kuye.spider.core.Request;
import me.kuye.spider.dto.answer.UpVoteResult;
import me.kuye.spider.entity.Answer;
import me.kuye.spider.entity.UpVoteUser;
import me.kuye.spider.util.Constant;
import me.kuye.spider.util.HttpConstant;

public class AnswerUpVoteProcessorHelper {
	private static Logger logger = LoggerFactory.getLogger(AnswerUpVoteProcessorHelper.class);

	/**
	* @Title: processUpVoteUserList
	* @Description: 解析点赞用户列表信息，并添加next请求
	* @param     参数
	* @return List<UpVoteUser>    返回类型
	* @throws
	*/
	public static List<UpVoteUser> processUpVoteUserList(Page page) {
		logger.info("问题的url : " + page.getRequest().getUrl());
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
			String methodUrl = Constant.ZHIHU_URL + nextUrl;
			page.getTargetRequest().add(new Request(HttpConstant.GET, methodUrl));
		}
		return userList;
	}
}
