package com.test.spider.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FechUtil {
	 static String htm_str;
	private static HttpClient hc;

	 @SuppressWarnings("deprecation")
	public static String getUrl(String url){
		 try{
		      hc = new DefaultHttpClient();
		      HttpGet hg = new HttpGet(url);
		      HttpResponse response = hc.execute(hg);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					InputStream stream = response.getEntity().getContent();
					ByteArrayOutputStream outStream = new ByteArrayOutputStream();
					int i = -1;
					while ((i = stream.read()) != -1) {
						outStream.write(i);
					}
					htm_str = new String(outStream.toByteArray(), "gb2312");
				}
			  }catch (Exception e) {  
				  e.printStackTrace();
			     }
		return htm_str;
	 }
}
