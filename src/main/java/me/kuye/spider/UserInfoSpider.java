package me.kuye.spider;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

import me.kuye.spider.downloader.HttpDownloader;
import me.kuye.spider.downloader.ZhiHuClientGenerator;
import me.kuye.spider.executor.ProcessThreadPoolExecutor;
import me.kuye.spider.executor.ThreadPoolMonitor;
import me.kuye.spider.helper.LoginCookiesHelper;
import me.kuye.spider.pipeline.Storage;
import me.kuye.spider.task.DownloadTask;
import me.kuye.spider.task.ProcessorTask;

/**
 * @author xianyijun
 *
 */
public class UserInfoSpider {
	public static final Storage STORAGE = new Storage();

	public static void main(String[] args) {
		HttpDownloader downloader = new HttpDownloader();
		CloseableHttpClient client = downloader.getHttpClient("user");
		download("https://www.zhihu.com/people/aullik5/followees", client);
	}

	public static void download(String startUrl, CloseableHttpClient client) {
		ThreadPoolExecutor downloadThreadPoolExecutor = new ThreadPoolExecutor(5, 10, 3, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.DiscardOldestPolicy());
		ProcessThreadPoolExecutor processThreadPoolExecutor = new ProcessThreadPoolExecutor(1, 1, 3, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.DiscardOldestPolicy());
		HttpGet request = new HttpGet(startUrl);
		downloadThreadPoolExecutor.execute(new DownloadTask(request, STORAGE, client,
				processThreadPoolExecutor, downloadThreadPoolExecutor));
		ThreadPoolMonitor processThradPoolMonitor = new ThreadPoolMonitor(processThreadPoolExecutor, "解析页面线程池");
		ThreadPoolMonitor downloadThreadPoolMonitor = new ThreadPoolMonitor(downloadThreadPoolExecutor, "下载页面线程池");
		new Thread(processThradPoolMonitor).start();
		new Thread(downloadThreadPoolMonitor).start();
		while (true) {
			if (ProcessorTask.userCount.longValue() > 1000000L) {
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