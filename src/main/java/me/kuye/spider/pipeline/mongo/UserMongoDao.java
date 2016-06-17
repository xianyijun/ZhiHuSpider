package me.kuye.spider.pipeline.mongo;

import com.mongodb.client.model.Filters;

import me.kuye.spider.entity.User;
import me.kuye.spider.util.MongoUtil;

public class UserMongoDao extends MongoBaseDao<User> {
	private static final String USER_COLLECTION_NAME = "user";

	public boolean save(User user) {
		return mongoManager.insertOne(USER_COLLECTION_NAME, MongoUtil.objectToDocument(User.class, user));
	}

	public boolean exist(String hashId) {
		return mongoManager.finyOne(USER_COLLECTION_NAME, Filters.eq("hashId", hashId)) != null;
	}
}
