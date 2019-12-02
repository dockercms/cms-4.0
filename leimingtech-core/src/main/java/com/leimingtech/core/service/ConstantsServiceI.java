package com.leimingtech.core.service;

import java.util.List;

import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.TSRole;
import com.leimingtech.core.entity.TSUser;

public interface ConstantsServiceI extends CommonService{

	/**
	 * 是否有工作流
	 * @param contentCatId
	 * @param contents
	 */
	void isWorkFlow(ContentsEntity contents,ContentCatEntity contentcat);
	/**
	 * 是否到发布时间
	 * @param contents
	 * @param contentcat
	 */
	void toPublished(ContentsEntity contents,ContentCatEntity contentcat);
	
	/**
	 * 工作流
	 * @param contents
	 * @param contentCatId
	 */
	void enterworkflow(ContentsEntity contents,ContentCatEntity concat);
	/**
	 * 工作流内容送审
	 * @param ids
	 * @return
	 */
	String songshen(String ids,String token);
	/**
	 * 工作流审核
	 * @param ids
	 * @param result
	 * @return
	 */
	String contentsAudit(String ids,String result);
	/**
	 * 下线、回收站
	 * @param result
	 * @return
	 */
	String downContent(String contentsId,String result);
	
	void encode(String contentId) throws Exception;
	/**
	 * 判断当前工作流是否为第一步
	 * @param contentCat
	 * @param roleList
	 * @return
	 */
	public String firstStep(ContentCatEntity contentCat,List<TSRole> roleList);
	/**
	 * 判断是否有权限审批工作流
	 * @param roleList
	 * @return
	 */
	public boolean isFun(TSUser user,Integer type);
	
}
