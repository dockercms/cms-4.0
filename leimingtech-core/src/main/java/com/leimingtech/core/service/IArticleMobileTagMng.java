package com.leimingtech.core.service;

import java.util.Map;

import com.leimingtech.core.entity.ContentsMobileEntity;

public interface IArticleMobileTagMng extends CommonService{

	/**
	 * 获取内容相关模型
	 * @param content
	 * @return
	 */
	public Map<String, Object> getContent(ContentsMobileEntity content);
}
