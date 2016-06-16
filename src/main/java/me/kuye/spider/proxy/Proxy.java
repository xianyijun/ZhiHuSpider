package me.kuye.spider.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Proxy {
	public static void main(String[] args) throws IOException {
		System.getProperties().setProperty("proxySet", "true");
		System.getProperties().setProperty("http.proxyHost", "175.152.210.39");
		System.getProperties().setProperty("http.proxyPort", "8090");
		BufferedReader in = null;
		try {
			String result = "";
			URLConnection connection = new URL("http://www.baidu.com/").openConnection();
			connection.setConnectTimeout(6000); // 6s
			connection.setReadTimeout(6000);
			connection.setUseCaches(false);
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			Document doc = Jsoup.parse(result);
			System.out.println(doc);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}
}
