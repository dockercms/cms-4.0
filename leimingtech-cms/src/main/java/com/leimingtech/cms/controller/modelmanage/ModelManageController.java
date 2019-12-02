package com.leimingtech.cms.controller.modelmanage;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ModelManageEntity;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ModelManageServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.ParamsUtils;


/**   
 * @Title: Controller
 * @Description: 模型管理
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-15 14:47:49
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/modelManageController")
public class ModelManageController extends BaseController {

	@Autowired
	private ModelManageServiceI modelManageService;
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
	 * 模型管理列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(ModelManageEntity modelManage, HttpServletRequest request) {
		//获取模型管理列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		Map param=request.getParameterMap();
		PageList pageList = this.modelManageService.getPageList(pageSize,pageNo,modelManage,param);
		List<ModelManageEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "modelManageController.do?table");
		m.put("currentChangePageURL", "modelManageController.do?table&"+ParamsUtils.getUrlParams(request));
		return new ModelAndView("cms/modelmanage/modelManageList", m);
	}

	/**
	 * 模型管理添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new ModelManageEntity());
		return new ModelAndView("cms/modelmanage/modelManage", m);
	}
	
	/**
	 * 模型管理更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		
		Map<String, Object> m = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(id)){
			ModelManageEntity modelManage = modelManageService.getEntity(id);
			m.put("page", modelManage);
		}else{
			m.put("page", new ModelManageEntity());
		}
		
		return new ModelAndView("cms/modelmanage/modelManage", m);
	}

	/**
	 * 模型管理保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(ModelManageEntity modelManage, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtils.isNotEmpty(modelManage.getId())) {
			message = "模型管理["+modelManage.getModelName()+"]更新成功";
			ModelManageEntity t = modelManageService.getEntity(modelManage.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(modelManage, t);
				modelManageService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "模型管理更新失败";
			}
		} else {
			message = "模型管理["+modelManage.getModelName()+"]添加成功";
			modelManage.setCreatedtime(new Date());//创建时间
			modelManageService.save(modelManage);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "modelManageController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 模型管理删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		ModelManageEntity modelManage = modelManageService.getEntity(id);
		message = "模型管理["+modelManage.getModelName()+"]删除成功";
		
		modelManageService.delete(modelManage);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "modelManageController.do?table");
		String str = j.toString();
		return str;
	}
}
