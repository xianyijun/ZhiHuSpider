package me.kuye.spider.downloader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.core.Page;
import me.kuye.spider.core.Request;
import me.kuye.spider.util.HttpConstant;

public class HttpDownloader {
	private static Logger logger = LoggerFactory.getLogger(HttpDownloader.class);

	private final Map<String, CloseableHttpClient> clientMap = new HashMap<>();
	private HttpClientGenerator generator = new HttpClientGenerator();

	public CloseableHttpClient getHttpClient(String domain) {
		if (domain == HttpConstant.NO_COOKIE) {
			return generator.getClient(HttpConstant.NO_COOKIE);
		}
		CloseableHttpClient client = clientMap.get(domain);
		if (client == null) {
			synchronized (this) {
				client = clientMap.get(domain);
				if (client == null) {
					client = generator.getClient(domain);
					clientMap.put(domain, client);
				}
			}
		}
		return client;
	}

	public void setThreaNum(int threadNum) {
		this.generator.setPoolSize(threadNum);
	}

	public Page download(Request request, String domain) {
		HttpUriRequest executeRequest = buildExecuteRequest(request).setUri(request.getUrl()).build();
		CloseableHttpResponse response = null;
		if (request.getExtra().containsKey(HttpConstant.NO_COOKIE)) {
			domain = HttpConstant.NO_COOKIE;
		}
		CloseableHttpClient client = getHttpClient(domain);
		try {
			try {
				response = client.execute(executeRequest);
			} catch (IOException e) {
				logger.info("IOException", e);
				return null;
			}

			int statusCode = response.getStatusLine().getStatusCode();
			while (statusCode == 429) {
				Thread.sleep(1000);
				response = client.execute(executeRequest);
				statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != 429) {
					break;
				}
			}
			if (statusCode == HttpStatus.SC_OK) {
				String content = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
				Page page = handlePage(content, request);
				return page;
			} else if (statusCode == 500 || statusCode == 502 || statusCode == 504) {
				Thread.sleep(1000);
				return null;
			}
		} catch (ClientProtocolException e) {
			logger.info(" clientProtocolException ", e);
		} catch (IOException e) {
			logger.info(" IOException ", e);
		} catch (InterruptedException e) {
			logger.info(" InterruptedException ", e);
		} finally {
			if (response != null && response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				executeRequest.abort();
				if (response.getEntity() != null) {
					try {
						response.getEntity().writeTo(System.out);
						EntityUtils.consumeQuietly(response.getEntity());
						response.close();
					} catch (UnsupportedOperationException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	private RequestBuilder buildExecuteRequest(Request request) {
		String method = request.getMethod();
		if (method == null || method.equalsIgnoreCase(HttpConstant.GET)) {
			return RequestBuilder.get();
		} else if (method.equalsIgnoreCase(HttpConstant.POST)) {
			RequestBuilder requestBuilder = RequestBuilder.post();
			@SuppressWarnings("unchecked")
			List<NameValuePair> nameValuePairList = (List<NameValuePair>) request.getExtra(HttpConstant.NAMEVALUEPAIR);
			if (nameValuePairList != null && nameValuePairList.size() > 0) {
				for (NameValuePair nameValuePair : nameValuePairList) {
					requestBuilder.addParameter(nameValuePair);
				}
			}
			return requestBuilder;
		} else if (method.equalsIgnoreCase(HttpConstant.HEAD)) {
			return RequestBuilder.head();
		} else if (method.equalsIgnoreCase(HttpConstant.PUT)) {
			return RequestBuilder.put();
		} else if (method.equalsIgnoreCase(HttpConstant.DELETE)) {
			return RequestBuilder.delete();
		} else if (method.equalsIgnoreCase(HttpConstant.TRACE)) {
			return RequestBuilder.trace();
		}
		throw new IllegalArgumentException(" the http method vaild");
	}

	private Page handlePage(String content, Request request) {
		Page page = new Page();
		page.setRequest(request);
		page.setRawtext(content);
		page.setDocument(Jsoup.parse(content));
		return page;
	}
}
