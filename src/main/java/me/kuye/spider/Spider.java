package me.kuye.spider;

import me.kuye.spider.downloader.HttpDownloader;

public interface Spider {
	public void download(String startUrl, HttpDownloader downloader);
}
