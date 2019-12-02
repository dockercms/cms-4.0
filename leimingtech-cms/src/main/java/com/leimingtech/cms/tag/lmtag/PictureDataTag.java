package com.leimingtech.cms.tag.lmtag;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.cms.service.tag.IPictureGroupTagMng;
import com.leimingtech.core.common.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 组图数据标签
 * 
 * @author liuzhen 2014年5月26日 18:42:35
 * 
 */
@Component
public class PictureDataTag extends BaseFreeMarkerTag {

	@Autowired
	private IPictureGroupTagMng picMng;

	@Override
	protected Object exec(Map params) throws TemplateModelException {
		return picMng.getPictureDataByTag(params);
	}

}
