package me.kuye.spider.pipeline;

import java.util.List;

import me.kuye.spider.entity.Entity;

public class ConsolePipeline implements Pipeline {

	@Override
	public void process(List<Entity> entityList) {
		for (Entity entity : entityList) {
			System.out.println(entity);
		}
	}

}
