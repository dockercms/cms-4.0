package com.leimingtech.cms.tag.lmtag;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.leimingtech.cms.service.tag.IBackTagMng;
import com.leimingtech.core.common.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 新闻上一篇标签
 * @author wangyu
 *
 */
@Component
public class BackTag extends BaseFreeMarkerTag{
	@Autowired
	private IBackTagMng BackTagMng;
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		String cid = (String) params.get("cid");
		return BackTagMng.getBackTag(cid);
	}

	

	

	

}
