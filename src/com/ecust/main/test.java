/**
 * 
 */
package com.ecust.main;

import com.ecust.database.DBOperation;
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
		String sql= "SELECT * FROM  `jditems` LIMIT 0 , 30";
		try{
			System.out.println(DBOperation.execSQL(sql));
		}catch(SQLException e){
			System.out.println(e);
		}
	}
}
