package com.leimingtech.mobile.controller.sysiostoken;
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
import com.leimingtech.mobile.entity.sysiostoken.SysIosTokenEntity;
import com.leimingtech.mobile.service.sysiostoken.SysIosTokenServiceI;

/**   
 * @Title: Controller
 * @Description: 获取IOS设备的token
 * @author 
 * @date 2014-09-05 09:29:24
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/sysIsoTokenController")
public class SysIosTokenController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SysIosTokenController.class);

	@Autowired
	private SysIosTokenServiceI sysIsoTokenService;
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
	 * 获取IOS设备的token列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "sysIsoToken")
	public ModelAndView sysIsoToken(SysIosTokenEntity sysIsoToken, HttpServletRequest request) {
		//获取获取IOS设备的token列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(SysIosTokenEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, sysIsoToken, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.sysIsoTokenService.getPageList(cq, true);
		List<SysIosTokenEntity> resultList = pageList.getResultList();
		
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
		m.put("actionUrl", "sysIsoTokenController.do?sysIsoToken");
		return new ModelAndView("mobile/sysisotoken/sysIsoTokenList", m);
	}

	/**
	 * 获取IOS设备的token添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("sysIsoToken", new SysIosTokenEntity());
		return new ModelAndView("mobile/sysisotoken/sysIsoToken", m);
	}
	
	/**
	 * 获取IOS设备的token更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		SysIosTokenEntity sysIsoToken = sysIsoTokenService.getEntity(SysIosTokenEntity.class, id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("sysIsoToken", sysIsoToken);
		return new ModelAndView("mobile/sysisotoken/sysIsoToken", m);
	}

	/**
	 * 获取IOS设备的token保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(SysIosTokenEntity sysIsoToken, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(sysIsoToken.getId())) {
			message = "获取IOS设备的token["+sysIsoToken.getPhoneType()+"]更新成功";
			SysIosTokenEntity t = sysIsoTokenService.get(SysIosTokenEntity.class, sysIsoToken.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sysIsoToken, t);
				sysIsoTokenService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "获取IOS设备的token["+sysIsoToken.getPhoneType()+"]更新失败";
			}
		} else {
			message = "获取IOS设备的token["+sysIsoToken.getPhoneType()+"]添加成功";
			sysIsoToken.setCreatedtime(new Date());//创建时间
			sysIsoTokenService.save(sysIsoToken);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "sysIsoTokenController.do?sysIsoToken");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 获取IOS设备的token删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		SysIosTokenEntity sysIsoToken = sysIsoTokenService.getEntity(SysIosTokenEntity.class, id);
		message = "获取IOS设备的token["+sysIsoToken.getPhoneType()+"]删除成功";
		sysIsoTokenService.delete(sysIsoToken);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "sysIsoTokenController.do?sysIsoToken");
		String str = j.toString();
		return str;
	}
}
