package com.leimingtech.cms.tag.lmtag;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.core.common.BaseFreeMarkerTag;
import com.leimingtech.core.service.IContentTagMng;

import freemarker.template.TemplateModelException;

/**
 * 置顶文档标签
 * 
 * @author liuzhen 2014年4月25日 16:37:19
 * 
 */
@Component
public class ArticleTopTag extends BaseFreeMarkerTag {

	@Autowired
	private IContentTagMng articleMng;

	@Override
	protected Object exec(Map params) throws TemplateModelException {
		return articleMng.getContentByTagTop(params);
	}

}
