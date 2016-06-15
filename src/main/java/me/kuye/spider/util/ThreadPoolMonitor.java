package me.kuye.spider.util;

import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolMonitor implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ThreadPoolMonitor.class);
	private ThreadPoolExecutor executor;
	private volatile boolean stop = false;
	private String name;

	public ThreadPoolMonitor(ThreadPoolExecutor executor, String name) {
		this.executor = executor;
		this.name = name;
	}

	@Override
	public void run() {
		this.executor.getQueue().size();
		while (!stop) {
			logger.info(name + String.format(
					"[monitor] [%d/%d] Active: %d, Completed: %d, queueSize: %d, Task: %d, isShutdown: %s, isTerminated: %s",
					this.executor.getPoolSize(), this.executor.getCorePoolSize(), this.executor.getActiveCount(),
					this.executor.getCompletedTaskCount(), this.executor.getQueue().size(),
					this.executor.getTaskCount(), this.executor.isShutdown(), this.executor.isTerminated()));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error("InterruptedException", e);
			}
		}

	}

	public void shutdown() {
		this.stop = true;
	}
}
