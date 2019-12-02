package com.leimingtech.cms.tag.lmtag;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.core.common.BaseFreeMarkerTag;
import com.leimingtech.core.entity.SectionDataEntity;
import com.leimingtech.core.service.SectionDataServiceI;

import freemarker.template.TemplateModelException;

/**
 * 区块数据标签
 * 
 * @author liuzhen 2014年5月6日 09:34:02
 * 
 */
@Component
public class SectionDataTag extends BaseFreeMarkerTag {

	@Autowired
	private SectionDataServiceI sectionDataMng;// 区块数据管理

	@Override
	protected Object exec(Map params) throws TemplateModelException {
		List<SectionDataEntity> advList = sectionDataMng.getSectionDataListByTag(params);// 通过参数获取区块数据列表
		return advList;
	}

}
