package com.test.spider;


import com.test.spider.util.SpiderTask;
import com.test.spider.util.SqlUtil;

public class SpiderMain {
	
	final static String jdMapUrl = mConstants.JD_MAP_URL;
	static  String htm_str;
	static String html;
	public static void main(String[] args) {
		SqlUtil mSqlUtil = new SqlUtil(mConstants.DB_NAME,mConstants.DB_USER_NAME,mConstants.DB_USER_PASS);
		mValue.setmSqlUtil(mSqlUtil);
		
		new Thread(new SpiderTask(jdMapUrl)
		{}){}.start();

	}
}
