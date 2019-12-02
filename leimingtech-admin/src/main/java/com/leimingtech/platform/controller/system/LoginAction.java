package com.leimingtech.platform.controller.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.leimingtech.core.util.*;
import com.leimingtech.platform.service.function.FunctionServiceI;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.leimingtech.cms.json.AjaxJson;
import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.NumberComparator;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.TSFunction;
import com.leimingtech.core.entity.TSRole;
import com.leimingtech.core.entity.TSRoleUser;
import com.leimingtech.core.entity.TSUser;
import com.leimingtech.core.extend.datasource.DataSourceContextHolder;
import com.leimingtech.core.extend.datasource.DataSourceType;
import com.leimingtech.core.service.SiteServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.UserService;

/**
 * 登陆初始化控制器
 * 
 * 
 */
@Controller
@RequestMapping("/loginAction")
public class LoginAction {

	private SystemService systemService;

	private UserService userService;
	@Autowired
	private FunctionServiceI functionService;
	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 检查用户名称
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "checkuser")
	@ResponseBody
	public String checkuser(TSUser user, HttpServletRequest req) {
		int users = userService.getList(TSUser.class).size();
		String userName = req.getParameter("userName");
		String userNames=userName.toLowerCase();
		String pwd = req.getParameter("password");
		String password = PasswordUtil.encrypt(user.getUserName().toLowerCase(), pwd,
				PasswordUtil.getStaticSalt());
		String hql = " from TSUser u where u.userName = '" + userNames
				+ "' and u.password='" + password + "'";
		List<TSUser> userList = userService.findByQueryString(hql);
		if (userList.size() != 0) {
			user = userList.get(0);
		}
		HttpSession session = ContextHolderUtils.getSession();
		DataSourceContextHolder
				.setDataSourceType(DataSourceType.dataSource_lmcms);
		JSONObject j = new JSONObject();
		// 添加验证码
		String randCode = req.getParameter("randCode");
		if(StringUtils.isEmpty(randCode)){
			j.accumulate("msg", "请输入验证码");
			j.accumulate("isSuccess", false);
			return j.toString();
		}else if(!randCode.equalsIgnoreCase(String.valueOf(session.getAttribute("randCode")))){
			j.accumulate("msg", "验证码错误");
			j.accumulate("isSuccess", false);
			return j.toString();
		}else if(users == 0){
			j.accumulate("msg", "无用户");
			j.accumulate("isSuccess", false);
		}else if(userList.size() == 0){
			j.accumulate("msg", "用户名或密码错误!");
			j.accumulate("isSuccess", false);
		}else {
			TSUser u = userService.checkUserExits(user);
			if (u != null) {
				// if (user.getUserKey().equals(u.getUserKey())) {
				if (true) {

					Subject subject = SecurityUtils.getSubject();
					UsernamePasswordToken token = new UsernamePasswordToken(userNames,password);
					subject.login(token);

					Client client=ClientManager.getInstance().getClient(session.getId());
					String logInfo = "["+user.getUserName()+"]登录成功";
					client.setIp(IpUtil.getIpAddr(req));
					client.setLogindatetime(new Date());
					client.setUser(u);
					ClientManager.getInstance().addClinet(session.getId(),
							client);
					j.accumulate("msg", logInfo);
					j.accumulate("isSuccess", true);
					// 添加登陆日志
					systemService.addLog(logInfo,
							Globals.Log_Leavel_INFO, Globals.Log_Type_LOGIN);
				}
			} 
		}
		//添加验证码
		String str = j.toString();
		return str;
	}

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "login")
	public ModelAndView login(HttpServletRequest request, String siteid) {
		DataSourceContextHolder
				.setDataSourceType(DataSourceType.dataSource_lmcms);
		TSUser user = PlatFormUtil.getSessionUser();
		String roles = "";
		if (user != null) {
			List<TSRoleUser> rUsers = userService.findByProperty(
					TSRoleUser.class, "TSUser.id", user.getId());
			PlatFormUtil.getFunctionSet(rUsers);
			CMSUtil.initPCCatalogPermission();// 初始化PC栏目用户权限
			MobileUtil.initMobileCatalogPermission();// 初始化移动栏目用户权限
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				roles += role.getRoleName() + ",";
			}
			if (roles.length() > 0) {
				roles = roles.substring(0, roles.length() - 1);
			}
			//获得自己权限中的站点
			List<SiteEntity> siteList =userService.getRoleSite();
			
			String path = "http://" + request.getServerName() + ":"
					+ request.getServerPort() + request.getContextPath();
			request.setAttribute("siteList", siteList);
			request.setAttribute("roleName", roles);
			request.setAttribute("userName", user.getUserName());
			request.setAttribute("realName", user.getRealName());
			request.setAttribute("headPortrait", user.getHeadPortrait());
			request.setAttribute("loginPath", path);

			List<TSFunction> functionList = PlatFormUtil.getFunctionMap(user)
					.get(0);

			if(functionList==null){
				request.setAttribute("horezantalMenu",new ArrayList<TSFunction>());// 头部横向一级菜单2014/04/21
			}else{
				try {
					request.setAttribute("horezantalMenu",
							MetroMenu.getHorezantalMenu(functionList));// 头部横向一级菜单2014/04/21
				} catch (Exception e) {
					LogUtil.error("初始化系统菜单错误",e);
					ClientManager.getInstance().removeClinet(
							ContextHolderUtils.getSession().getId());
					return new ModelAndView("lmPage/main/login");
				}
			}
			ModelAndView mav = new ModelAndView("lmPage/main/main");
			return mav;
		} else {
			return new ModelAndView("lmPage/main/login");
		}

	}

	/**
	 * 点击头部菜单 获取左边子菜单freemarke
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "LeftMenu")
	public ModelAndView LeftMenu(HttpServletRequest request) {
		String funid = request.getParameter("topid");
		List<TSFunction> childFunList = null;
		if (StringUtils.isNotEmpty(funid)) {
			childFunList=functionService.getListByPid(funid);
			HttpSession session = ContextHolderUtils.getSession();

			Client client = ClientManager.getInstance().getClient(
					session.getId());
			if (client == null) {
				client = ClientManager.getInstance().getClient(
						ContextHolderUtils.getRequest().getParameter(
								"sessionId"));
			}

			Set<String> privurl = (Set<String>) client.getPrivurl();
			filterFun(childFunList, privurl);
		} else {
			childFunList = new ArrayList<TSFunction>();
		}
		Map<String, Object> m = new HashMap<String, Object>();

		m.put("list", childFunList);
		return new ModelAndView("lmPage/main/leftchild", m);
	}

	private void filterFun(List<TSFunction> childFunList, Set<String> privurl) {

		if(privurl==null || privurl.size()==0){
			childFunList.clear();
			return;
		}

		if (childFunList != null && childFunList.size() > 0) {
			int maxval = childFunList.size();
			for (int i = maxval - 1; i >= 0; i--) {
				TSFunction function =childFunList.get(i);
				if (!privurl.contains(function.getFunctionUrl())) {
					childFunList.remove(i);
					continue;
				}
				if ("#".equals(function.getFunctionUrl())) {
					Map<String, TSFunction> functionMap = PlatFormUtil
							.getUserFunction(PlatFormUtil.getSessionUser());
					if (functionMap.get(function.getId()) == null) {
						childFunList.remove(i);
						continue;
					}
				}

				List<TSFunction> childList=functionService.getListByPid(function.getId());

				filterFun(childList, privurl);
			}
		}
	}

	/**
	 * 退出系统
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		//shiro退出
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()){
			subject.logout();
		}
		String logInfo = "已退出登陆";
		systemService.addLog(logInfo, Globals.Log_Leavel_INFO, Globals.Log_Type_EXIT);
		ClientManager.getInstance().removeClinet(session.getId());
		ModelAndView modelAndView = new ModelAndView(new RedirectView(
				"loginAction.do?login"));

		return modelAndView;
	}

	/**
	 * 首页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "home")
	public ModelAndView home(HttpServletRequest request) {
		return new ModelAndView("login");
	}

	/**
	 * 无权限页面提示跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "noAuth")
	public ModelAndView noAuth(HttpServletRequest request,HttpServletResponse response) {
		Boolean isalert = Boolean.valueOf(request.getParameter("alert"));
		Boolean ajax = Boolean.valueOf(request.getParameter("ajax"));
		if (isalert) {
			return new ModelAndView("lmPage/main/noAuthAlert");
		}else if(ajax){
			JSONObject j=new JSONObject();
			j.accumulate("isSuccess", false);
			j.accumulate("msg", "对不起，你无此权限");
			response.setContentType("text/html;charset=utf-8");
			try {
				response.getWriter().println("\"{\\\"isSuccess\\\":false,\\\"msg\\\":\\\"对不起，你无此权限\\\"}\"");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}else {
			return new ModelAndView("lmPage/main/noAuth");
		}
		
	}

}
