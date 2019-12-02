package com.leimingtech.platform.service;

import java.util.List;
import java.util.Map;

import com.leimingtech.core.entity.TSLog;
import com.leimingtech.core.hibernate.qbc.PageList;

/**
 * 日志管理接口
 * 
 * @author liuzhen
 * 
 */
public interface LogService {

	/**
	 * 根据id获取log
	 * 
	 * @param id
	 * @return
	 */
	TSLog getEntity(String id);

	/**
	 * 删除一条日志
	 * @param tslog
	 */
	void delete(TSLog tslog);

	/**
	 * 获取日志总数
	 * @return
	 */
	Long getLogCount();

	List findByQueryString();

	PageList getPageList(int pageSize,int pageNo,TSLog logs,Map param);

}
