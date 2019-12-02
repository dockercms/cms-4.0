package com.leimingtech.core.service;


public interface MessagesServiceI extends CommonService{

	/**
	 * 统计留言年sql
	 * @param value
	 * @param month
	 * @param staticsType
	 * @return
	 */
	public String yearSqlMessage(int value,String month,String staticsType,String dbType);
	/**
	 * 统计留言月sql
	 * @param year
	 * @param month
	 * @param staticsType
	 * @param i
	 * @return
	 */
	public String monthSqlMessage(String year,String month,String staticsType,int i,String dbType);
	/**
	 * 统计留言天sql
	 * @param year
	 * @param month
	 * @param staticsType
	 * @param i
	 * @return
	 */
	public String daySqlMessage(String year,String month,String staticsType,int i,String dbType);
	
}
