package com.leimingtech.cms.tag.lmtag;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.cms.service.tag.ICatalogTagMng;
import com.leimingtech.core.common.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 栏目标签
 * 
 * @author liuzhen 2014年4月25日 16:07:52
 * 
 */
@Component
public class CatalogTag extends BaseFreeMarkerTag {

	@Autowired
	private ICatalogTagMng catalogMng;

	@Override
	protected Object exec(Map params) throws TemplateModelException {
		return catalogMng.getCatalogByTag(params);
	}

}
