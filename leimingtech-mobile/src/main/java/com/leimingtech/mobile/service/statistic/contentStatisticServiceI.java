package com.leimingtech.mobile.service.statistic;

import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.entity.model.ModelsEntity;
import com.leimingtech.core.service.CommonService;

import java.util.List;

public interface contentStatisticServiceI extends CommonService {
	//用到的数据表有：
	//cm_content 内容表
	//cm_mobile_channel 频道表
	//t_s_commentary //评论表
	//cms_model 模型表
	//cm_content_article 文章表 lmcms/articleMobileController.do?save&divValue=
	//cm_picture_group 图组表 ///lmcms/pictureGroupMobileController.do?save
	//cm_link 链接表  //lmcms/contentLinkMobileController.do?save
	//cm_video 视频表 /lmcms/contentVideoMobileController.do?save
	//cm_vote 投票 /lmcms/voteMobileController.do?save
	//cm_survey 调查 /lmcms/surveyMobileController.do?save
	
	
	/**
	 * 获取频道集合
	 * @return
	 */
	List<MobileChannelEntity> getListChannel();
	/**
	 * 获取频道
	 * @param id 频道ID
	 * @return 频道实体
	 */
	MobileChannelEntity getChannelById(String id);
	/**
	 * 获取模型集合
	 * @return
	 */
	List<ModelsEntity> getListMobileChannel();
	/**
	 * 获取模型
	 * @param id 模型ID
	 * @return 模型实体
	 */
	ModelsEntity getMobileChannelById(String id);
	/**
	 * 获取移动平台内容统计
	 * @param catId 频道ID
	 * @param classify 模型ID
	 * @param startDate 开始日期
	 * @param stopDate 结束日期
	 * @param type 统计类型
	 * @return 统计:访问量统计,发布文章统计,评论统计
	 */
	public net.sf.json.JSONArray getStatistic(String catId,String classify,String startDate,String stopDate,String type); 

}
