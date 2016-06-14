package me.kuye.spider;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

import me.kuye.spider.executor.StatisticsThreadPoolExecutor;
import me.kuye.spider.fetcher.ZhiHuFetcher;
import me.kuye.spider.helper.LoginCookiesHelper;
import me.kuye.spider.pipeline.Storage;
import me.kuye.spider.task.FetchTask;

public class ZhiHuSpider {
	private static final Storage STORAGE = new Storage();

	public static void main(String[] args) {
		CloseableHttpClient client = ZhiHuFetcher.getInstance().getClient();
		HttpClientContext context = ZhiHuFetcher.getInstance().getContext();
		context.setCookieStore((CookieStore) LoginCookiesHelper.antiSerializeCookies("/cookies"));
		download("https://www.zhihu.com/people/ku-xie-wei-diao-ling/followees", client, context);
	}

	public static void download(String startUrl, CloseableHttpClient client, HttpClientContext context) {
		ThreadPoolExecutor downloadThreadPoolExecutor = new ThreadPoolExecutor(5, 10, 3, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.DiscardOldestPolicy());
		StatisticsThreadPoolExecutor statisticsThreadPoolExecutor = new StatisticsThreadPoolExecutor(1, 1, 5,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.DiscardOldestPolicy());
		HttpGet request = new HttpGet(startUrl);
		downloadThreadPoolExecutor.execute(new FetchTask(request, STORAGE, context, client,
				statisticsThreadPoolExecutor, downloadThreadPoolExecutor));
	}
}