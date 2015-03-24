package com.test.spider;

import java.util.concurrent.LinkedBlockingQueue;

import com.test.spider.util.SpiderTask;

public class SpiderMain {
	
	final static String jdMapUrl = mConstants.JD_MAP_URL;
	static  String htm_str;
	static String html;
	public static void main(String[] args) {
		new Thread(new SpiderTask(jdMapUrl)
		{}){}.start();

	}
}
