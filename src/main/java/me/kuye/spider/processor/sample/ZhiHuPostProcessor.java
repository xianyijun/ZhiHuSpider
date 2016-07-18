package me.kuye.spider.processor.sample;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import me.kuye.spider.core.Page;
import me.kuye.spider.core.Request;
import me.kuye.spider.core.SimpleSpider;
import me.kuye.spider.dto.column.ColumnDetail;
import me.kuye.spider.dto.post.PostDetail;
import me.kuye.spider.entity.Post;
import me.kuye.spider.pipeline.impl.ConsolePipeline;
import me.kuye.spider.processor.Processor;
import me.kuye.spider.processor.helper.PostProcessorHelper;
import me.kuye.spider.util.Constant;
import me.kuye.spider.util.HttpConstant;

/**
 * @author xianyijun
 *	抓取具体专栏信息
 */
public class ZhiHuPostProcessor implements Processor {
	private static final Logger logger = LoggerFactory.getLogger(ZhiHuPostProcessor.class);

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
		// TODO 使用正则匹配
		if (request.getUrl().indexOf("/posts") == -1) {
			ColumnDetail columnDetail = JSON.parseObject(page.getRawtext(), ColumnDetail.class);
			String firstPostUrl = "https://zhuanlan.zhihu.com/api/columns/" + columnDetail.getSlug() + "/posts";
			page.getTargetRequest().add(new Request(HttpConstant.GET, firstPostUrl));
			if (columnDetail.getPostsCount() > 20) {
				for (int i = 0; i < columnDetail.getPostsCount() / 20; i++) {
					String postUrl = "https://zhuanlan.zhihu.com/api/columns/" + columnDetail.getSlug()
							+ "/posts?limit=" + (i + 1) * 20 + "&offset=20";
					page.getTargetRequest().add(new Request(HttpConstant.GET, postUrl));
				}
			}
		} else {
			List<PostDetail> postDetailList = JSON.parseArray(page.getRawtext(), PostDetail.class);
			List<Post> postList = PostProcessorHelper.convertPostDetailListToPostList(postDetailList);
			page.getResult().addAll(postList);
		}
	}

	public static void main(String[] args) {
		//默认种子url
		String url = "https://zhuanlan.zhihu.com/Chenrui18124";
		//从命令行中传入种子url
		if (args != null && args.length > 0) {
			url = args[0];
		}
		//也可以通过正则来匹配对应slug
		String slug = url.substring(url.lastIndexOf("/") + 1);
		String columnUrl = Constant.ZHIHU_ZHUANLAN_COLUMN_URL.replace("{slug}", slug);
		SimpleSpider.getInstance(new ZhiHuPostProcessor()).setThreadNum(3).setDomain("post")
				.addPipeline(new ConsolePipeline()).setStartRequest(new Request(HttpConstant.GET, columnUrl)).run();
	}
}
