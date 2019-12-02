package com.leimingtech.core.service;

import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentLinkEntity;
import com.leimingtech.core.entity.ContentsEntity;

public interface ContentLinkServiceI extends CommonService{

	/**
	 * 保存链接方法
	 * @param contents
	 * @param contentLink
	 * @return
	 */
	void saveLink(ContentsEntity contents,ContentLinkEntity contentLink);
	/**
	 * 更新链接方法
	 * @param contents
	 * @param contentLink
	 * @param contentcat
	 * @return
	 */
	void updateLink(ContentsEntity contents,ContentLinkEntity contentLink,ContentCatEntity contentcat);
}
