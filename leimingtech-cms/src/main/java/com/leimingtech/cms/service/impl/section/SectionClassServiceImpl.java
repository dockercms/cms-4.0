package com.leimingtech.cms.service.impl.section;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.entity.section.SectionClassEntity;
import com.leimingtech.cms.service.SectionClassServiceI;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.service.SectionServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.impl.CommonServiceImpl;

/**
 * 区块分类管理接口实现
 * 
 * @author liuzhen 2014年5月5日 11:05:24
 * 
 */
@Service("sectionClassService")
@Transactional
public class SectionClassServiceImpl extends CommonServiceImpl implements SectionClassServiceI {

	@Autowired
	private SystemService systemService;

	@Autowired
	private SectionServiceI sectionMng;

	/**
	 * 删除区块分类 同时也将删除与之关联的区块信息
	 * 
	 * @param sectionClass
	 *            区块
	 */
	@Override
	public void deleteSectionClass(SectionClassEntity sectionClass) {
		List<String> ids = new ArrayList<String>();
		getchildidList(ids, sectionClass);

		String[] idArray = new String[ids.size()];
		for (int i = 0; i < ids.size(); i++) {
			idArray[i] = ids.get(i);
		}

		// 删除关联的区块分类之前删除关联的区块和区块数据
		sectionMng.deleteSectionByClass(idArray);

		String message = "";
		for (int i = ids.size() - 1; i >= 0; i--) {
			message = "区块分类 " + sectionClass.getName() + " 删除成功！";
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
			deleteEntityById(SectionClassEntity.class, ids.get(i));
		}
	}

	private void getchildidList(List<String> idList, SectionClassEntity function) {
		if (function != null) {
			idList.add(function.getId());
			List<SectionClassEntity> SectionClassList = findByProperty(SectionClassEntity.class, "sectionClass.id", function.getId());
			for (SectionClassEntity sectionClass : SectionClassList) {
				this.getchildidList(idList, sectionClass);
			}
		}
	}

}