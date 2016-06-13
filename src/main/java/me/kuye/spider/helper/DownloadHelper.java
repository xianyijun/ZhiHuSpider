package me.kuye.spider.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

public class DownloadHelper {
	public static void downloadFile(String url, CloseableHttpClient client, HttpClientContext context, String savePath,
			String fileName, boolean isReplacable) {
		HttpGet method = new HttpGet(url);
		try {
			CloseableHttpResponse response = client.execute(method, context);
			File file = new File(savePath);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			file = new File(savePath + fileName);
			if (!file.exists() || isReplacable) {
				OutputStream os = new FileOutputStream(file);
				InputStream is = response.getEntity().getContent();
				byte[] buffer = new byte[(int) response.getEntity().getContentLength()];
				while (true) {
					int len = is.read(buffer);
					if (len == -1) {
						break;
					}
					byte[] temp = new byte[len];
					System.arraycopy(buffer, 0, temp, 0, len);
					os.write(temp);
				}
				os.close();
				is.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
	}
}
