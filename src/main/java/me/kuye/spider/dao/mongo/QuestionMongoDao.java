package me.kuye.spider.dao.mongo;

import me.kuye.spider.entity.Question;
import me.kuye.spider.util.MongoUtil;

public class QuestionMongoDao extends MongoBaseDao<Question> {
	private static final String QUESTION_COLLECTION_NAME = "question";

	public boolean save(Question question) {
		return mongoManager.insertOne(QUESTION_COLLECTION_NAME, MongoUtil.objectToDocument(Question.class, question));
	}
}
