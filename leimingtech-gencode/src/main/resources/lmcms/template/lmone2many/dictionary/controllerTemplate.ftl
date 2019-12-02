package ${bussiPackage}.controller.${entityPackage};
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.common.PlatformConstants;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.service.SystemService;
import ${bussiPackage}.entity.${entityPackage}.${entityName}Entity;
import ${bussiPackage}.service.${entityPackage}.${entityName}ServiceI;
<#list subTab as sub>
import ${bussiPackage}.entity.${sub.entityPackage}.${sub.entityName}Entity;
</#list>

/**   
 * @Title: Controller
 * @Description: ${ftl_description}
 * @author 
 * @date ${ftl_create_time}
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/${entityName?uncap_first}Controller")
public class ${entityName}Controller extends BaseController {

	private String message;
	@Autowired
	private ${entityName}ServiceI ${entityName?uncap_first}Service;
	@Autowired
	private SystemService systemService;
	
	/**
	 * ${ftl_description}列表页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "${entityName?uncap_first}")
	public ModelAndView ${entityName?uncap_first}(${entityName}Entity ${entityName?uncap_first},HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = ${entityName?uncap_first}Service.getPageList(${entityName?uncap_first}, param,
				pageSize, pageNo);
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "${entityName?uncap_first}Controller.do?${entityName?uncap_first}");
		return new ModelAndView("${webRootPackage?substring(webRootPackage?last_index_of('/')+1)}/${entityPackage}/${entityName?uncap_first}List",m);
	}

	/**
	 * ${ftl_description}添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		${entityName}Entity ${entityName?uncap_first} = null;
		if (StringUtil.isNotEmpty(id)) {
			${entityName?uncap_first} = ${entityName?uncap_first}Service.getEntity(${primary_key_type}.valueOf(id));
		} else {
			${entityName?uncap_first} = new ${entityName}Entity();
		}
		m.put("${entityName?uncap_first}", ${entityName?uncap_first});
		return new ModelAndView("${webRootPackage?substring(webRootPackage?last_index_of('/')+1)}/${entityPackage}/${entityName?uncap_first}", m);
	}
	
	/**
	 * ${ftl_description}修改
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		${entityName}Entity ${entityName?uncap_first} = null;
		if (StringUtil.isNotEmpty(id)) {
			${entityName?uncap_first} = ${entityName?uncap_first}Service.getEntity(${primary_key_type}.valueOf(id));
		} else {
			${entityName?uncap_first} = new ${entityName}Entity();
		}
		m.put("${entityName?uncap_first}", ${entityName?uncap_first});
		return new ModelAndView("${webRootPackage?substring(webRootPackage?last_index_of('/')+1)}/${entityPackage}/${entityName?uncap_first}", m);
	}
	
	/**
	 * ${ftl_description}保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(${entityName}Entity ${entityName?uncap_first}, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(${entityName?uncap_first}.getId())) {
			message = "${ftl_description}更新成功";
			${entityName}Entity t = ${entityName?uncap_first}Service.getEntity(${entityName?uncap_first}.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(${entityName?uncap_first}, t);
				${entityName?uncap_first}Service.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "${ftl_description}更新失败";
			}
		} else {
			message = "${ftl_description}添加成功";
			${entityName?uncap_first}Service.save(${entityName?uncap_first});
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "${entityName?uncap_first}Controller.do?${entityName?uncap_first}");
		String str = j.toString();
		return str;
	}

	/**
	 * ${ftl_description}删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(${entityName}Entity ${entityName?uncap_first},HttpServletRequest request) {
		JSONObject j = new JSONObject();
		${entityName?uncap_first} = ${entityName?uncap_first}Service.getEntity(${entityName?uncap_first}.getId());
		message = "${ftl_description}删除成功";
		${entityName?uncap_first}Service.delete(${entityName?uncap_first});
		systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "${entityName?uncap_first}Controller.do?${entityName?uncap_first}");
		String str = j.toString();
		return str;
	}
	<#list subTab as sub>
	/**
	 * ${sub.ftlDescription}列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "${sub.entityName?uncap_first}")
	public ModelAndView ${sub.entityName?uncap_first}(${sub.entityName}Entity ${sub.entityName?uncap_first},HttpServletRequest request) {
		String ${sub.foreignKeys[1]?uncap_first} = request.getParameter("${sub.foreignKeys[1]?uncap_first}");
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = ${entityName?uncap_first}Service.getListByPid(${sub.entityName?uncap_first}, param,
				pageSize, pageNo,${primary_key_type}.valueOf(${sub.foreignKeys[1]?uncap_first}));
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("${sub.foreignKeys[1]?uncap_first}", ${sub.foreignKeys[1]?uncap_first});
		m.put("actionUrl", "${entityName?uncap_first}Controller.do?${sub.entityName?uncap_first}");
		return new ModelAndView("${webRootPackage?substring(webRootPackage?last_index_of('/')+1)}/${sub.entityPackage}/${sub.entityName?uncap_first}List",m);
	}
	
	/**
	 * ${sub.ftlDescription}添加
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "add${sub.entityName}")
	public ModelAndView add${sub.entityName}(HttpServletRequest request) {
		String id = request.getParameter("id");
		String ${sub.foreignKeys[1]?uncap_first} = request.getParameter("${sub.foreignKeys[1]?uncap_first}");
		Map<String, Object> m = new HashMap<String, Object>();
		${sub.entityName}Entity ${sub.entityName?uncap_first} = null;
		if (StringUtil.isNotEmpty(id)) {
			${sub.entityName?uncap_first} = ${entityName?uncap_first}Service.get${sub.entityName}Entity(${primary_key_type}.valueOf(id));
		} else {
			${sub.entityName?uncap_first} = new ${sub.entityName}Entity();
		}
		request.setAttribute("${sub.foreignKeys[1]?uncap_first}", ${sub.foreignKeys[1]?uncap_first});
		m.put("${sub.entityName?uncap_first}", ${sub.entityName?uncap_first});
		return new ModelAndView("${webRootPackage?substring(webRootPackage?last_index_of('/')+1)}/${sub.entityPackage}/${sub.entityName?uncap_first}", m);
	}
	
	/**
	 * ${sub.ftlDescription}修改
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "edit${sub.entityName}")
	public ModelAndView dit${sub.entityName}(HttpServletRequest request) {
		String id = request.getParameter("id");
		String ${sub.foreignKeys[1]?uncap_first} = request.getParameter("${sub.foreignKeys[1]?uncap_first}");
		Map<String, Object> m = new HashMap<String, Object>();
		${sub.entityName}Entity ${sub.entityName?uncap_first} = null;
		if (StringUtil.isNotEmpty(id)) {
			${sub.entityName?uncap_first} = ${entityName?uncap_first}Service.get${sub.entityName}Entity(${primary_key_type}.valueOf(id));
		} else {
			${sub.entityName?uncap_first} = new ${sub.entityName}Entity();
		}
		request.setAttribute("${sub.foreignKeys[1]?uncap_first}", ${sub.foreignKeys[1]?uncap_first});
		m.put("${sub.entityName?uncap_first}", ${sub.entityName?uncap_first});
		return new ModelAndView("${webRootPackage?substring(webRootPackage?last_index_of('/')+1)}/${sub.entityPackage}/${sub.entityName?uncap_first}", m);
	}
	
	/**
	 * ${sub.ftlDescription}保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save${sub.entityName}")
	@ResponseBody
	public String save${sub.entityName}(${sub.entityName}Entity ${sub.entityName?uncap_first}, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(${sub.entityName?uncap_first}.getId())) {
			message = "${sub.ftlDescription}【"+${sub.entityName?uncap_first}.getId()+"】更新成功";
			${sub.entityName}Entity t = ${entityName?uncap_first}Service.get${sub.entityName}Entity(${sub.entityName?uncap_first}.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(${sub.entityName?uncap_first}, t);
				${entityName?uncap_first}Service.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "${sub.ftlDescription}【"+${sub.entityName?uncap_first}.getId()+"】更新失败";
			}
		} else {
			message = "${sub.ftlDescription}【"+${sub.entityName?uncap_first}.getId()+"】添加成功";
			${entityName?uncap_first}Service.save(${sub.entityName?uncap_first});
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "${entityName?uncap_first}Controller.do?${sub.entityName?uncap_first}&${sub.foreignKeys[1]?uncap_first}="+${sub.entityName?uncap_first}.get${sub.foreignKeys[1]}());
		String str = j.toString();
		return str;
	}

	/**
	 * ${sub.ftlDescription}删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del${sub.entityName}")
	@ResponseBody
	public String delSub(${sub.entityName}Entity ${sub.entityName?uncap_first},HttpServletRequest request) {
		JSONObject j = new JSONObject();
		${sub.entityName?uncap_first} = ${entityName?uncap_first}Service.get${sub.entityName}Entity(${sub.entityName?uncap_first}.getId());
		message = "${sub.ftlDescription}【"+${sub.entityName?uncap_first}.getId()+"】删除成功";
		${entityName?uncap_first}Service.delete(${sub.entityName?uncap_first});
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "${entityName?uncap_first}Controller.do?${sub.entityName?uncap_first}&${sub.foreignKeys[1]?uncap_first}="+${sub.entityName?uncap_first}.get${sub.foreignKeys[1]}());
		String str = j.toString();
		return str;
	}
	</#list>
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
