package com.leimingtech.cms.controller.video;
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

import com.leimingtech.cms.controller.contents.ContentClassify;
import com.leimingtech.cms.controller.contents.ContentsbaseController;
import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentTagEntity;
import com.leimingtech.core.entity.ContentVideoEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.ModelExtEntity;
import com.leimingtech.core.entity.ModelItemEntity;
import com.leimingtech.core.entity.ModelManageEntity;
import com.leimingtech.core.entity.RelateContentEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.SourceEntity;
import com.leimingtech.core.entity.VideoalburmarticleEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ConstantsServiceI;
import com.leimingtech.core.service.ContentTagServiceI;
import com.leimingtech.core.service.ContentVideoServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.ModelItemServiceI;
import com.leimingtech.core.service.ModelManageServiceI;
import com.leimingtech.core.service.SourceServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.VideoalburmarticleServiceI;
import com.leimingtech.core.service.modelext.ModelExtServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;

/**   
 * @Title: Controller
 * @Description: 视频
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-28 14:07:11
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/contentVideoController")
public class ContentVideoController extends ContentsbaseController {

	@Autowired
	private ContentVideoServiceI contentVideoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ContentsServiceI contentsService;
	@Autowired
	private ModelManageServiceI modelManageService;
	@Autowired
	private ModelExtServiceI modelExtService;
	@Autowired
	private ModelItemServiceI modelItemService;
	@Autowired
	private ConstantsServiceI constantsService;
	@Autowired
	private VideoalburmarticleServiceI videoalburmarticleService;
	@Autowired
	private ContentTagServiceI contentTagService;
	@Autowired
	private SourceServiceI sourceService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 视频列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(ContentVideoEntity contentVideo, HttpServletRequest request) {
		//获取视频列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(ContentVideoEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		contentVideo.setSiteid(site.getId());
		HqlGenerateUtil.installHql(cq, contentVideo, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.contentVideoService.getPageList(cq, true);
		List<ContentVideoEntity> testList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "contentVideoController.do?table");
		return new ModelAndView("cms/video/contentVideoList", m);
	}

	/**
	 * 视频添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		String contentCatId = reqeust.getParameter("contentCatId");
		String modelsId = reqeust.getParameter("modelsId");
		//当前毫秒数
		String temporary = String.valueOf(System.currentTimeMillis());
		//区分添加/编辑页面
		String flag = "add";
		ContentCatEntity contentCat = contentsService.get(ContentCatEntity.class, String.valueOf(contentCatId));
		ModelManageEntity modelManage = null;
		if(StringUtils.isNotEmpty(contentCat.getJsonid())){
			modelManage = modelManageService.getEntity(contentCat.getJsonid());
		}
		List<ModelItemEntity> modelItemList = new ArrayList<ModelItemEntity>();
		if(modelManage!=null){
			modelItemList = modelItemService.findByModelId(modelManage.getId());
		}
		List<ContentTagEntity> contentTagList = contentTagService.loadAll(ContentTagEntity.class);  //获取Tags标签内容
		List<SourceEntity> sourceEntityList = sourceService.loadAll(SourceEntity.class);  //获取来源内容
		String sourceStr = "";
		for (int i = 0; i < sourceEntityList.size(); i++) {
			sourceStr += sourceEntityList.get(i).getName()+",";
		}
		ContentsEntity contents = new ContentsEntity();
		//设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
		contents.setClassify(ContentClassify.CONTENT_VIDEO);
		Map<String, Object> m = new HashMap<String, Object>();
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(sourceStr)){
			m.put("sourceStr", sourceStr.substring(0, sourceStr.length()-1));
		}else{
			m.put("sourceStr", CmsConstants.DEFAULT_SOURCE);
		}
		SiteEntity site =PlatFormUtil.getSessionSite();
		m.put("site", site);
		m.put("page", new ContentVideoEntity());
		m.put("contents", contents);
		m.put("contentCat", contentCat);
		m.put("modelsId", modelsId);
		m.put("modelItemList", modelItemList);
		m.put("modelManage", modelManage);
		m.put("flag", flag);
		m.put("contentTagList", contentTagList);
		m.put("sourceEntityList", sourceEntityList);
		m.put("markpeople", PlatFormUtil.getSessionUser().getUserName());
		m.put("temporary", temporary);
		m.put("classify", ContentClassify.CONTENT_VIDEO);
		m.put("sessionId", reqeust.getSession().getId());
		m.put("memberinfo", PlatFormUtil.getSessionUser());
		return new ModelAndView("cms/video/contentVideo_open", m);
	}
	
	/**
	 * 视频更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		ContentVideoEntity contentVideo = contentVideoService.getEntity(ContentVideoEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", contentVideo);
		m.put("memberinfo", PlatFormUtil.getSessionUser());
		return new ModelAndView("cms/video/contentVideo", m);
	}

	/**
	 * 视频保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(ContentVideoEntity contentVideo,ContentsEntity contents,ModelItemEntity modelItem, HttpServletRequest request) {
		//顺序值
		String divValue = request.getParameter("divValue");
		String[] ulValue = divValue.split(",");
		String sourceName=contents.getSourceid();  //来源名称
		if(StringUtils.isNotEmpty(sourceName)){
			contents.setSourceid(sourceName.substring(0, sourceName.length()-1));
		}
		//获取当前毫秒数
		String temporary = request.getParameter("temporary");
		//栏目id
		String contentCatId = request.getParameter("contentCatId");
		//模型模块id
		String modelsId = request.getParameter("modelsId");
		//扩展字段id
		String modelId = request.getParameter("modelId");
		//获取内容id
		String contentsId = request.getParameter("contentsId");
		//同时发布的栏目id
		String funVal0 = request.getParameter("funVal0");
		//获取参数名称
		List<ModelItemEntity> modelItemList = new ArrayList<ModelItemEntity>();
		if(modelId!=null&&!modelId.equals("")){
			modelItemList = modelItemService.findByModelId(modelId);
		}
		ContentCatEntity contentcat=contentVideoService.getEntity(ContentCatEntity.class, String.valueOf(contentCatId));
		JSONObject j = new JSONObject();
		if (StringUtils.isNotEmpty(contentVideo.getId())) {
			message = "视频["+contentVideo.getVideoname()+"]更新成功";
			ContentVideoEntity t = contentVideoService.get(ContentVideoEntity.class, contentVideo.getId());
			ContentsEntity c = contentsService.get(ContentsEntity.class, String.valueOf(contentsId));
			contents.setId(null);
			try {
				MyBeanUtils.copyBeanNotNull2Bean(contents, c);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//获取自定义字段/值
			String sql = "select modelext.id from cms_model_ext modelext where modelext.model_id="+modelId+" and content_id="+c.getId();
			List<ModelExtEntity> modelExtList = new ArrayList<ModelExtEntity>();
			if(modelId!=null&&!modelId.equals("")){
				List<Map<String, Object>> mapList = contentVideoService.findForJdbc(sql);
				if(mapList.size()>0){
					Map<String, Object> map;
					for(int k=0;k<mapList.size();k++){
						String modelextid = "";
						map = mapList.get(k);
						modelextid = map.get("id")+"";
						ModelExtEntity modelExt1 = modelExtService.getEntity(modelextid);
						modelExtList.add(modelExt1);
					}
				}
			}
			try {
				//保存参数名称和value
				for(int i=0;i<modelExtList.size();i++){
					ModelExtEntity modelExt = modelExtList.get(i);
					String name = request.getParameter("name"+i);//label
					String input = request.getParameter(i+"");//input
					if(name.equals(modelExt.getAttrName())){
						modelExt.setAttrValue(input);
						modelExtService.saveOrUpdate(modelExt);
					}
				}
				MyBeanUtils.copyBeanNotNull2Bean(contentVideo, t);
				c.setModified(new Date());//修改时间
				c.setModifiedby(PlatFormUtil.getSessionUser().getUserName());//修改人
				contentsService.saveOrUpdate(c);
				
				contentVideoService.saveOrUpdate(t);
				//更新相关内容表，新增的相关项
				List<RelateContentEntity> relateContentList = contentVideoService.findByProperty(RelateContentEntity.class, "contentId", temporary);
				for(RelateContentEntity relateContent:relateContentList){
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
					contentVideoService.saveOrUpdate(relateContent);
				}
				//已添加的相关项，用于修改排序
				if(!ulValue[0].equals("")){
					List<RelateContentEntity> relateContentListT = contentVideoService.findByProperty(RelateContentEntity.class, "contentId", String.valueOf(c.getId()));
					for(RelateContentEntity relateContent:relateContentListT){
							for(int i=0;i<ulValue.length;i++){
								String[] ul = ulValue[i].split("_");
								if(ul[1].equals(String.valueOf(relateContent.getId()))){
									relateContent.setRelateOrder(Integer.valueOf(ul[0]));
								}
							}
							contentVideoService.saveOrUpdate(relateContent);
					}
				}
				
				// 保存视频专辑
				if(StringUtils.isNotEmpty(contentVideo.getSpecial())){
					VideoalburmarticleEntity ablurmarticle = new VideoalburmarticleEntity();
					ablurmarticle = videoalburmarticleService.getVideoAlburmArticleByContentId(c.getId());
					ablurmarticle.setAlburmid(String.valueOf(contentVideo.getSpecial()));
					//保存视频专辑
					videoalburmarticleService.saveOrUpdateAlburmArticle(ablurmarticle);
				}
				//进入判断工作流和状态
				constantsService.isWorkFlow(c, contentcat);
				
				j.accumulate("contentsid", c.getId());
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "视频["+contentVideo.getVideoname()+"]更新失败";
			}
		} else {
			message = "视频["+contentVideo.getVideoname()+"]添加成功";
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			contents.setSiteid(site.getId());
			contents.setPathids(contentcat.getPathids());
			contents.setCatid(contentcat.getId());
			contents.setModelid(String.valueOf(modelsId));
			//设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
			contents.setClassify(ContentClassify.CONTENT_VIDEO);
			//当前人id
			String userId = "",userName = "";
			if(PlatFormUtil.getSessionUser()!=null){
				userId= PlatFormUtil.getSessionUser().getId();
				userName = PlatFormUtil.getSessionUser().getUserName();
			}
			contents.setCreated(new Date());//创建时间
			contents.setCreatedby(userName);//创建人
			contents.setPublishedbyid(userId);//发布人id
			contents.setSynCatid(funVal0);//同时发布id
			contents.setPv(0);
			contentsService.save(contents);
			
			//在modelExt中保存modelId/attrName
			if(modelId!=null&&!modelId.equals("")){
				for(int i=0;i<modelItemList.size();i++){
					modelItem=modelItemList.get(i);
					String name = request.getParameter("name"+i);//label
					String input = request.getParameter(i+"");//input
					if(name.equals(modelItem.getItemLabel())){
						ModelExtEntity modelExt = new ModelExtEntity();
						modelExt.setAttrName(modelItem.getItemLabel());
						modelExt.setModelId(String.valueOf(modelId));
						modelExt.setContentId(contents.getId());
						modelExt.setModelItemId(modelItem.getId());
						modelExt.setAttrToken(modelItem.getField());
						modelExt.setDataType(modelItem.getDataType());
						modelExt.setAttrValue(input);
						modelExt.setCreatedtime(new Date());//创建时间
						modelExtService.save(modelExt);
					}
				}
			}
			contentVideo.setContentid(contents.getId());
			contentVideo.setSiteid(site.getId());
			contentVideo.setCreatedtime(new Date());//创建时间
			contentVideoService.save(contentVideo);
			if(StringUtils.isNotEmpty(contentVideo.getSpecial())){
				VideoalburmarticleEntity ablurmarticle = new VideoalburmarticleEntity();
				ablurmarticle.setArticleid(contents.getId());
				ablurmarticle.setArticleid(String.valueOf(contentVideo.getSpecial()));
				//保存视频专辑
				videoalburmarticleService.saveOrUpdateAlburmArticle(ablurmarticle);
			}
			//保存相关内容表
			List<RelateContentEntity> relateContentList = contentVideoService.findByProperty(RelateContentEntity.class, "contentId", temporary);
			for(RelateContentEntity relateContent:relateContentList){
				if(!ulValue[0].equals("")){
					for(int i=0;i<ulValue.length;i++){
						String[] ul = ulValue[i].split("_");
						if(ul[1].equals(String.valueOf(relateContent.getId()))){
							relateContent.setRelateOrder(Integer.valueOf(ul[0]));
						}
					}
				}
				relateContent.setContentId(String.valueOf(contents.getId()));
				relateContent.setRelateType(contents.getClassify());
				contentVideoService.saveOrUpdate(relateContent);
			}
			
			j.accumulate("contentsid", contents.getId());
			
			//进入判断工作流和状态
			constantsService.isWorkFlow(contents, contentcat);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentsController.do?table&tab=alone&contentCatId="+contentCatId);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 视频删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		ContentVideoEntity contentVideo = contentVideoService.getEntity(ContentVideoEntity.class, String.valueOf(id));
		message = "视频["+contentVideo.getVideoname()+"]删除成功";
		contentVideoService.delete(contentVideo);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentVideoController.do?table");
		String str = j.toString();
		return str;
	}
}
