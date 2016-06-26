package me.kuye.spider;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONObject;

import me.kuye.spider.downloader.HttpDownloader;
import me.kuye.spider.vo.AnswerResult;

/**
 * @author xianyijun
 *
 */
public class AnswerSpider {
	public static void main(String[] args) {
		// https://www.zhihu.com/node/QuestionAnswerListV2?method=next&params=%7B%22url_token%22%3A35720340%2C%22pagesize%22%3A10%2C%22offset%22%3A20%7D&_xsrf=2ed0ca3e32800c09bb7d35f42d23cb69
		HttpDownloader downloader = new HttpDownloader();
		CloseableHttpClient client = downloader.getHttpClient(null);
		String url = "https://www.zhihu.com/node/QuestionAnswerListV2";
		HttpPost request = null;
		CloseableHttpResponse response = null;
		List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
		try {
			request = new HttpPost(url);
			valuePairs.add(new BasicNameValuePair("method", "next"));
			JSONObject obj = new JSONObject();
			obj.put("url_token", "35720340");
			obj.put("pagesize", 10);
			obj.put("offset", 20);
			valuePairs.add(new BasicNameValuePair("params", obj.toJSONString()));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
			request.setEntity(entity);
			response = client.execute(request);
			String result = EntityUtils.toString(response.getEntity());
			AnswerResult answerResult = JSONObject.parseObject(result, AnswerResult.class);
			String[] msg = answerResult.getMsg();
			for (int i = 0; i < msg.length; i++) {
				Document doc = Jsoup.parse(msg[i]);
				System.out.println(doc);
				System.out.println("//==================================================");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
