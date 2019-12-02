package com.leimingtech.platform.service.impl.operation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.entity.operation.OperationEntity;
import com.leimingtech.platform.service.operation.OperationServiceI;

@Service("operationService")
@Transactional
public class OperationServiceImpl extends CommonServiceImpl implements
		OperationServiceI {

	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;

	/**
	 * 验证Url是否在功能表中存在
	 * 
	 * @param url
	 * @return
	 */
	@Override
	public boolean checkUrlExist(String url) {
		List<OperationEntity> operaList = commonService.findByProperty(
				OperationEntity.class, "operationurl", url);
		if (operaList == null || operaList.size() == 0) {
			return false;
		}
		return true;
	}

}