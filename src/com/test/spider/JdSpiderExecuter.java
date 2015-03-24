package com.test.spider;

import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

import com.test.spider.util.BloomFilter;
import com.test.spider.util.JsoupUtil;
import com.test.spider.util.SqlUtil;

public class JdSpiderExecuter extends TimerTask {
	//京东专用爬去线程
	private String url;
	private static String table=mConstants.JD_TABLE;
	LinkedBlockingQueue<String> mQueue = new LinkedBlockingQueue<String>();
	
	public JdSpiderExecuter(String url){	
		this.url=url;
	}
	
	@Override
	public void run() {
		mQueue=JsoupUtil.praseQueue(url);
		SqlUtil mSqlUtil = new SqlUtil(mConstants.DB_NAME,mConstants.DB_USER_NAME,mConstants.DB_USER_PASS);
		if(!mValue.getDbState()){
		mSqlUtil.deleteAll(table);	//清空数据库
		}
		if (mValue.getDbState()){
		while(!mQueue.isEmpty()){
			String url=mQueue.poll();
			if (!BloomFilter.ifNotContainsSet(url)) {
				JsoupUtil.ExcuteItemQueue(url,mSqlUtil,table);
				}else{
					System.out.println("数据库未清空！");
				}
			}
		}
	}

}
