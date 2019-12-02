package com.leimingtech.core.service;

import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.SurveyEntity;

public interface SurveyServiceI extends CommonService{

	/**
	 * 保存调查
	 * @param contents
	 * @param survey
	 */
	void saveSurvey(ContentsEntity contents,SurveyEntity survey);
	/**
	 * 修改调查
	 * @param contents
	 * @param survey
	 * @param contentcat
	 * @param temporary
	 * @param divValue
	 */
	void updateSurvey(ContentsEntity contents,SurveyEntity survey,ContentCatEntity contentcat,String temporary,String divValue);

	/**
	 * 根据内容id获取调查
	 * @param contentId 内容id
	 * @return 调查
	 */
	SurveyEntity getSurveyByContentId(String contentId);
}
