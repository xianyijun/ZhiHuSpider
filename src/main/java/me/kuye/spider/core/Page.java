package me.kuye.spider.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;

import me.kuye.spider.entity.Entity;

public class Page {
	private Document document;
	private String rawtext;
	private List<Entity> result = new ArrayList<>();
	private Request request;
	private List<Request> targetRequest = new LinkedList<>();

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

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public List<Request> getTargetRequest() {
		return targetRequest;
	}

	public void setTargetRequest(List<Request> targetRequest) {
		this.targetRequest = targetRequest;
	}

	public String getRawtext() {
		return rawtext;
	}

	public void setRawtext(String rawtext) {
		this.rawtext = rawtext;
	}
	
}
