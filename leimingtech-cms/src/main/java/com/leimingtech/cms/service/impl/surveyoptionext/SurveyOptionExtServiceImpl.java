package com.leimingtech.cms.service.impl.surveyoptionext;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.SurveyOptionExtEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.SurveyOptionExtServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("surveyOptionExtService")
@Transactional
public class SurveyOptionExtServiceImpl extends CommonServiceImpl implements SurveyOptionExtServiceI {
    /**
     * 公共dao接口
     */
    @Autowired
    private CommonService commonService;

    /**
     * 获取调查选项下的所有子项
     *
     * @param optionsId 选项id
     * @return 子项
     */
    @Override
    public List<SurveyOptionExtEntity> getAllExtByOptionId(String optionsId) {
        CriteriaQuery cq = new CriteriaQuery(SurveyOptionExtEntity.class);
        cq.eq("optionsid", optionsId);
        cq.addOrder("extSort", SortDirection.desc);
        cq.addOrder("createdtime", SortDirection.asc);
        cq.add();

        return this.commonService.getListByCriteriaQuery(cq, false);
    }
}