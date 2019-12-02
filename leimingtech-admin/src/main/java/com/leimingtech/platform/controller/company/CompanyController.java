package com.leimingtech.platform.controller.company;
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

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.platform.entity.company.CompanyEntity;
import com.leimingtech.platform.service.company.CompanyServiceI;

/**   
 * @Title: Controller
 * @Description: 公司信息
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-03 17:42:02
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/companyController")
public class CompanyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CompanyController.class);

	@Autowired
	private CompanyServiceI companyService;
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
	 * 公司信息列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(CompanyEntity company, HttpServletRequest request) {
		//获取公司信息列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(CompanyEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, company, param);
		//排序条件
		cq.addOrder("createdtime",SortDirection.desc);
		cq.add();
		PageList pageList = this.companyService.getPageList(cq, true);
		List<CompanyEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "companyController.do?table");
		return new ModelAndView("lmPage/company/companyList", m);
	}

	/**
	 * 公司信息添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		CompanyEntity company = new CompanyEntity();
		m.put("page", company);
		return new ModelAndView("lmPage/company/company", m);
	}
	
	/**
	 * 公司信息更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		CompanyEntity company = companyService.getEntity(CompanyEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", company);
		return new ModelAndView("lmPage/company/company", m);
	}

	/**
	 * 公司信息保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(CompanyEntity company, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(company.getId())) {
			message = "公司信息["+company.getCompanyName()+"]更新成功";
			CompanyEntity t = companyService.get(CompanyEntity.class, company.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(company, t);
				companyService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "公司信息["+company.getCompanyName()+"]更新失败";
			}
		} else {
			message = "公司信息["+company.getCompanyName()+"]添加成功";
			company.setCreatedtime(new Date());//创建时间
			companyService.save(company);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "companyController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 公司信息删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		CompanyEntity company = companyService.getEntity(CompanyEntity.class, String.valueOf(id));
		message = "公司信息["+company.getCompanyName()+"]删除成功";
		companyService.delete(company);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "companyController.do?table");
		String str = j.toString();
		return str;
	}
	/**
	 * 百度地图跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "baiduMap")
	public ModelAndView baiduMap(HttpServletRequest reqeust){
		String longitude = reqeust.getParameter("longitude");
		String latitude = reqeust.getParameter("latitude");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("latitude", latitude);
		m.put("longitude", longitude);
		return new ModelAndView("lmPage/company/baiduMap", m);
	}
}
