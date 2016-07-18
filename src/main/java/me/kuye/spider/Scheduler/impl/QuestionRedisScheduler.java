package me.kuye.spider.Scheduler.impl;

import me.kuye.spider.Scheduler.RedisScheduler;
import me.kuye.spider.core.Request;

public class QuestionRedisScheduler extends RedisScheduler {
	private static final String QUESTION_DETAIL_QUEUE_KEY = "question_detail_url_queue";

	@Override
	protected String getNextUrl() {
		return redisManager.lpop(QUESTION_DETAIL_QUEUE_KEY);
	}

	@Override
	protected String getQueueKey(Request request) {
		return QUESTION_DETAIL_QUEUE_KEY;
	}

}
