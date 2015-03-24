package com.test.spider;

import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

import com.test.spider.util.BloomFilter;
import com.test.spider.util.JdFetcher;
import com.test.spider.util.JsoupUtil;

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
		mQueue=JsoupUtil.praseQueue(url);			//取得list队列
		if(!mValue.getDbState()&&mValue.getmSqlUtil()!=null){
			mValue.getmSqlUtil().deleteAll(table);	//清空数据库
		}
		JdFetcher.fetchQueue(mQueue);
	}

}
