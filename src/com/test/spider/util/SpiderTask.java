package com.test.spider.util;


import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.test.spider.JdSpiderExecuter;
import com.test.spider.api.Task;

public class SpiderTask implements Task {
	private String url;
	
	public SpiderTask(String url){
		this.url=url;
	}
	@Override
	public void run() {
		dailyTask();
	}
	
	private void dailyTask()
	{

		int hour=5;
		int min=0;
		int sec=0;

		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, sec);
		Date time = calendar.getTime();

		Timer timer = new Timer();
	    timer.schedule(new JdSpiderExecuter(url), time);
	}

}
