package com.leimingtech.cms.controller.businessconsulting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.leimingtech.core.entity.BusinessConsultingEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.BusinessConsultingServiceI;
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
@RequestMapping("/front/businessFrontController")
public class BusinessFrontController extends BaseController {

	private String message;
	/** 业务咨询接口 */
	@Autowired
	private BusinessConsultingServiceI businessConsultingService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	@Autowired
	private CommonService commonService;
	
	
		/**
		 * 前台 业务咨询
		 * 
		 * @return
		 */
		@RequestMapping(params = "businessList")
		public ModelAndView businessList(HttpServletRequest reqeust){
					
			//获取留言表列表
					
					int pageSize = StringUtils.isEmpty(reqeust.getParameter("pageSize")) ? 20 : Integer.valueOf(reqeust.getParameter("pageSize"));
					int pageNo = StringUtils.isEmpty(reqeust.getParameter("pageNo")) ? 1 : Integer.valueOf(reqeust.getParameter("pageNo"));
					
					CriteriaQuery cq = new CriteriaQuery(BusinessConsultingEntity.class, pageSize, pageNo, "", "");	
					//排序条件
					cq.addOrder("createtime",SortDirection.desc);
	
					cq.eq("replyStatus", "1");
					cq.eq("siteid", PlatFormUtil.getFrontSessionSiteId());
					cq.add();
					List<BusinessConsultingEntity> businessList = commonService
							.getListByCriteriaQuery(cq, false);
					PageList pageList = commonService.getPageList(cq, true);
					List<BusinessConsultingEntity> resultList = pageList.getResultList();
					
					int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
					if(pageCount <= 0){
						pageCount = 1;
					}
					Map<String, Object> m = new HashMap<String, Object>();
					SiteEntity site = PlatFormUtil.getFrontSessionSite();
					String sitePath = site.getSitePath();
					List<TSType> departList = TSTypegroup.allTypes.get("depart");// 部门列表
					List<TSType> classList = TSTypegroup.allTypes.get("business_class");// 业务类别
					m.put("pageList", resultList);
					m.put("pageNo", pageList.getCurPageNO());
					m.put("pageCount", pageCount);
					m.put("pageSize", pageSize);
					m.put("sitePath", sitePath);
					m.put("site", site);
					m.put("departList", departList);
					m.put("classList", classList);
					m.put("countNum", businessList.size());
					m.put("searchUrl", "businessFrontController.do?businessList");
					return new ModelAndView("wwwroot/" + sitePath + "/template/hudongjiaoliu/yewuzixun", m);
		}
		
		/**
		 * 业务咨询保存
		 * 
		 * @return
		 */
		@RequestMapping(params = "saveBusiness")
		@ResponseBody
		public String saveBusiness(BusinessConsultingEntity businessConsulting,HttpServletRequest request) {
		
				
				JSONObject j = new JSONObject();
				HttpSession session = ContextHolderUtils.getSession();
				String randCode = session.getAttribute("randCode").toString();// 验证码
				String code = request.getParameter("valicode");
				
				if(!randCode.equalsIgnoreCase(code)){
					message = "验证码错误!";
				}else{
					message = "添加成功";
					businessConsulting.setMessageTime(new Date());	
					businessConsulting.setCreatetime(new Date());//创建时间
					businessConsulting.setReplyStatus("0");
					SiteEntity site = PlatFormUtil.getFrontSessionSite();
					businessConsulting.setSiteid(site.getId());
					businessConsultingService.save(businessConsulting);
					systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
					j.accumulate("isSuccess", true);
				}
				j.accumulate("msg", message);
				String str = j.toString();
				return str;
		}
		
		
		@RequestMapping(params = "hdjlhfzx")
		@ResponseBody
		public String hdjlhfzx(HttpServletRequest request) {
			
			CriteriaQuery cq = new CriteriaQuery(BusinessConsultingEntity.class);	
			//排序条件
			cq.addOrder("createtime",SortDirection.desc);

			cq.eq("replyStatus", "1");
			cq.eq("siteid", PlatFormUtil.getFrontSessionSiteId());
			cq.add();
			List<BusinessConsultingEntity> businessList = commonService
					.getListByCriteriaQuery(cq, false);
			List<String> zxtimeList = new ArrayList<String>();
			String zxdate="";
			for (BusinessConsultingEntity business : businessList) {
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				zxdate=df.format(business.getMessageTime());
				zxtimeList.add(zxdate);
			}
			JSONObject j = new JSONObject();
			j.accumulate("businessList", businessList);
			j.accumulate("zxtimeList", zxtimeList);
			String str = j.toString();
			return str ;
		}
		
		
		/**
		 * 前台业务咨询详情
		 * 
		 * @return
		 */
		@RequestMapping(params = "businessDetail")
		public ModelAndView businessDetail(HttpServletRequest reqeust){
					
			//获取留言表列表
					
					
					String id = reqeust.getParameter("businessId"); //留言id
					
					BusinessConsultingEntity businessconsulting = businessConsultingService.getEntity(id);
					
					Map<String, Object> m = new HashMap<String, Object>();
					SiteEntity site = PlatFormUtil.getFrontSessionSite();
					String sitePath = site.getSitePath();
					List<TSType> departList = TSTypegroup.allTypes.get("depart");// 部门列表
					List<TSType> classList = TSTypegroup.allTypes.get("business_class");// 业务类别
					m.put("sitePath", sitePath);
					m.put("site", site);
					m.put("departList", departList);
					m.put("classList", classList);
					m.put("businessconsulting", businessconsulting);
			
					return new ModelAndView("wwwroot/" + sitePath + "/template/hudongjiaoliu/yewuzixun_detail", m);
		}
		
		
		
		
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
