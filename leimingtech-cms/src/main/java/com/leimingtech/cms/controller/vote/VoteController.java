package com.leimingtech.cms.controller.vote;
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
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentTagEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.ModelExtEntity;
import com.leimingtech.core.entity.ModelItemEntity;
import com.leimingtech.core.entity.ModelManageEntity;
import com.leimingtech.core.entity.RelateContentEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.SourceEntity;
import com.leimingtech.core.entity.VoteEntity;
import com.leimingtech.core.entity.VoteOptionEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ConstantsServiceI;
import com.leimingtech.core.service.ContentTagServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.ModelItemServiceI;
import com.leimingtech.core.service.ModelManageServiceI;
import com.leimingtech.core.service.SourceServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.VoteOptionServiceI;
import com.leimingtech.core.service.VoteServiceI;
import com.leimingtech.core.service.modelext.ModelExtServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;

/**   
 * @Title: Controller
 * @Description: 投票
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-29 11:01:55
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/voteController")
public class VoteController extends ContentsbaseController {

	@Autowired
	private VoteServiceI voteService;
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
	private VoteOptionServiceI voteOptionService;
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
	 * 投票列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(VoteEntity vote, HttpServletRequest request) {
		//获取投票列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(VoteEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		vote.setSiteid(site.getId());
		HqlGenerateUtil.installHql(cq, vote, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.voteService.getPageList(cq, true);
		List<VoteEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "voteController.do?table");
		return new ModelAndView("cms/vote/voteList", m);
	}

	/**
	 * 选择投票
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "selectVote")
	public ModelAndView selsectVote(ContentsEntity contents, HttpServletRequest request) {
		//获取投票列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
				
		CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		contents.setSiteid(site.getId());
		HqlGenerateUtil.installHql(cq, contents, param);
		//排序条件
		cq.eq("modelid", "8");
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		PageList pageList = this.contentsService.getPageList(cq, true);
		List<ContentsEntity> testList = pageList.getResultList();
				
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
		m.put("actionUrl", "voteController.do?selectVote");
		return new ModelAndView("cms/vote/selectVoteList", m);
	}
	/**
	 * 选择已有投票
	 * 
	 * @return
	 */
	@RequestMapping(params = "selectHaveVote")
	public ModelAndView showDiv(HttpServletRequest request) {
		String checked = request.getParameter("checked");
		JSONObject j = new JSONObject();
		// 有选中项时
		if (StringUtils.isNotEmpty(checked)) {
			String[] checkArray = checked.split(",");
			for (int i = 0; i < checkArray.length; i++) {
				if (checkArray[i] != "") {
					//获取选择的投票
					VoteEntity vote = voteService
							.get(VoteEntity.class,
									String.valueOf(checkArray[i]));
				}
			}
		}
		
		Map<String, Object> m = new HashMap<String, Object>();
		
		return new ModelAndView("cms/article/dialog_attacharticleDiv", m);
	}
	/**
	 * 投票添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		String contentCatId = reqeust.getParameter("contentCatId");
		String modelsId = reqeust.getParameter("modelsId");
		//临时给投票选项中的投票id赋值为当前毫秒数
		String temporary = String.valueOf(System.currentTimeMillis());
		
		String optionVId = reqeust.getParameter("optionVId");
		List<VoteOptionEntity> voteOptionList = new ArrayList<VoteOptionEntity>();
		if(StringUtils.isNotEmpty(optionVId)){
			//获取投票选项
			voteOptionList = voteOptionService.findByProperty(VoteOptionEntity.class, "voteid", optionVId);
		}
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
		ContentsEntity contents = new ContentsEntity();
		//设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
		contents.setClassify(ContentClassify.CONTENT_VOTE);
		Map<String, Object> m = new HashMap<String, Object>();
		SiteEntity site =PlatFormUtil.getSessionSite();
		m.put("site", site);
		m.put("page", new VoteEntity());
		m.put("contents", contents);
		m.put("contentCat", contentCat);
		m.put("modelsId", modelsId);
		m.put("modelItemList", modelItemList);
		m.put("modelManage", modelManage);
		m.put("flag", flag);
		m.put("contentTagList", contentTagList);
		m.put("sourceEntityList", sourceEntityList);
		m.put("voteOptionList", voteOptionList);
		m.put("temporary", temporary);
		m.put("markpeople", PlatFormUtil.getSessionUser().getUserName());
		m.put("classify", ContentClassify.CONTENT_VOTE);
		m.put("sessionId", reqeust.getSession().getId());
		m.put("memberinfo", PlatFormUtil.getSessionUser());
		return new ModelAndView("cms/vote/vote_open", m);
	}
	/**
	 * 稿件详情投票添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addVote")
	public ModelAndView add(HttpServletRequest reqeust){
		String contentCatId = reqeust.getParameter("contentCatId");
		String modelsId = reqeust.getParameter("modelsId");
		//临时给投票选项中的投票id赋值为当前毫秒数
		String temporary = String.valueOf(System.currentTimeMillis());
		
		String optionVId = reqeust.getParameter("optionVId");
		List<VoteOptionEntity> voteOptionList = new ArrayList<VoteOptionEntity>();
		if(StringUtils.isNotEmpty(optionVId)){
			//获取投票选项
			voteOptionList = voteOptionService.findByProperty(VoteOptionEntity.class, "voteid", optionVId);
		}
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
		ContentsEntity contents = new ContentsEntity();
		//设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
		contents.setClassify(ContentClassify.CONTENT_VOTE);
		Map<String, Object> m = new HashMap<String, Object>();
		SiteEntity site =PlatFormUtil.getSessionSite();
		m.put("site", site);
		m.put("page", new VoteEntity());
		m.put("contents", contents);
		m.put("contentCat", contentCat);
		m.put("modelsId", modelsId);
		m.put("modelItemList", modelItemList);
		m.put("modelManage", modelManage);
		m.put("flag", flag);
		m.put("contentTagList", contentTagList);
		m.put("sourceEntityList", sourceEntityList);
		m.put("voteOptionList", voteOptionList);
		m.put("temporary", temporary);
		m.put("markpeople", PlatFormUtil.getSessionUser().getUserName());
		m.put("classify", ContentClassify.CONTENT_VOTE);
		m.put("sessionId", reqeust.getSession().getId());
		m.put("memberinfo", PlatFormUtil.getSessionUser());
		return new ModelAndView("cms/vote/contentVote", m);
	}
	/**
	 * 投票选项跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "jumpOption")
	public ModelAndView jumpOption(HttpServletRequest reqeust){
		//临时给投票选项中的投票id赋值为当前毫秒数
		String optionVId = reqeust.getParameter("optionVId");
		String voteId = reqeust.getParameter("voteId");
		
		List<VoteOptionEntity> voteOptionList = new ArrayList<VoteOptionEntity>();
		List<VoteOptionEntity> voteOptionListT = new ArrayList<VoteOptionEntity>();
		if(StringUtils.isNotEmpty(optionVId)){
			//获取投票选项
			voteOptionList = voteOptionService.findByProperty(VoteOptionEntity.class, "voteid", optionVId);
		}
		if(StringUtils.isNotEmpty(voteId)){
			voteOptionListT = voteOptionService.findByProperty(VoteOptionEntity.class, "voteid", voteId);
			for(VoteOptionEntity voteOption:voteOptionListT){
				voteOptionList.add(voteOption);
			}
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("voteOptionList", voteOptionList);
		m.put("temporary", optionVId);
		m.put("voteId", voteId);
		return new ModelAndView("cms/voteoption/voteOptionRefrech", m);
	}
	
	/**
	 * 投票更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		VoteEntity vote = voteService.getEntity(VoteEntity.class, String.valueOf(id));
		List<ContentTagEntity> contentTagList = contentTagService.loadAll(ContentTagEntity.class);  //获取Tags标签内容
		List<SourceEntity> sourceEntityList = sourceService.loadAll(SourceEntity.class);  //获取来源内容
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", vote);
		m.put("contentTagLists", contentTagList);
		m.put("sourceEntityLists", sourceEntityList);
		m.put("memberinfo", PlatFormUtil.getSessionUser());
		return new ModelAndView("cms/vote/vote", m);
	}
	
	/**
	 * 稿件详情投票保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "addSaveVote")
	@ResponseBody
	public String addSaveVote(VoteEntity vote,ContentsEntity contents, HttpServletRequest request) {
		//顺序值
		String sourceName=contents.getSourceid();  //来源名称
		if(StringUtils.isNotEmpty(sourceName)){
			contents.setSourceid(sourceName.substring(0, sourceName.length()-1));
		}
		//毫秒数
		String optionVId = request.getParameter("temporary");
		List<VoteOptionEntity> voteOptionList = new ArrayList<VoteOptionEntity>();
		if(StringUtils.isNotEmpty(optionVId)){
			//获取投票选项
			voteOptionList = voteOptionService.findByProperty(VoteOptionEntity.class, "voteid", optionVId);
		}
		JSONObject j = new JSONObject();
		
			message = "投票添加成功";
			//获取当前站点id
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			vote.setContentid(contents.getId());
			vote.setSiteid(site.getId());
			vote.setCreatedtime(new Date());//创建时间
			voteService.save(vote);
			for(VoteOptionEntity voteOption:voteOptionList){
				voteOption.setVoteid(String.valueOf(vote.getId()));
				voteOptionService.saveOrUpdate(voteOption);
			}
		j.accumulate("voteid",vote.getId());
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}
	/**
	 * 投票保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(VoteEntity vote,ContentsEntity contents,ModelItemEntity modelItem, HttpServletRequest request) {
		//顺序值
		String divValue = request.getParameter("divValue");
		String[] ulValue = divValue.split(",");
		String sourceName=contents.getSourceid();  //来源名称
		if(StringUtils.isNotEmpty(sourceName)){
			contents.setSourceid(sourceName.substring(0, sourceName.length()-1));
		}
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
		//毫秒数
		String optionVId = request.getParameter("temporary");
		List<VoteOptionEntity> voteOptionList = new ArrayList<VoteOptionEntity>();
		if(StringUtils.isNotEmpty(optionVId)){
			//获取投票选项
			voteOptionList = voteOptionService.findByProperty(VoteOptionEntity.class, "voteid", optionVId);
		}
		ContentCatEntity contentcat=voteService.getEntity(ContentCatEntity.class, String.valueOf(contentCatId));
		JSONObject j = new JSONObject();
		if (StringUtils.isNotEmpty(vote.getId())) {
			message = "投票["+contents.getTitle()+"]更新成功";
			VoteEntity t = voteService.get(VoteEntity.class, vote.getId());
			ContentsEntity c = contentsService.get(ContentsEntity.class, String.valueOf(contentsId));
			contents.setId(null);
			try {
				MyBeanUtils.copyBeanNotNull2Bean(contents, c);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//获取自定义字段/值
			List<ModelExtEntity> modelExtList = new ArrayList<ModelExtEntity>();
			if(modelId!=null&&!modelId.equals("")){
				String sql = "select modelext.id from cms_model_ext modelext where modelext.model_id="+modelId+" and content_id="+c.getId();
				List<Map<String, Object>> mapList = voteService.findForJdbc(sql);
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
				MyBeanUtils.copyBeanNotNull2Bean(vote, t);
				c.setModified(new Date());//修改时间
				c.setModifiedby(PlatFormUtil.getSessionUser().getUserName());//修改人
				contentsService.saveOrUpdate(c);
				
				voteService.saveOrUpdate(t);
				for(VoteOptionEntity voteOption:voteOptionList){
					voteOption.setVoteid(String.valueOf(t.getId()));
					voteOptionService.saveOrUpdate(voteOption);
				}
				//更新相关内容表，新增的相关项
				List<RelateContentEntity> relateContentList = voteService.findByProperty(RelateContentEntity.class, "contentId", optionVId);
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
					voteService.saveOrUpdate(relateContent);
				}
				//已添加的相关项，用于修改排序
				if(!ulValue[0].equals("")){
					List<RelateContentEntity> relateContentListT = voteService.findByProperty(RelateContentEntity.class, "contentId", String.valueOf(c.getId()));
					for(RelateContentEntity relateContent:relateContentListT){
							for(int i=0;i<ulValue.length;i++){
								String[] ul = ulValue[i].split("_");
								if(ul[1].equals(String.valueOf(relateContent.getId()))){
									relateContent.setRelateOrder(Integer.valueOf(ul[0]));
								}
							}
							voteService.saveOrUpdate(relateContent);
					}
				}
				//进入判断工作流和状态
				constantsService.isWorkFlow(c, contentcat);
				j.accumulate("contentsid", c.getId());
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "投票["+contents.getTitle()+"]更新失败";
			}
		} else {
			message = "投票["+contents.getTitle()+"]添加成功";
			//获取当前站点id
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			contents.setSiteid(site.getId());
			contents.setPathids(contentcat.getPathids());
			contents.setCatid(contentcat.getId());
			contents.setModelid(String.valueOf(modelsId));
			//设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
			contents.setClassify(ContentClassify.CONTENT_VOTE);
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
			vote.setContentid(contents.getId());
			vote.setSiteid(site.getId());
			vote.setCreatedtime(new Date());//创建时间
			voteService.save(vote);
			for(VoteOptionEntity voteOption:voteOptionList){
				voteOption.setVoteid(String.valueOf(vote.getId()));
				voteOptionService.saveOrUpdate(voteOption);
			}
			
			//保存相关内容表
			List<RelateContentEntity> relateContentList = voteService.findByProperty(RelateContentEntity.class, "contentId", optionVId);
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
				voteService.saveOrUpdate(relateContent);
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
	 * 投票删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		VoteEntity vote = voteService.getEntity(VoteEntity.class, String.valueOf(id));
		message = "投票["+vote.getVoteintroduce()+"]删除成功";
		voteService.delete(vote);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "voteController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 *添加投票选项
	 * 
	 * @return
	 */
	@RequestMapping(value = "addVoteOption")
	public ModelAndView addVoteOption() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("option", System.currentTimeMillis());
		return new ModelAndView("cms/voteoption/voteOptionItem", m);
	}
	
}
