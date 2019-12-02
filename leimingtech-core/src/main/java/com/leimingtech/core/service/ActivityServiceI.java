package com.leimingtech.core.service;

import java.util.List;

import com.leimingtech.core.entity.ActivityEntity;
import com.leimingtech.core.entity.ActivityOptionContentEntity;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;

public interface ActivityServiceI extends CommonService{
	/**
	 * 保存活动
	 * @param contents
	 * @param vote
	 */
	void saveActivity(ContentsEntity contents,ActivityEntity activity);
	/**
	 * 修改活动
	 * @param contents
	 * @param vote
	 * @param contentcat
	 * @param voteOptionList
	 * @param temporary
	 * @param divValue
	 */
	void updateActivity(ContentsEntity contents,ActivityEntity activity,ContentCatEntity contentcat,List<ActivityOptionContentEntity> activityOptionList,String temporary,String divValue);
}
