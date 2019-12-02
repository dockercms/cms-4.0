package com.leimingtech.cms.controller.surveyoptionext;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.leimingtech.core.service.SurveyOptionExtServiceI;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.SurveyOptionExtEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 调查选项内容
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-05-05 17:45:51
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/surveyOptionExtController")
public class SurveyOptionExtController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SurveyOptionExtController.class);

	@Autowired
	private SurveyOptionExtServiceI surveyOptionExtService;
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
	 * 调查选项内容列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(SurveyOptionExtEntity surveyOptionExt, HttpServletRequest request) {
		//获取调查选项内容列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(SurveyOptionExtEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, surveyOptionExt, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.surveyOptionExtService.getPageList(cq, true);
		List<SurveyOptionExtEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "surveyOptionExtController.do?table");
		return new ModelAndView("cms/surveyoptionext/surveyOptionExtList", m);
	}

	/**
	 * 调查选项内容添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		//临时给调查选项中的选项id赋值为当前毫秒数
		String optionVId = reqeust.getParameter("optionVId");
		//选项id
		String surveyOptionId = reqeust.getParameter("surveyOptionId");
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new SurveyOptionExtEntity());
		m.put("optionVId", optionVId);
		m.put("surveyOptionId", surveyOptionId);
		return new ModelAndView("cms/surveyoptionext/surveyOptionExt", m);
	}
	
	/**
	 * 调查选项内容更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		SurveyOptionExtEntity surveyOptionExt = surveyOptionExtService.getEntity(SurveyOptionExtEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", surveyOptionExt);
		return new ModelAndView("cms/surveyoptionext/surveyOptionExt", m);
	}

	/**
	 * 调查选项内容保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(SurveyOptionExtEntity surveyOptionExt, HttpServletRequest request) {
		//传递参数跳转到调查选项添加页面
		String optionVId = request.getParameter("optionVId");
		//选项id
		String surveyOptionId = request.getParameter("surveyOptionId");
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(surveyOptionExt.getId())) {
			message = "调查选项内容["+surveyOptionExt.getExtOptionname()+"]更新成功";
			SurveyOptionExtEntity t = surveyOptionExtService.get(SurveyOptionExtEntity.class, surveyOptionExt.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(surveyOptionExt, t);
				if (StringUtils.isEmpty(surveyOptionExt.getOptionsid())) {
					if (StringUtils.isNotEmpty(surveyOptionId)) {
						surveyOptionExt.setOptionsid(surveyOptionId);
					} else if (StringUtils.isNotEmpty(optionVId)) {
						surveyOptionExt.setOptionsid(optionVId);
					}
				}
				surveyOptionExtService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "调查选项内容["+surveyOptionExt.getExtOptionname()+"]更新失败";
			}
		} else {
			message = "调查选项内容["+surveyOptionExt.getExtOptionname()+"]添加成功";
			if (StringUtils.isEmpty(surveyOptionExt.getOptionsid())) {
				if (StringUtils.isNotEmpty(surveyOptionId)) {
					surveyOptionExt.setOptionsid(surveyOptionId);
				} else if (StringUtils.isNotEmpty(optionVId)) {
					surveyOptionExt.setOptionsid(optionVId);
				}
			}
			surveyOptionExt.setCreatedtime(new Date());//创建时间
			surveyOptionExtService.save(surveyOptionExt);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "surveyOptionController.do?jumpOption&optionVId="+optionVId+"&surveyOptionId="+surveyOptionId);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 调查选项内容删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String extid = request.getParameter("id");
		//传递参数跳转到调查选项添加页面
		String optionVId = request.getParameter("optionVId");
		//选项id
		String surveyOptionId = request.getParameter("surveyOptionId");
		SurveyOptionExtEntity surveyOptionExt = surveyOptionExtService.getEntity(SurveyOptionExtEntity.class, String.valueOf(extid));
		message = "调查选项内容["+surveyOptionExt.getExtOptionname()+"]删除成功";
		surveyOptionExtService.delete(surveyOptionExt);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "surveyOptionController.do?jumpOption&optionVId="+optionVId+"&surveyOptionId="+surveyOptionId);
		String str = j.toString();
		return str;
	}
}
