package com.leimingtech.cms.controller.surveyoption;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.*;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SurveyOptionServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**   
 * @Title: Controller
 * @Description: 调查选项
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-05-05 16:45:09
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/surveyOptionController")
public class SurveyOptionController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SurveyOptionController.class);

	@Autowired
	private SurveyOptionServiceI surveyOptionService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 调查选项列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(SurveyOptionEntity surveyOption, HttpServletRequest request) {
		//获取内容id
		String contentsId = request.getParameter("contentsId");
		
		String contentCatId = request.getParameter("contentCatId");
		
		ContentsEntity content=surveyOptionService.getEntity(ContentsEntity.class, String.valueOf(contentsId));
		
		List<SurveyEntity> surveyList = surveyOptionService.findByProperty(SurveyEntity.class, "contentid", String.valueOf(contentsId));
		SurveyEntity survey=surveyList.get(0);
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(SurveyOptionEntity.class, pageSize, pageNo, "", "");
		cq.eq("surveyid", survey.getId());
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, surveyOption, param);
		//排序条件
		cq.addOrder("createdtime",SortDirection.desc);
		cq.addOrder("optionsort", SortDirection.desc);
		cq.add();
		PageList pageList = this.surveyOptionService.getPageList(cq, true);
		List<SurveyOptionEntity> testList = pageList.getResultList();
		
		if(testList!=null && testList.size()>0){
			List<TSTypegroup> typegroups=surveyOptionService.findByProperty(TSTypegroup.class, "typegroupcode", "survey_fieldtype");
			if(typegroups!=null && typegroups.size()>0){
				String pid=typegroups.get(0).getId();
				for (SurveyOptionEntity surveyOptionEntity : testList) {
					if(StringUtils.isNotEmpty(surveyOptionEntity.getDataType())){
						
						CriteriaQuery typecq = new CriteriaQuery(TSType.class);
						typecq.eq("TSTypegroup.id", pid);
						typecq.eq("typecode", surveyOptionEntity.getDataType());
						typecq.add();
						List<TSType> types=surveyOptionService.getListByCriteriaQuery(typecq, false);
						if(types!=null &&types.size()>0){
							surveyOptionEntity.setDataTypeStr(types.get(0).getTypename());
						}
					}
				}
			}
		}
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		//调查
		m.put("survey", survey);
		m.put("searchMap", param);
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("contentCatId", contentCatId);
		m.put("pageCount", pageCount);
		m.put("contentsId", contentsId);
		m.put("content", content);
		m.put("actionUrl", "surveyOptionController.do?table");
		return new ModelAndView("cms/surveyoption/surveyOptionList", m);
	}

	/**
	 * 调查选项添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		//调查id
		String surveyId = reqeust.getParameter("surveyId");
		//获取内容id
		String contentsId = reqeust.getParameter("contentsId");
		//类型标记
		String mark = reqeust.getParameter("mark");
		//临时给调查选项中的投票id赋值为当前毫秒数
		String temporary = String.valueOf(System.currentTimeMillis());
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new SurveyOptionEntity());
		m.put("surveyId", surveyId);
		m.put("contentsId", contentsId);
		m.put("temporary", temporary);
		m.put("mark", mark);
		return new ModelAndView("cms/surveyoption/surveyOption", m);
	}
	
	/**
	 * 调查选项更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		//调查id
		String surveyId = reqeust.getParameter("surveyId");
		//获取内容id
		String contentsId = reqeust.getParameter("contentsId");
		SurveyOptionEntity surveyOption = surveyOptionService.getEntity(SurveyOptionEntity.class, String.valueOf(id));
		String mark = surveyOption.getDataType();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", surveyOption);
		m.put("surveyId", surveyId);
		m.put("mark", mark);
		m.put("contentsId", contentsId);
		return new ModelAndView("cms/surveyoption/surveyOption", m);
	}

	/**
	 * 调查选项保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(SurveyOptionEntity surveyOption, HttpServletRequest request) {
		//传递参数跳转到调查选项添加页面
		String optionVId = request.getParameter("optionVId");
		//调查id
		String surveyId = request.getParameter("surveyId");
		//获取内容id
		String contentsId = request.getParameter("contentsId");
		//判断类型
		String mark = request.getParameter("mark");
		
		List<SurveyOptionExtEntity> surveyOptionExtList = new ArrayList<SurveyOptionExtEntity>();
		if(optionVId!=""){
			surveyOptionExtList = surveyOptionService.findByProperty(SurveyOptionExtEntity.class, "optionsid", optionVId);
		}
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(surveyOption.getId())) {
			message = "调查选项["+surveyOption.getOptionname()+"]更新成功";
			SurveyOptionEntity t = surveyOptionService.get(SurveyOptionEntity.class, surveyOption.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(surveyOption, t);
				surveyOptionService.saveOrUpdate(t);
				for(SurveyOptionExtEntity surveyOptionExt:surveyOptionExtList){
					surveyOptionExt.setOptionsid(String.valueOf(t.getId()));
					surveyOptionService.saveOrUpdate(surveyOptionExt);
				}
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "调查选项["+surveyOption.getOptionname()+"]更新失败";
			}
		} else {
			message = "调查选项["+surveyOption.getOptionname()+"]添加成功";
			surveyOption.setSurveyid(String.valueOf(surveyId));
			surveyOption.setDataType(mark);
			surveyOption.setCreatedtime(new Date());//创建时间
			surveyOptionService.save(surveyOption);
			for(SurveyOptionExtEntity surveyOptionExt:surveyOptionExtList){
				surveyOptionExt.setOptionsid(String.valueOf(surveyOption.getId()));
				surveyOptionService.saveOrUpdate(surveyOptionExt);
			}
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "surveyOptionController.do?table&contentsId="+contentsId);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 调查选项删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		//获取栏目id
		String contentCatId = request.getParameter("contentCatId");
		//获取内容id
		String contentsId = request.getParameter("contentsId");
		SurveyOptionEntity surveyOption = surveyOptionService.getEntity(SurveyOptionEntity.class, String.valueOf(id));
		message = "调查选项["+surveyOption.getOptionname()+"]删除成功";
		surveyOptionService.delete(surveyOption);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "surveyOptionController.do?table&contentCatId="+contentCatId+"&contentsId="+contentsId);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 调查选项跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "jumpOption")
	public ModelAndView jumpOption(HttpServletRequest reqeust){
		//临时给调查选项中的选项id赋值为当前毫秒数
		String optionVId = reqeust.getParameter("optionVId");
		//选项id
		String surveyOptionId = reqeust.getParameter("surveyOptionId");
		
		List<SurveyOptionExtEntity> surveyOptionExtList = new ArrayList<SurveyOptionExtEntity>();
		if(!(StringUtils.isEmpty(optionVId)&&StringUtils.isEmpty(surveyOptionId))){
			CriteriaQuery typecq = new CriteriaQuery(SurveyOptionExtEntity.class);
			if(StringUtils.isNotEmpty(optionVId) && StringUtils.isNotEmpty(surveyOptionId)){
				typecq.or(Restrictions.eq("optionsid", optionVId), Restrictions.eq("optionsid", surveyOptionId));
			}else if(StringUtils.isNotEmpty(optionVId)){
				typecq.eq("optionsid", optionVId);
			}else if(StringUtils.isNotEmpty(surveyOptionId)){
				typecq.eq("optionsid", surveyOptionId);
			}
			typecq.addOrder("createdtime", SortDirection.desc);
			typecq.addOrder("extSort", SortDirection.desc);
			typecq.add();
			surveyOptionExtList=surveyOptionService.getListByCriteriaQuery(typecq, false);
		}
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("optionVId", optionVId);
		m.put("surveyOptionId", surveyOptionId);
		m.put("surveyOptionExtList", surveyOptionExtList);
		return new ModelAndView("cms/surveyoptionext/surveyOptionRefrech", m);
	}
}
