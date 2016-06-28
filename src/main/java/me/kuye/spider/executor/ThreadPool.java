package me.kuye.spider.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPool {
	private int threadNum;
	private AtomicInteger threadAlive = new AtomicInteger();
	private ReentrantLock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private ExecutorService executorService;

	public ThreadPool(int threadNum) {
		this.threadNum = threadNum;
		this.executorService = Executors.newFixedThreadPool(threadNum);
	}

	public ThreadPool(int threadNum, ExecutorService executorService) {
		this.threadNum = threadNum;
		this.executorService = executorService;
	}

	public void execute(final Runnable runnable) {
		if (threadAlive.get() >= threadNum) {
			try {
				lock.lock();
				while (threadAlive.get() >= threadNum) {
					try {
						condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} finally {
				lock.unlock();
			}
		}
		threadAlive.incrementAndGet();
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					runnable.run();
				} finally {
					try {
						lock.lock();
						threadAlive.decrementAndGet();
						condition.signal();
					} finally {
						lock.unlock();
					}
				}
			}
		});
	}

	public boolean isShutdown() {
		return executorService.isShutdown();
	}

	public void shutdown() {
		executorService.shutdown();
	}

	public int getThreadAlive() {
		return threadAlive.get();
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

}
