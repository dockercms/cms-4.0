package com.leimingtech.core.service.impl;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.model.ModelsEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.ModelsServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("modelsService")
@Transactional
public class ModelsServiceImpl implements ModelsServiceI {

	@Autowired
	private CommonService commonService;

	/**
	 * 根据条件获取模型列表
	 * 
	 * @param columnName
	 *            要获取的列名,如果不取指定列请传*号要获取的列名,如果不取指定列请传*号,如指定请传如：id, name
	 * @param where
	 *            条件,没有条件传空,如有条件请传如： and id = 1
	 * @return
	 */
	public List<Map<String, Object>> ModelListForJdbc(String columnName,
			String where) {
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(columnName);
		sql.append(" from cms_model ");
		sql.append(" where 1=1 ");
		sql.append(where);
		List<Map<String, Object>> list = commonService.findForJdbc(sql
				.toString());
		return list;
	}

	/**
	 * 获取所有开启的模型
	 * 
	 * @return
	 */
	@Override
	public List<ModelsEntity> getAllOpenModels() {
		CriteriaQuery cq = new CriteriaQuery(ModelsEntity.class);
		cq.eq("disabled", 0);
		// 排序条件
		cq.addOrder("sort", SortDirection.desc);
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		return commonService.getListByCriteriaQuery(cq, false);
	}
}