package me.kuye.spider.pipeline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xianyijun
 *
 */
public class Storage {
	private Logger logger = LoggerFactory.getLogger(Storage.class);
	private BlockingQueue<String> resultQueue;

	public Storage() {
		resultQueue = new LinkedBlockingQueue<String>();
	}

	public void push(String item) {
		try {
			resultQueue.put(item);
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.info(" throws InterruptedException", e);
		}
	}

	public String pop() {
		String item = null;
		try {
			item = resultQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.info(" throws InterruptedException", e);
		}
		return item;
	}

	public BlockingQueue<String> getResultQueue() {
		return resultQueue;
	}
}
