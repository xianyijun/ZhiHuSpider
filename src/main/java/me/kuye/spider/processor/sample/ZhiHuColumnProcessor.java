package me.kuye.spider.processor.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import me.kuye.spider.ZhiHuSpider;
import me.kuye.spider.entity.Column;
import me.kuye.spider.entity.Page;
import me.kuye.spider.entity.Request;
import me.kuye.spider.pipeline.ConsolePipeline;
import me.kuye.spider.processor.Processor;
import me.kuye.spider.processor.helper.ZhiHuColumnProcessorHelper;
import me.kuye.spider.util.Constant;
import me.kuye.spider.util.HttpConstant;
import me.kuye.spider.vo.column.ColumnDetail;

public class ZhiHuColumnProcessor implements Processor {
	private static final Logger logger = LoggerFactory.getLogger(ZhiHuColumnProcessor.class);

	@Override
	public void process(Page page) {
		ColumnDetail columnDetail = JSON.parseObject(page.getRawtext(), ColumnDetail.class);
		logger.debug(columnDetail.toString());
		Column column = ZhiHuColumnProcessorHelper.convertColumnDetailToColumn(columnDetail);
		page.getResult().add(column);
	}

	public static void main(String[] args) {
		//默认种子url
		String url = "https://zhuanlan.zhihu.com/otaku";
		//从命令行中传入种子url
		if (args != null && args.length > 0) {
			url = args[0];
		}
		//也可以通过正则来匹配对应slug
		String slug = url.substring(url.lastIndexOf("/") + 1);
		String columnUrl = Constant.ZHIHU_ZHUANLAN_COLUMN_URL.replace("{slug}", slug);
		ZhiHuSpider.getInstance(new ZhiHuColumnProcessor()).setThreadNum(3).setDomain("column")
				.addPipeline(new ConsolePipeline()).setStartRequest(new Request(HttpConstant.GET, columnUrl)).run();
	}
}
