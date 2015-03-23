package com.test.spider;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.test.spider.util.SpiderTask;
import com.test.spider.util.FechUtil;

public class SpiderMain {
	
	final static String jdMapUrl = mConstants.JD_MAP_URL;
	static  String htm_str;
	static String html;
	public static void main(String[] args) {
		final LinkedBlockingQueue<String> urlList = new LinkedBlockingQueue<String>();	//单类商品URL
		new Thread(new SpiderTask(jdMapUrl,urlList)
		{}){}.start();

	}
}
