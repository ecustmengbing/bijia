package com.test.spider.util;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;



public class ListFilter {
	public final static int LIST =0;
	public final static int ITEM =1;
	private static Pattern pattern;
	
	public static Map<String , String> UrlFilter(Map<String, String>  map,int type) {
		
		if (type==LIST) {
			 pattern=Pattern.compile("^(http://list|https://list|list){1}[\\w\\.\\-/:]+");
		}
		else if (type==ITEM) {
			 pattern=Pattern.compile("^(http://item|https://item|item){1}[\\w\\.\\-/:]+");
		}
		

		Iterator<Entry<String, String>> list=map.entrySet().iterator();
		while(list.hasNext()){
			Entry<String, String> oneEntry = list.next();
			String value =oneEntry.getValue();
		    boolean flag= pattern.matcher(value).matches();
		    //System.out.println(flag);
		       if (flag==false) {
				 list.remove();
			}
		}
		return map;
	}
	
	public static boolean UrlJudge(String url,int type){
		switch(type){
		case LIST:
			pattern = Pattern.compile("^(http://list|https://list|list){1}[\\w\\.\\-/:]+");
			break;
		case ITEM:
			pattern = Pattern.compile("^(http://item|https://item|item){1}[\\w\\.\\-/:]+");
			break;
		default :
			pattern = null;
			break;
		}
		return pattern.matcher(url).matches();
	}

}
