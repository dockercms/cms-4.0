package com.leimingtech.cms.service;

import com.leimingtech.cms.entity.section.SectionClassEntity;
import com.leimingtech.core.service.CommonService;

/**
 * 区块分类管理接口
 * 
 * @author liuzhen 2014年5月5日 11:04:48
 * 
 */
public interface SectionClassServiceI extends CommonService {

	/**
	 * 删除区块分类 同时也将删除与之关联的区块信息
	 * 
	 * @param sectionClass
	 *            区块
	 */
	void deleteSectionClass(SectionClassEntity sectionClass);

}
