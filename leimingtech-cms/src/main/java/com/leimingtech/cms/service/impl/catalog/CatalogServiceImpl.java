package com.leimingtech.cms.service.impl.catalog;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.catalog.CatalogServiceI;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.core.util.SystemPath;

/**
 * 栏目管理接口I实现
 * 
 * @author liuzhen 2014年5月4日 09:54:02
 * 
 */
@Service("catalogService")
@Transactional
public class CatalogServiceImpl extends CommonServiceImpl implements CatalogServiceI {

	/**
	 * 获取栏目路径
	 * 
	 * @param catalogid
	 *            栏目id
	 * @return
	 */
	@Override
	public String getCatalogPath(Integer catalogid) {
		ContentCatEntity catalog = getEntity(ContentCatEntity.class, catalogid);
		if (catalog != null) {
			catalog.getPath();
		}
		return "";
	}

	/**
	 * 获取栏目全路径路径 如：
	 * 
	 * @param catalogid
	 * @return
	 */
	@Override
	public String getCatalogFullPath(Integer catalogid) {
		String catalogPath = getCatalogPath(catalogid);
		if (StringUtils.isEmpty(catalogPath))
			return "";

		String separator = System.getProperty("file.separator");
		catalogPath = catalogPath.replaceAll("/", separator + separator);
		return SystemPath.getSysPath() + catalogPath;
	}

	/**
	 * 获取当前站点的栏目列表
	 * @return
	 */
	public List<ContentCatEntity> catalogList(String siteid) {
		List<ContentCatEntity> catalogList = new ArrayList<ContentCatEntity>();
		catalogList = findByProperty(ContentCatEntity.class, "siteid", siteid);
		return catalogList;
	}
	
	

}
