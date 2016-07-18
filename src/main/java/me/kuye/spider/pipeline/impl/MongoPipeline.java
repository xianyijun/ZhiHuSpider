package me.kuye.spider.pipeline.impl;

import java.util.List;

import me.kuye.spider.dao.mongo.QuestionMongoDao;
import me.kuye.spider.entity.Entity;
import me.kuye.spider.entity.Question;
import me.kuye.spider.manager.MongoManager;
import me.kuye.spider.pipeline.Pipeline;
import me.kuye.spider.util.MongoUtil;

public class MongoPipeline implements Pipeline {
	private MongoManager manager = new MongoManager();

	@Override
	public void process(List<Entity> entityList) {
		for (Entity entity : entityList) {
			manager.insertOne(entity.getKey(), MongoUtil.objectToDocument(entity.getClass(), entity));
		}
	}

}
