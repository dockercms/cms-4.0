package com.leimingtech.core.service;

import com.leimingtech.core.entity.model.ModelsEntity;

import java.util.List;


public interface ModelsServiceI {

	/**
	 * 获取所有开启的模型
	 * 
	 * @return
	 */
	List<ModelsEntity> getAllOpenModels();
}
