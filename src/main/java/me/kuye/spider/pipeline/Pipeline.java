package me.kuye.spider.pipeline;

import java.util.List;

import me.kuye.spider.entity.Entity;

public interface Pipeline {
	public void process(List<Entity> entityList);
}
