package com.leimingtech.cms.tag.lmtag;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.cms.service.tag.IVoteTagMng;
import com.leimingtech.core.common.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 投票标签
 * 
 * @author liuzhen 2014年5月21日 17:42:59
 * 
 */
@Component
public class VoteTag extends BaseFreeMarkerTag {

	@Autowired
	private IVoteTagMng voteMng;// 投票标签接口

	@Override
	protected Object exec(Map params) throws TemplateModelException {
		return voteMng.getVote(params);
	}

}
