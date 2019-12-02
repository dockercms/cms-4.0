package com.leimingtech.cms.tag.lmtag;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.leimingtech.cms.service.tag.IBelowTagMng;
import com.leimingtech.core.common.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 新闻下一篇标签
 * @author wangyu
 *
 */
@Component
public class BelowTag extends BaseFreeMarkerTag{
	@Autowired
	private IBelowTagMng BelowTagMng;
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		String cid = (String) params.get("cid");
		return BelowTagMng.getBelowTag(cid);
	}

	

	

	

}
