package com.leimingtech.cms.controller.interview;
import java.util.ArrayList;
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

import com.leimingtech.cms.controller.contents.ContentsbaseController;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.ContentClassify;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentTagEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.InterviewEntity;
import com.leimingtech.core.entity.InterviewGuestEntity;
import com.leimingtech.core.entity.ModelExtEntity;
import com.leimingtech.core.entity.ModelItemEntity;
import com.leimingtech.core.entity.ModelManageEntity;
import com.leimingtech.core.entity.SourceEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ContentTagServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.InterviewServiceI;
import com.leimingtech.core.service.ModelItemServiceI;
import com.leimingtech.core.service.ModelManageServiceI;
import com.leimingtech.core.service.SourceServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.modelext.ModelExtServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;

/**   
 * @Title: Controller
 * @Description: 访谈
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-05-08 11:26:03
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/interviewController")
public class InterviewController extends ContentsbaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(InterviewController.class);

	@Autowired
	private InterviewServiceI interviewService;
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
	 * 访谈列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(InterviewEntity interview, HttpServletRequest request) {
		//获取访谈列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(InterviewEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, interview, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.interviewService.getPageList(cq, true);
		List<InterviewEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "interviewController.do?table");
		return new ModelAndView("cms/interview/interviewList", m);
	}

	/**
	 * 访谈添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		String contentCatId = reqeust.getParameter("contentCatId");
		String modelsId = reqeust.getParameter("modelsId");
		//临时给访谈选项中的投票id赋值为当前毫秒数
		String temporary = String.valueOf(System.currentTimeMillis());
		//区分添加/编辑页面
		String flag = "add";
		ContentCatEntity contentCat = contentsService.get(ContentCatEntity.class, String.valueOf(contentCatId));
		ModelManageEntity modelManage = modelManageService.getEntity(contentCat.getJsonid());
		List<ModelItemEntity> modelItemList = modelItemService.findByModelId(modelManage.getId());
		List<ContentTagEntity> contentTagList = contentTagService.loadAll(ContentTagEntity.class);  //获取Tags标签内容
		List<SourceEntity> sourceEntityList = sourceService.loadAll(SourceEntity.class);  //获取来源内容
		//当前人
		String markpeople = "";
		if(PlatFormUtil.getSessionUser()!=null){
			markpeople= PlatFormUtil.getSessionUser().getUserName();
			if(StringUtils.isEmpty(markpeople)){
				markpeople=PlatFormUtil.getSessionUser().getRealName();
			}
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new InterviewEntity());
		m.put("contents", new ContentsEntity());
		m.put("contentCat", contentCat);
		m.put("modelsId", modelsId);
		m.put("modelItemList", modelItemList);
		m.put("modelManage", modelManage);
		m.put("flag", flag);
		m.put("contentTagList", contentTagList);
		m.put("sourceEntityList", sourceEntityList);
		m.put("temporary", temporary);
		m.put("markpeople", markpeople);
		return new ModelAndView("cms/interview/interview_open", m);
	}
	
	/**
	 * 访谈更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		InterviewEntity interview = interviewService.getEntity(InterviewEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", interview);
		return new ModelAndView("cms/interview/interview", m);
	}

	/**
	 * 访谈保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(InterviewEntity interview,ContentsEntity contents,ModelItemEntity modelItem, HttpServletRequest request) {
		//栏目id
		String contentCatId = request.getParameter("contentCatId");
		//模型模块id
		String modelsId = request.getParameter("modelsId");
		//扩展字段id
		String modelId = request.getParameter("modelId");
		//获取内容id
		String contentsId = request.getParameter("contentsId");
		//获取参数名称
		List<ModelItemEntity> modelItemList = modelItemService.findByModelId(modelId);
		//毫秒数
		String optionId = request.getParameter("temporary");
		List<InterviewGuestEntity> interviewGuestList = new ArrayList<InterviewGuestEntity>();
		if(optionId!=""){
			interviewGuestList = interviewService.findByProperty(InterviewGuestEntity.class, "interviewid", optionId);
			
		}

		JSONObject j = new JSONObject();
		if (StringUtils.isNotEmpty(interview.getId())) {
			message = "访谈["+contents.getTitle()+"]更新成功";
			InterviewEntity t = interviewService.get(InterviewEntity.class, interview.getId());
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
			List<Map<String, Object>> mapList = interviewService.findForJdbc(sql);
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
				MyBeanUtils.copyBeanNotNull2Bean(interview, t);
				contentsService.saveOrUpdate(c);
				interviewService.saveOrUpdate(t);
				for(InterviewGuestEntity interviewGuest:interviewGuestList){
					interviewGuest.setInterviewid(String.valueOf(t.getId()));
					interviewService.saveOrUpdate(interviewGuest);
				}
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "访谈更新失败";
			}
		} else {
			message = "访谈["+contents.getTitle()+"]添加成功";
			ContentCatEntity contentcat=interviewService.getEntity(ContentCatEntity.class, String.valueOf(contentCatId));
			contents.setCatid(contentcat.getId());
			contents.setModelid(String.valueOf(modelsId));
			//设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
			contents.setClassify(ContentClassify.CONTENT_INTERVIEW);
			contents.setCreated(new Date());//创建时间
			contentsService.save(contents);
			
			//进入工作流，判断是否需要审核，如果需要设为待审，如果不需要审核设为已审
			enterworkflow(contents, String.valueOf(contentCatId));
			
			//在modelExt中保存modelId/attrName
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
			interview.setContentid(contents.getId());
			interview.setCreatedtime(new Date());//创建时间
			interviewService.save(interview);
			for(InterviewGuestEntity interviewGuest:interviewGuestList){
				interviewGuest.setInterviewid(String.valueOf(interview.getId()));
				interviewService.saveOrUpdate(interviewGuest);
			}
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentsController.do?table&tab=alone&contentCatId="+contentCatId);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 访谈删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		InterviewEntity interview = interviewService.getEntity(InterviewEntity.class, id);
		message = "访谈["+interview.getInterviewIntroduce()+"]删除成功";
		interviewService.delete(interview);
		systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "interviewController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 嘉宾选项跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "jumpOption")
	public ModelAndView jumpOption(HttpServletRequest reqeust){
		//临时给调查选项中的选项id赋值为当前毫秒数
		String optionId = reqeust.getParameter("optionId");
		//访谈id
		String interviewId = reqeust.getParameter("interviewId");
		
		List<InterviewGuestEntity> interviewGuestExtList = new ArrayList<InterviewGuestEntity>();
		List<InterviewGuestEntity> interviewGuestExtListT = new ArrayList<InterviewGuestEntity>();
		if(optionId!=""){
			//获取访谈选项
			interviewGuestExtList = interviewService.findByProperty(InterviewGuestEntity.class, "interviewid", optionId);
		}
		if(interviewId!=""){
			interviewGuestExtListT = interviewService.findByProperty(InterviewGuestEntity.class, "interviewid", interviewId);
			for(InterviewGuestEntity interviewGuestExt:interviewGuestExtListT){
				interviewGuestExtList.add(interviewGuestExt);
			}
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("optionId", optionId);
		m.put("interviewId", interviewId);
		m.put("interviewGuestExtList", interviewGuestExtList);
		return new ModelAndView("cms/interviewguest/interviewGuestRefrech", m);
	}
}
