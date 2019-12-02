package com.leimingtech.cms.tag.lmtag;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.cms.entity.friendlink.FriendLinkEntity;
import com.leimingtech.cms.service.FriendLinkServiceI;
import com.leimingtech.core.common.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 友情链接标签
 * 
 * @author liuzhen 2014年4月25日 14:37:41
 * 
 * <br/>
 *         <#assign friendLinkTag =newTag("friendLinkTag")> <br/>
 *         <#assign friendLinkTag
 *         =friendLinkTag("{'name':'品牌专区（图片链接）','count':'2'}")>
 */
@Component
public class FriendLinkTag extends BaseFreeMarkerTag {

	@Autowired
	private FriendLinkServiceI friendLinkService;

	@Override
	protected Object exec(Map params) throws TemplateModelException {
		List<FriendLinkEntity> friendLinkList = friendLinkService.getFriendLinkListByTag(params);
		return friendLinkList;
	}

}
