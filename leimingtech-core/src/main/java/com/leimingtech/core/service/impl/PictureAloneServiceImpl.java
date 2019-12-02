package com.leimingtech.core.service.impl;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.PictureAloneEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.PictureAloneServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 组图内图片管理接口
 */
@Service("pictureAloneService")
@Transactional
public class PictureAloneServiceImpl extends CommonServiceImpl implements PictureAloneServiceI {

    /**
     * 公共dao接口
     */
    @Autowired
    private CommonService commonService;

    /**
     * 根据组图id获取所有图片
     *
     * @param pictureGroupId 组图id
     * @return 所有图片
     */
    @Override
    public List<PictureAloneEntity> findAllByPictureGroupId(String pictureGroupId) {
        CriteriaQuery cq = new CriteriaQuery(PictureAloneEntity.class);
        cq.eq("picturegroupid",pictureGroupId);
        cq.addOrder("pictureSort", SortDirection.desc);
        cq.addOrder("createdtime",SortDirection.asc);
        cq.add();
        return this.commonService.getListByCriteriaQuery(cq,false);
    }
}