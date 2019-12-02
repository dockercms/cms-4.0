package com.leimingtech.cms.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;

/**
 * 会员中心投搞
 * @author LKANG
 * 2014-08-13 10:55:00
 */
public interface ContributeServiceI extends CommonService{
	
	/**
	 * 保存爆料即保存文章
	 * @param map map中包含request中所有的参数
	 */
	void saveContribute(Map<String, String> map);
	
	/**
	 * 根据文章id获取文章内容
	 * @param id
	 * @return
	 */
	Map<String, Object> getContentById(String id);
	
	/**
	 * 根据会员id获取投搞文章列表
	 * @param pagesize
	 * @param pageno
	 * @param map
	 * @param memberid
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	PageList contentsList(Integer pageSize, Integer pageNo, Map map, String memberid, ContentsEntity contens);
	/**
	 * 获取允许投稿的栏目
	 * @return
	 */
	JSONObject getContributeCat();
	/**
	 * 获取文章内容列表
	 * @param contentList
	 * @return
	 */
	JSONObject getContentList(List<ContentsEntity> contentList);
}
