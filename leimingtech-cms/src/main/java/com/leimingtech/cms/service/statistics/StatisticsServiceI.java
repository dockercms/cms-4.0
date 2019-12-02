package com.leimingtech.cms.service.statistics;

import net.sf.json.JSONArray;

import com.leimingtech.core.service.CommonService;

public interface StatisticsServiceI extends CommonService{

	/**
	 * 统计数据
	 * @param searchYear
	 * @param searchMonth
	 * @param staticsType
	 * @return
	 */
	public JSONArray statisticsData(String searchYear,String searchMonth,String staticsType);
	/**
	 * 按年、月统计
	 * @param year
	 * @param month
	 * @param staticsType
	 * @return
	 */
	public JSONArray yearStat(String year,String month,String staticsType);
	/**
	 * 按月进行查询，显示天数
	 * @param days
	 * @param year
	 * @param month
	 * @param staticsType
	 * @return
	 */
	public JSONArray days(int days,String year,String month,String staticsType,String dbType);
	/**
	 * 会员、评论、留言年sql
	 * @param value
	 * @param month
	 * @param staticsType
	 * @return
	 */
	public String yearSql(int value,String month,String staticsType,String dbType);
	/**
	 * 会员、评论、留言月sql
	 * @param year
	 * @param month
	 * @param staticsType
	 * @param i
	 * @return
	 */
	public String monthSql(String year,String month,String staticsType,int i,String dbType);
	/**
	 * 会员、评论、留言天sql
	 * @param year
	 * @param month
	 * @param staticsType
	 * @param i
	 * @return
	 */
	public String daySql(String year,String month,String staticsType,int i,String dbType);
}
