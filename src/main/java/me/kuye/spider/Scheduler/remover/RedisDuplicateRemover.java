package me.kuye.spider.Scheduler.remover;

import me.kuye.spider.dao.redis.UrlItemDao;
import me.kuye.spider.entity.Request;

public class RedisDuplicateRemover implements DuplicateRemover {
	private UrlItemDao urlItemDao = new UrlItemDao();

	@Override
	public boolean isDuplicate(Request request) {
		return urlItemDao.exist(Integer.toString(request.hashCode()));
	}

}
