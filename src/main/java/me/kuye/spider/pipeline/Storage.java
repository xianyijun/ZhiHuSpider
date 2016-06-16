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
	private ResultItem resultItem;

	public Storage() {
		resultQueue = new LinkedBlockingQueue<String>();
		resultItem = new ResultItem();
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

	public ResultItem getResultItem() {
		return resultItem;
	}

	public void setResultItem(ResultItem resultItem) {
		this.resultItem = resultItem;
	}

	public Logger getLogger() {
		return logger;
	}

	public BlockingQueue<String> getResultQueue() {
		return resultQueue;
	}
}
