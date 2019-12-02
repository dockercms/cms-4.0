package com.leimingtech.core.service;

import java.util.List;
import java.util.Map;

import com.leimingtech.core.entity.SectionDataEntity;

/**
 * 区块数据管理接口
 * 
 * @author liuzhen 2014年5月5日 11:39:56
 * 
 */
public interface SectionDataServiceI extends CommonService {

	/**
	 * 通过区块删除区块数据
	 * 
	 * @param idArray
	 *            区块
	 */
	void delSectionDataBySection(String[] idArray);

	/**
	 * 获取区块数据列表 （此方法由标签调用）
	 * 
	 * @param params
	 * @param name
	 *            区块名 可以是id
	 * @param count
	 *            指定获取数据量
	 * @return 区块数据
	 */
	List<SectionDataEntity> getSectionDataListByTag(Map params);

	/**
	 * 向区块数据中添加添加人信息
	 * 
	 * @param list
	 */
	void waterListAddCreateUser(List<SectionDataEntity> list);

}
