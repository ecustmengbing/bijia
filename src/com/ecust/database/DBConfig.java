package com.ecust.database;


/**
 * 
 *************************
 * 数据库配置类
 ************************* 
 * @author kootain
 * @creation 2015年3月25日
 *
 */

public final class DBConfig 
{
	private static DBConfig instance = null;

	private String jdbcDriver;//驱动器名称
	private String dbUrl;//数据库url
	private String dbUsername;//数据库用户名
	private String dbPassword;//密码
	
	private int initialConnectionsNum = 5;//初始化连接池大小
	private int maxConnectionsNum = 20;//最大连接池长度
	private int incrementalConnections = 2;//每次递增的长度

	/**
	 * 得到静态实例
	 * @return 返回静态实例
	 */
	public static DBConfig getInstance()
	{
        if (instance == null) 
        {
            instance = new DBConfig();
        }
        return instance;
    }
	/**
	 * 构造函数
	 */
	public DBConfig()
	{
		try {
//			this.jdbcDriver = XMLOperator.getValueByTagname("database", "jdbcDriver");
//			this.dbUrl = XMLOperator.getValueByTagname("database", "dbUrl");
//			this.dbUsername = XMLOperator.getValueByTagname("database", "dbUsername");
//			this.dbPassword = XMLOperator.getValueByTagname("database", "dbPassword");
			this.jdbcDriver = "com.mysql.jdbc.Driver";
			this.dbUrl = "jdbc:mysql://localhost:3306/spiderdb";
			this.dbUsername = "root";
			this.dbPassword =  "root";
		} catch (Exception e) {
//			MyLogger.getLogger(DBConfig.class.getName()).fatal("read db config error");
		}
	}
	/**
	 * 
	 * @return jdbc驱动
	 */
	
	public String getJdbcDriver()
	{
		return this.jdbcDriver;
	}
	/**
	 * 
	 * @return 数据库URL
	 */
	public String getdbURL()
	{
		return this.dbUrl;
	}
	/**
	 * 
	 * @return  数据库用户名
	 */
	public String getDbUserName()
	{
		return this.dbUsername;
	}
	/**
	 * 
	 * @return  数据库用户密码
	 */
	public String getPassWd()
	{
		return this.dbPassword;
	}
	/**
	 * 
	 * @return 初始化连接池的长度
	 */
	public int getInitialConnectionsNum()
	{
		return this.initialConnectionsNum;
	}
	/**
	 * 
	 * @return  最大连接池中的连接数
	 */
	public int getMaxConnectionsNum()
	{
		return this.maxConnectionsNum;
	}
	/**
	 * 
	 * @return  每次递增的连接个数
	 */
	public int getIncrementalConnections()
	{
		return this.incrementalConnections;
	}

}