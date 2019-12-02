package com.leimingtech.platform.service.operation;

import com.leimingtech.core.service.CommonService;


/**
 * 操作功能管理接口
 * 
 * @author liuzhen
 * 
 */
public interface OperationServiceI extends CommonService {

	/**
	 * 验证Url是否在功能表中存在
	 * 
	 * @param url
	 * @return
	 */
	boolean checkUrlExist(String url);
}
