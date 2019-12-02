package com.leimingtech.cms.service.acquisition;

import java.util.List;

import com.leimingtech.cms.entity.acquisition.AcquisitionreplaceEntity;
import com.leimingtech.core.service.CommonService;

/**
 * 接口 数据采集关联表 内容替换
 * 
 * @author liuzhen 2015年4月7日 16:11:48
 * 
 */
public interface AcquisitionreplaceServiceI extends CommonService {

	/**
	 * 根据采集id获取内容替换集合<br/>
	 * 按照创建时间倒序返回数据
	 * 
	 * @param acqId
	 *            采集id
	 * @return 内容替换集合
	 */
	List<AcquisitionreplaceEntity> getListByAcq(String acqId);
}
