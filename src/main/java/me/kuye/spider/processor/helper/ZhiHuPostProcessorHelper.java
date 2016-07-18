package me.kuye.spider.processor.helper;

import java.util.LinkedList;
import java.util.List;

import me.kuye.spider.entity.Post;
import me.kuye.spider.vo.post.PostDetail;

public class ZhiHuPostProcessorHelper {

	public static List<Post> convertPostDetailListToPostList(List<PostDetail> postDetailList) {
		List<Post> postList = new LinkedList<>();
		for (PostDetail postDetail : postDetailList) {
			Post post = convertPostDetailToPost(postDetail);
			postList.add(post);
		}
		return postList;
	}

	private static Post convertPostDetailToPost(PostDetail postDetail) {
		Post post = new Post();
		post.setAuthorName(postDetail.getAuthor() != null ? postDetail.getAuthor().getName() : "");
		post.setCommentsCount(postDetail.getCommentsCount());
		post.setCommetsUrl(postDetail.getLinks().getComments());
		post.setContent(postDetail.getContent());
		post.setHashId(postDetail.getAuthor() != null ? postDetail.getAuthor().getHash() : "");
		post.setPostUrl(postDetail.getUrl());
		post.setPublishedTime(postDetail.getPublishedTime());
		post.setTitle(postDetail.getTitle());
		post.setTitleImage(postDetail.getTitleImage());
		return post;
	}

}
