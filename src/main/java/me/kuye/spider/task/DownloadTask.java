package me.kuye.spider.task;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.executor.ProcessThreadPoolExecutor;
import me.kuye.spider.pipeline.Storage;

public class DownloadTask implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(DownloadTask.class);
	public static AtomicLong downloadPageCount = new AtomicLong();
	private HttpGet request = null;
	private final Storage storage;
	private HttpClientContext context;
	private CloseableHttpClient client;
	private ProcessThreadPoolExecutor processThreadPoolExecutor;
	private ThreadPoolExecutor downloadThreadPoolExecutor;

	public DownloadTask(HttpGet request, Storage storage, HttpClientContext context, CloseableHttpClient client,
			ProcessThreadPoolExecutor processThreadPoolExecutor, ThreadPoolExecutor downloadThreadPoolExecutor) {
		this.request = request;
		this.storage = storage;
		this.context = context;
		this.client = client;
		this.processThreadPoolExecutor = processThreadPoolExecutor;
		this.downloadThreadPoolExecutor = downloadThreadPoolExecutor;
	}

	public void run() {
		CloseableHttpResponse response = null;
		try {
			response = client.execute(request, context);
			int statusCode = response.getStatusLine().getStatusCode();
			logger.info(" the request uri " + request.getURI() + " request statuscode :" + statusCode);
			while (statusCode == 429) {
				Thread.sleep(1000);
				response = client.execute(request, context);
				statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != 429) {
					break;
				}
			}
			if (statusCode == HttpStatus.SC_OK) {
				downloadPageCount.incrementAndGet();
				String content = EntityUtils.toString(response.getEntity());
				storage.push(content);
				processThreadPoolExecutor.execute(new ProcessorTask(storage, client, context, processThreadPoolExecutor,
						downloadThreadPoolExecutor));
			} else if (statusCode == 500 || statusCode == 502 || statusCode == 504) {
				Thread.sleep(1000);
				downloadThreadPoolExecutor.execute(new DownloadTask(request, storage, context, client,
						processThreadPoolExecutor, downloadThreadPoolExecutor));
				return;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			logger.info(" clientProtocolException ", e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(" IOException ");
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.info(" InterruptedException ", e);
		} finally {
			if (response != null && response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				request.abort();
				if (response.getEntity() != null) {
					try {
						request.releaseConnection();
						EntityUtils.consumeQuietly(response.getEntity());
					} catch (UnsupportedOperationException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
