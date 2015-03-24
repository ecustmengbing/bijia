package com.ecust.database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 *************************
 * 数据库连接类
 ************************* 
 * @author kootain
 * @creation 2015年3月25日
 *
 */

public  class DBConnection 
{
	private static DBConnection dbCon=null;
	private ConnectionPool pool;
	/**
	 * 构造函数
	 * @throws SQLException
	 */
	public  DBConnection() throws SQLException
	{
		pool=ConnectionPool.getInstance();
	}
	/**
	 * 获取静态实例
	 * @return 获取静态实例
	 * @throws SQLException
	 */
	public static DBConnection  getInstance() throws SQLException
	{
		if(dbCon==null)
		{
			dbCon=new DBConnection();
		}
		return dbCon;
	}
	/**
	 * 获取数据库连接Connection
	 * @return 数据库连接Connection
	 * @throws SQLException 
	 */
	public  Connection getConnection() throws SQLException
	{	
		return pool.getConnection();
	}
	/**
	 * 归还数据库连接给连接池
	 */
	public void close(Connection con)
	{
		pool.returnConnection(con);
	}
	/**
	 * 获取数据库连接池实例
	 * @return 数据库连接池实例
	 */
	public ConnectionPool getPool()
	{
		return this.pool;
	}
}

