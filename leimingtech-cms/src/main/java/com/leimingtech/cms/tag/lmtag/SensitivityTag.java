package com.leimingtech.cms.tag.lmtag;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.leimingtech.cms.entity.sensitivity.SensitivityEntity;
import com.leimingtech.cms.service.SensitivityServiceI;
import com.leimingtech.core.common.BaseFreeMarkerTag;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;

import freemarker.template.TemplateModelException;

/**
 * 幻灯片标签
 * 
 * @author liuzhen 2014年4月9日 14:26:28
 * 
 */
@Component
@Scope("prototype")
public class SensitivityTag extends BaseFreeMarkerTag {

	private final static Integer PAGESIZE = 5;//每页条数

	@Autowired
	private SensitivityServiceI sensitivityService;

	@Override
	protected Object exec(Map params) throws TemplateModelException {
//		String id = (String) params.get("id");
//		if (StringUtil.isEmpty(id)) {
//			throw new TemplateModelException("标签id参数不能为空");
//		}
		CriteriaQuery cq = new CriteriaQuery(SensitivityEntity.class);
		cq.add();
		List<SensitivityEntity> list = sensitivityService.getListByCriteriaQuery(cq, false);
		return list;
	}

}
