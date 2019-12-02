package com.leimingtech.cms.controller.vote;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.service.tag.IVoteTagMng;
import com.leimingtech.core.controller.BaseController;

/**
 * 投票标签（提供前端页面js交互）
 * 
 * @author liuzhen 2014年5月22日 16:19:42
 * 
 */
@Controller
@RequestMapping("/front/voteTagAct")
public class VoteTagAction extends BaseController {

	@Autowired
	private IVoteTagMng voteMng;

	@RequestMapping(params = "getVote")
	public ModelAndView getVote(HttpServletRequest request, String contentid) {

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("contentid", contentid);
		return new ModelAndView("/cms/tag/vote", m);
	}
}
