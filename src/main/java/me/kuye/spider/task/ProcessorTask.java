package me.kuye.spider.task;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.entity.User;
import me.kuye.spider.executor.StatisticsThreadPoolExecutor;
import me.kuye.spider.pipeline.Storage;
import me.kuye.spider.util.UserInfo;

public class ProcessorTask implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(ProcessorTask.class);
	private static AtomicLong userCount = new AtomicLong();
	private static AtomicLong pageCount = new AtomicLong();
	private Storage storage;
	private CloseableHttpClient client;
	private HttpClientContext context;
	private StatisticsThreadPoolExecutor statisticsThreadPoolExecutor;
	private ThreadPoolExecutor downloadThreadPoolExecutor;

	public ProcessorTask() {
	}

	public ProcessorTask(Storage storage, CloseableHttpClient client, HttpClientContext context,
			StatisticsThreadPoolExecutor statisticsThreadPoolExecutor, ThreadPoolExecutor downloadThreadPoolExecutor) {
		this.storage = storage;
		this.client = client;
		this.context = context;
		this.statisticsThreadPoolExecutor = statisticsThreadPoolExecutor;
		this.downloadThreadPoolExecutor = downloadThreadPoolExecutor;
	}

	public void run() {
		pageCount.incrementAndGet();
		try {
			String content = storage.pop();
			Document document = Jsoup.parse(content);
			User user = null;
			if (document.select("title").size() > 0) {
				parseUserDetail(document);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 
	 * public static final String LOCATION = "location"; public static final
	 * String BUSINESS = "business"; public static final String GENDER =
	 * "gender"; public static final String EMPLOYMENT="employment"; public
	 * static final String POSITION="position"; public static final String
	 * EDUCATION="education"; public static final String
	 * EDUCATION_EXTRA="education-extra";
	 */
	private void parseUserDetail(Document document) {
		String location = getUserInfo(document, UserInfo.LOCATION);
		String business = getUserInfo(document, UserInfo.BUSINESS);
		String gender = getUserInfo(document, UserInfo.GENDER);
		String employment = getUserInfo(document, UserInfo.EMPLOYMENT);
		String position = getUserInfo(document, UserInfo.POSITION);
		String education = getUserInfo(document, UserInfo.EDUCATION);
		String educationExtra = getUserInfo(document, UserInfo.EDUCATION_EXTRA);
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
