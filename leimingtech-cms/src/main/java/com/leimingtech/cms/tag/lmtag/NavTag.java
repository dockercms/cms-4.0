package com.leimingtech.cms.tag.lmtag;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.cms.service.tag.INavTagMng;
import com.leimingtech.core.common.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 导航标签(navigation)
 * 
 * @author liuzhen 2014年5月19日 17:13:58
 * 
 */
@Component
public class NavTag extends BaseFreeMarkerTag {
	
	@Autowired
	private INavTagMng navTagMng;

	@Override
	protected Object exec(Map params) throws TemplateModelException {
		
		return navTagMng.getnavTag(params);
	}

}
