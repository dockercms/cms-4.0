package com.leimingtech.wap.waptag;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.core.common.BaseFreeMarkerTag;
import com.leimingtech.core.service.ContentsServiceI;

import freemarker.template.TemplateModelException;

/**
 * wap模板中内容列表标签
 * 
 * @author liuzhen
 * 
 */
@Component
public class WapContentTag extends BaseFreeMarkerTag {

	@Autowired
	private ContentsServiceI contentsService;// 内容管理接口

	@Override
	protected Object exec(Map params) throws TemplateModelException {
		return contentsService.getContentByTag(params);
	}

}
