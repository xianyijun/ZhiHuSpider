package me.kuye.spider.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpRequestBase;

public class Request implements Serializable {
	private static final long serialVersionUID = -2124102246753044544L;
	private HttpRequestBase request;
	private String method;
	private String url;
	private Map<String, String> extra = new HashMap<String, String>();

	public Request(String method, String url, HttpRequestBase request) {
		this.method = method;
		this.url = url;
		this.request = request;
	}

	public HttpRequestBase getRequest() {
		return request;
	}

	public void setRequest(HttpRequestBase request) {
		this.request = request;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getExtra() {
		return extra;
	}

	public void setExtra(Map<String, String> extra) {
		this.extra = extra;
	}

	public Request addExtra(String key, String value) {
		extra.put(key, value);
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extra == null) ? 0 : extra.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Request other = (Request) obj;
		if (extra == null) {
			if (other.extra != null)
				return false;
		} else if (!extra.equals(other.extra))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Request [request=" + request + ", method=" + method + ", url=" + url + ", extra=" + extra + "]";
	}

}
