package me.kuye.spider.Scheduler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueScheduler implements Scheduler {
	private static Logger logger = LoggerFactory.getLogger(QueueScheduler.class);
	private BlockingQueue<HttpRequestBase> queue = new LinkedBlockingQueue<>();

	@Override
	public void push(HttpRequestBase request) {
		try {
			queue.put(request);
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.info(" throws InterruptedException", e);
		}
	}

	@Override
	public HttpRequestBase poll() {
		HttpRequestBase request = null;
		try {
			request = queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.info(" throws InterruptedException", e);
		}
		return request;
	}

}
