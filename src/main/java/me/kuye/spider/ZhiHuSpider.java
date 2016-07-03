package me.kuye.spider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.Chronology;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.Scheduler.QueueScheduler;
import me.kuye.spider.Scheduler.Scheduler;
import me.kuye.spider.downloader.HttpDownloader;
import me.kuye.spider.entity.Page;
import me.kuye.spider.entity.Request;
import me.kuye.spider.executor.ThreadPool;
import me.kuye.spider.pipeline.ConsolePipeline;
import me.kuye.spider.pipeline.Pipeline;
import me.kuye.spider.processor.Processor;

/**
 * @author xianyijun
 *
 */
/**
 * @author xianyijun
 *
 */
public class ZhiHuSpider implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(ZhiHuSpider.class);

	protected HttpDownloader downloader;

	protected Scheduler scheduler = new QueueScheduler();

	protected List<Pipeline> pipelineList = new ArrayList<>();
	// TODO 将http request进行抽象
	protected Request startRequest;

	protected ThreadPool threadPool;

	protected ExecutorService executorService;

	private int threadNum = 1;
	private final AtomicLong pageCount = new AtomicLong(0);

	private LocalDateTime startTime;

	private ReentrantLock urlLock = new ReentrantLock();
	private Condition newUrlCondition = urlLock.newCondition();

	private int sleepTime = 3000;

	private int retryTime = 5000;

	private String domain;

	private Processor processor;

	private ZhiHuSpider(Processor processor) {
		this.processor = processor;
	}

	public static ZhiHuSpider getInstance(Processor processor) {
		return new ZhiHuSpider(processor);
	}

	@Override
	public void run() {
		initSpider();
		while (!Thread.currentThread().isInterrupted()) {
			Request request = scheduler.poll();
			if (request == null) {
				if (threadPool.getThreadAlive() == 0) {
					break;
				}
				// 等待新的请求连接
				waitNewUrl();
			} else {
				final Request finalRequest = request;
				threadPool.execute(new Runnable() {
					@Override
					public void run() {
						try {
							processRequest(finalRequest, domain);
						} finally {
							pageCount.incrementAndGet();
							signalNewUrl();
						}
					}
				});
			}
		}
		close();
	}

	private void close() {
		LocalDateTime endTime = LocalDateTime.now();
		threadPool.shutdown();
		logger.info(" 爬虫从 " + startTime + " 开始抓取 , 到 " + endTime + " 结束 .");
	}

	private void waitNewUrl() {
		urlLock.lock();
		try {
			if (threadPool.getThreadAlive() == 0) {
				return;
			}
			newUrlCondition.await(sleepTime, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			urlLock.unlock();
		}
	}

	private void signalNewUrl() {
		try {
			urlLock.lock();
			newUrlCondition.signalAll();
		} finally {
			urlLock.unlock();
		}
	}

	private void processRequest(Request request, String domain) {
		Page page = downloader.download(request, domain);
		if (page == null) {
			sleep(retryTime);
			return;
		}
		processor.process(page);
		extractAndAddRequest(page);
		for (Pipeline pipeline : pipelineList) {
			pipeline.process(page.getResult());
		}
		sleep(sleepTime);
	}

	private void extractAndAddRequest(Page page) {
		for (Request request : page.getTargetRequest()) {
			addRequest(request);
		}
	}

	private void addRequest(Request request) {
		scheduler.push(request);
	}

	private void sleep(int retryTime) {
		try {
			Thread.sleep(retryTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: initSpider @Description: 初始化默认组件 @param 参数 @return void
	 *         返回类型 @throws
	 */
	private void initSpider() {
		if (downloader == null) {
			this.downloader = new HttpDownloader();
		}
		this.downloader.setThreaNum(threadNum);
		if (pipelineList.isEmpty()) {
			pipelineList.add(new ConsolePipeline());
		}
		if (threadPool == null || threadPool.isShutdown()) {
			if (executorService != null && !executorService.isShutdown()) {
				this.threadPool = new ThreadPool(threadNum, executorService);
			} else {
				this.threadPool = new ThreadPool(threadNum);
			}
		}
		if (startRequest != null) {
			scheduler.push(startRequest);
		}
		this.startTime = LocalDateTime.now();
	}

	public HttpDownloader getDownloader() {
		return downloader;
	}

	public ZhiHuSpider setDownloader(HttpDownloader downloader) {
		this.downloader = downloader;
		return this;
	}

	public static Logger getLogger() {
		return logger;
	}

	public List<Pipeline> getPipelineList() {
		return pipelineList;
	}

	public ThreadPool getThreadPool() {
		return threadPool;
	}

	public AtomicLong getPageCount() {
		return pageCount;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public ReentrantLock getUrlLock() {
		return urlLock;
	}

	public Condition getNewUrlCondition() {
		return newUrlCondition;
	}

	public int getSleepTime() {
		return sleepTime;
	}

	public int getRetryTime() {
		return retryTime;
	}

	public Processor getProcessor() {
		return processor;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public ZhiHuSpider setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
		return this;
	}

	public ZhiHuSpider addPipeline(Pipeline pipeline) {
		this.pipelineList.add(pipeline);
		return this;
	}

	public Request getStartRequest() {
		return startRequest;
	}

	public ZhiHuSpider setStartRequest(Request startRequest) {
		this.startRequest = startRequest;
		return this;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public ZhiHuSpider setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
		return this;
	}

	public int getThreadNum() {
		return threadNum;
	}

	public ZhiHuSpider setThreadNum(int threadNum) {
		this.threadNum = threadNum;
		return this;
	}

	public String getDomain() {
		return domain;
	}

	public ZhiHuSpider setDomain(String domain) {
		this.domain = domain;
		return this;
	}

}
