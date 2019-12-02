package com.leimingtech.core.service;

import com.leimingtech.core.entity.SurveyOptionExtEntity;

import java.util.List;

public interface SurveyOptionExtServiceI extends CommonService{

    /**
     * 获取调查选项下的所有子项
     * @param optionsId 选项id
     * @return 子项
     */
    List<SurveyOptionExtEntity> getAllExtByOptionId(String optionsId);
}
