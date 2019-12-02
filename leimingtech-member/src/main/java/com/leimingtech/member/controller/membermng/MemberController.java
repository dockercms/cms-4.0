package com.leimingtech.member.controller.membermng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.leimingtech.core.entity.member.MemberEntity;
import net.sf.json.JSONObject;

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
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.member.entity.membermng.MemberGroupEntity;


/**
 * @Title: Controller
 * @Description: 前台用户
 * @author
 * @date 2014-05-20 11:42:02
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/memberController")
public class MemberController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MemberController.class);

	@Autowired
	private MemberServiceI memberService;
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
	 * 字典类型列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "member")
	public ModelAndView member(MemberEntity member, HttpServletRequest request) {
		// 获取分组列表
		int pageSize = 10;
		String pageNoStr = request.getParameter("pageNo");
		int pageNo = 1;
		if (pageNoStr != null && !"".equals(pageNoStr) && pageNoStr.matches("[0-9]+")) {
			pageNo = Integer.valueOf(pageNoStr);
		} else {
			pageNo = 1;
		}

		CriteriaQuery cq = new CriteriaQuery(MemberEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, member, param);
		
		// 排序条件
		cq.addOrder("createtime",SortDirection.desc );
		cq.add();
		PageList pageList = memberService.getPageList(cq, true);
		List<MemberEntity> memberList = pageList.getResultList();
		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		List<MemberGroupEntity> memberlvList = memberService.getList(MemberGroupEntity.class);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("memberList", memberList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("memberlvList", memberlvList);
		m.put("actionUrl", "memberController.do?member");
		return new ModelAndView("member/membermng/memberList", m);
	}

	/**
	 * 跳转添加或修改类型页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addOrUpdate")
	public ModelAndView addOrUpdateType(HttpServletRequest request) {
		String id = request.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		MemberEntity member = null;
		List<MemberGroupEntity> userGroupList = new ArrayList<MemberGroupEntity>();
		if (StringUtil.isNotEmpty(id)) {
			userGroupList = memberService.getList(MemberGroupEntity.class);
			member = memberService.getEntity(MemberEntity.class, String.valueOf(id));
		} else {
			userGroupList = memberService.getList(MemberGroupEntity.class);
			member = new MemberEntity();
		}
		m.put("member", member);
		m.put("userGroupList", userGroupList);
		return new ModelAndView("member/membermng/member", m);
	}

	/**
	 * 字典类型保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(MemberEntity member, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		
		String lv=member.getMembergroupsid();
		if(lv!=null){
			MemberGroupEntity group=memberService.getEntity(MemberGroupEntity.class, lv);
			if(group!=null){
				member.setMemberlevel(group.getUsergroupsname());
			}
		}else{
			List<MemberGroupEntity> gList=memberService.findByProperty(MemberGroupEntity.class, "defalutLv", 1);
			if(gList!=null&&gList.size()>0){
				member.setMemberlevel(gList.get(0).getUsergroupsname());
			}
		}
		if (StringUtil.isNotEmpty(member.getId())) {
			message = "前台用户["+member.getUsername()+"]更新成功";
			MemberEntity t = memberService.get(MemberEntity.class, member.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(member, t);
				memberService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "前台用户["+member.getUsername()+"]更新失败";
			}
		} else {
			message = "前台用户["+member.getUsername()+"]添加成功";
			member.setCreatetime(new Date());//创建时间
			memberService.save(member);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "memberController.do?member");
		String str = j.toString();
		return str;
	}

	/**
	 * 字典类型删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(MemberEntity member, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		member = memberService.getEntity(MemberEntity.class, member.getId());
		message = "前台用户["+member.getUsername()+"]删除成功";
		memberService.delete(member);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "memberController.do?member");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 替换会员卡图
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(params = "replace")
	@ResponseBody
	public void replace() throws IOException {
		JSONObject j = new JSONObject();
		List<MemberEntity> memberlist = memberService.loadAll(MemberEntity.class);
		for (MemberEntity value : memberlist) {
			memberService.createMemberCard(value);
		}
		j.accumulate("toUrl", "memberController.do?replace");
	}
}
