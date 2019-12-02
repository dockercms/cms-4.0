package com.leimingtech.mobile.controller.video;
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

import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.common.ContentMobileClassify;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.ContentVideoMobileEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.SourceEntity;
import com.leimingtech.core.service.ContentVideoMobileServiceI;
import com.leimingtech.core.service.ContentsMobileServiceI;
import com.leimingtech.core.service.StaticMobileHtmlServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.mobile.controller.contents.ContentsMobilebaseController;
import com.leimingtech.mobile.entity.videoclassify.MobileVideoClassifyEntity;

/**   
 * @Title: Controller
 * @Description: 移动视频
 * @author zhangxiaoqiang
 * @date 2014年7月1日16:24:27
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/contentVideoMobileController")
public class ContentVideoMobileController extends ContentsMobilebaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ContentVideoMobileController.class);

	@Autowired
	private ContentVideoMobileServiceI contentVideoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private StaticMobileHtmlServiceI staticMobileImpl;
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
	 * 移动视频添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(ContentsEntity contents,HttpServletRequest reqeust){
		String mobileChannelId = reqeust.getParameter("mobileChannelId");
		String contentId = reqeust.getParameter("contentId");
		String classify = ContentMobileClassify.CONTENT_VIDEO;
		MobileChannelEntity mobileChannel = contentVideoService.get(MobileChannelEntity.class,  mobileChannelId) ;
		List<SourceEntity> sourceEntityList = contentVideoService.loadAll(SourceEntity.class);  //获取来源内容
		String sourceStr = "";
		for (int i = 0; i < sourceEntityList.size(); i++) {
			sourceStr += sourceEntityList.get(i).getName()+",";
		}
		//全部视频分类
		List<MobileVideoClassifyEntity> videoClassifyList = contentVideoService.loadAll(MobileVideoClassifyEntity.class);
		Map<String, Object> m = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(sourceStr)) {
			m.put("sourceStr", sourceStr.substring(0, sourceStr.length() - 1));
		} else {
			m.put("sourceStr", CmsConstants.DEFAULT_SOURCE);
		}
		if(StringUtil.isNotEmpty(contentId)){
			contents = contentVideoService.get(ContentsEntity.class,  contentId) ;
		}
		m.put("page", new ContentVideoMobileEntity());
		m.put("contentsMobile", new ContentsMobileEntity());
		m.put("contents", contents);
		m.put("mobileChannel", mobileChannel);
		m.put("classify", classify);
		m.put("sourceEntityList", sourceEntityList);
		m.put("markpeople", markpeople());
		m.put("sessionId", reqeust.getSession().getId());
		m.put("videoClassifyList", videoClassifyList);
		m.put("specialids", reqeust.getParameter("specialids"));
		m.put("memberinfo", PlatFormUtil.getSessionUser());
		return new ModelAndView("mobile/video/contentVideo", m);
	}

	/**
	 * 移动视频保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(ContentVideoMobileEntity contentVideo,ContentsMobileEntity contentsMobile,HttpServletRequest request)throws Exception {
		String sourceName=contentsMobile.getSourceid();  //来源名称
		if(StringUtil.isNotEmpty(sourceName)){
			contentsMobile.setSourceid(sourceName.substring(0, sourceName.length()));
		}
		//频道id
		String mobileChannelId = request.getParameter("mobileChannelId");
		//获取内容id
		String contentsMobileId = request.getParameter("contentsMobileId");
		//选择的专题
		String specialids = request.getParameter("specialids");
		JSONObject j = new JSONObject();
		MobileChannelEntity mobileChannel = contentVideoService.getEntity(MobileChannelEntity.class, mobileChannelId );
		if (StringUtil.isNotEmpty(contentVideo.getId())) {
			message = "视频["+contentVideo.getVideoname()+"]更新成功";
			ContentVideoMobileEntity t = contentVideoService.get(ContentVideoMobileEntity.class, contentVideo.getId());
			ContentsMobileEntity c = contentVideoService.get(ContentsMobileEntity.class,  contentsMobileId );
			contentsMobile.setId(null);
			try {
				MyBeanUtils.copyBeanNotNull2Bean(contentsMobile, c);
				MyBeanUtils.copyBeanNotNull2Bean(contentVideo, t);
				c.setModified(new Date());//修改时间
				c.setModifiedby(markpeople());//修改人
				contentVideoService.saveOrUpdate(c);
//				staticImpl.staticArticleActOnPreview(c);
				contentVideoService.saveOrUpdate(t);
				//发布静态页
				staticMobileImpl.staticMobileArticle(PlatFormUtil.getSessionSite(), c, mobileChannel);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
				//保存专题
				if(StringUtil.isNotEmpty(specialids)){
					contentsMobileService.saveOrDel(contentsMobile.getRelevanceid().toString(),specialids);
				}
			} catch (Exception e) {
				e.printStackTrace();
				message = "视频["+contentVideo.getVideoname()+"]更新失败";
			}
		} else {
			message = "视频["+contentVideo.getVideoname()+"]添加成功";
			contentsMobile.setPathids(mobileChannel.getPathids());
			contentsMobile.setCatid( mobileChannelId );
			//设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
			contentsMobile.setClassify(ContentMobileClassify.CONTENT_VIDEO);
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
			contentVideoService.save(contentsMobile);
//			staticImpl.staticArticleActOnPreview(contents);
			//进入工作流，判断是否需要审核，如果需要设为待审，如果不需要审核设为已审
			enterworkflow(contentsMobile,  mobileChannelId );
			contentVideo.setContentid(contentsMobile.getId() );
			contentVideo.setSiteid(site.getId()+"");
			contentVideo.setCreatedtime(new Date());//创建时间
			contentVideoService.save(contentVideo);
			//发布静态页
			staticMobileImpl.staticMobileArticle(site, contentsMobile, mobileChannel);
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
}
