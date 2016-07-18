package me.kuye.spider.Scheduler.impl;

import me.kuye.spider.Scheduler.RedisScheduler;
import me.kuye.spider.core.Request;

//TODO
public class AnswerRedisScheduler extends RedisScheduler {
	private static final String ANSWER_INFO_QUEUE_KEY = "zhihu_answer_url_queue";

	@Override
	protected String getNextUrl() {
		return redisManager.lpop(ANSWER_INFO_QUEUE_KEY);
	}

	@Override
	protected String getQueueKey(Request request) {
		return ANSWER_INFO_QUEUE_KEY;
	}
}
