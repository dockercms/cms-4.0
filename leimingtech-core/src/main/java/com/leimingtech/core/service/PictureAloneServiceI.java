package com.leimingtech.core.service;


import com.leimingtech.core.entity.PictureAloneEntity;

import java.util.List;

/**
 * 组图内图片管理接口
 */
public interface PictureAloneServiceI extends CommonService{

    /**
     * 根据组图id获取所有图片
     * @param pictureGroupId 组图id
     * @return 所有图片
     */
    List<PictureAloneEntity> findAllByPictureGroupId(String pictureGroupId);

}
