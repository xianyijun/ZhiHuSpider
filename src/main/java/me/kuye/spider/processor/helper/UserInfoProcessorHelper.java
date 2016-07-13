package me.kuye.spider.processor.helper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.entity.User;
import me.kuye.spider.util.Constant;
import me.kuye.spider.util.UserInfo;

public class UserInfoProcessorHelper {
	private static Logger logger = LoggerFactory.getLogger(UserInfoProcessorHelper.class);

	public  static User parseUserDetail(Document document) {
		User user = new User();
		user.setLocation(getUserInfo(document, UserInfo.LOCATION));
		user.setBusiness(getUserInfo(document, UserInfo.BUSINESS));
		user.setGender(getUserInfo(document, UserInfo.GENDER));
		user.setEmployment(getUserInfo(document, UserInfo.EMPLOYMENT));
		user.setPosition((getUserInfo(document, UserInfo.POSITION)));
		user.setEducation(getUserInfo(document, UserInfo.EDUCATION));
		user.setEducationExtra(getUserInfo(document, UserInfo.EDUCATION_EXTRA));
		try {
			user.setUserName(document.select(".title-section.ellipsis a").first().text());
			user.setUserUrl(Constant.ZHIHU_URL + document.select(".title-section.ellipsis a").first().attr("href"));
		} catch (NullPointerException e) {
			logger.info(document.toString());
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

}
