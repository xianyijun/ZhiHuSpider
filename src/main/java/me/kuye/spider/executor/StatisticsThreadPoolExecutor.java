package me.kuye.spider.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.pipeline.Storage;

public class StatisticsThreadPoolExecutor extends ThreadPoolExecutor {
	private static Logger logger = LoggerFactory.getLogger(StatisticsThreadPoolExecutor.class);
	private long startTime = 0;

	public StatisticsThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
		startTime = System.currentTimeMillis();
	}

	@Override
	protected void terminated() {
		super.terminated();
		long endTime = System.currentTimeMillis();
		logger.info("耗费时间 : "+( endTime - startTime)+"ms");
	}
}
