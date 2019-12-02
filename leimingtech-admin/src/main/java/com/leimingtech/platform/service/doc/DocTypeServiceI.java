package com.leimingtech.platform.service.doc;

import com.leimingtech.core.service.CommonService;
import com.leimingtech.platform.entity.doc.DocTypeEntity;

/**
 * 文档类别管理接口
 * 
 * @author liuzhen 2014年6月11日 16:56:59
 * 
 */
public interface DocTypeServiceI extends CommonService {
	/**
	 * 删除文档类别（级联删除子集）
	 * 
	 * @param docType
	 */
	void delete(DocTypeEntity docType);
}
