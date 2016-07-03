package me.kuye.spider.Scheduler.remover;

import me.kuye.spider.dao.redis.UrlItemDao;
import me.kuye.spider.entity.Request;
import me.kuye.spider.util.MD5Util;

public class RedisDuplicateRemover implements DuplicateRemover {
	private UrlItemDao urlItemDao = new UrlItemDao();

	@Override
	public boolean isDuplicate(Request request) {
		return !urlItemDao.add(MD5Util.MD5Encode(request.toString()));
	}

}
