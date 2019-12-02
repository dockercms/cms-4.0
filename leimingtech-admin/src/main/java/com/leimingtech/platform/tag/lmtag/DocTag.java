package com.leimingtech.platform.tag.lmtag;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.core.common.BaseFreeMarkerTag;
import com.leimingtech.platform.service.doc.DocServiceI;

import freemarker.template.TemplateModelException;

/**
 * 文档标签
 * 
 * @author liuzhen 2014年6月11日 17:47:50
 * 
 */
@Component
public class DocTag extends BaseFreeMarkerTag {
	
	@Autowired
	private DocServiceI docService;

	@Override
	protected Object exec(Map params) throws TemplateModelException {
		return  docService.getDocListByTag(params);
	}

}
