package com.leimingtech.cms.controller.modelitem;
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
import com.leimingtech.core.entity.ModelItemEntity;
import com.leimingtech.core.entity.ModelManageEntity;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ModelItemServiceI;
import com.leimingtech.core.service.ModelManageServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.oConvertUtils;


/**   
 * @Title: Controller
 * @Description: 字段
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-15 14:48:32
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/modelItemController")
public class ModelItemController extends BaseController {

	@Autowired
	private ModelItemServiceI modelItemService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ModelManageServiceI modelManageService;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 字段列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(ModelItemEntity modelItem, HttpServletRequest request) {
		String modelManageId = request.getParameter("modelId");
		
		ModelManageEntity modelManage = null;
		//获取模型管理
		if(StringUtils.isNotEmpty(modelManageId)){
			modelManage = modelManageService.getEntity(modelManageId);
		}
		
		Map param=request.getParameterMap();
		PageList pageList = this.modelItemService.getPageList(modelItem,param);
		
		List<ModelItemEntity> testList = pageList.getResultList();
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", testList);
		m.put("modelManage", modelManage);
		m.put("actionUrl", "modelItemController.do?table&modelId="+modelManageId);
		return new ModelAndView("cms/modelitem/modelItemList", m);
	}

	/**
	 * 字段更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");

		Map<String, Object> m = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(id)){
			ModelItemEntity modelItem = modelItemService.getEntity(id);
			m.put("page", modelItem);
		}else{
			String modelManageId = reqeust.getParameter("modelId");
			int dataType = oConvertUtils.getInt(reqeust.getParameter("dataType"), 1);
			
			ModelItemEntity modelItem =new ModelItemEntity();
			modelItem.setModelId(modelManageId);
			modelItem.setDataType(dataType);
			m.put("page", modelItem);
		}
		return new ModelAndView("cms/modelitem/modelItemEdit", m);
	}

	/**
	 * 字段保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(ModelItemEntity modelItem,HttpServletRequest request) {
		JSONObject j = new JSONObject();
		
		if(StringUtils.isBlank(modelItem.getItemLabel())){
			j.accumulate("isSuccess", false);
			j.accumulate("msg", "字段名称不能为空！");
			return j.toString();
		}
		
		if(StringUtils.isBlank(modelItem.getField())){
			j.accumulate("isSuccess", false);
			j.accumulate("msg", "变量名不能为空！");
			return j.toString();
		}
		
		if(StringUtils.isBlank(modelItem.getModelId())){
			j.accumulate("isSuccess", false);
			j.accumulate("msg", "您没有指定方案！");
			return j.toString();
		}
		
		if (StringUtils.isNotEmpty(modelItem.getId())) {
			message = "字段["+modelItem.getItemLabel()+"]更新成功";
			ModelItemEntity t = modelItemService.getEntity(modelItem.getId());
			
			if(!t.getField().equals(modelItem.getField())){
				boolean flag=modelItemService.checkFieldExits(modelItem.getModelId(),modelItem.getField());//检查指定方案中变量名是否有重名
				if(flag){
					//变量名有重名
					j.accumulate("isSuccess", false);
					j.accumulate("msg", modelItem.getField()+"已被使用，请更换为其他的变量名");
					return j.toString();
				}
			}
			
			try {
				MyBeanUtils.copyBeanNotNull2Bean(modelItem, t);
				modelItemService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "字段更新失败";
			}
		} else {
			boolean flag=modelItemService.checkFieldExits(modelItem.getModelId(),modelItem.getField());//检查指定方案中变量名是否有重名
			if(flag){
				//变量名有重名
				j.accumulate("isSuccess", false);
				j.accumulate("msg", modelItem.getField()+"已被使用，请更换为其他的变量名");
				return j.toString();
			}
			message = "字段["+modelItem.getItemLabel()+"]添加成功";
			modelItem.setSiteid(PlatFormUtil.getSessionSiteId());
			modelItem.setCreatedtime(new Date());//创建时间
			int priority=modelItemService.getMaxPriorityByModel(modelItem.getModelId());//获取指定方案中的最大排序值
			modelItem.setPriority(priority+1);
			modelItemService.save(modelItem);
			
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "modelItemController.do?table&modelId="+modelItem.getModelId());
		String str = j.toString();
		return str;
	}
	
	/**
	 * 字段删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		ModelItemEntity modelItem = modelItemService.getEntity(id);
		
		String modelManageId=modelItem.getModelId();
		message = "字段["+modelItem.getItemLabel()+"]删除成功";
		modelItemService.delete(modelItem);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "modelItemController.do?table&modelId="+modelManageId);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 保存排序
	 * 
	 * @return
	 */
	@RequestMapping(params = "saveSort")
	@ResponseBody
	public String saveSort(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String[] ids=request.getParameterValues("id");
		modelItemService.updateSort(ids);//重新调整排序
		message="最新排序已保存";
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}
}
