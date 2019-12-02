package com.leimingtech.cms.service.tag;

import java.util.Map;

import com.leimingtech.core.service.CommonService;

/**
 * 栏目标签接口
 * 
 * @author liuzhen 2014年4月28日 09:53:33
 * 
 */
public interface ICatalogTagMng extends CommonService {

	/**
	 * 获取栏目（此方法由标签调用）
	 * 
	 * @param params
	 *            栏目参数
	 * @return 根据参数不同既可以获取单个栏目也可以获取栏目列表 Catalog or List<Catalog>
	 */
	Object getCatalogByTag(Map params);

	Object getDefaultCat(Map params);
}
