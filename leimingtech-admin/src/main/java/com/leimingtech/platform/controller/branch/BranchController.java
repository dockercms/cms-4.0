package com.leimingtech.platform.controller.branch;
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
import com.leimingtech.platform.entity.branch.BranchEntity;
import com.leimingtech.platform.service.branch.BranchServiceI;



/**   
 * @Title: Controller
 * @Description: 分支机构
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-04 10:21:11
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/branchController")
public class BranchController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BranchController.class);

	@Autowired
	private BranchServiceI branchService;
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
	 * 分支机构列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(BranchEntity branch, HttpServletRequest request) {
		String companyId = request.getParameter("companyId");
		//获取分支机构列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(BranchEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, branch, param);
		//排序条件
		cq.addOrder("createdtime",SortDirection.desc);
		cq.add();
		PageList pageList = this.branchService.getPageList(cq, true);
		List<BranchEntity> branchList = branchService.findByProperty(BranchEntity.class, "pid", companyId);
		pageList.setResultList(branchList);
		List<BranchEntity> testList = pageList.getResultList();
		
		
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
		m.put("companyId", companyId);
		m.put("actionUrl", "branchController.do?table");
		return new ModelAndView("lmPage/branch/branchList", m);
	}

	/**
	 * 分支机构添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		String companyId = reqeust.getParameter("companyId");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new BranchEntity());
		m.put("companyId", companyId);
		return new ModelAndView("lmPage/branch/branch", m);
	}
	
	/**
	 * 分支机构更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		BranchEntity branch = branchService.getEntity(BranchEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", branch);
		return new ModelAndView("lmPage/branch/branch", m);
	}

	/**
	 * 分支机构保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(BranchEntity branch, HttpServletRequest request) {
		String companyId = request.getParameter("companyId");
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(branch.getId())) {
			message = "分支机构["+branch.getCompanyName()+"]更新成功";
			BranchEntity t = branchService.get(BranchEntity.class, branch.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(branch, t);
				branchService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "分支机构["+branch.getCompanyName()+"]更新失败";
			}
		} else {
			message = "分支机构["+branch.getCompanyName()+"]添加成功";
			branch.setPid(companyId);
			branch.setCreatedtime(new Date());//创建时间
			branchService.save(branch);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "branchController.do?table&companyId="+companyId);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 分支机构删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		BranchEntity branch = branchService.getEntity(BranchEntity.class, String.valueOf(id));
		message = "分支机构["+branch.getCompanyName()+"]删除成功";
		branchService.delete(branch);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "branchController.do?table");
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
