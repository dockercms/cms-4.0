package com.leimingtech.core.service;

import java.util.List;

import com.leimingtech.core.entity.ContentsEntity;

/**
 * 非法内容管理接口
 * 
 * @author liuzhen 2014年8月20日 11:54:46
 * 
 */
public interface ContentIllegalServiceI {

	/**
	 * 删除内容页面文件
	 * 
	 * @param content
	 */
	void delContentHtmlFile(ContentsEntity content);

	/**
	 * 删除多个内容页面文件
	 * 
	 * @param contentList
	 */
	void delContentHtmlFile(List<ContentsEntity> contentList);

	/**
	 * 根据内容id删除页面文件
	 * 
	 * @param id
	 */
	void delContentHtmlFile(Integer id);
}
