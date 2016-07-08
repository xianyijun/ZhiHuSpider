package me.kuye.spider.pipeline;

import java.util.List;

import me.kuye.spider.dao.mysql.UserDao;
import me.kuye.spider.entity.Entity;
import me.kuye.spider.entity.User;

public class UserPipeline implements Pipeline {
	private UserDao userDao = new UserDao();

	@Override
	public void process(List<Entity> entityList) {
		for (Entity entity : entityList) {
			userDao.insert((User) entity);
		}
	}

}
