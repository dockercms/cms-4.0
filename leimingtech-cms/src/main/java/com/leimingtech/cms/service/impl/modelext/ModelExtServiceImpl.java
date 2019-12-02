package com.leimingtech.cms.service.impl.modelext;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.ModelExtEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.modelext.ModelExtServiceI;

@Service("modelExtService")
@Transactional
public class ModelExtServiceImpl implements ModelExtServiceI {
	
	@Autowired
	private CommonService commonService;

	@Override
	public void save(ModelExtEntity modelExt) {
		commonService.save(modelExt);
	}

	@Override
	public void saveOrUpdate(ModelExtEntity modelExt) {
		commonService.saveOrUpdate(modelExt);

	}

	@Override
	public ModelExtEntity getEntity(String id) {
		return commonService.getEntity(ModelExtEntity.class, id);
	}

	@Override
	public List<ModelExtEntity> getContentAllExt(String id) {
		CriteriaQuery cq = new CriteriaQuery(ModelExtEntity.class);
		cq.eq("contentId", id);
		cq.addOrder("createdtime", SortDirection.asc);
		cq.add();
		return commonService.getListByCriteriaQuery(cq, false);
	}

	@Override
	public void delete(ModelExtEntity modelExt) {
		commonService.delete(modelExt);
	}

	@Override
	public List<ModelExtEntity> findByItemId(String itemId) {
		return commonService.findByProperty(ModelExtEntity.class, "modelItemId", itemId);
	}

}