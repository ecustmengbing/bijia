/**
 * 
 */
package com.ecust.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import org.apache.log4j.Logger;

import com.ecust.orm.SpiderModle;
//import com.ecust.util.MyLogger;

/**
 *************************
 * 数据库操作类
 ************************* 
 * @author kootain
 * @creation 2015年3月25日
 *
 */
public class DBOperation 
{ 
	/**
	 * 类方法，执行有结果集返回的sql操作
	 * @param sql SQL语句
	 * @return 结果集
	 * @throws SQLException
	 */
	 public static ResultSet execSQL(String sql) throws SQLException 
	 {
		 DBConnection DBC=DBConnection.getInstance();
		 Connection conn= DBC.getConnection();
		 Statement stmt=conn.createStatement();
		 ResultSet rs=stmt.executeQuery(sql);
		 DBC.close(conn);//归还连接池
		 return rs;
	 } 
	 /**
	  * 执行无结果集返回的SQL语句
	  * @param sql SQL语句
	  * @return 执行成功返回true，否则返回false
	  * @throws SQLException
	  */
	 public static boolean exec(String sql) throws SQLException 
	 {
		 DBConnection DBC=DBConnection.getInstance();
		 Connection conn= DBC.getConnection();
		 PreparedStatement ps= (PreparedStatement) conn.prepareStatement(sql);
		 if(!ps.execute())
		 {
			 DBC.close(conn);//归还连接池
			 //System.out.println("write database success!");
//			 Logger logger=MyLogger.getLogger(DBOperation.class.getName());
//			 logger.info("sql execute success ");
			 return true;
		 }
		 else
		 { 
			 DBC.close(conn);//归还连接池
//			 Logger logger=MyLogger.getLogger(DBOperation.class.getName());
//			 logger.error("execute faild: "+sql);
		     return false; 
		 }
	 }
	 /**
	  * 将数据模型对象写到数据库
	  * @param dm 数据模型对象
	  * @return 执行成功返回true，否则返回false
	  * @throws SQLException
	  */
	 public static boolean insert(SpiderModle dm) throws SQLException
	 {
		return DBOperation.exec(generateSQL(dm));
	 }
	 /**
	  * 生成SQl语句
	  * @param dm
	  * @return  sql SQL语句
 	  */
	 private static String generateSQL(SpiderModle dm)
	 {
		 /*
		  * 数据库字段：
		  * Iname,Iid,Ihost,Iprice,Ifirst_cat,Isecond_cat,Ithird_cat,Iurl,Iimg_url,Idescription 
		  * 
		  */
		String sql = "INSERT INTO "+dm.getTableName()+"(Iname,Iid,Ihost,Iprice,Ifirst_cat,Isecond_cat,Ithird_cat,Iurl,Iimg_url,Idescription)"
			  		+" VALUES('"
				    +dm.getName()+"','"
			  		+dm.getId()+"','"
				    +dm.getHost()+"','"
			  		+dm.getPrice()+"','"
				    +dm.getCatFirst()+"','"
			  		+dm.getCatSecond()+"','"
				    +dm.getCatThird()+"','"
			  		+dm.getUrl()+"','"
				    +dm.getImageUrl()+"','"
			  		+dm.getDescription()+"')";
		//System.out.print(sql);
		 return sql;		 
	 }
	 
}

