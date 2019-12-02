package com.leimingtech.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.SectionEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.SectionDataServiceI;
import com.leimingtech.core.service.SectionServiceI;
import com.leimingtech.core.service.SystemService;

/**
 * 区块管理接口实现
 * 
 * @author liuzhen 2014年5月5日 11:23:53
 * 
 */
@Service("sectionService")
@Transactional
public class SectionServiceImpl extends CommonServiceImpl implements SectionServiceI {

	@Autowired
	private SystemService systemService;

	@Autowired
	private SectionDataServiceI SectionDataMng;

	/**
	 * 通过区块分类id删除区块列表 删除区块同时也将会删除区块关联的区块数据
	 * 
	 * @param ids
	 *            分类ids
	 */
	public void deleteSectionByClass(String[] ids) {
		String message = "";

		CriteriaQuery cq = new CriteriaQuery(SectionEntity.class);
		cq.in("classid", ids);
		cq.add();
		List<SectionEntity> list = getListByCriteriaQuery(cq, false);

		delSectionList(list);
	}

	/**
	 * 删除区块列表 删除区块同时也将会删除区块关联的区块数据
	 * 
	 * @param list
	 *            区块
	 */
	public void delSectionList(List<SectionEntity> list) {
		String message;
		String[] idArray = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			idArray[i] = list.get(i).getId();
		}

		// 删除区块之前先删除区块数据
		SectionDataMng.delSectionDataBySection(idArray);

		for (SectionEntity section : list) {
			message = "区块 " + section.getName() + " 删除成功！";
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
			delete(section);
		}
	}

}