package com.leimingtech.member.controller.membermng;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.member.entity.membermng.MemberGroupEntity;
import com.leimingtech.member.service.membermng.MemberGroupServiceI;
/**   
 * @Title: Controller
 * @Description: 前台用户组
 * @author 
 * @date 2014-05-20 11:42:06
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/memberGroupController")
public class MemberGroupController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MemberGroupController.class);

	@Autowired
	private MemberGroupServiceI memberGroupService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 前台用户组列表页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "memberGroup")
	public ModelAndView memberGroup(MemberGroupEntity memberGroup,HttpServletRequest request) {
		// 获取分组列表
//		int pageSize = 10;
//		String pageNoStr = request.getParameter("pageNo");
//		int pageNo = 1;
//		if (pageNoStr != null && !"".equals(pageNoStr) && pageNoStr.matches("[0-9]+")) {
//			pageNo = Integer.valueOf(pageNoStr);
//		} else {
//			pageNo = 1;
//		}
		//获取内容列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		CriteriaQuery cq = new CriteriaQuery(MemberGroupEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, memberGroup, param);
		// 排序条件
		cq.addOrder("createdtime",SortDirection.desc );

		cq.add();
		PageList pageList = memberGroupService.getPageList(cq, true);
		List<MemberGroupEntity> memberGroupList = pageList.getResultList();
		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("memberGroupList", memberGroupList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "memberGroupController.do?memberGroup");
		return new ModelAndView("member/membermng/memberGroupList",m);
	}

	/**
	 * 前台用户组添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addOrUpdate")
	public ModelAndView addOrUpdate(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		MemberGroupEntity memberGroup = null;
		if (StringUtil.isNotEmpty(id)) {
			memberGroup = memberGroupService.getEntity(MemberGroupEntity.class, String.valueOf(id));
		} else {
			memberGroup = new MemberGroupEntity();
		}
		m.put("memberGroup", memberGroup);
		return new ModelAndView("member/membermng/memberGroup", m);
	}
	
	/**
	 * 前台用户组保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(MemberGroupEntity memberGroup, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(memberGroup.getId())) {
			message = "前台用户组["+memberGroup.getUsergroupsname()+"]更新成功";
			MemberGroupEntity t = memberGroupService.get(MemberGroupEntity.class, memberGroup.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(memberGroup, t);
				memberGroupService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "前台用户组["+memberGroup.getUsergroupsname()+"]更新失败";
			}
		} else {
			message = "前台用户组["+memberGroup.getUsergroupsname()+"]添加成功";
			memberGroup.setCreatedtime(new Date());//创建时间
			memberGroupService.save(memberGroup);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "memberGroupController.do?memberGroup");
		String str = j.toString();
		return str;
	}

	/**
	 * 前台用户组删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(MemberGroupEntity memberGroup,HttpServletRequest request) {
		JSONObject j = new JSONObject();
		memberGroup = memberGroupService.getEntity(MemberGroupEntity.class, memberGroup.getId());
		message = "前台用户组["+memberGroup.getUsergroupsname()+"]删除成功";
		memberGroupService.delMain(memberGroup);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "memberGroupController.do?memberGroup");
		String str = j.toString();
		return str;
	}
}
