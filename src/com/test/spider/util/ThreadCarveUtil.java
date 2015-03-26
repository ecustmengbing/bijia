package com.test.spider.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.test.spider.mConstants;

public class ThreadCarveUtil {
	
	public static ArrayList<Queue<String>> Carve(ArrayList<String> queue){
		
		int threadSize = mConstants.THREAD_SIZE;
		ArrayList<Queue<String>> queues  = new ArrayList<Queue<String>>();
		int threadNum = getTreadNum(queue.size(),threadSize);
		if(threadNum==1){
			Queue<String> tempQueue = new LinkedList<String> (queue);
			queues.add(tempQueue);
			return queues;
		}
		for(int i=0;i<threadNum;i++){
			if(queue.size()>=(i+1)*threadSize-1){
			Queue<String> tempQueue = new LinkedList<String> ( queue.subList(i*threadSize, (i+1)*threadSize-1));
			queues.add(tempQueue);
			}else{
				Queue<String> tempQueue = new LinkedList<String> ( queue.subList(i*threadSize,queue.size()-1));
				queues.add(tempQueue);
				break;
			}
		}
		queue = null;
		return queues;
	}
	
	public static int getTreadNum(int queueSize,int threadSize){
		int intSize = queueSize/threadSize;
		if(intSize==0){
			return 1;
		}
		float floatSize = (float)queueSize/threadSize;
		if(floatSize==intSize){
			return intSize;
		}
		return intSize+1;
		
	}
}
