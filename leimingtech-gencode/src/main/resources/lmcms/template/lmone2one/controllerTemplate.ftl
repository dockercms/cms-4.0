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
	/** ${ftl_description}接口 */
	@Autowired
	private ${entityName}ServiceI ${entityName?uncap_first}Service;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	
	/**
	 * ${ftl_description}列表
	 * 
	 * @param request
	 */
	@RequestMapping(params = "${entityName?uncap_first}")
	public ModelAndView ${entityName?uncap_first}(${entityName}Entity ${entityName?uncap_first}, HttpServletRequest request) {
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
		return new ModelAndView("${webRootPackage?substring(webRootPackage?last_index_of('/')+1)}/${entityPackage}/${entityName?uncap_first}List", m);
	}

	/**
	 * ${ftl_description}添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("${entityName?uncap_first}", new ${entityName}Entity());
		return new ModelAndView("${webRootPackage?substring(webRootPackage?last_index_of('/')+1)}/${entityPackage}/${entityName?uncap_first}", m);
	}
	
	/**
	 * ${ftl_description}更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		${entityName}Entity ${entityName?uncap_first} = ${entityName?uncap_first}Service.getEntity(${primary_key_type}.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
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
		boolean isSuccess = true;
		
		if (StringUtils.isNotEmpty(${entityName?uncap_first}.getId())) {
			message = "${ftl_description}【" + ${entityName?uncap_first}.getId() + "】更新成功";
			${entityName}Entity t = ${entityName?uncap_first}Service.getEntity(${entityName?uncap_first}.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(${entityName?uncap_first}, t);
				${entityName?uncap_first}Service.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "${ftl_description}【" + ${entityName?uncap_first}.getId() + "】更新失败";
				LogUtil.error(message, e);
				isSuccess = false;
			}
		} else {
			message = "${ftl_description}【" + ${entityName?uncap_first}.getId() + "】添加成功";
			${entityName?uncap_first}Service.save(${entityName?uncap_first});
			LogUtil.info(message);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", isSuccess);
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
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		${entityName}Entity ${entityName?uncap_first} = ${entityName?uncap_first}Service.getEntity(${primary_key_type}.valueOf(id));
		message = "${ftl_description}【" + ${entityName?uncap_first}.getId() + "】删除成功";
		${entityName?uncap_first}Service.delete(${entityName?uncap_first});
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "${entityName?uncap_first}Controller.do?${entityName?uncap_first}");
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
