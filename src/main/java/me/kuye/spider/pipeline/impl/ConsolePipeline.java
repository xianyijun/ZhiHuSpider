package me.kuye.spider.pipeline.impl;

import java.util.List;

import me.kuye.spider.entity.Entity;
import me.kuye.spider.pipeline.Pipeline;

public class ConsolePipeline implements Pipeline {

	@Override
	public void process(List<Entity> entityList) {
		for (Entity entity : entityList) {
			System.out.println(entity);
		}
	}

}
