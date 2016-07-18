package me.kuye.spider.util;

/**
 * @author xianyijun
 *	常量配置
 */
public final class Constant {
	public static final String ZHIHU_PHONE_LOGIN_URL = "https://www.zhihu.com/login/phone_num";
	public static final String ZHIHU_URL = "https://www.zhihu.com";
	public static final String VALIE_CODE_IMAGE = "https://www.zhihu.com/captcha.gif?type=login";
	public static final String COOIKES_SERIALIZE_PATH = "src/main/resources/cookies";
	public static final String ZHIHU_EMAIL_LOGIN_URL = "https://www.zhihu.com/login/email";
	public static final String ZHIHU_FOLLOWEES_URL = "https://www.zhihu.com/node/ProfileFolloweesListV2";
	public static final String ZHIHU_DEFAULT_PROPERTIES = "/default.properties";

	public static final String ZHIHU_ANSWER_URL = "https://www.zhihu.com/node/QuestionAnswerListV2";
	public static final String ZHIHU_ANSWER_POST_URL = "https://www.zhihu.com/node/QuestionAnswerListV2?method=next&params=%7B%22url_token%22%3A{url_token}%2C%22pagesize%22%3A{pagesize}%2C%22offset%22%3A{offset}%7D&_xsrf={_xsrf}";

	public static final String QUESTION_URL_TOKEN = "urlToken";
	public static final String ZHIHU_ZHUANLAN_COLUMN_URL = "https://zhuanlan.zhihu.com/api/columns/{slug}";
	public static final String ANSWER_UPVOTE_USER_URL = "https://www.zhihu.com/answer/{dataAid}/voters_profile?&offset=0";

}
