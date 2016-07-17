package me.kuye.spider.processor.sample;

import me.kuye.spider.ZhiHuSpider;
import me.kuye.spider.Scheduler.impl.UserRedisScheduler;
import me.kuye.spider.entity.Page;
import me.kuye.spider.entity.Request;
import me.kuye.spider.pipeline.ConsolePipeline;
import me.kuye.spider.pipeline.UserPipeline;
import me.kuye.spider.processor.Processor;
import me.kuye.spider.util.HttpConstant;

/**
 * @author xianyijun
 *	抓取具体专栏信息
 */
public class ZhiHuZhuanLanDetailProcessor implements Processor {

	@Override
	public void process(Page page) {
	}

	public static void main(String[] args) {
		String url = "https://zhuanlan.zhihu.com/anitalk";
		ZhiHuSpider.getInstance(new ZhiHuUserProcessor()).setThreadNum(3).setDomain("question")
				.addPipeline(new ConsolePipeline()).setStartRequest(new Request(HttpConstant.GET, url)).run();
	}
}
