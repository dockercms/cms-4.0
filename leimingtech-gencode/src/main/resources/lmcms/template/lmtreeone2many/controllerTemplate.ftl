<#list originalColumns as po>
<#if po.fieldName == lmcms_table_id && lmcms_primary_key_policy == 'uuid'>
<#else>
	<#if po.fieldName?index_of("parentid")!=-1 || po.fieldName?index_of("Parentid")!=-1>
		<#assign parent=po>
	</#if>
	<#if po.fieldName?index_of("level")!=-1 || po.fieldName?index_of("Level")!=-1>
		<#assign level=po>
	</#if>
	<#if po.fieldName?index_of("sort")!=-1 || po.fieldName?index_of("Sort")!=-1>
		<#assign sort=po>
	</#if>
	<#if po.fieldName?index_of("pathids")!=-1 || po.fieldName?index_of("Pathids")!=-1>
		<#assign pathids=po>
	</#if>
	<#if po.fieldName?index_of("parentids")!=-1 || po.fieldName?index_of("Parentids")!=-1>
		<#assign parentids=po>
	</#if>
	<#if po.fieldName?index_of("name")!=-1 || po.fieldName?index_of("Name")!=-1>
		<#assign name=po>
	</#if>
</#if>
</#list>
package ${bussiPackage}.controller.${entityPackage};

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

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.service.SystemService;
import ${bussiPackage}.entity.${entityPackage}.${entityName}Entity;
import ${bussiPackage}.service.${entityPackage}.${entityName}ServiceI;

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
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	/** ${ftl_description}接口 */
	@Autowired
	private ${entityName}ServiceI ${entityName?uncap_first}Service;
	
	/**
	 * ${ftl_description}列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "${entityName?uncap_first}")
	public ModelAndView ${entityName?uncap_first}(HttpServletRequest request) {
	
		String id = request.getParameter("id");
		
		${entityName}Entity ${entityName?uncap_first} = null;
		List<${entityName}Entity> ${entityName?uncap_first}List = null;
		if (StringUtils.isNotEmpty(id) && !"-1".equals(id)) {
			${entityName?uncap_first} = ${entityName?uncap_first}Service.getEntity(${primary_key_type}.valueOf(id));
			${entityName?uncap_first}List = ${entityName?uncap_first}Service.getListByPid(id);
		} else {
			${entityName?uncap_first} = new ${entityName}Entity();
			${entityName?uncap_first}.setName("顶级节点");
			${entityName?uncap_first}List = ${entityName?uncap_first}Service.getAllRoot();
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("jstreeData", ${entityName?uncap_first}Service.getTreeJson(id).toString());
		m.put("${entityName?uncap_first}", ${entityName?uncap_first});
		m.put("${entityName?uncap_first}List", ${entityName?uncap_first}List);
		
		return new ModelAndView("${webRootPackage?substring(webRootPackage?last_index_of('/')+1)}/${entityPackage}/${entityName?uncap_first}List", m);
	}
	
	/**
	 * 显示${ftl_description}
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "show")
	public ModelAndView show(HttpServletRequest request) {
		String id = request.getParameter("id");
		
		${entityName}Entity ${entityName?uncap_first} = null;
		List<TreeEntity> ${entityName?uncap_first}List = null;
		
		if(StringUtils.isEmpty(id)|| "-1".equals(id)){
			//点击顶级节点
			${entityName?uncap_first} = new ${entityName}Entity();
			${entityName?uncap_first}.setId(null);
			${entityName?uncap_first}.setName("顶级节点");
			${entityName?uncap_first}List = ${entityName?uncap_first}Service.getAllRoot();
		}else{
			${entityName?uncap_first} =${entityName?uncap_first}Service.getEntity(${primary_key_type}.valueOf(id));
			${entityName?uncap_first}List = ${entityName?uncap_first}Service.getListByPid(${primary_key_type}.valueOf(id));
		}
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("${entityName?uncap_first}", ${entityName?uncap_first});
		m.put("${entityName?uncap_first}List", ${entityName?uncap_first}List);
		
		return new ModelAndView("${webRootPackage?substring(webRootPackage?last_index_of('/')+1)}/${entityPackage}/${entityName?uncap_first}Show", m);
	}
	
	/**
	 * ${ftl_description}添加
	 * @param request
	 * @return
	 */
	
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest request){
		String id = request.getParameter("id");
		${entityName}Entity ${entityName?uncap_first} = null;
		if(StringUtils.isNotEmpty(id)){
			${entityName?uncap_first} = ${entityName?uncap_first}Service.getEntity(${primary_key_type}.valueOf(id));
		}else{
			String pid = request.getParameter("pid");
			${entityName?uncap_first} = new ${entityName}Entity();
			${entityName}Entity parent = new ${entityName}Entity();
			if(StringUtils.isNotEmpty(pid)){
				parent = ${entityName?uncap_first}Service.getEntity(${primary_key_type}.valueOf(pid));
			}
			${entityName?uncap_first}.set${entityName}(parent);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("${entityName?uncap_first}", ${entityName?uncap_first});
		return new ModelAndView("${webRootPackage?substring(webRootPackage?last_index_of('/')+1)}/${entityPackage}/${entityName?uncap_first}", m);
	}
	
	/**
	 * ${ftl_description}修改
	 * @param request
	 * @return
	 */
	
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest request){
		String id = request.getParameter("id");
		${entityName}Entity ${entityName?uncap_first} = null;
		if(StringUtils.isNotEmpty(id)){
			${entityName?uncap_first} = ${entityName?uncap_first}Service.getEntity(${primary_key_type}.valueOf(id));
		}else{
			String pid = request.getParameter("pid");
			${entityName?uncap_first} = new ${entityName}Entity();
			${entityName}Entity parent = new ${entityName}Entity();
			if(StringUtils.isNotEmpty(pid)){
				parent = ${entityName?uncap_first}Service.getEntity(${primary_key_type}.valueOf(pid));
			}
			${entityName?uncap_first}.set${entityName}(parent);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("${entityName?uncap_first}", ${entityName?uncap_first});
		return new ModelAndView("${webRootPackage?substring(webRootPackage?last_index_of('/')+1)}/${entityPackage}/${entityName?uncap_first}", m);
	}
	
	/**
	 * ${ftl_description}保存
	 *
	 * @param tree ${ftl_description}
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(${entityName}Entity ${entityName?uncap_first}, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;
		
		if (StringUtils.isNotEmpty(${entityName?uncap_first}.getId())) {
			${entityName}Entity t = ${entityName?uncap_first}Service.getEntity(${entityName?uncap_first}.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(${entityName?uncap_first}, t);
				${entityName?uncap_first}Service.saveOrUpdate(t);
				message = "${ftl_description} 【"+${entityName?uncap_first}.get${name.fieldName?cap_first}()+"】 更新成功";
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "${ftl_description} 【"+${entityName?uncap_first}.get${name.fieldName?cap_first}()+"】 更新失败";
				LogUtil.error(message);
				isSuccess = false;
			}
			
			j.accumulate("toUrl", "${entityName?uncap_first}Controller.do?${entityName?uncap_first}&id=" + ${entityName?uncap_first}.getId());
		} else {
			if(${entityName?uncap_first}.get${entityName}() == null || StringUtils.isEmpty(${entityName?uncap_first}.get${entityName}().getId() + "")){
				${entityName?uncap_first}.set${level.fieldName?cap_first}(0);
				${entityName?uncap_first}.set${parentids.fieldName?cap_first}("0,");
				${entityName?uncap_first}.set${pathids.fieldName?cap_first}("0,");
			}else{
				${entityName}Entity parent = ${entityName?uncap_first}.get${entityName}();
				parent = ${entityName?uncap_first}Service.getEntity(parent.getId());
				${entityName?uncap_first}.set${parentids.fieldName?cap_first}(parent.get${pathids.fieldName?cap_first}());
				${entityName?uncap_first}.set${pathids.fieldName?cap_first}(parent.get${pathids.fieldName?cap_first}());
				${entityName?uncap_first}.set${level.fieldName?cap_first}(parent.get${level.fieldName?cap_first}() + 1);
			}
			${entityName?uncap_first}Service.save(${entityName?uncap_first});
			${entityName?uncap_first}.set${pathids.fieldName?cap_first}(${entityName?uncap_first}.get${pathids.fieldName?cap_first}()  + "," + ${entityName?uncap_first}.getId());
			${entityName?uncap_first}Service.saveOrUpdate(${entityName?uncap_first});
			message = "${ftl_description} 【"+${entityName?uncap_first}.get${name.fieldName?cap_first}()+"】 添加成功";
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
			LogUtil.info(message);
			j.accumulate("toUrl", "${entityName?uncap_first}Controller.do?${entityName?uncap_first}&id="+${entityName?uncap_first}.getId());
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
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
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		${entityName}Entity ${entityName?uncap_first} = ${entityName?uncap_first}Service.getEntity(${primary_key_type}.valueOf(id));
		if (${entityName?uncap_first}.get${entityName}() != null
				&& StringUtils.isNotEmpty(${entityName?uncap_first}.get${entityName}().getId() + "")) {
			j.accumulate("toUrl", "${entityName?uncap_first}Controller.do?${entityName?uncap_first}&id=" + ${entityName?uncap_first}.get${entityName}().getId());
		}else{
			j.accumulate("toUrl", "${entityName?uncap_first}Controller.do?${entityName?uncap_first}");
		}
		message = "${ftl_description}【" + ${entityName?uncap_first}.get${name.fieldName?cap_first}() + "】删除成功";
		${entityName?uncap_first}Service.delete(${entityName?uncap_first});
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
