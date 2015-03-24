package com.test.spider;

public class mValue {
	//此类保存运行状态
	private static boolean dbIsClear=false;	//数据库是否清空

	public static boolean getDbState() {
		return dbIsClear;
	}

	public static void setDbState(boolean dbIsClear) {
		mValue.dbIsClear = dbIsClear;
	}
}
