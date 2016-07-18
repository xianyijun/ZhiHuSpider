package me.kuye.spider.pipeline.impl;

import java.util.List;

import me.kuye.spider.dao.mysql.AnswerDao;
import me.kuye.spider.entity.Answer;
import me.kuye.spider.entity.Entity;
import me.kuye.spider.pipeline.Pipeline;

public class AnswerPipeline implements Pipeline {
	private AnswerDao answerDao = new AnswerDao();

	@Override
	public void process(List<Entity> entityList) {
		for (Entity entity : entityList) {
			answerDao.insert((Answer) entity);
		}
	}

}
