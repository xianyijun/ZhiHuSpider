package me.kuye.spider.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.codec.Charsets;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kuye.spider.downloader.ZhiHuClientGenerator;
import me.kuye.spider.util.ConfigUtil;
import me.kuye.spider.util.Constant;

public class LoginCookiesHelper {
	private static Logger logger = LoggerFactory.getLogger(LoginCookiesHelper.class);

	public static Object antiSerializeCookies(String name) {
		InputStream fis = LoginCookiesHelper.class.getResourceAsStream(name);
		ObjectInputStream ois = null;
		Object object = null;
		try {
			ois = new ObjectInputStream(fis);
			object = ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (ois != null) {
					ois.close();
				}
			} catch (IOException e) {
				logger.error(" IOException", e);
			}
		}
		return object;
	}

	public static void serializeCookies(CookieStore cookieStore, String path) {
		ObjectOutputStream oos = null;
		FileOutputStream fos = null;
		try {
			File file = new File(path);
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(cookieStore);
			oos.flush();
		} catch (IOException e) {
			logger.info("IOException", e);
		} finally {
			try {
				if (oos != null) {
					oos.close();
					oos = null;
				}
				if (fos != null) {
					fos.close();
					fos = null;
				}
			} catch (IOException e) {
				logger.error("IOException", e);
			}
		}
	}

	public static void main(String[] args) {
		ZhiHuClientGenerator generator = new ZhiHuClientGenerator();
		CloseableHttpClient client = generator.getClient(null);
		HttpClientContext context = new HttpClientContext();
		login(client, context);
	}

	private static void login(CloseableHttpClient client, HttpClientContext context) {
		HttpPost request = null;
		String valieCode = "";
		List<NameValuePair> formParamList = new ArrayList<>();
		Properties properties = ConfigUtil.getProperties(Constant.ZHIHU_DEFAULT_PROPERTIES);
		String username = properties.getProperty("username");
		if (username.contains("@")) {
			request = new HttpPost(Constant.ZHIHU_EMAIL_LOGIN_URL);
			formParamList.add(new BasicNameValuePair("email", username));
		} else {
			request = new HttpPost(Constant.ZHIHU_PHONE_LOGIN_URL);
			formParamList.add(new BasicNameValuePair("phome_num", username));
		}
		valieCode = getVaileCode(client, context);
		formParamList.add(new BasicNameValuePair("captcha", valieCode));
		formParamList.add(new BasicNameValuePair("password", properties.getProperty("password")));
		formParamList.add(new BasicNameValuePair("remember_me", "true"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParamList, Charsets.UTF_8);
		request.setEntity(entity);
		try {
			client.execute(request, context);
			serializeCookies(context.getCookieStore(), Constant.COOIKES_SERIALIZE_PATH);
		} catch (IOException e) {
			logger.error("IOExceptione", e);
		}
	}

	private static String getVaileCode(CloseableHttpClient client, HttpClientContext context) {
		DownloadHelper.downloadFile(Constant.VALIE_CODE_IMAGE, client, context, "image/", "valie.gif", true);
		Scanner sc = new Scanner(System.in);
		String valieCode = sc.nextLine();
		sc.close();
		return valieCode;
	}

}
