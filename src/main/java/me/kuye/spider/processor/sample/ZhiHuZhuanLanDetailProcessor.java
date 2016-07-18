package me.kuye.spider.processor.sample;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

import me.kuye.spider.ZhiHuSpider;
import me.kuye.spider.entity.Page;
import me.kuye.spider.entity.Request;
import me.kuye.spider.pipeline.ConsolePipeline;
import me.kuye.spider.processor.Processor;
import me.kuye.spider.util.HttpConstant;
import me.kuye.spider.util.StringUtil;
import me.kuye.spider.vo.post.PostDetail;
import me.kuye.spider.vo.zhuanlan.ZhuanLanDetail;

/**
 * @author xianyijun
 *	抓取具体专栏信息
 */
public class ZhiHuZhuanLanDetailProcessor implements Processor {
	private static final Logger logger = LoggerFactory.getLogger(ZhiHuZhuanLanDetailProcessor.class);

	/*
	 * https://zhuanlan.zhihu.com/api/columns/iamelection/posts?limit=20&offset=20 获取更多的文章
	 * 
	 * https://zhuanlan.zhihu.com/api/recommendations/columns?limit=16&offset=32&seed=97随机获取专栏信息
	 * 
	 * https://zhuanlan.zhihu.com/api/columns/iamelection 获取专栏详情
	 * */
	@Override
	public void process(Page page) {
		Request request = page.getRequest();
		if (request.getUrl().indexOf("/posts") == -1) {
			ZhuanLanDetail zhuanLanDetail = JSON.parseObject(page.getRawtext(), ZhuanLanDetail.class);
			logger.info(zhuanLanDetail.toString());
			String firstPostUrl = "https://zhuanlan.zhihu.com/api/columns/" + zhuanLanDetail.getSlug() + "/posts";
			page.getTargetRequest().add(new Request(HttpConstant.GET, firstPostUrl));
			if (zhuanLanDetail.getPostsCount() > 20) {
				for (int i = 0; i < zhuanLanDetail.getPostsCount() / 20; i++) {
					String postUrl = "https://zhuanlan.zhihu.com/api/columns/" + zhuanLanDetail.getSlug()
							+ "/posts?limit=" + (i + 1) * 20 + "&offset=20";
					page.getTargetRequest().add(new Request(HttpConstant.GET, postUrl));
				}
			}
		} else {
			List<PostDetail> postDetailList = JSON.parseArray(page.getRawtext(), PostDetail.class);
			for (int i = 0; i < postDetailList.size(); i++) {
				logger.debug(postDetailList.get(i).toString());
			}
		}
	}

	public static void main(String[] args) {
		String url = "https://zhuanlan.zhihu.com/eateateatonlyknoweat";
		//也可以通过正则来匹配对应slug
		String columnsUrl = "https://zhuanlan.zhihu.com/api/columns/" + url.substring(url.lastIndexOf("/") + 1);
		logger.info(columnsUrl);
		ZhiHuSpider.getInstance(new ZhiHuZhuanLanDetailProcessor()).setThreadNum(3).setDomain("question")
				.addPipeline(new ConsolePipeline()).setStartRequest(new Request(HttpConstant.GET, columnsUrl)).run();
	}
}
