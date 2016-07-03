package me.kuye.spider.processor.sample;

import org.apache.http.client.methods.HttpGet;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.ZhiHuSpider;
import me.kuye.spider.entity.Page;
import me.kuye.spider.entity.Request;
import me.kuye.spider.entity.User;
import me.kuye.spider.pipeline.ConsolePipeline;
import me.kuye.spider.pipeline.MongoPipeline;
import me.kuye.spider.processor.Processor;
import me.kuye.spider.util.Constant;
import me.kuye.spider.util.UserInfo;

/**
 * @author xianyijun
 * 批量抓取用户信息
 */
public class ZhiHuUserProcessor implements Processor {
	private static Logger logger = LoggerFactory.getLogger(ZhiHuUserProcessor.class);

	@Override
	public void process(Page page) {
		Document document = page.getDocument();
		// 用户个人页面
		if (document.select("title").size() > 0) {
			User user = null;
			user = parseUserDetail(document);
			logger.info(user.toString());
			for (int i = 0; i < user.getFollowees() / 20 + 1; i++) {
				String url = "https://www.zhihu.com/node/ProfileFolloweesListV2?params={%22offset%22:" + 20 * i
						+ ",%22order_by%22:%22created%22,%22hash_id%22:%22" + user.getHashId() + "%22}";
				url = url.replaceAll("[{]", "%7B").replaceAll("[}]", "%7D").replaceAll(" ", "%20");
				HttpGet httpGet = new HttpGet(url);
				page.getTargetRequest().add(new Request(httpGet.getMethod(), httpGet.getURI().toString(), httpGet));
			}
			page.getResult().add(user);
		} else {
			Elements elements = document.select(".zm-list-content-medium .zm-list-content-title a");
			for (Element element : elements) {
				String url = element.attr("href") + "/followees";
				HttpGet httpGet = new HttpGet(url);
				page.getTargetRequest().add(new Request(httpGet.getMethod(), httpGet.getURI().toString(), httpGet));
			}
		}
	}

	private static User parseUserDetail(Document document) {
		User user = new User();
		user.setLocation(getUserInfo(document, UserInfo.LOCATION));
		user.setLocation(getUserInfo(document, UserInfo.BUSINESS));
		user.setLocation(getUserInfo(document, UserInfo.GENDER));
		user.setEmployment(getUserInfo(document, UserInfo.EMPLOYMENT));
		user.setPosition((getUserInfo(document, UserInfo.POSITION)));
		user.setEducation(getUserInfo(document, UserInfo.EDUCATION));
		user.setEducationExtra(getUserInfo(document, UserInfo.EDUCATION_EXTRA));
		try {
			user.setUserName(document.select(".title-section.ellipsis a").first().text());
			user.setUserUrl(Constant.ZHIHU_URL + document.select(".title-section.ellipsis a").first().attr("href"));
		} catch (NullPointerException e) {
			logger.info("NullPointerException", e);
		}
		user.setAgree(Integer.valueOf(document.select(".zm-profile-header-user-agree strong").first().text()));
		user.setThanks(Integer.valueOf(document.select(".zm-profile-header-user-thanks strong").first().text()));
		user.setFollowees(Integer.valueOf(document.select(".zm-profile-side-following strong").first().text()));
		user.setFollowers(Integer.valueOf(document.select(".zm-profile-side-following strong").get(1).text()));
		try {
			user.setHashId(document.select(".zm-profile-header-op-btns.clearfix button").first().attr("data-id"));
		} catch (NullPointerException e) {
			user.setHashId("9f6bd38abce3e6783f6aca46f0939e33");
		}
		return user;
	}

	private static String getUserInfo(Document document, String infoName) {
		Element element = null;
		if (infoName.equals(UserInfo.GENDER)) {
			Element e = document.select(".zm-profile-header-user-describe .gender.item").first();
			if (e != null) {
				String tmpGender = e.childNode(0).attr("class");
				String gender = tmpGender.substring(tmpGender.lastIndexOf("-") + 1);
				return gender;
			}
		} else {
			element = document.select(".zm-profile-header-user-describe ." + infoName + ".item").first();
		}
		if (element != null) {
			return element.attr("title");
		}
		return "";
	}

	public static void main(String[] args) {
		HttpGet getRequest = new HttpGet("https://www.zhihu.com/people/van-bruce");
		ZhiHuSpider.getInstance(new ZhiHuUserProcessor()).setThreadNum(3).setDomain("question")
				.addPipeline(new MongoPipeline()).addPipeline(new ConsolePipeline())
				.setStartRequest(new Request(getRequest.getMethod(), getRequest.getURI().toString(), getRequest)).run();
	}

}
