package me.kuye.spider.entity;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.methods.HttpRequestBase;
import org.jsoup.nodes.Document;

public class Page {
	private Document document;
	private List<Entity> result;
	private HttpRequestBase request;
	private List<HttpRequestBase> targetRequest = new LinkedList<>();

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public List<Entity> getResult() {
		return result;
	}

	public void setResult(List<Entity> result) {
		this.result = result;
	}

	public HttpRequestBase getRequest() {
		return request;
	}

	public void setRequest(HttpRequestBase request) {
		this.request = request;
	}

	public List<HttpRequestBase> getTargetRequest() {
		return targetRequest;
	}

	public void setTargetRequest(List<HttpRequestBase> targetRequest) {
		this.targetRequest = targetRequest;
	}

}
