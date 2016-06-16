package me.kuye.spider;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

import me.kuye.spider.executor.ProcessThreadPoolExecutor;
import me.kuye.spider.fetcher.ZhiHuFetcher;
import me.kuye.spider.helper.LoginCookiesHelper;
import me.kuye.spider.pipeline.Storage;
import me.kuye.spider.task.DownloadTask;
import me.kuye.spider.task.ProcessorTask;
import me.kuye.spider.util.ThreadPoolMonitor;

public class ZhiHuSpider {
	public static final Storage STORAGE = new Storage();

	public static void main(String[] args) {
		CloseableHttpClient client = ZhiHuFetcher.getInstance().getClient();
		HttpClientContext context = ZhiHuFetcher.getInstance().getContext();
		context.setCookieStore((CookieStore) LoginCookiesHelper.antiSerializeCookies("/cookies"));
		download("https://www.zhihu.com/people/sun_lei/followees", client, context);
	}

	public static void download(String startUrl, CloseableHttpClient client, HttpClientContext context) {
		ThreadPoolExecutor downloadThreadPoolExecutor = new ThreadPoolExecutor(5, 5, 3, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.DiscardOldestPolicy());
		ProcessThreadPoolExecutor processThreadPoolExecutor = new ProcessThreadPoolExecutor(3, 5, 5, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.DiscardOldestPolicy());
		HttpGet request = new HttpGet(startUrl);
		downloadThreadPoolExecutor.execute(new DownloadTask(request, STORAGE, context, client,
				processThreadPoolExecutor, downloadThreadPoolExecutor));
		ThreadPoolMonitor processThradPoolMonitor = new ThreadPoolMonitor(processThreadPoolExecutor, "解析页面线程池");
		ThreadPoolMonitor downloadThreadPoolMonitor = new ThreadPoolMonitor(downloadThreadPoolExecutor, "下载页面线程池");
		new Thread(processThradPoolMonitor).start();
		new Thread(downloadThreadPoolMonitor).start();
		while (true) {
			if (ProcessorTask.userCount.longValue() > 1000L) {
				downloadThreadPoolExecutor.shutdown();
				if (downloadThreadPoolExecutor.isTerminated() && STORAGE.getResultQueue().size() == 0) {
					processThreadPoolExecutor.shutdown();
					processThradPoolMonitor.shutdown();
					downloadThreadPoolMonitor.shutdown();
					break;
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}