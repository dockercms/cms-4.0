package com.leimingtech.cms.tag.lmtag;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.cms.service.tag.IActivityTagMng;
import com.leimingtech.core.common.BaseFreeMarkerTag;
import com.leimingtech.core.entity.ActivityEntity;

import freemarker.template.TemplateModelException;

/**
 * 活动标签
 * 
 * @author liuzhen 2014年4月25日 16:37:19
 * 
 */
@Component
public class ActivityTag extends BaseFreeMarkerTag {

	@Autowired
	private IActivityTagMng activityTagMng;

	@Override
	protected   ActivityEntity exec(Map params) throws TemplateModelException {
		
		String name = (String) params.get("contentId");
		return activityTagMng.getActivity(name);
	}

}
