package com.leimingtech.cms.controller.applypublic;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ApplyPublicEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ApplyPublicServiceI;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.PlatFormUtil;

/**   
 * @Title: Controller
 * @Description: 业务咨询
 * @author 
 * @date 2016-03-31 14:27:11
 * @version V1.0   
 *
 */

@Controller
@RequestMapping("/front/applyPublicFrontController")
public class ApplyPublicFrontController extends BaseController {

	private String message;
	/** 业务咨询接口 */
	@Autowired
	private ApplyPublicServiceI applyPublicService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	@Autowired
	private CommonService commonService;
	
	
		/**
		 * 前台 申请公开列表
		 * 
		 * @return
		 */
		@RequestMapping(params = "applyPublicList")
		public ModelAndView applyPublicList(HttpServletRequest reqeust){
					
			//申请公开列表
					
					int pageSize = StringUtils.isEmpty(reqeust.getParameter("pageSize")) ? 20 : Integer.valueOf(reqeust.getParameter("pageSize"));
					int pageNo = StringUtils.isEmpty(reqeust.getParameter("pageNo")) ? 1 : Integer.valueOf(reqeust.getParameter("pageNo"));
					
					CriteriaQuery cq = new CriteriaQuery(ApplyPublicEntity.class, pageSize, pageNo, "", "");	
					//排序条件
					cq.addOrder("createtime",SortDirection.desc);
					cq.eq("siteid", PlatFormUtil.getFrontSessionSiteId());
					cq.add();
					List<ApplyPublicEntity> applyPublicList = commonService
							.getListByCriteriaQuery(cq, false);
					PageList pageList = commonService.getPageList(cq, true);
					List<ApplyPublicEntity> resultList = pageList.getResultList();
					
					int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
					if(pageCount <= 0){
						pageCount = 1;
					}
					Map<String, Object> m = new HashMap<String, Object>();
					SiteEntity site = PlatFormUtil.getFrontSessionSite();
					String sitePath = site.getSitePath();
					List<TSType> statusList = TSTypegroup.allTypes.get("application_status");// 申请状态
					m.put("statusList", statusList);
					m.put("pageList", resultList);
					m.put("pageNo", pageList.getCurPageNO());
					m.put("pageCount", pageCount);
					m.put("pageSize", pageSize);
					m.put("sitePath", sitePath);
					m.put("site", site);
					
					m.put("countNum", applyPublicList.size());
					m.put("searchUrl", "applyPublicFrontController.do?applyPublicList");
					return new ModelAndView("wwwroot/" + sitePath + "/template/zhengwugongkai/shenqinggongkai_list", m);
		}
		
		/**
		 * 前台申请公开报存
		 * 
		 * @return
		 */
		@RequestMapping(params = "saveApplyPublic")
		@ResponseBody
		public String saveApplyPublic(ApplyPublicEntity applyPublic,HttpServletRequest request) {
		
				
					JSONObject j = new JSONObject();
					HttpSession session = ContextHolderUtils.getSession();
					message = "添加成功";
					applyPublic.setCreatetime(new Date());	
					applyPublic.setIsstat("0");//默认为未申请
					
					InetAddress ia=null;
					
					try {
						ia=ia.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
						
						
					String localip=ia.getHostAddress();
					
					SiteEntity site = PlatFormUtil.getFrontSessionSite();
					applyPublic.setSiteid(site.getId());
					applyPublic.setPublisherIp(localip);
					applyPublicService.save(applyPublic);
					systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
					j.accumulate("isSuccess", true);
				
				j.accumulate("msg", message);
				String str = j.toString();
				return str;
		}
		
	
	
		/**
		 * 前台申请公开详情
		 */
		
		@RequestMapping(params = "ApplyPublicDetail")
		public ModelAndView ApplyPublicDetail(HttpServletRequest reqeust){
				
					Map<String, Object> m = new HashMap<String, Object>();
					String id=reqeust.getParameter("id");
					ApplyPublicEntity applyPublic=applyPublicService.getEntity(id);
					SiteEntity site = PlatFormUtil.getFrontSessionSite();
					String sitePath = site.getSitePath();
					List<TSType> carrierList = TSTypegroup.allTypes.get("carrier_form");// 载体形式
					List<TSType> informationList = TSTypegroup.allTypes.get("information_mode");// 信息方式
					List<TSType> statusList = TSTypegroup.allTypes.get("application_status");// 申请状态
					m.put("statusList", statusList);
					m.put("carrierList", carrierList);
					m.put("informationList", informationList);
					m.put("sitePath", sitePath);
					m.put("site", site);
					m.put("applyPublic", applyPublic);
				
					return new ModelAndView("wwwroot/" + sitePath + "/template/zhengwugongkai/shenqinggongkai_detail", m);
		}
		
		
		/**
		 * 前台申请公开添加页面
		 * 
		 * @return
		 */
		@RequestMapping(params = "addApplyPublic")
		public ModelAndView addApplyPublic(HttpServletRequest reqeust){
				
					Map<String, Object> m = new HashMap<String, Object>();
					SiteEntity site = PlatFormUtil.getFrontSessionSite();
					String sitePath = site.getSitePath();
					List<TSType> carrierList = TSTypegroup.allTypes.get("carrier_form");// 载体形式
					List<TSType> informationList = TSTypegroup.allTypes.get("information_mode");// 信息方式
					Date sysdate = new Date();
					m.put("carrierList", carrierList);
					m.put("informationList", informationList);
					m.put("sitePath", sitePath);
					m.put("site", site);
					m.put("sysdate", sysdate);
					return new ModelAndView("wwwroot/" + sitePath + "/template/zhengwugongkai/shenqinggongkai_edit", m);
		}
		
		
		
		
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
