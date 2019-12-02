package com.leimingtech.mobile.controller.survey;
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

import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.ContentClassify;
import com.leimingtech.core.common.ContentMobileClassify;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.SurveyLogEntity;
import com.leimingtech.core.entity.SurveyMobileEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ContentsMobileServiceI;
import com.leimingtech.core.service.IStatic;
import com.leimingtech.core.service.StaticMobileHtmlServiceI;
import com.leimingtech.core.service.SurveyMobileServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.mobile.controller.contents.ContentsMobilebaseController;

/**   
 * @Title: Controller
 * @Description: 移动调查
 * @author zhangxiaoqiang
 * @date 2014年7月1日18:27:39
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/surveyMobileController")
public class SurveyMobileController extends ContentsMobilebaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SurveyMobileController.class);

	@Autowired
	private SurveyMobileServiceI surveyMobileService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private StaticMobileHtmlServiceI staticMobileImpl;
	@Autowired
	private IStatic staticImpl;
	@Autowired
	private ContentsMobileServiceI contentsMobileService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 调查添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(ContentsEntity contents,HttpServletRequest request){
		String mobileChannelId = request.getParameter("mobileChannelId");
		String contentId = request.getParameter("contentId");
		String classify = ContentMobileClassify.CONTENT_SURVEY;
		MobileChannelEntity mobileChannel = surveyMobileService.get(MobileChannelEntity.class,  mobileChannelId) ;
		//获取路径前缀
		String conpath = ContextHolderUtils.getRequest().getContextPath();
		if(StringUtil.isNotEmpty(contentId)){
			contents = surveyMobileService.get(ContentsEntity.class,  contentId );
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new SurveyMobileEntity());
		m.put("contentsMobile", new ContentsMobileEntity());
		m.put("contents", contents);
		m.put("mobileChannel", mobileChannel);
		m.put("markpeople", markpeople());
		m.put("classify", classify);
		m.put("conpath", conpath);
		m.put("sessionId", request.getSession().getId());
		m.put("specialids", request.getParameter("specialids"));
		m.put("memberinfo", PlatFormUtil.getSessionUser());
		return new ModelAndView("mobile/survey/survey", m);
	}
	

	/**
	 * 调查保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(SurveyMobileEntity surveyMobile,ContentsMobileEntity contentsMobile,HttpServletRequest request) {
		//频道id
		String mobileChannelId = request.getParameter("mobileChannelId");
		//获取内容id
		String contentsMobileId = request.getParameter("contentsMobileId");
		//调查PCId
		String surveyId = request.getParameter("surveyId");
		//选择的专题
		String specialids = request.getParameter("specialids");
		JSONObject j = new JSONObject();
		MobileChannelEntity mobileChannel = surveyMobileService.getEntity(MobileChannelEntity.class, mobileChannelId );
		if (StringUtil.isNotEmpty(surveyMobile.getId())) {
			SurveyMobileEntity t = surveyMobileService.get(SurveyMobileEntity.class, surveyMobile.getId());
			ContentsMobileEntity c = surveyMobileService.get(ContentsMobileEntity.class,  contentsMobileId) ;
			contentsMobile.setId(null);
			try {
				MyBeanUtils.copyBeanNotNull2Bean(contentsMobile, c);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				MyBeanUtils.copyBeanNotNull2Bean(surveyMobile, t);
				c.setModified(new Date());//修改时间
				c.setModifiedby(markpeople());//修改人
				surveyMobileService.saveOrUpdate(c);
				surveyMobileService.saveOrUpdate(t);
				try {
					staticMobileImpl.staticMobileArticle(PlatFormUtil.getSessionSite(), c, mobileChannel);
				} catch (Exception e) {
					e.printStackTrace();
					LogUtil.error(c.getTitle()+"生成页面失败");
				}
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
				//保存专题
				if(StringUtil.isNotEmpty(specialids)){
					contentsMobileService.saveOrDel(contentsMobile.getRelevanceid().toString(),specialids);
				}
				message = "调查["+c.getTitle()+"]更新成功";
			} catch (Exception e) {
				e.printStackTrace();
				message = "调查["+c.getTitle()+"]更新失败";
			}
		} else {
			message = "调查["+contentsMobile.getTitle()+"]添加成功";
			contentsMobile.setPathids(mobileChannel.getPathids());
			
			contentsMobile.setCatid( mobileChannelId );
			//设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
			contentsMobile.setClassify(ContentClassify.CONTENT_SURVEY);
			//当前人id
			String userId = "";
			if(PlatFormUtil.getSessionUser()!=null){
				userId= PlatFormUtil.getSessionUser().getId();
			}
			contentsMobile.setCreated(new Date());//创建时间
			contentsMobile.setCreatedby(markpeople());//创建人
			contentsMobile.setPublishedbyid(userId);//发布人id
			contentsMobile.setPublished(new Date());//发布时间
			SiteEntity site=PlatFormUtil.getSessionSite();
			contentsMobile.setSiteid(site.getId()+"");
			surveyMobileService.save(contentsMobile);
			
			//进入工作流，判断是否需要审核，如果需要设为待审，如果不需要审核设为已审
			enterworkflow(contentsMobile,  mobileChannelId );
			
			surveyMobile.setContentid(contentsMobile.getId() );
			surveyMobile.setSurveyid( surveyId );
			surveyMobile.setSiteid(site.getId()+"");
			surveyMobile.setCreatedtime(new Date());//创建时间
			surveyMobileService.save(surveyMobile);
			try {
				staticMobileImpl.staticMobileArticle(PlatFormUtil.getSessionSite(), contentsMobile, mobileChannel);
			} catch (Exception e) {
				e.printStackTrace();
				LogUtil.error(contentsMobile.getTitle() + "生成页面失败");
			}
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
			//保存专题
			if(StringUtil.isNotEmpty(specialids)){
				contentsMobileService.saveOrDel(contentsMobile.getRelevanceid().toString(),specialids);
			}
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentsMobileController.do?table&tab=alone&mobileChannelId="+mobileChannelId);
		String str = j.toString();
		return str;
	}
	/**
	 * 查看记录
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "surveyLog")
	public ModelAndView surveyLog(HttpServletRequest request){
		String surveyId = request.getParameter("surveyId");
		//获取调查日志列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(SurveyLogEntity.class, pageSize, pageNo, "", "");
		cq.eq("surveyid",  surveyId );
		cq.addOrder("created",SortDirection.desc );
		cq.add();
		PageList pageList = this.surveyMobileService.getPageList(cq, true);
		List<SurveyLogEntity> resultList = pageList.getResultList();
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("pageList", resultList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "surveyMobileController.do?surveyLog&surveyId="+surveyId);
		return new ModelAndView("mobile/survey/surveyLogList", m);
	}
}
