package com.ecust.database;

import java.sql.*;
import java.util.Vector;

/**
 * 
 *************************
 * 数据库连接池
 ************************* 
 * @author kootain
 * @creation 2015年3月25日
 *
 */

public class ConnectionPool 
{
	private DBConfig config;//MySql配置类
	private static ConnectionPool instance=null; 
	
	private String jdbcDriver;
	private String dbUrl;
	private String dbUsername;
	private String dbPassword;
	private String testTable;
	private int initialConnectionsNum;
	private int maxConnectionsNum;
	private int incrementalConnections;
//	private static Logger logger=MyLogger.getLogger(ConnectionPool.class.getName());
	
	private Vector<PooledConnection> connections = null;
	
	/**
	 * 构造函数
	 */
	public ConnectionPool()
	{
		//从配置类的静态实体加载参数
		this.config=DBConfig.getInstance();
		this.jdbcDriver=config.getJdbcDriver();
		this.dbUrl=config.getdbURL();
		this.dbUsername =config.getDbUserName();
		this.dbPassword = config.getPassWd();
		
		this.initialConnectionsNum=config.getInitialConnectionsNum();
		this.maxConnectionsNum=config.getMaxConnectionsNum();
		this.incrementalConnections=config.getIncrementalConnections();
		
		//System.out.print(jdbcDriver+dbUrl+dbUsername+dbPassword);
		//测试用表
		this.testTable = "news";
		
		try {
			this.createPool();//创建连接池
		} catch (InstantiationException e) {
		
//			logger.error(e);
		} catch (IllegalAccessException e) {
//			logger.error(e);
			
		} catch (ClassNotFoundException e) {
//			logger.error(e);
			
		} catch (SQLException e) {
//			logger.error(e);
		}
	}
	/**
	 * 得到连接池实体
	 * @return instance 返回类实体
	 */
	public static ConnectionPool getInstance()
	{
		if(instance==null)
		{
			instance=new ConnectionPool();
		}
		return instance;
	}
	/**
	 * 得到一个线程安全的连接
	 * @throws SQLException 
	 */
	public synchronized Connection getConnection () throws SQLException 
	{
		Connection con = null;
	
		if (this.connections == null)
		{
			//return this.getFreeConnection();
			return con;
		}
		//获取一个新的链接
		try {
			con = this.getFreeConnection();
		} catch (SQLException e) {
//			logger.warn("can not get free DB connection");
		}
		//等待可用连接
		while(con == null)
		{
			this.wait(30);
			try {
				con = this.getFreeConnection();
			} catch (SQLException e) {
//				logger.warn("can not get free DB connection");
			}
		}
		
		return con;
	}
	
	/** 
	 * 线程安全的创建一个连接池
	 */
	public synchronized void createPool() throws InstantiationException, IllegalAccessException, 
	ClassNotFoundException, SQLException
	{
		if (this.connections != null)
		{
			return ;
		}
		
		Driver driver = (Driver)(Class.forName(this.jdbcDriver).newInstance());
		DriverManager.registerDriver(driver);
		this.connections = new Vector<PooledConnection>();
		this.createConnections(this.initialConnectionsNum);
	}
	/**
	 * 将借出去的连接返还给连接池
	 */
	public void returnConnection(Connection con)
	{
		if (this.connections == null)
		{
			return ;
		}
		for (int i = 0; i < this.connections.size(); ++i)
		{
			PooledConnection pool = this.connections.get(i);
			if (con == pool.getCon())
			{
				pool.setBusy(false);
			}
		}
		
	}
	
	/**
	 * 刷新连接池
	 */
	public synchronized void refreshConneciontPool () throws SQLException
	{
		if (this.connections == null)
		{
			return ;
		}
		for (int i = 0; i < this.connections.size(); ++i)
		{
			PooledConnection pool = this.connections.get(i);
			if (pool.isBusy())
			{
				this.wait(5000);
			}
			this.closeConnection(pool.getCon());
			pool.setCon(this.newConnection());
			pool.setBusy(false);
		}
	}

	/**
	 * 关闭连接池
	 */
	public void closeConnectionPool()
	{
		if (this.connections == null)
		{
			return ;
		}
		for (int i = 0; i < this.connections.size(); ++i)
		{
			PooledConnection pool = this.connections.get(i);
			if (pool.isBusy())
			{
				this.wait(5000);
			}
			this.closeConnection(pool.getCon());
			this.connections.remove(i);
		}
		this.connections = null;
	}
	
	/**
	 * 创建（多个）连接
	 */
	private void createConnections (int num) throws SQLException
	{
		for (int i = 0; i < num; ++i)
		{
			if (this.connections.size() >= this.maxConnectionsNum)
			{
				return;
			}
			this.connections.addElement
			(new PooledConnection(newConnection()));
		}
		
	}
	
	/**
	 * 创建连接
	 */
	private Connection newConnection() throws SQLException
	{

		Connection con = DriverManager.getConnection(this.dbUrl, 
				this.dbUsername, this.dbPassword);
		if (this.connections.size() == 0)
		{
			DatabaseMetaData metadata = con.getMetaData();
			int dbMaxConnectionsNum = metadata.getMaxConnections();
			if (dbMaxConnectionsNum > 0 
					&& this.maxConnectionsNum > dbMaxConnectionsNum)
			{
				this.maxConnectionsNum = dbMaxConnectionsNum;
			}
		}
		return con;
	}
	
	/**
	 * 得到一个可用的连接
	 */
	private Connection getFreeConnection() throws SQLException
	{
		Connection con = null;
		con = this.findFreeConnection();
		if (con == null)
		{
			this.createConnections(this.incrementalConnections);
			con = this.findFreeConnection();
		}
		return con;
	}
	
	/**
	 * 找到可用的连接
	 */
	private Connection findFreeConnection () throws SQLException
	{
		Connection con = null;
		for (int i = 0; i < this.connections.size(); ++i)
		{
			PooledConnection pol = (PooledConnection)this.connections.get(i);
			if (!pol.isBusy())
			{
				con = pol.getCon();
				pol.setBusy(true);

				if (!this.testCon(con))
				{
					con = this.newConnection();
					pol.setCon(con);
				}
				break;
			}
		}
		return con;
	}
	
	/**
	 * 内部类
	 */
	public boolean testCon (Connection con)
	{
		boolean useable = true;
		try
		{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select count(*) from " + this.testTable);
			rs.next();
		}
		catch(SQLException e)
		{
			useable = false;
			this.closeConnection(con);
		}
		return useable;
	}
	
	/**
	 * 等待
	 */
	private void wait(int mSecond)
	{
		try {
			Thread.sleep(mSecond);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the jdbcDriver
	 */
	public String getJdbcDriver() {
		return jdbcDriver;
	}

	/**
	 * @param jdbcDriver the jdbcDriver to set
	 */
	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	/**
	 * @return the dbUrl
	 */
	public String getDbUrl() {
		return dbUrl;
	}

	/**
	 * @param dbUrl the dbUrl to set
	 */
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	/**
	 * @return the dbUsername
	 */
	public String getDbUsername() {
		return dbUsername;
	}

	/**
	 * @param dbUsername the dbUsername to set
	 */
	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	/**
	 * @return the dbPassword
	 */
	public String getDbPassword() {
		return dbPassword;
	}

	/**
	 * @param dbPassword the dbPassword to set
	 */
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	/**
	 * @return the testTable
	 */
	public String getTestTable() {
		return testTable;
	}

	/**
	 * @param testTable the testTable to set
	 */
	public void setTestTable(String testTable) {
		this.testTable = testTable;
	}

	/**
	 * @return the initialConnectionsNum
	 */
	public int getInitialConnectionsNum() {
		return initialConnectionsNum;
	}

	/**
	 * @param initialConnectionsNum the initialConnectionsNum to set
	 */
	public void setInitialConnectionsNum(int initialConnectionsNum) {
		this.initialConnectionsNum = initialConnectionsNum;
	}

	/**
	 * @return the maxConnectionsNum
	 */
	public int getMaxConnectionsNum() {
		return maxConnectionsNum;
	}

	/**
	 * @param maxConnectionsNum the maxConnectionsNum to set
	 */
	public void setMaxConnectionsNum(int maxConnectionsNum) {
		this.maxConnectionsNum = maxConnectionsNum;
	}

	/**
	 * @return the incrementalConnections
	 */
	public int getIncrementalConnections() {
		return incrementalConnections;
	}

	/**
	 * @param incrementalConnections the incrementalConnections to set
	 */
	public void setIncrementalConnections(int incrementalConnections) {
		this.incrementalConnections = incrementalConnections;
	}

	/**
	 * @return the connections
	 */
	public Vector<PooledConnection> getConnections() {
		return connections;
	}

	/**
	 * @param connections the connections to set
	 */
	public void setConnections(Vector<PooledConnection> connections) {
		this.connections = connections;
	}

	/**
	 * 关闭连接
	 */
	private void closeConnection (Connection con)
	{
		try
		{
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 内部类
	 */
	class PooledConnection
	{
		private Connection con = null;
		private boolean busy = false;
		
		public PooledConnection(Connection con)
		{
			this.con = con;
		}

		/**
		 * @return the con
		 */
		public Connection getCon() {
			return con;
		}

		/**
		 * @param con the con to set
		 */
		public void setCon(Connection con) {
			this.con = con;
		}

		/**
		 * @return the busy
		 */
		public boolean isBusy() {
			return busy;
		}

		/**
		 * @param busy the busy to set
		 */
		public void setBusy(boolean busy) {
			this.busy = busy;
		}
	}

}
