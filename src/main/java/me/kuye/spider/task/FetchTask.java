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

import me.kuye.spider.executor.StatisticsThreadPoolExecutor;
import me.kuye.spider.pipeline.Storage;

public class FetchTask implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(FetchTask.class);
	private static AtomicLong downloadPageCount = new AtomicLong();
	private HttpGet request = null;
	private Storage storage;
	private HttpClientContext context;
	private CloseableHttpClient client;
	private StatisticsThreadPoolExecutor statisticsThreadPoolExecutor;
	private ThreadPoolExecutor downloadThreadPoolExecutor;

	public FetchTask() {
	}

	public FetchTask(HttpGet request, Storage storage, HttpClientContext context, CloseableHttpClient client,
			StatisticsThreadPoolExecutor statisticsThreadPoolExecutor, ThreadPoolExecutor downloadThreadPoolExecutor) {
		this.request = request;
		this.storage = storage;
		this.context = context;
		this.client = client;
		this.statisticsThreadPoolExecutor = statisticsThreadPoolExecutor;
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
				downloadThreadPoolExecutor.execute(new ProcessorTask(storage, client, context,
						statisticsThreadPoolExecutor, downloadThreadPoolExecutor));
			} else if (statusCode == 500 || statusCode == 502 || statusCode == 504) {
				Thread.sleep(1000);
				statisticsThreadPoolExecutor.execute(new FetchTask(request, storage, context, client,
						statisticsThreadPoolExecutor, downloadThreadPoolExecutor));
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
			if (response.getEntity() != null) {
				try {
					request.releaseConnection();
					response.getEntity().getContent().close();
				} catch (UnsupportedOperationException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (response.getStatusLine().getStatusCode() != 200) {
				request.abort();
			}
		}
	}

}
