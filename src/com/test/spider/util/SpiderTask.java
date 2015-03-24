package com.test.spider.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.test.spider.api.Task;
import com.test.spider.entity.Item;

public class SpiderTask implements Task {
	private String url;
	private static String html;
	private static String table="JDitems";
	LinkedHashMap<String,String> mMap = new LinkedHashMap<String,String>();
	LinkedBlockingQueue<String> mQueue = new LinkedBlockingQueue<String>();
	
	public SpiderTask(String url,LinkedBlockingQueue<String> urlList){
		this.url=url;
	}
	@Override
	public void run() {
		//以下注释html本地保存方法
		//html = FechUtil.getUrl(url);
		//FileUtil.toTxt(html);
		mQueue=JsoupUtil.praseQueue(url);
//		System.out.println("打印网站列表："+mQueue);
//		Item test = FetchItemUtil.getJDItemInfo("http://item.jd.com/1378541.html");
//		System.out.println("打印测试信息："+test.toString());
		sqlUtil msqlUtil = new sqlUtil("spiderDB","root","root");
//		msqlUtil.addItem(test, "JDitems");
//		msqlUtil.getAll("JDitems");
//		msqlUtil.deleteAll("JDitems");
//		msqlUtil.getAll("JDitems");
		while(!mQueue.isEmpty()){
			String url=mQueue.poll();
			if (!BloomFilter.ifNotContainsSet(url)) {
				JsoupUtil.ExcuteItemQueue(url,msqlUtil,table);
			}
			
		}

	}

}
