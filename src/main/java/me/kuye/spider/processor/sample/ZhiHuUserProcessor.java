package me.kuye.spider.processor.sample;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.ZhiHuSpider;
import me.kuye.spider.Scheduler.impl.UserRedisScheduler;
import me.kuye.spider.entity.Page;
import me.kuye.spider.entity.Request;
import me.kuye.spider.entity.User;
import me.kuye.spider.pipeline.ConsolePipeline;
import me.kuye.spider.pipeline.UserPipeline;
import me.kuye.spider.processor.Processor;
import me.kuye.spider.processor.helper.UserInfoProcessorHelper;
import me.kuye.spider.util.HttpConstant;

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
			try {
				user = UserInfoProcessorHelper.parseUserDetail(document);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			for (int i = 0; i < user.getFollowees() / 20 + 1; i++) {
				String url = "https://www.zhihu.com/node/ProfileFolloweesListV2?params={%22offset%22:" + 20 * i
						+ ",%22order_by%22:%22created%22,%22hash_id%22:%22" + user.getHashId() + "%22}";
				url = url.replaceAll("[{]", "%7B").replaceAll("[}]", "%7D").replaceAll(" ", "%20");
				page.getTargetRequest().add(new Request(HttpConstant.GET, url));
			}
			page.getResult().add(user);
		} else {
			Elements elements = document.select(".zm-list-content-medium .zm-list-content-title a");
			for (Element element : elements) {
				String url = element.attr("href") + "/followees";
				page.getTargetRequest().add(new Request(HttpConstant.GET, url));
			}
		}
	}

	public static void main(String[] args) {
		String url = "https://www.zhihu.com/people/seawaver/followees";
		ZhiHuSpider.getInstance(new ZhiHuUserProcessor()).setThreadNum(3).setDomain("question")
				.setScheduler(new UserRedisScheduler()).addPipeline(new UserPipeline())
				.addPipeline(new ConsolePipeline()).setStartRequest(new Request(HttpConstant.GET, url)).run();
	}

}
