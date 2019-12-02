package com.leimingtech.cms.service;

import com.leimingtech.core.service.CommonService;

import java.util.Map;

public interface RelateContentServiceI extends CommonService{

    /**
     * 根据参数查询相关内容列表
     * @param params
     * @return
     */
    Object getRalateContentList(Map params);
}
