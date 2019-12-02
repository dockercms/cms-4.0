package com.leimingtech.cms.tag.lmtag;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.cms.entity.advertising.AdvertisingEntity;
import com.leimingtech.cms.service.advertising.AdvertisingServiceI;
import com.leimingtech.core.common.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 广告标签
 * 
 * @author liuzhen 2014年4月24日 17:23:59
 * 
 * <br/>
 *         <#assign advTag =newTag("advTag")><br/>
 *         <#assign advTag =advTag("{'name':'页头banner3','count':'5'}")>
 */
@Component
public class AdvTag extends BaseFreeMarkerTag {

	@Autowired
	private AdvertisingServiceI advMng;// 广告管理

	@Override
	protected Object exec(Map params) throws TemplateModelException {
		List<AdvertisingEntity> advList = advMng.getAdvListByTag(params);// 通过参数获取广告列表
		return advList;
	}

}
