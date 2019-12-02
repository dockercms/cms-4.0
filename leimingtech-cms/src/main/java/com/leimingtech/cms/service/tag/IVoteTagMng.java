package com.leimingtech.cms.service.tag;

import java.util.Map;

import com.leimingtech.core.service.CommonService;

/**
 * 投票标签接口
 * 
 * @author liuzhen 2014年5月21日 17:45:04
 * 
 */
public interface IVoteTagMng extends CommonService{

	/**
	 * 通过标签传递参数获取投票
	 * 
	 * @param params
	 * @return
	 */
	Object getVote(Map params);

	/**
	 * 通过标签传递参数获取投票选项
	 * 
	 * @param params
	 * @return
	 */
	Object getVoteOption(Map params);

}
