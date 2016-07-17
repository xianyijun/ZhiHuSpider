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
import me.kuye.spider.vo.ZhuanLanDetail;

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
		logger.info(page.getRawtext());
		ZhuanLanDetail zhuanLanDetail = JSON.parseObject(page.getRawtext(), ZhuanLanDetail.class);
		logger.info(zhuanLanDetail.toString());
	}

	public static void main(String[] args) {
		String url = "https://zhuanlan.zhihu.com/stress-w0v0w-";
		//也可以通过正则来匹配对应slug
		String columnsUrl = "https://zhuanlan.zhihu.com/api/columns/" + url.substring(url.lastIndexOf("/") + 1);
		logger.info(columnsUrl);
		ZhiHuSpider.getInstance(new ZhiHuZhuanLanDetailProcessor()).setThreadNum(3).setDomain("question")
				.addPipeline(new ConsolePipeline()).setStartRequest(new Request(HttpConstant.GET, columnsUrl)).run();
	}
}
