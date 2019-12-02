package com.leimingtech.core.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.ModelExtEntity;
import com.leimingtech.core.entity.ModelItemEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.ModelItemServiceI;
import com.leimingtech.core.service.modelext.ModelExtServiceI;
import com.leimingtech.core.util.oConvertUtils;

@Service("modelItemService")
@Transactional
public class ModelItemServiceImpl implements ModelItemServiceI {
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ModelExtServiceI modelExtService;

	@Override
	public PageList getPageList(ModelItemEntity modelItem, Map param) {
		CriteriaQuery cq = new CriteriaQuery(ModelItemEntity.class);
		//查询条件组装器
		HqlGenerateUtil.installHql(cq, modelItem, param);
		//排序条件
		cq.addOrder("priority", SortDirection.asc);
		cq.addOrder("createdtime", SortDirection.asc);
		cq.add();
		PageList pageList = this.commonService.getPageList(cq,false);
		return pageList;
	}

	@Override
	public ModelItemEntity getEntity(String id) {
		return commonService.getEntity(ModelItemEntity.class, id);
	}

	@Override
	public void saveOrUpdate(ModelItemEntity modelItem) {
		commonService.saveOrUpdate(modelItem);
	}

	@Override
	public void save(ModelItemEntity modelItem) {
		commonService.save(modelItem);
	}

	@Override
	public void delete(ModelItemEntity modelItem) {
		
		//删除参数名称和value
		List<ModelExtEntity> modelExtList = modelExtService.findByItemId(modelItem.getId());
		for(ModelExtEntity modelExt:modelExtList){
			modelExtService.delete(modelExt);
		}
		
		commonService.delete(modelItem);
	}

	/**
	 * 根据方案查询所有字段
	 * @param modelId
	 * @return
	 */
	@Override
	public List<ModelItemEntity> findByModelId(String modelId) {
		CriteriaQuery cq = new CriteriaQuery(ModelItemEntity.class);
		cq.eq("modelId", modelId);
		cq.addOrder("priority", SortDirection.asc);
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		return commonService.getListByCriteriaQuery(cq, false);
	}

	/**
	 * 按照数据进行调整排序值
	 * @param ids
	 */
	@Override
	public void updateSort(String[] ids) {
		if(ids==null || ids.length==0){
			return;
		}
		
		for (int i = 0; i < ids.length; i++) {
			updateSort(ids[i],i+1);
		}
	}
	
	/**
	 * 修改单个字段的排序值
	 * @param id
	 * @param newSort
	 * @return 修改时受影响的行数 大于0证明已经更新成功
	 */
	private int updateSort(String id, int newSort) {
		String sql = "update cms_model_item set priority = ? where id = ?";
		return commonService.executeSql(sql, newSort, id);
	}

	/**
	 * 获取指定方案中的最大排序值
	 * 
	 * @param modelId
	 * @return
	 */
	@Override
	public int getMaxPriorityByModel(String modelId) {
		String sql = "select max(priority) max from cms_model_item where model_id = ?";
		Map<String, Object> m = commonService.findOneForJdbc(sql, modelId);
		int maxPriority = oConvertUtils.getInt(m.get("max"), 0);
		return maxPriority;
	}

	/**
	 * 检查指定方案中变量名是否有重名
	 * @param modelId
	 * @param field
	 * @return
	 */
	@Override
	public boolean checkFieldExits(String modelId, String field) {

		String sql = "select id from cms_model_item where model_id = ? and field = ?";
		List<Map<String, Object>> list = commonService.findForJdbc(sql,
				modelId, field);
		if (list != null && list.size() > 0) {
			return true;
		}

		return false;
	}
	
}