package com.leimingtech.cms.controller.survey;
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

import com.leimingtech.cms.entity.survey.SurveyLogExtEntity;
import com.leimingtech.cms.service.survey.SurveyLogExtServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 投票日志选项文本
 * @author 
 * @date 2014-06-16 15:28:17
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/suiveyLogExtController")
public class SurveyLogExtController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SurveyLogExtController.class);

	@Autowired
	private SurveyLogExtServiceI suiveyLogExtService;
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
	 * 投票日志选项文本列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "suiveyLogExt")
	public ModelAndView suiveyLogExt(SurveyLogExtEntity suiveyLogExt, HttpServletRequest request) {
		//获取投票日志选项文本列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(SurveyLogExtEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, suiveyLogExt, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.suiveyLogExtService.getPageList(cq, true);
		List<SurveyLogExtEntity> resultList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", resultList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "suiveyLogExtController.do?suiveyLogExt");
		return new ModelAndView("cms/survey/suiveyLogExtList", m);
	}

	/**
	 * 投票日志选项文本添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("suiveyLogExt", new SurveyLogExtEntity());
		return new ModelAndView("cms/survey/suiveyLogExt", m);
	}
	
	/**
	 * 投票日志选项文本更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		SurveyLogExtEntity suiveyLogExt = suiveyLogExtService.getEntity(SurveyLogExtEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("suiveyLogExt", suiveyLogExt);
		return new ModelAndView("cms/survey/suiveyLogExt", m);
	}

	/**
	 * 投票日志选项文本保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(SurveyLogExtEntity suiveyLogExt, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(suiveyLogExt.getId())) {
			message = "投票日志选项文本["+suiveyLogExt.getExt()+"]更新成功";
			SurveyLogExtEntity t = suiveyLogExtService.get(SurveyLogExtEntity.class, suiveyLogExt.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(suiveyLogExt, t);
				suiveyLogExtService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "投票日志选项文本["+suiveyLogExt.getExt()+"]更新失败";
			}
		} else {
			message = "投票日志选项文本["+suiveyLogExt.getExt()+"]添加成功";
			suiveyLogExt.setCreatedtime(new Date());//创建时间
			suiveyLogExtService.save(suiveyLogExt);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "suiveyLogExtController.do?suiveyLogExt");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 投票日志选项文本删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		SurveyLogExtEntity suiveyLogExt = suiveyLogExtService.getEntity(SurveyLogExtEntity.class, String.valueOf(id));
		message = "投票日志选项文本["+suiveyLogExt.getExt()+"]删除成功";
		suiveyLogExtService.delete(suiveyLogExt);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "suiveyLogExtController.do?suiveyLogExt");
		String str = j.toString();
		return str;
	}
}
