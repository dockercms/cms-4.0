package com.leimingtech.core.service;

import com.leimingtech.core.entity.SurveyOptionEntity;

import java.util.List;

public interface SurveyOptionServiceI extends CommonService{

    /**
     * 获取调查选项
     * @param surveyId
     * @return
     */
    List<SurveyOptionEntity> getAllOptionBySurveyId(String surveyId);
}
