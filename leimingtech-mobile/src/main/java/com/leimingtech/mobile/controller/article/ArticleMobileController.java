package com.leimingtech.mobile.controller.article;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.common.ContentMobileClassify;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.ArticleMobileEntity;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.ContentTagEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.SourceEntity;
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.service.ArticleMobileServiceI;
import com.leimingtech.core.service.ContentTagServiceI;
import com.leimingtech.core.service.ContentsMobileServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.MobileChannelServiceI;
import com.leimingtech.core.service.SourceServiceI;
import com.leimingtech.core.service.StaticMobileHtmlServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.TypegroupServiceI;
import com.leimingtech.core.service.WorkFlowServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.mobile.controller.contents.ContentsMobilebaseController;
import com.leimingtech.mobile.entity.relatecontent.RelateContentMobileEntity;
import com.leimingtech.mobile.service.relatecontent.RelateContentMobileServiceI;


/**   
 * @Title: Controller
 * @Description: 移动文章
 * @author zhangxiaoqiang
 * @date 2014年7月1日16:19:14
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/articleMobileController")
public class ArticleMobileController extends ContentsMobilebaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ArticleMobileController.class);

	@Autowired
	private WorkFlowServiceI workFlowService;
	@Autowired
	private ArticleMobileServiceI articleMobileService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ContentsMobileServiceI contentsMobileService;
	@Autowired
	private MobileChannelServiceI mobileChannelService;
	@Autowired
	private StaticMobileHtmlServiceI staticMobileImpl;
	@Autowired
	private ContentsServiceI contentsService;
	/**移动相关文章管理接口*/
	@Autowired
	private RelateContentMobileServiceI relateContentMobileService;
	/**文章标签管理接口*/
	@Autowired
	private ContentTagServiceI contentTagService;
	/**来源管理接口*/
	@Autowired
	private SourceServiceI sourceService;
	/**数据字典分类管理接口*/
	@Autowired
	private TypegroupServiceI typegroupService;
	private String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 移动文章添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(ContentsEntity contents,HttpServletRequest reqeust){
//		String contentCatId = reqeust.getParameter("contentCatId");
		String mobileChannelId = reqeust.getParameter("mobileChannelId");
		String contentId = reqeust.getParameter("contentId");
		//当前毫秒数
		String temporary = String.valueOf(System.currentTimeMillis());
		String classify = ContentMobileClassify.CONTENT_ARTICLE;
		if(StringUtil.isNotEmpty(contentId)){
			contents = contentsService.get(ContentsEntity.class,  contentId );
		}
		MobileChannelEntity mobileChannel = mobileChannelService.getEntity(mobileChannelId);
		List<ContentTagEntity> contentTagList = contentTagService.loadAll(ContentTagEntity.class);  //获取Tags标签内容
		List<SourceEntity> sourceEntityList = sourceService.loadAll(SourceEntity.class);  //获取来源内容
		//从数据字典中获取内容标记
		TSTypegroup typeGroup = typegroupService.findByTypegroupCode("content_mark");
		List<TSType> typeList = typeGroup.getTSTypes();
		Map<String, Object> m = new HashMap<String, Object>();
		String sourceStr = "";
		for (int i = 0; i < sourceEntityList.size(); i++) {
			sourceStr += sourceEntityList.get(i).getName()+",";
		}
		if (StringUtils.isNotEmpty(sourceStr)) {
			m.put("sourceStr", sourceStr.substring(0, sourceStr.length() - 1));
		} else {
			m.put("sourceStr", CmsConstants.DEFAULT_SOURCE);
		}
		m.put("page", new ArticleMobileEntity());
		m.put("contentsMobile", new ContentsMobileEntity());
		m.put("contents", contents);
		m.put("mobileChannel", mobileChannel);
		m.put("classify", classify);
		m.put("typeList", typeList);
		m.put("contentTagList", contentTagList);
		m.put("sourceEntityList", sourceEntityList);
		m.put("markpeople", markpeople());
		m.put("temporary", temporary);
		m.put("sessionId", reqeust.getSession().getId());
		m.put("specialids", reqeust.getParameter("specialids"));
		m.put("memberinfo", PlatFormUtil.getSessionUser());
		return new ModelAndView("mobile/article/article", m);
	}

	/**
	 * 移动文章保存
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(ArticleMobileEntity article,ContentsMobileEntity contentsMobile,HttpServletRequest request) throws Exception {
		//顺序值
		String divValue = request.getParameter("divValue");
		String[] ulValue = divValue.split(",");
		String sourceName=contentsMobile.getSourceid();  //来源名称
		if(StringUtil.isNotEmpty(sourceName)){
			contentsMobile.setSourceid(sourceName.substring(0, sourceName.length()));
		}
		//频道id
		String mobileChannelId = request.getParameter("mobileChannelId");
		//获取内容id
		String contentsMobileId = request.getParameter("contentsMobileId");
		//获取当前毫秒数
		String temporary = request.getParameter("temporary");
		//选择的专题
		String specialids = request.getParameter("specialids");
		MobileChannelEntity mobileChannel = mobileChannelService.getEntity(mobileChannelId );
		JSONObject j = new JSONObject();
		
		if (StringUtil.isNotEmpty(article.getId())) {
			message = "更新成功";
			ArticleMobileEntity t = articleMobileService.get(ArticleMobileEntity.class, article.getId());
			ContentsMobileEntity c = contentsMobileService.getEntity(contentsMobileId );
			contentsMobile.setId(null);
			String logInfo = "移动内容【" + c.getTitle() + "】";
			try {
				MyBeanUtils.copyBeanNotNull2Bean(contentsMobile, c);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				MyBeanUtils.copyBeanNotNull2Bean(article, t);
				
				c.setModified(new Date());//修改时间
				c.setModifiedby(markpeople());//修改人
				contentsMobileService.saveOrUpdate(c);
//				staticImpl.staticArticleActOnPreview(c);
				String con = StringEscapeUtils.unescapeHtml4(t.getContent().trim());
				t.setContent(con);
				articleMobileService.saveOrUpdate(t);
				//更新相关内容表，新增的相关项
				List<RelateContentMobileEntity> relateContentList = relateContentMobileService.getListByContentID(temporary);
				for(RelateContentMobileEntity relateContent:relateContentList){
					if(!ulValue[0].equals("")){
						for(int i=0;i<ulValue.length;i++){
							String[] ul = ulValue[i].split("_");
							if(ul[1].equals(relateContent.getId())){
								relateContent.setRelateOrder(Integer.valueOf(ul[0]));
							}
						}
					}
					relateContent.setContentId(String.valueOf(c.getId()));
					relateContent.setRelateType(c.getClassify());
					relateContentMobileService.saveOrUpdate(relateContent);
				}
				//已添加的相关项，用于修改排序
				if(!ulValue[0].equals("")){
					List<RelateContentMobileEntity> relateContentListT = relateContentMobileService.getListByContentID(c.getId());
					for(RelateContentMobileEntity relateContent:relateContentListT){
							for(int i=0;i<ulValue.length;i++){
								String[] ul = ulValue[i].split("_");
								if(ul[1].equals(relateContent.getId())){
									relateContent.setRelateOrder(Integer.valueOf(ul[0]));
								}
							}
							relateContentMobileService.saveOrUpdate(relateContent);
					}
				}
				//发布静态页
				staticMobileImpl.staticMobileArticle(PlatFormUtil.getSessionSite(), c, mobileChannel);
				systemService.addLog(logInfo+message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
				//生成二维码
				contentsMobileService.encode(c.getId()+"");
				//保存专题
				if(StringUtil.isNotEmpty(specialids)){
					contentsMobileService.saveOrDel(contentsMobile.getRelevanceid().toString(),specialids);
				}
			} catch (Exception e) {
				e.printStackTrace();
				message = "更新失败";
			}
		} else {
			message = "添加成功";
			String logInfo = "移动内容【" + contentsMobile.getTitle() + "】";
			contentsMobile.setPathids(mobileChannel.getPathids());
			
			contentsMobile.setCatid( mobileChannelId );
			//设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
			contentsMobile.setClassify(ContentMobileClassify.CONTENT_ARTICLE);
			//当前人id
			String userId = "";
			if(PlatFormUtil.getSessionUser()!=null){
				userId= PlatFormUtil.getSessionUser().getId();
			}
			contentsMobile.setCreated(new Date());//创建时间
			contentsMobile.setCreatedby(markpeople());//创建人
			contentsMobile.setPublishedbyid(userId);//发布人id
			contentsMobile.setPublished(new Date());//发布时间
			contentsMobile.setSiteid(PlatFormUtil.getSessionSiteId()+"");
			contentsMobileService.save(contentsMobile);
//			staticImpl.staticArticleActOnPreview(contents);
			//进入工作流，判断是否需要审核，如果需要设为待审，如果不需要审核设为已审
			enterworkflow(contentsMobile,  mobileChannelId );
			article.setContentid(contentsMobile.getId()+"");
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			article.setSiteid(site.getId()+"");
			article.setCreatedtime(new Date());
			articleMobileService.save(article);
			//保存相关内容表
			List<RelateContentMobileEntity> relateContentList = relateContentMobileService.getListByContentID(temporary);
			for(RelateContentMobileEntity relateContent:relateContentList){
				if(!ulValue[0].equals("")){
					for(int i=0;i<ulValue.length;i++){
						String[] ul = ulValue[i].split("_");
						if(ul[1].equals(relateContent.getId())){
							relateContent.setRelateOrder(Integer.valueOf(ul[0]));
						}
					}
				}
				relateContent.setContentId(String.valueOf(contentsMobile.getId()));
				relateContent.setRelateType(contentsMobile.getClassify());
				relateContentMobileService.saveOrUpdate(relateContent);
			}
			//发布静态页
			staticMobileImpl.staticMobileArticle(site, contentsMobile, mobileChannel);
			systemService.addLog(logInfo+message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
			//生成二维码
			contentsMobileService.encode(contentsMobile.getId()+"");
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
	@ResponseBody
	@RequestMapping(params = "publishArticle")
	public void publishArticle(HttpServletRequest request) throws Exception{
		String contentsId = request.getParameter("contentsId");

		ContentsMobileEntity contentsMobile = contentsMobileService.getEntity(contentsId);

		MobileChannelEntity mobileChannel = mobileChannelService.getEntity(contentsMobile.getCatid());
		
		//发布静态页
		staticMobileImpl.staticMobileArticle(PlatFormUtil.getSessionSite(), contentsMobile, mobileChannel);
	}
}
