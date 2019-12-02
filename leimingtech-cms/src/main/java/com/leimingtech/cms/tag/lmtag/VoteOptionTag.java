package com.leimingtech.cms.tag.lmtag;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.cms.service.tag.IVoteTagMng;
import com.leimingtech.core.common.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 投票选项标签
 * 
 * @author liuzhen 2014-5-23 10:53:38
 * 
 */
@Component
public class VoteOptionTag extends BaseFreeMarkerTag {
	@Autowired
	private IVoteTagMng voteMng;// 投票标签接口

	@Override
	protected Object exec(Map params) throws TemplateModelException {
		return voteMng.getVoteOption(params);
	}

}
