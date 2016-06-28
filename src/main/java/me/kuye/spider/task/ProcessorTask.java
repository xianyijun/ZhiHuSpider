package me.kuye.spider.task;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.dao.mongo.UserMongoDao;
import me.kuye.spider.dao.redis.UrlItemDao;
import me.kuye.spider.entity.User;
import me.kuye.spider.executor.ProcessThreadPoolExecutor;
import me.kuye.spider.pipeline.Storage;
import me.kuye.spider.util.Constant;
import me.kuye.spider.util.MD5Util;
import me.kuye.spider.util.UserInfo;

public class ProcessorTask implements Task {
	private static Logger logger = LoggerFactory.getLogger(ProcessorTask.class);
	private final Storage storage;
	private CloseableHttpClient client;
	private ProcessThreadPoolExecutor processThreadPoolExecutor;
	private ThreadPoolExecutor downloadThreadPoolExecutor;
	public static final AtomicLong userCount = new AtomicLong();
	public static final AtomicLong pageCount = new AtomicLong();
	private UserMongoDao userDao = new UserMongoDao();
	private UrlItemDao urlItemDao = new UrlItemDao();

	public ProcessorTask(Storage storage, CloseableHttpClient client,
			ProcessThreadPoolExecutor processThreadPoolExecutor, ThreadPoolExecutor downloadThreadPoolExecutor) {
		this.storage = storage;
		this.client = client;
		this.processThreadPoolExecutor = processThreadPoolExecutor;
		this.downloadThreadPoolExecutor = downloadThreadPoolExecutor;
	}

	public void run() {
		try {
			String content = storage.pop();
			Document document = Jsoup.parse(content);
			User user = null;
			// 用户个人页面
			if (document.select("title").size() > 0) {
				user = parseUserDetail(document);
				logger.info(user.toString());
				userCount.getAndIncrement();
				if ((!userDao.exist(user.getHashId())) && userDao.save(user)) {
					pageCount.incrementAndGet();
					logger.info("当前已经添加用户数: " + userCount);
				} else {
					logger.info("当用户已经添加过了" + user);
				}
				// https://www.zhihu.com/node/ProfileFolloweesListV2?method=next&params=%7B%22offset%22%3A20%2C%22order_by%22%3A%22created%22%2C%22hash_id%22%3A%229f6bd38abce3e6783f6aca46f0939e33%22%7D&_xsrf=7d97966cb8f4291e6992caed26e50f10
				for (int i = 0; i < user.getFollowees() / 20 + 1; i++) {
					String url = "https://www.zhihu.com/node/ProfileFolloweesListV2?params={%22offset%22:" + 20 * i
							+ ",%22order_by%22:%22created%22,%22hash_id%22:%22" + user.getHashId() + "%22}";
					url = url.replaceAll("[{]", "%7B").replaceAll("[}]", "%7D").replaceAll(" ", "%20");
					if (downloadThreadPoolExecutor.getQueue().size() <= 100) {
						handleUrl(url);
					}
				}
			} else {
				Elements es = document.select(".zm-list-content-medium .zm-list-content-title a");
				for (Element temp : es) {
					String userIndex = temp.attr("href") + "/followees";
					handleUrl(userIndex);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleUrl(String url) {
		String md5Url = MD5Util.MD5Encode(url);
		if (urlItemDao.add(md5Url) || downloadThreadPoolExecutor.getQueue().size() <= 50) {
			if (processThreadPoolExecutor.getQueue().size() <= 100) {
				HttpGet request = null;
				try {
					// 防止知乎反爬策略，访问频率太快
					Thread.sleep(800);
					request = new HttpGet(url);
					downloadThreadPoolExecutor.execute(new DownloadTask(request, storage, client,
							processThreadPoolExecutor, downloadThreadPoolExecutor));
				} catch (IllegalArgumentException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			// logger.info(url + " 链接已经解析过了 :");
		}
	}

	private User parseUserDetail(Document document) {
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
			logger.info(document.toString());
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

	private String getUserInfo(Document document, String infoName) {
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
