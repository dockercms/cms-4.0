package com.leimingtech.cms.service.impl.surveyoption;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.SurveyOptionEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.SurveyOptionServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("surveyOptionService")
@Transactional
public class SurveyOptionServiceImpl extends CommonServiceImpl implements SurveyOptionServiceI {
    /**
     * 公共dao接口
     */
    @Autowired
    private CommonService commonService;

    /**
     * 获取调查选项
     *
     * @param surveyId
     * @return
     */
    @Override
    public List<SurveyOptionEntity> getAllOptionBySurveyId(String surveyId) {
        CriteriaQuery cq = new CriteriaQuery(SurveyOptionEntity.class);
        cq.eq("surveyid", surveyId);
        cq.addOrder("optionsort", SortDirection.desc);
        cq.addOrder("createdtime", SortDirection.asc);
        cq.add();
        return this.commonService.getListByCriteriaQuery(cq, false);
    }
}