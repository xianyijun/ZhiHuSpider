package me.kuye.spider.Scheduler;

import me.kuye.spider.entity.Request;

public class UserRedisScheduler extends RedisScheduler {
	private static final String USER_FOLLOWEE_QUEUE_KEY = "zhihu_user_followee_url_queue";
	private static final String USER_INFO_QUEUE_KEY = "zhi_info_url_queue";

	@Override
	public Request poll() {
		String url = redisManager.lpop(USER_INFO_QUEUE_KEY);
		if (url == null) {
			url = redisManager.lpop(USER_FOLLOWEE_QUEUE_KEY);
		}
		return doPoll(url);
	}

	@Override
	protected String getQueueKey(Request request) {
		String url = request.getUrl();
		if (url.startsWith("https://www.zhihu.com/node/ProfileFolloweesListV2")) {
			return USER_FOLLOWEE_QUEUE_KEY;
		} else {
			return USER_INFO_QUEUE_KEY;
		}
	}
}
