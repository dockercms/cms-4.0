package com.leimingtech.mobile.controller.picturegroup;
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
import com.leimingtech.core.entity.ContentTagEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.entity.PictureAloneEntity;
import com.leimingtech.core.entity.PictureAloneMobileEntity;
import com.leimingtech.core.entity.PictureGroupMobileEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.SourceEntity;
import com.leimingtech.core.service.ContentsMobileServiceI;
import com.leimingtech.core.service.IStatic;
import com.leimingtech.core.service.PictureGroupMobileServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.mobile.controller.contents.ContentsMobilebaseController;
import com.leimingtech.mobile.entity.pictureclassify.MobilePictureClassifyEntity;

/**   
 * @Title: Controller
 * @Description: 移动组图
 * @author zhangxiaoqiang
 * @date 2014年7月1日16:23:38
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/pictureGroupMobileController")
public class PictureGroupMobileController extends ContentsMobilebaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PictureGroupMobileController.class);

	@Autowired
	private PictureGroupMobileServiceI pictureGroupMobileService;
	@Autowired
	private SystemService systemService;
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
	 * 移动组图添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(ContentsEntity contents,HttpServletRequest reqeust){
		String mobileChannelId = reqeust.getParameter("mobileChannelId");
		String contentId = reqeust.getParameter("contentId");
		//临时给单个图片id赋值为当前毫秒数
		String temporary = String.valueOf(System.currentTimeMillis());
		String classify = ContentMobileClassify.CONTENT_PICTUREGROUP;
		MobileChannelEntity mobileChannel = pictureGroupMobileService.get(MobileChannelEntity.class,  mobileChannelId );
		List<ContentTagEntity> contentTagList = pictureGroupMobileService.loadAll(ContentTagEntity.class);  //获取Tags标签内容
		List<SourceEntity> sourceEntityList = pictureGroupMobileService.loadAll(SourceEntity.class);  //获取来源内容
		//来源
		String sourceStr = "";
		for (int i = 0; i < sourceEntityList.size(); i++) {
			sourceStr += sourceEntityList.get(i).getName()+",";
		}
		//全部图片分类
		List<MobilePictureClassifyEntity> pictureClassifyList = pictureGroupMobileService.loadAll(MobilePictureClassifyEntity.class);
		if(StringUtil.isNotEmpty(contentId)){
			contents = pictureGroupMobileService.get(ContentsEntity.class,  contentId );
		}
		Map<String, Object> m = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(sourceStr)) {
			m.put("sourceStr", sourceStr.substring(0, sourceStr.length() - 1));
		} else {
			m.put("sourceStr", CmsConstants.DEFAULT_SOURCE);
		}
		m.put("page", new PictureGroupMobileEntity());
		m.put("contentsMobile", new ContentsMobileEntity());
		m.put("contents", contents);
		m.put("mobileChannel", mobileChannel);
		m.put("temporary", temporary);
		m.put("classify", classify);
		m.put("contentTagList", contentTagList);
		m.put("sourceEntityList", sourceEntityList);
		m.put("markpeople", markpeople());
		m.put("sessionId", reqeust.getSession().getId());
		m.put("pictureClassifyList", pictureClassifyList);
		m.put("specialids", reqeust.getParameter("specialids"));
		m.put("memberinfo", PlatFormUtil.getSessionUser());
		return new ModelAndView("mobile/picturegroup/pictureGroup", m);
	}

	/**
	 * 移动组图保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(PictureGroupMobileEntity pictureGroup,ContentsMobileEntity contentsMobile,HttpServletRequest request) {
		//频道id
		String mobileChannelId = request.getParameter("mobileChannelId");
		//获取内容id
		String contentsMobileId = request.getParameter("contentsMobileId");
		//来源名称
		String sourceName=contentsMobile.getSourceid();  
		//选取的组图id
		String pictureId = request.getParameter("pictureId");
		//选择的专题
		String specialids = request.getParameter("specialids");
		if(StringUtil.isNotEmpty(sourceName)){
			contentsMobile.setSourceid(sourceName.substring(0, sourceName.length()));
		}
		List<PictureAloneEntity> pictureAloneList = pictureGroupMobileService.findByProperty(PictureAloneEntity.class, "picturegroupid", pictureId);
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(pictureGroup.getId())) {
			message = "移动内容\t【组图\t"+contentsMobile.getTitle()+"】更新成功";
			PictureGroupMobileEntity t = pictureGroupMobileService.get(PictureGroupMobileEntity.class, pictureGroup.getId());
			ContentsMobileEntity c = contentsMobileService.getEntity(contentsMobileId );
			contentsMobile.setId(null);
			try {
				MyBeanUtils.copyBeanNotNull2Bean(contentsMobile, c);
				MyBeanUtils.copyBeanNotNull2Bean(pictureGroup, t);
				c.setModified(new Date());//修改时间
				c.setModifiedby(markpeople());//修改人
				contentsMobileService.saveOrUpdate(c);
				//选取新组图前，删除掉之前已保存的数据
				List<PictureAloneMobileEntity> pictureAloneMobileList = pictureGroupMobileService.findByProperty(PictureAloneMobileEntity.class, "picturegroupid", String.valueOf(t.getId()));
				for(PictureAloneMobileEntity pictureAloneMobile:pictureAloneMobileList){
					pictureGroupMobileService.delete(pictureAloneMobile);
				}
				pictureGroupMobileService.saveOrUpdate(t);
				//保存最新选择的组图数据
				for(PictureAloneEntity pictureAlone:pictureAloneList){
					PictureAloneMobileEntity pictureAloneMobile = new PictureAloneMobileEntity();
					pictureAloneMobile.setPictureHeight(pictureAlone.getPictureHeight());
					pictureAloneMobile.setPictureMessage(pictureAlone.getPictureMessage());
					pictureAloneMobile.setPictureWidth(pictureAlone.getPictureWidth());
					pictureAloneMobile.setPictureUrl(pictureAlone.getPictureUrl());
					pictureAloneMobile.setPictureSort(pictureAlone.getPictureSort());
					pictureAloneMobile.setPicturegroupid(String.valueOf(t.getId()));
					pictureGroupMobileService.saveOrUpdate(pictureAloneMobile);
				}
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
				//保存专题
				if(StringUtil.isNotEmpty(specialids)){
					contentsMobileService.saveOrDel(contentsMobile.getRelevanceid().toString(),specialids);
				}
			} catch (Exception e) {
				e.printStackTrace();
				message = "组图更新失败";
			}
		} else {
			message = "移动内容\t【组图\t"+contentsMobile.getTitle()+"】添加成功";
			MobileChannelEntity mobileChannel = pictureGroupMobileService.getEntity(MobileChannelEntity.class, mobileChannelId );
			contentsMobile.setPathids(mobileChannel.getPathids());
			
			contentsMobile.setCatid( mobileChannelId );
			//设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
			contentsMobile.setClassify(ContentMobileClassify.CONTENT_PICTUREGROUP);
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
			contentsMobileService.save(contentsMobile);
//			staticImpl.staticArticleActOnPreview(contents);
			
			//进入工作流，判断是否需要审核，如果需要设为待审，如果不需要审核设为已审
			enterworkflow(contentsMobile, mobileChannelId );
			
			pictureGroup.setContentid(contentsMobile.getId());
			pictureGroup.setSiteid(site.getId()+"");
			pictureGroup.setCreatedtime(new Date());//创建时间
			pictureGroupMobileService.save(pictureGroup);
			
			for(PictureAloneEntity pictureAlone:pictureAloneList){
				PictureAloneMobileEntity pictureAloneMobile = new PictureAloneMobileEntity();
				pictureAloneMobile.setPictureHeight(pictureAlone.getPictureHeight());
				pictureAloneMobile.setPictureMessage(pictureAlone.getPictureMessage());
				pictureAloneMobile.setPictureWidth(pictureAlone.getPictureWidth());
				pictureAloneMobile.setPictureUrl(pictureAlone.getPictureUrl());
				pictureAloneMobile.setPictureSort(pictureAlone.getPictureSort());
				pictureAloneMobile.setPicturegroupid(String.valueOf(pictureGroup.getId()));
				pictureGroupMobileService.saveOrUpdate(pictureAloneMobile);
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
	
}
