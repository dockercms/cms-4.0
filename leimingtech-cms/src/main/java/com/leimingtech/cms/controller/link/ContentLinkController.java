package com.leimingtech.cms.controller.link;
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
import com.leimingtech.core.entity.ContentLinkEntity;
import com.leimingtech.core.entity.ContentTagEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.ModelExtEntity;
import com.leimingtech.core.entity.ModelItemEntity;
import com.leimingtech.core.entity.ModelManageEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.SourceEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ConstantsServiceI;
import com.leimingtech.core.service.ContentLinkServiceI;
import com.leimingtech.core.service.ContentTagServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.ModelItemServiceI;
import com.leimingtech.core.service.ModelManageServiceI;
import com.leimingtech.core.service.SourceServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.modelext.ModelExtServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;

/**   
 * @Title: Controller
 * @Description: 链接
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-28 14:06:30
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/contentLinkController")
public class ContentLinkController extends ContentsbaseController {

	@Autowired
	private ContentLinkServiceI contentLinkService;
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
	 * 链接列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(ContentLinkEntity contentLink, HttpServletRequest request) {
		//获取链接列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(ContentLinkEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		contentLink.setSiteid(site.getId());
		HqlGenerateUtil.installHql(cq, contentLink, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.contentLinkService.getPageList(cq, true);
		List<ContentLinkEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "contentLinkController.do?table");
		return new ModelAndView("cms/link/contentLinkList", m);
	}

	/**
	 * 链接添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		String contentCatId = reqeust.getParameter("contentCatId");
		String modelsId = reqeust.getParameter("modelsId");
		/** 当前毫秒数 */
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
		contents.setClassify(ContentClassify.CONTENT_LINK);
		Map<String, Object> m = new HashMap<String, Object>();
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(sourceStr)){
			m.put("sourceStr", sourceStr.substring(0, sourceStr.length()-1));
		}else{
			m.put("sourceStr", CmsConstants.DEFAULT_SOURCE);
		}
		SiteEntity site =PlatFormUtil.getSessionSite();
		m.put("site", site);
		m.put("page", new ContentLinkEntity());
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
		m.put("classify", ContentClassify.CONTENT_LINK);
		m.put("sessionId", reqeust.getSession().getId());
		return new ModelAndView("cms/link/contentLink_open", m);
	}
	
	/**
	 * 链接更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		ContentLinkEntity contentLink = contentLinkService.getEntity(ContentLinkEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", contentLink);
		return new ModelAndView("cms/link/contentLink", m);
	}

	/**
	 * 链接保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(ContentLinkEntity contentLink,ContentsEntity contents,ModelItemEntity modelItem,ContentCatEntity contentcat, HttpServletRequest request) {
		
		//扩展字段id
		String modelId = request.getParameter("modelId");
		//获取当前毫秒数
		String temporary = request.getParameter("temporary");
		String contentsId = request.getParameter("contentsId");
		
		JSONObject j = new JSONObject();
		if (StringUtils.isNotEmpty(contentLink.getId())) {
			message = "PC内容\t【链接\t"+contents.getTitle()+"】更新成功";
			ContentLinkEntity t = contentLinkService.get(ContentLinkEntity.class, contentLink.getId());
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
				List<Map<String, Object>> mapList = contentLinkService.findForJdbc(sql);
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
				MyBeanUtils.copyBeanNotNull2Bean(contentLink, t);
				c.setModified(new Date());//修改时间
				c.setModifiedby(PlatFormUtil.getSessionUser().getUserName());//修改人
				contentsService.saveOrUpdate(c);
				contentLinkService.saveOrUpdate(t);
				//进入判断工作流和状态
				constantsService.isWorkFlow(c, contentcat);
				j.accumulate("contentsid", c.getId());
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "链接更新失败";
			}
		} else {
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentsController.do?table&tab=alone&contentCatId="+contentcat.getId());
		String str = j.toString();
		return str;
	}
	
	/**
	 * 链接删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		ContentLinkEntity contentLink = contentLinkService.getEntity(ContentLinkEntity.class, String.valueOf(id));
		message = "链接["+contentLink.getLinkname()+"]删除成功";
		contentLinkService.delete(contentLink);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentLinkController.do?table");
		String str = j.toString();
		return str;
	}
}
