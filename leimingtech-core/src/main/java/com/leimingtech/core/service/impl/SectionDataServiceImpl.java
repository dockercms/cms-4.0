package com.leimingtech.core.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.SectionDataEntity;
import com.leimingtech.core.entity.SectionEntity;
import com.leimingtech.core.entity.TSUser;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.SectionDataServiceI;
import com.leimingtech.core.service.SystemService;

/**
 * 区块数据管理接口实现
 * 
 * @author liuzhen 2014年5月5日 11:42:41
 * 
 */
@Service("sectionDataService")
@Transactional
public class SectionDataServiceImpl extends CommonServiceImpl implements SectionDataServiceI {

	@Autowired
	private SystemService systemService;

	/**
	 * 通过区块删除区块数据
	 * 
	 * @param ids
	 *            区块
	 */
	@Override
	public void delSectionDataBySection(String[] ids) {
		if (ids.length == 0)
			return;

		String message = "";

		CriteriaQuery cq = new CriteriaQuery(SectionDataEntity.class);
		cq.in("sectionid", ids);
		cq.add();
		List<SectionDataEntity> list = getListByCriteriaQuery(cq, false);

		for (SectionDataEntity sectionData : list) {
			message = "区块数据 " + sectionData.getTitle() + " 删除成功！";
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
			delete(sectionData);
		}
	}

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
	@Override
	public List<SectionDataEntity> getSectionDataListByTag(Map params) {
		String section = (String) params.get("name");
		String count = (String) params.get("count");

		if (StringUtils.isEmpty(section))
			return null;

		CriteriaQuery cq = new CriteriaQuery(SectionDataEntity.class);
		String sectionid = section;
		List<SectionEntity> sectionList = findByProperty(SectionEntity.class,
				"name", section);
		if (sectionList != null && sectionList.size() > 0) {
			sectionid = sectionList.get(0).getId();
		}
		cq.eq("sectionid", sectionid);
		cq.addOrder("sort", SortDirection.desc);
		cq.add();
		List<SectionDataEntity> list = getListByCriteriaQuery(cq, false);

		if (StringUtils.isNotEmpty(count) && StringUtils.isNumeric(count)
				&& list != null) {

			if (list.size() > Integer.valueOf(count)) {
				list = list.subList(0, Integer.valueOf(count));
			}
		}
		return list;

	}

	/**
	 * 向区块数据中添加添加人信息
	 * 
	 * @param list
	 */
	@Override
	public void waterListAddCreateUser(List<SectionDataEntity> list) {
		if (list == null || list.size() == 0)
			return;

		for (int i = 0; i < list.size(); i++) {
			SectionDataEntity data = list.get(i);
			String userid = data.getCreatedby();
			if (StringUtils.isEmpty(userid)) {
				data.setCreateUser(new TSUser());
				continue;
			}
			TSUser user = getEntity(TSUser.class, userid);
			data.setCreateUser(user);
		}

	}

}