package com.leimingtech.cms.controller.picturegroup;
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

import com.leimingtech.cms.controller.contents.ContentsbaseController;
import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.common.ContentClassify;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentTagEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.ModelExtEntity;
import com.leimingtech.core.entity.ModelItemEntity;
import com.leimingtech.core.entity.ModelManageEntity;
import com.leimingtech.core.entity.PictureAloneEntity;
import com.leimingtech.core.entity.PictureGroupEntity;
import com.leimingtech.core.entity.RelateContentEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.SourceEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ConstantsServiceI;
import com.leimingtech.core.service.ContentTagServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.ModelItemServiceI;
import com.leimingtech.core.service.ModelManageServiceI;
import com.leimingtech.core.service.PictureGroupServiceI;
import com.leimingtech.core.service.SourceServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.modelext.ModelExtServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;

/**   
 * @Title: Controller
 * @Description: 组图
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-23 16:13:10
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/pictureGroupController")
public class PictureGroupController extends ContentsbaseController {

	@Autowired
	private PictureGroupServiceI pictureGroupService;
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
	 * 组图列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(PictureGroupEntity pictureGroup, HttpServletRequest request) {
		//获取组图列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(PictureGroupEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		pictureGroup.setSiteid(site.getId());
		HqlGenerateUtil.installHql(cq, pictureGroup, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.pictureGroupService.getPageList(cq, true);
		List<PictureGroupEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "pictureGroupController.do?table");
		return new ModelAndView("cms/picturegroup/pictureGroupList", m);
	}

	/**
	 * 组图添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		String contentCatId = reqeust.getParameter("contentCatId");
		String modelsId = reqeust.getParameter("modelsId");
		//临时给单个图片id赋值为当前毫秒数
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
		//来源
		String sourceStr = "";
		for (int i = 0; i < sourceEntityList.size(); i++) {
			sourceStr += sourceEntityList.get(i).getName()+",";
		}
		ContentsEntity contents = new ContentsEntity();
		//设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
		contents.setClassify(ContentClassify.CONTENT_PICTUREGROUP);
		Map<String, Object> m = new HashMap<String, Object>();
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(sourceStr)){
			m.put("sourceStr", sourceStr.substring(0, sourceStr.length()-1));
		}else{
			m.put("sourceStr", CmsConstants.DEFAULT_SOURCE);
		}
		SiteEntity site =PlatFormUtil.getSessionSite();
		m.put("site", site);
		m.put("page", new PictureGroupEntity());
		m.put("contents", contents);
		m.put("contentCat", contentCat);
		m.put("modelsId", modelsId);
		m.put("modelItemList", modelItemList);
		m.put("modelManage", modelManage);
		m.put("temporary", temporary);
		m.put("flag", flag);
		m.put("contentTagList", contentTagList);
		m.put("sourceEntityList", sourceEntityList);
		m.put("markpeople", PlatFormUtil.getSessionUser().getUserName());
		m.put("classify", ContentClassify.CONTENT_PICTUREGROUP);
		m.put("sessionId", reqeust.getSession().getId());
		m.put("memberinfo", PlatFormUtil.getSessionUser());
		return new ModelAndView("cms/picturegroup/pictureGroup_open", m);
	}
	
	/**
	 * 组图更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		PictureGroupEntity pictureGroup = pictureGroupService.getEntity(PictureGroupEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", pictureGroup);
		m.put("memberinfo", PlatFormUtil.getSessionUser());
		return new ModelAndView("cms/picturegroup/pictureGroup", m);
	}

	/**
	 * 组图保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(PictureGroupEntity pictureGroup,ContentsEntity contents,ModelItemEntity modelItem, HttpServletRequest request) {
		String contentCatId = request.getParameter("contentCatId");
		//模型模块id
		String modelsId = request.getParameter("modelsId");
		//扩展字段id
		String modelId = request.getParameter("modelId");
		//获取内容id
		String contentsId = request.getParameter("contentsId");
		//毫秒数
		String temporary = request.getParameter("temporary");
		//顺序值
		String divValue = request.getParameter("divValue");
		//同时发布的栏目id
		String funVal0 = request.getParameter("funVal0");
		
		String[] ulValue = divValue.split(",");
		//来源名称
		String sourceName=contents.getSourceid();  
		if(StringUtils.isNotEmpty(sourceName)){
			contents.setSourceid(sourceName.substring(0, sourceName.length()-1));
		}
		//获取参数名称
		List<ModelItemEntity> modelItemList = new ArrayList<ModelItemEntity>();
		if(StringUtils.isNotEmpty(modelId)){
			modelItemList = modelItemService.findByModelId(modelId);
		}
		List<PictureAloneEntity> pictureAloneList = new ArrayList<PictureAloneEntity>();
		if(StringUtils.isNotEmpty(temporary)){
			pictureAloneList = pictureGroupService.findByProperty(PictureAloneEntity.class, "picturegroupid", temporary);
		}
		ContentCatEntity contentcat=pictureGroupService.getEntity(ContentCatEntity.class, String.valueOf(contentCatId));
		JSONObject j = new JSONObject();
		if (StringUtils.isNotEmpty(pictureGroup.getId())) {
			j.accumulate("contentsid", contentsId);
			message = "PC内容\t【组图\t"+contents.getTitle()+"】更新成功";
			PictureGroupEntity t = pictureGroupService.get(PictureGroupEntity.class, pictureGroup.getId());
			ContentsEntity c = contentsService.get(ContentsEntity.class,String.valueOf(contentsId));
			contents.setId(null);
			try {
				MyBeanUtils.copyBeanNotNull2Bean(contents, c);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//获取自定义字段/值
			List<ModelExtEntity> modelExtList = new ArrayList<ModelExtEntity>();
			if(StringUtils.isNotEmpty(modelId)){
				String sql = "select modelext.id from cms_model_ext modelext where modelext.model_id="+modelId+" and content_id="+c.getId();
				List<Map<String, Object>> mapList = pictureGroupService.findForJdbc(sql);
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
				MyBeanUtils.copyBeanNotNull2Bean(pictureGroup, t);
				c.setModified(new Date());//修改时间
				c.setModifiedby(PlatFormUtil.getSessionUser().getUserName());//修改人
				contentsService.saveOrUpdate(c);
				
				pictureGroupService.saveOrUpdate(t);
				for(PictureAloneEntity pictureAlone:pictureAloneList){
					pictureAlone.setPicturegroupid(String.valueOf(t.getId()));
					pictureGroupService.saveOrUpdate(pictureAlone);
				}
				//更新相关内容表，新增的相关项
				List<RelateContentEntity> relateContentList = pictureGroupService.findByProperty(RelateContentEntity.class, "contentId", temporary);
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
					pictureGroupService.saveOrUpdate(relateContent);
				}
				//已添加的相关项，用于修改排序
				if(!ulValue[0].equals("")){
					List<RelateContentEntity> relateContentListT = pictureGroupService.findByProperty(RelateContentEntity.class, "contentId", String.valueOf(c.getId()));
					for(RelateContentEntity relateContent:relateContentListT){
							for(int i=0;i<ulValue.length;i++){
								String[] ul = ulValue[i].split("_");
								if(ul[1].equals(String.valueOf(relateContent.getId()))){
									relateContent.setRelateOrder(Integer.valueOf(ul[0]));
								}
							}
							pictureGroupService.saveOrUpdate(relateContent);
					}
				}
				//进入判断工作流和状态
				constantsService.isWorkFlow(c, contentcat);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "组图"+contents.getTitle()+"更新失败";
			}
		} else {
			message = "PC内容\t【组图\t"+contents.getTitle()+"】添加成功";
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			contents.setSiteid(site.getId());
			contents.setPathids(contentcat.getPathids());
			contents.setCatid(contentcat.getId());
			contents.setModelid(String.valueOf(modelsId));
			//设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
			contents.setClassify(ContentClassify.CONTENT_PICTUREGROUP);
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
			contentsService.save(contents);
			j.accumulate("contentsid", contents.getId());
			
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
			pictureGroup.setContentid(contents.getId());
			
			pictureGroup.setSiteid(site.getId());
			pictureGroup.setCreatedtime(new Date());//创建时间
			pictureGroupService.save(pictureGroup);
			
			for(PictureAloneEntity pictureAlone:pictureAloneList){
				pictureAlone.setPicturegroupid(String.valueOf(pictureGroup.getId()));
				pictureGroupService.saveOrUpdate(pictureAlone);
			}
			//保存相关内容表
			List<RelateContentEntity> relateContentList = pictureGroupService.findByProperty(RelateContentEntity.class, "contentId", temporary);
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
				pictureGroupService.saveOrUpdate(relateContent);
			}
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
	 * 组图删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		PictureGroupEntity pictureGroup = pictureGroupService.getEntity(PictureGroupEntity.class, String.valueOf(id));
		message = "【组图"+pictureGroup.getImage()+"】删除成功";
		pictureGroupService.delete(pictureGroup);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "pictureGroupController.do?table");
		String str = j.toString();
		return str;
	}
}
