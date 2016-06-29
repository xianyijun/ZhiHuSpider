package me.kuye.spider.sample;

import org.apache.http.client.methods.HttpGet;

import me.kuye.spider.ZhiHuSpider;
import me.kuye.spider.entity.Page;
import me.kuye.spider.processor.Processor;

public class ZhiQuestionProcessor implements Processor {
	public static void main(String[] args) {
		ZhiHuSpider.getInstance(new ZhiQuestionProcessor()).setThreadNum(3).setDomain("question")
				.setStartRequest(new HttpGet("https://www.zhihu.com/question/24328297")).run();
	}

	@Override
	public void process(Page page) {
		System.out.println(page.getDocument());
	}
}
