package com.leimingtech.cms.controller.rolesite;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.TSRole;
import com.leimingtech.core.service.RoleServiceI;
import com.leimingtech.core.service.RoleSiteServiceI;
import com.leimingtech.core.service.SiteServiceI;
import com.leimingtech.core.service.SystemService;



/**
 * @Title: Controller
 * @Description: 站点权限
 * @author
 * @date 2015-10-20 11:16:57
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/roleSiteController")
public class RoleSiteController extends BaseController {

	private String message;
	/** 站点权限接口 */
	@Autowired
	private RoleSiteServiceI roleSiteService;
	/** 权限管理接口 */
	@Autowired
	private RoleServiceI roleService;
	@Autowired
	private SystemService systemService;
	/** 站点管理接口 */
	@Autowired
	private SiteServiceI siteService;

	/**
	 * 跳转到站点权限设置页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "formRoleSiteView")
	public ModelAndView formRoleSiteView(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		TSRole role = roleService.getEntity(roleId);

		Set<String> siteids = roleSiteService.findSiteSetByRole(roleId);

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("role", role);
		m.put("siteTreeData", siteService.getSiteTreeData(siteids).toString());
		return new ModelAndView("cms/rolesite/siteTree", m);
	}
	
	/**
	 * 保存已勾选PC栏目权限
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveSiteRole")
	@ResponseBody
	public String saveSiteRole(HttpServletRequest request){
		String roleId = request.getParameter("roleId");
		String funVal = request.getParameter("funVal");
		TSRole role = roleService.getEntity(roleId);
		JSONObject j = new JSONObject();
		try {
			message = "角色权限《"+role.getRoleName()+"》更新站点权限成功";
			roleSiteService.saveSiteRole(roleId, funVal);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			message+="<br/><span style='color:red'>注意：变更权限需要重新登录才会生效！</span>";
		} catch (Exception e) {
			message = "站点权限更新失败，原因："+e.getMessage();
			e.printStackTrace();
			return error(message).toString();
		}
		return success(message).accumulate("toUrl", "roleController.do?role").toString();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
