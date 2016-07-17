package me.kuye.spider.processor.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.ZhiHuSpider;
import me.kuye.spider.entity.Page;
import me.kuye.spider.entity.Request;
import me.kuye.spider.pipeline.ConsolePipeline;
import me.kuye.spider.processor.Processor;
import me.kuye.spider.util.HttpConstant;

/**
 * @author xianyijun
 *	抓取具体专栏信息
 */
public class ZhiHuZhuanLanDetailProcessor implements Processor {
	private static final Logger logger = LoggerFactory.getLogger(ZhiHuZhuanLanDetailProcessor.class);

	@Override
	public void process(Page page) {
		logger.info(page.getRawtext());
	}

	public static void main(String[] args) {
		String url = "https://zhuanlan.zhihu.com/anitalk";
		ZhiHuSpider.getInstance(new ZhiHuZhuanLanDetailProcessor()).setThreadNum(3).setDomain("question")
				.addPipeline(new ConsolePipeline()).setStartRequest(new Request(HttpConstant.GET, url)).run();
	}
}
