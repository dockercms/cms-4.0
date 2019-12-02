package com.leimingtech.cms.tag.lmtag;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.cms.service.tag.IFriendLinkCtgTagMng;
import com.leimingtech.core.common.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 友情链接分类标签
 * 
 * @author liuzhen 2014年4月25日 16:37:19
 * 
 */
@Component
public class FriendLinkCtgTag extends BaseFreeMarkerTag {

	@Autowired
	private IFriendLinkCtgTagMng friendLinkCtgTagMng;

	@Override
	protected   Object exec(Map params) throws TemplateModelException {
		
		
		return friendLinkCtgTagMng.getFriendLinkCtg(params);
	}

}
