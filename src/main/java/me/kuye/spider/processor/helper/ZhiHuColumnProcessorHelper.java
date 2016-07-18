package me.kuye.spider.processor.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.entity.Column;
import me.kuye.spider.vo.column.ColumnDetail;
import me.kuye.spider.vo.column.PostTopic;

public class ZhiHuColumnProcessorHelper {
	private static final Logger logger = LoggerFactory.getLogger(ZhiHuColumnProcessorHelper.class);

	public static Column convertColumnDetailToColumn(ColumnDetail columnDetail) {
		Column column = new Column();
		column.setCreatorName(columnDetail.getCreator() != null ? columnDetail.getCreator().getName() : "");
		column.setDescription(columnDetail.getDescription());
		column.setFollowersCount(columnDetail.getFollowersCount());
		column.setHashId(columnDetail.getCreator() != null ? columnDetail.getCreator().getHash() : null);
		column.setIntro(columnDetail.getIntro());
		column.setPostsCount(columnDetail.getPostsCount());
		column.setReason(columnDetail.getReason());
		column.setUrl(columnDetail.getUrl());
		column.setName(columnDetail.getName());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < columnDetail.getTopics().length; i++) {
			sb.append(columnDetail.getTopics()[i].getName());
			if (i < columnDetail.getTopics().length - 1) {
				sb.append(":");
			}
		}
		logger.debug(sb.toString());
		column.setTopics(sb.toString());
		PostTopic[] postTopics = columnDetail.getPostTopics();
		sb = new StringBuilder();
		for (int i = 0; i < postTopics.length; i++) {
			sb.append(postTopics[i].getName()).append(":").append(postTopics[i].getPostsCount());
			if (i < postTopics.length - 1) {
				sb.append(",");
			}
		}
		logger.debug(sb.toString());
		column.setPostTopics(sb.toString());
		return column;
	}

}
