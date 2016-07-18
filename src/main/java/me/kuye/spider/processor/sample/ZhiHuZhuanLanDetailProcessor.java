package me.kuye.spider.processor.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import me.kuye.spider.ZhiHuSpider;
import me.kuye.spider.entity.Page;
import me.kuye.spider.entity.Request;
import me.kuye.spider.pipeline.ConsolePipeline;
import me.kuye.spider.processor.Processor;
import me.kuye.spider.util.HttpConstant;
import me.kuye.spider.util.StringUtil;
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
		if (request.getUrl().indexOf("/posts?")==-1) {
			String result = StringUtil.decodeUnicode(page.getRawtext());
			ZhuanLanDetail zhuanLanDetail = JSON.parseObject(result, ZhuanLanDetail.class);
			logger.info(zhuanLanDetail.toString());
			for (int i = 0; i < zhuanLanDetail.getPostsCount() / 20 + 1; i++) {
				String postUrl = "https://zhuanlan.zhihu.com/api/columns/" + zhuanLanDetail.getSlug() + "/posts?limit="
						+ i * 20 + "&offset=20";
				page.getTargetRequest().add(new Request(HttpConstant.GET,postUrl));
			}
		}else{
			String result = StringUtil.decodeUnicode(page.getRawtext());
			logger.debug(result);
		}
	}

	public static void main(String[] args) {
		String url = "https://zhuanlan.zhihu.com/chuapp";
		//也可以通过正则来匹配对应slug
		String columnsUrl = "https://zhuanlan.zhihu.com/api/columns/" + url.substring(url.lastIndexOf("/") + 1);
		logger.info(columnsUrl);
		ZhiHuSpider.getInstance(new ZhiHuZhuanLanDetailProcessor()).setThreadNum(3).setDomain("question")
				.addPipeline(new ConsolePipeline()).setStartRequest(new Request(HttpConstant.GET, columnsUrl)).run();
	}
	
}
