package com.leimingtech.core.service;

import java.util.List;

import com.leimingtech.core.entity.SectionEntity;

/**
 * 区块管理接口
 * 
 * @author liuzhen 2014年5月5日 11:23:25
 * 
 */
public interface SectionServiceI extends CommonService {

	/**
	 * 通过区块分类id删除区块列表 删除区块同时也将会删除区块关联的区块数据
	 * 
	 * @param idArray
	 *            分类ids
	 */
	void deleteSectionByClass(String[] idArray);

	/**
	 * 删除区块列表 删除区块同时也将会删除区块关联的区块数据
	 * 
	 * @param list
	 *            区块
	 */
	void delSectionList(List<SectionEntity> list);

}
