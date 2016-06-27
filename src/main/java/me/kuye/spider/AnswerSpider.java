package me.kuye.spider;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import me.kuye.spider.downloader.HttpDownloader;
import me.kuye.spider.util.Constant;
import me.kuye.spider.vo.AnswerResult;

/**
 * @author xianyijun
 *
 */
public class AnswerSpider {
	private static final Logger logger = LoggerFactory.getLogger(AnswerSpider.class);

	public static void main(String[] args) throws ClientProtocolException, IOException {
		// https://www.zhihu.com/node/QuestionAnswerListV2?method=next&params=%7B%22url_token%22%3A35720340%2C%22pagesize%22%3A10%2C%22offset%22%3A20%7D&_xsrf=2ed0ca3e32800c09bb7d35f42d23cb69
		HttpDownloader downloader = new HttpDownloader();
		CloseableHttpClient client = downloader.getHttpClient(null);

		String questionUrl = "https://www.zhihu.com/question/39152967";
		HttpGet questionRequest = new HttpGet(questionUrl);

		HttpPost answerRequest = new HttpPost(Constant.ZHIHU_ANSWER_URL);

		CloseableHttpResponse response = null;

		response = client.execute(questionRequest);

		Document doc = Jsoup.parse(EntityUtils.toString(response.getEntity()));
		
		int answerNum = Integer.parseInt(doc.select("#zh-question-answer-num").attr("data-num"));
		String urlToken = doc.select("#zh-single-question-page").attr("data-urltoken");
		
		logger.info(" answerNum :" + answerNum + " urlToken: " + urlToken);
		
		List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
		valuePairs.add(new BasicNameValuePair("method", "next"));
		JSONObject obj = new JSONObject();
		obj.put("url_token", urlToken);
		// 并没有什么用，服务器端固定为10
		obj.put("pagesize", 10);
		try {
			for (int i = 0; i < answerNum / 10 + 1; i++) {
				obj.put("offset", 10 * i);
				valuePairs.add(new BasicNameValuePair("params", obj.toJSONString()));
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
				answerRequest.setEntity(entity);
				response = client.execute(answerRequest);
				String result = EntityUtils.toString(response.getEntity());
				logger.info(result);
				AnswerResult answerResult = JSONObject.parseObject(result, AnswerResult.class);
				String[] msg = answerResult.getMsg();
				for (int j = 0; j < msg.length; j++) {
					Document answerDoc = Jsoup.parse(msg[j]);
					System.out.println(answerDoc.select("div.zm-item-answer link").attr("href"));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
