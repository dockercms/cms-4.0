package com.leimingtech.cms.service.acquisition;

import com.leimingtech.core.service.CommonService;

/**
 * 采集历史记录管理
 * @author liuzhen 2014年4月17日 14:35:53
 * 
 */
public interface AcquisitionHistoryServiceI extends CommonService {
	/**
	 * 判断当前内容是否已经被采集
	 * 
	 * @param Boolean
	 *            title 传递参数是否是标题
	 * @param String
	 *            value 当title为true value值标题 当title为false value值是URL
	 */
	public Boolean checkExistByProperties(Boolean title, String value);
}
