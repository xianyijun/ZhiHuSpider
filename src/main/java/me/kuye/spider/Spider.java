package me.kuye.spider;

import me.kuye.spider.downloader.HttpDownloader;

/**
 * @author xianyijun
 *
 */
public interface Spider {
	public void download(String startUrl, HttpDownloader downloader);
}
