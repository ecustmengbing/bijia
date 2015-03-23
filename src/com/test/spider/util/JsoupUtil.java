package com.test.spider.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupUtil {
	public static LinkedHashMap<String,String> praseMap(String url){ 
		LinkedHashMap<String,String> mMap = new LinkedHashMap<String,String>();
		try {
            Document doc = Jsoup.connect(url).get(); 
            Elements lists = doc.getElementsByClass("mc");
            Elements links = lists.select("a[href]"); 
            for (Element link : links) { 
            	mMap.put(link.text(), link.attr("abs:href"));
            }  
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mMap;
	}
	public static LinkedBlockingQueue<String> praseQueue(String url){
		LinkedBlockingQueue<String> mQueue = new LinkedBlockingQueue<String>();
		try {
            Document doc = Jsoup.connect(url).get(); 
            Elements lists = doc.getElementsByClass("mc");
            Elements links = lists.select("a[href]"); 
            for (Element link : links) { 
            	if(ListFilter.UrlJudge(link.attr("abs:href"), ListFilter.LIST)){
            		mQueue.add(link.attr("abs:href"));
            	}
            }  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mQueue; 
	}
}
