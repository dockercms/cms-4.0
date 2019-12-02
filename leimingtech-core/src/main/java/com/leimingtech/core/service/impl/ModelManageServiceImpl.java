package com.leimingtech.core.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.ModelItemEntity;
import com.leimingtech.core.entity.ModelManageEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.ModelItemServiceI;
import com.leimingtech.core.service.ModelManageServiceI;

/**
 * 拓展字段方案管理接口
 * 
 * @author liuzhen
 * 
 */
@Service("modelManageService")
@Transactional
public class ModelManageServiceImpl implements ModelManageServiceI {

	/**
	 * 公共dao
	 */
	@Autowired
	private CommonService commonService;
	@Autowired
	private ModelItemServiceI modelItemService;

	/**
	 * 根据id获取方案
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public ModelManageEntity getEntity(String id) {
		return commonService.getEntity(ModelManageEntity.class, id);
	}

	@Override
	public PageList getPageList(int pageSize, int pageNo,
			ModelManageEntity modelManage, Map param) {
		CriteriaQuery cq = new CriteriaQuery(ModelManageEntity.class, pageSize,
				pageNo, "", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, modelManage, param);
		// 排序条件
		cq.addOrder("priority", SortDirection.desc);
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		return commonService.getPageList(cq, true);
	}

	@Override
	public void saveOrUpdate(ModelManageEntity modelManage) {
		commonService.saveOrUpdate(modelManage);
	}

	@Override
	public void save(ModelManageEntity modelManage) {
		commonService.save(modelManage);
	}

	@Override
	public void delete(ModelManageEntity modelManage) {
		//删除模型项目
		List<ModelItemEntity> modelItemList = modelItemService.findByModelId(modelManage.getId());
		for(ModelItemEntity modelItem:modelItemList){
			modelItemService.delete(modelItem);
		}
		commonService.delete(modelManage);
	}

	@Override
	public List<ModelManageEntity> getAll() {
		CriteriaQuery cq = new CriteriaQuery(ModelManageEntity.class);
		// 排序条件
		cq.addOrder("priority", SortDirection.desc);
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		return commonService.getListByCriteriaQuery(cq, false);
	}

}