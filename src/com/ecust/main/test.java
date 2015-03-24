/**
 * 
 */
package com.ecust.main;


import java.sql.SQLException;
import java.util.ArrayList;
import com.ecust.orm.SpiderModle;


/**
 *************************
 * 
 ************************* 
 * @author kootain
 * @creation 2015年3月25日
 *
 */
public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<String> a = new ArrayList<String>() ;
		a.add("a1");
		a.add("a2");
		a.add("a3");
		SpiderModle item = new SpiderModle("1","2","3","4",a,"5","6","7"); 
		try{
			SpiderModle.insert(item);
			System.out.println("ok");
		}catch(SQLException e){
			System.out.println(e);
		}
	}
}
