package me.kuye.spider.dao.mongo;

import me.kuye.spider.manager.MongoManager;

public class MongoBaseDao<T> {
	protected static final MongoManager mongoManager = MongoManager.getInstance();
}
