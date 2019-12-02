package com.leimingtech.mobile.controller.link;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.common.ContentClassify;
import com.leimingtech.core.common.ContentMobileClassify;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.ContentLinkMobileEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.service.ContentLinkMobileServiceI;
import com.leimingtech.core.service.ContentsMobileServiceI;
import com.leimingtech.core.service.IStatic;
import com.leimingtech.core.service.StaticMobileHtmlServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.mobile.controller.contents.ContentsMobilebaseController;

/**   
 * @Title: Controller
 * @Description: 移动链接
 * @author zhangxiaoqiang
 * @date 2014年7月1日16:21:10
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/contentLinkMobileController")
public class ContentLinkMobileController extends ContentsMobilebaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ContentLinkMobileController.class);

	@Autowired
	private ContentLinkMobileServiceI contentLinkMobileService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private StaticMobileHtmlServiceI staticMobileImpl;
	@Autowired
	private ContentsMobileServiceI contentsMobileService;
	
	@Autowired
	private IStatic staticImpl;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 移动链接添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(ContentsEntity contents,HttpServletRequest reqeust){
		String mobileChannelId= reqeust.getParameter("mobileChannelId");
		String classify = ContentMobileClassify.CONTENT_LINK;
		String contentId = reqeust.getParameter("contentId");
		//区分添加/编辑页面
		String flag = "add";
		MobileChannelEntity mobileChannel = contentLinkMobileService.get(MobileChannelEntity.class,  mobileChannelId );
		if(StringUtil.isNotEmpty(contentId)){
			contents = contentLinkMobileService.get(ContentsEntity.class,  contentId );
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new ContentLinkMobileEntity());
		m.put("contentsMobile", new ContentsMobileEntity());
		m.put("classify", classify);
		m.put("contents", contents);
		m.put("mobileChannel", mobileChannel);
		m.put("markpeople", markpeople());
		m.put("flag", flag);
		m.put("sessionId", reqeust.getSession().getId());
		m.put("memberinfo", PlatFormUtil.getSessionUser());
		m.put("specialids", reqeust.getParameter("specialids"));
		return new ModelAndView("mobile/link/contentLink", m);
	}
	

	/**
	 * 移动链接保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(ContentLinkMobileEntity contentLink,ContentsMobileEntity contentsMobile,HttpServletRequest request) {
		//频道id
		String mobileChannelId = request.getParameter("mobileChannelId");
		//获取内容id
		String contentsMobileId = request.getParameter("contentsMobileId");
		//选择的专题
		String specialids = request.getParameter("specialids");
		MobileChannelEntity mobileChannel = contentLinkMobileService.getEntity(MobileChannelEntity.class, mobileChannelId );
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(contentLink.getId())) {
			message = "链接["+contentLink.getLinkremark()+"]更新成功";
			ContentLinkMobileEntity t = contentLinkMobileService.get(ContentLinkMobileEntity.class, contentLink.getId());
			ContentsMobileEntity c = contentLinkMobileService.get(ContentsMobileEntity.class,  contentsMobileId );
			contentsMobile.setId(null);
			try {
				MyBeanUtils.copyBeanNotNull2Bean(contentsMobile, c);
				MyBeanUtils.copyBeanNotNull2Bean(contentLink, t);
				c.setModified(new Date());//修改时间
				c.setModifiedby(markpeople());//修改人
				c.setUrl(t.getLinkurl());
				
				contentLinkMobileService.saveOrUpdate(c);
//				staticImpl.staticArticleActOnPreview(c);
				contentLinkMobileService.saveOrUpdate(t);
				//发布静态页
				staticMobileImpl.staticMobileArticle(PlatFormUtil.getSessionSite(), c, mobileChannel);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
				//保存专题
				if(StringUtil.isNotEmpty(specialids)){
					contentsMobileService.saveOrDel(contentsMobile.getRelevanceid().toString(),specialids);
				}
			} catch (Exception e) {
				e.printStackTrace();
				message = "链接["+contentLink.getLinkremark()+"]更新失败";
			}
		} else {
			try {
				message = "链接["+contentLink.getLinkremark()+"]添加成功";
				contentsMobile.setPathids(mobileChannel.getPathids());
				
				contentsMobile.setCatid( mobileChannelId );
				//设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
				contentsMobile.setClassify(ContentClassify.CONTENT_LINK);
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
				
				contentsMobile.setUrl(contentLink.getLinkurl());
				contentLinkMobileService.save(contentsMobile);
                //staticImpl.staticArticleActOnPreview(contents);
				//进入工作流，判断是否需要审核，如果需要设为待审，如果不需要审核设为已审
				enterworkflow(contentsMobile, mobileChannelId );
				contentLink.setContentid(contentsMobile.getId());
				contentLink.setSiteid(site.getId()+"");
				contentLink.setCreatedtime(new Date());//创建时间
				contentLinkMobileService.save(contentLink);
				//发布静态页
				staticMobileImpl.staticMobileArticle(site, contentsMobile, mobileChannel);
				systemService.addLog(message, Globals.Log_Leavel_INFO , Globals.Log_Type_INSERT );
				//保存专题
				if(StringUtil.isNotEmpty(specialids)){
					contentsMobileService.saveOrDel(contentsMobile.getRelevanceid().toString(),specialids);
				}
			} catch (Exception e) {
				e.printStackTrace(); 
			}
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentsMobileController.do?table&tab=alone&mobileChannelId="+mobileChannelId);
		String str = j.toString();
		return str;
	}
}
