package com.test.spider.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.test.spider.api.Task;

public class SpiderTask implements Task {
	private String url;
	private static String html;
	LinkedHashMap<String,String> mMap = new LinkedHashMap<String,String>();
	LinkedBlockingQueue<String> mQueue = new LinkedBlockingQueue<String>();
	
	public SpiderTask(String url,LinkedBlockingQueue<String> urlList){
		this.url=url;
	}
	@Override
	public void run() {
		//以下注释html本地保存方法
//		html = FechUtil.getUrl(url);
//		FileUtil.toTxt(html);
//		mMap=JsoupUtil.praseMap(url);
//		mMap = (LinkedHashMap<String, String>) ListFilter.UrlFilter(mMap, ListFilter.LIST);
		mQueue=JsoupUtil.praseQueue(url);
		System.out.println(mQueue);

	}

}
