package com.test.spider;

import com.test.spider.util.SqlUtil;

public class mValue {
	//此类保存运行状态
	private static boolean dbIsClear=false;	//数据库是否清空
	private static SqlUtil mSqlUtil;
	public static boolean getDbState() {
		return dbIsClear;
	}

	public static void setDbState(boolean dbIsClear) {
		mValue.dbIsClear = dbIsClear;
	}

	public static SqlUtil getmSqlUtil() {
		return mSqlUtil;
	}

	public static void setmSqlUtil(SqlUtil mSqlUtil) {
		mValue.mSqlUtil = mSqlUtil;
	}
}
