package com.leimingtech.cms.service.comments;

import com.leimingtech.core.service.CommonService;

public interface CommentsServiceI extends CommonService{

	/**
	 * 统计评论年sql
	 * @param value
	 * @param month
	 * @param staticsType
	 * @return
	 */
	public String yearSqlComments(int value,String month,String staticsType,String dbType);
	/**
	 * 统计评论月sql
	 * @param year
	 * @param month
	 * @param staticsType
	 * @param i
	 * @return
	 */
	public String monthSqlComments(String year,String month,String staticsType,int i,String dbType);
	/**
	 * 统计评论天sql
	 * @param year
	 * @param month
	 * @param staticsType
	 * @param i
	 * @return
	 */
	public String daySqlComments(String year,String month,String staticsType,int i,String dbType);
}
