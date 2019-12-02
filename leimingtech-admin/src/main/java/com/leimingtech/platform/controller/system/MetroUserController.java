package com.leimingtech.platform.controller.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.service.statistics.StatisticsServiceI;
import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.TSDepart;
import com.leimingtech.core.entity.TSLog;
import com.leimingtech.core.entity.TSRole;
import com.leimingtech.core.entity.TSRoleUser;
import com.leimingtech.core.entity.TSUser;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.UserService;
import com.leimingtech.core.util.DBTypeUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PasswordUtil;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.core.util.oConvertUtils;
import com.leimingtech.platform.controller.UserController;

/**
 * @Title: Controller
 * @Description: 用户管理
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/metroUserController")
public class MetroUserController extends BaseController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(UserController.class);

	private UserService userService;
	private SystemService systemService;
	private String message = null;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Autowired
	private StatisticsServiceI statisticsService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(params = "table")
	public ModelAndView table(HttpServletRequest request) {
		// 获取文章列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));
		
		String userName=oConvertUtils.getString(request.getParameter("userName"));
		String realName=oConvertUtils.getString(request.getParameter("realName"));
		String departName=oConvertUtils.getString(request.getParameter("departName"));
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		Short[] userstate = new Short[] { Globals.User_Normal,
				Globals.User_ADMIN };
		
		cq.in("status", userstate);
		/*
		 * 创建关联部门表引用
		 * @author：wangyu 
		 */
		cq.createAlias("TSDepart", "td");
		if(StringUtils.isNotBlank(userName)){
			cq.like("userName", "%"+userName+"%");//用户名查询
		}
		if(StringUtils.isNotBlank(realName)){
			cq.like("realName", "%"+realName+"%");//真实姓名查询
		}
		if(StringUtils.isNotBlank(departName)){
			cq.like("td.departname", "%"+departName+"%");//部门查询 
		}
		cq.addOrder("createdtime", SortDirection.desc);
		cq.setField("createdtime,realName");
		cq.add();
		PageList pageList = this.userService.getPageList(cq, true);
		
		List<TSUser> userList = pageList.getResultList();
		addUserRoleInfo(userList);
		
		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("userList", userList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "metroUserController.do?table");
		return new ModelAndView("lmPage/user/userTab", m);
	}

	public void addUserRoleInfo(List<TSUser> userList) {
		if (userList == null || userList.size() == 0) {
			return;
		}

		for (int i = 0; i < userList.size(); i++) {
			TSUser user = userList.get(i);
			String sql = "select  role.id,role.rolename from cms_role  role ,cms_user  users,cms_role_user  role_user  where role.id=role_user.roleid and users.id=role_user.userid and users.id='"+ user.getId() + "'";
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			mapList = userService.findForJdbc(sql);
			String rol_name = "";
			for (int j = 0; j < mapList.size(); j++) {
				Map<String, Object> map = mapList.get(j);

				if (j == mapList.size() - 1) {
					rol_name += map.get("rolename");
				} else {
					rol_name += map.get("rolename") + ",";
				}
			}
			user.setUserKey(rol_name);
		}

	}

	@RequestMapping(params = "addPage")
	public ModelAndView addPage(HttpServletRequest reqeust) {
		return new ModelAndView("lmPage/user/add");
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust) {
		TSUser user = new TSUser();
		List Deplist = userService.getList(TSDepart.class);
		List Rolelist = userService.getList(TSRole.class);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("department", Deplist);
		m.put("roles", new TSRole());
		reqeust.setAttribute("user", user);
		return new ModelAndView("lmPage/user/add_model", m);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "updatePage")
	public ModelAndView updatePage(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		List Deplist = userService.getList(TSDepart.class);
		List Rolelist = userService.getList(TSRole.class);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("department", Deplist);
		m.put("roles", Rolelist);
		return new ModelAndView("lmPage/user/add", m);
	}

	@RequestMapping(params = "resetKey")
	@ResponseBody
	public String resetKey(HttpServletRequest request) {
		String id = request.getParameter("id");
		String key = request.getParameter("key");
		TSUser user = userService.getEntity(TSUser.class, id); // 用户
		user.setPassword(PasswordUtil.encrypt(user.getUserName(), key,
				PasswordUtil.getStaticSalt()));
		userService.saveOrUpdate(user);
		JSONObject j = new JSONObject();
		j.accumulate("message", "用户 " + user.getUserName()
				+ " 重置密码成功(初始密码为：123456)");
		String str = j.toString();
		return str;
	}

	/**
	 * 修改密码页面
	 * 
	 * @param reqeust
	 * @return
	 */
	@RequestMapping(params = "editKey")
	public ModelAndView editKey(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		TSUser user = userService.getEntity(TSUser.class, id); // 用户
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("user", user);
		return new ModelAndView("lmPage/user/editKey", m);
	}

	/**
	 * 修改密码页面
	 * 
	 * @param reqeust
	 * @return
	 */
	@RequestMapping(params = "editCurrentKey")
	public ModelAndView editCurrentKey(HttpServletRequest reqeust) {
		TSUser user = PlatFormUtil.getSessionUser();
		user = userService.getEntity(TSUser.class, user.getId());
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("user", user);
		return new ModelAndView("lmPage/user/editCurrentKey", m);
	}

	/**
	 * 新密码加密
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "encryption")
	@ResponseBody
	public String encryption(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		TSUser user = userService.getEntity(TSUser.class, userId); // 用户
		String pwdOld = request.getParameter("pwdOld");
		String pwdEn = PasswordUtil.encrypt(user.getUserName(), pwdOld,
				PasswordUtil.getStaticSalt());
		JSONObject j = new JSONObject();
		j.accumulate("pwdEn", pwdEn);
		String str = j.toString();
		return str;
	}

	public static void main(String[] args) {
		String username = "admin";
		String pwd = "123456";
		String pwdEn = PasswordUtil.encrypt(username, pwd,
				PasswordUtil.getStaticSalt());
		System.out.println(pwdEn);
	}

	@RequestMapping(params = "savePwd")
	@ResponseBody
	public String savePwd(HttpServletRequest request) {
		String id = request.getParameter("id");
		TSUser user = userService.getEntity(TSUser.class, id); // 用户
		String password = request.getParameter("password");
		user.setPassword(PasswordUtil.encrypt(user.getUserName(), password,
				PasswordUtil.getStaticSalt()));
		userService.saveOrUpdate(user);
		JSONObject j = new JSONObject();
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		// j.accumulate("toUrl", "metroUserController.do?table");
		String str = j.toString();
		return str;
	}

	@RequestMapping(params = "add")
	@ResponseBody
	public String add(TSUser user, HttpServletRequest request) {
		// AjaxJson j = new AjaxJson();
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(user.getId())) {
			message = "文档信息【" + user.getUserName() + "】更新成功";
			TSUser t = userService.get(TSUser.class, user.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(user, t);
				userService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "文档信息【" + user.getUserName() + "】更新失败";
			}
		} else {
			message = "文档信息【" + user.getUserName() + "】添加成功";
			user.setCreatedtime(new Date());// 创建时间
			userService.save(user);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "metroUserController.do?table");
		String str = j.toString();
		return str;
	}

	/**
	 * 用户录入
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "saveUser")
	@ResponseBody
	public String saveUser(HttpServletRequest req, TSUser user) {
		
	/*	String uName = oConvertUtils.getString(req
				.getParameter("uName"));
		user.setUserName(uName);*/
		
		JSONObject j = new JSONObject();
		// 得到用户的角色
		try {
			String roleid = oConvertUtils.getString(req.getParameter("roleid"));
			String password = oConvertUtils.getString(req
					.getParameter("pwd"));
			if (StringUtil.isNotEmpty(user.getId())) {
				TSUser users = userService
						.getEntity(TSUser.class, user.getId());
				users.setEmail(user.getEmail());
				users.setOfficePhone(user.getOfficePhone());
				users.setMobilePhone(user.getMobilePhone());
				users.setHeadPortrait(req.getParameter("headPortrait"));// 头像
				if (null != user.getTSDepart()
						&& null != user.getTSDepart().getId()) {
					users.setTSDepart(user.getTSDepart());
				}
				users.setUserName(user.getUserName().toLowerCase());
				users.setRealName(user.getRealName());
				users.setStatus(Globals.User_Normal);
				if (null != user.getActivitiSync()) {
					users.setActivitiSync(user.getActivitiSync());
				}
				// users.setPassword(PasswordUtil.encrypt(user.getUserName(),
				// password, PasswordUtil.getStaticSalt()));
				userService.saveOrUpdate(users);
				List<TSRoleUser> ru = userService.findByProperty(
						TSRoleUser.class, "TSUser.id", user.getId());
				userService.deleteAllEntitie(ru);
				message = "更新用户【" + users.getUserName() + "】成功";

				if (StringUtil.isNotEmpty(roleid)) {
					saveRoleUser(users, roleid);
				}
				
				if(PlatFormUtil.getSessionUser().getId().equals(user.getId())){
					HttpSession session = ContextHolderUtils.getSession();
					Client client=ClientManager.getInstance().getClient(session.getId());
					client.setUser(user);
					ClientManager.getInstance().addClinet(session.getId(),
							client);
				}
				
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
			} else {
				TSUser users = userService.findUniqueByProperty(TSUser.class,
						"userName", user.getUserName());
				if (users != null) {
					message = "用户: " + users.getUserName() + "已经存在";
				} else {
					user.setPassword(PasswordUtil.encrypt(user.getUserName(),
							password, PasswordUtil.getStaticSalt()));
					if (user.getTSDepart().equals("")) {
						user.setTSDepart(null);
					}
					user.setUserName(user.getUserName().toLowerCase());
					user.setStatus(Globals.User_Normal);
					user.setCreatedtime(new Date());// 创建时间
					userService.save(user);
					message = "添加用户【" + user.getUserName() + "】成功";
					if (StringUtil.isNotEmpty(roleid)) {
						saveRoleUser(user, roleid);
					}
					systemService.addLog(message, Globals.Log_Leavel_INFO,
							Globals.Log_Type_INSERT);

				}

			}
			j.accumulate("isSuccess", true);
		} catch (Exception e) {
			message = "保存异常操作失败";
			j.accumulate("isSuccess", false);
			e.printStackTrace();
		}
		j.accumulate("msg", message);
		j.accumulate("toUrl", "metroUserController.do?table");
		String str = j.toString();
		return str;
	}

	protected void saveRoleUser(TSUser user, String roleidstr) {
		String[] roleids = roleidstr.split(",");
		for (int i = 0; i < roleids.length; i++) {
			TSRoleUser rUser = new TSRoleUser();
			TSRole role = userService.getEntity(TSRole.class, roleids[i]);
			rUser.setTSRole(role);
			rUser.setTSUser(user);
			rUser.setCreatedtime(new Date());// 创建时间
			userService.save(rUser);
		}
	}

	/**
	 * 用户信息删除
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(TSUser user, HttpServletRequest req) {
		JSONObject j = new JSONObject();
		if ("admin".equals(user.getUserName())) {
			message = "超级管理员[admin]不可删除";
			j.accumulate("isSuccess", false);
		} else {
			user = userService.getEntity(TSUser.class, user.getId());
			List<TSRoleUser> roleUser = userService.findByProperty(
					TSRoleUser.class, "TSUser.id", user.getId());
			if (!user.getStatus().equals(Globals.User_ADMIN)) {
				if (roleUser.size() > 0) {
					// 删除用户时先删除用户和角色关系表
					delRoleUser(user);
					delLogUser(user);
					userService.delete(user);
					message = "删除用户：" + user.getUserName() + "成功";
					systemService.addLog(message, Globals.Log_Leavel_INFO,
							Globals.Log_Type_DEL);
				} else {
					delLogUser(user);
					userService.delete(user);
					message = "删除用户：" + user.getUserName() + "成功";
					systemService.addLog(message, Globals.Log_Leavel_INFO,
							Globals.Log_Type_DEL);
				}
				j.accumulate("isSuccess", true);
			} else {
				message = "超级管理员不可删除";
			}
		}
		j.accumulate("msg", message);
		j.accumulate("toUrl", "metroUserController.do?table");
		String str = j.toString();
		return str;
	}

	public void delRoleUser(TSUser user) {
		// 同步删除用户角色关联表
		List<TSRoleUser> roleUserList = userService.findByProperty(
				TSRoleUser.class, "TSUser.id", user.getId());
		if (roleUserList.size() >= 1) {
			for (TSRoleUser tRoleUser : roleUserList) {
				userService.delete(tRoleUser);
			}
		}
	}

	public void delLogUser(TSUser user) {
		// 同步删除用户日志关联表
		List<TSLog> tsLogList = userService.findByProperty(TSLog.class,
				"TSUser.id", user.getId());
		if (tsLogList.size() >= 1) {
			for (TSLog tsLog : tsLogList) {
				userService.delete(tsLog);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		TSUser user = null;
		user = userService.getEntity(TSUser.class, id); // 用户
		TSDepart depart = new TSDepart();
		if (null != user.getTSDepart()) {
			depart = userService
					.get(TSDepart.class, user.getTSDepart().getId());
		}
		List Deplist = userService.getList(TSDepart.class);
		// List Rolelist= systemService.getList(TSRole.class);
		String sql = "select  role.id,role.rolename from cms_role  role ,cms_user  users,cms_role_user  role_user  where role.id=role_user.roleid and users.id=role_user.userid and users.id='"
				+ user.getId() + "'";
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		mapList = userService.findForJdbc(sql);
		Map<String, Object> m = new HashMap<String, Object>();
		String rol_name = "";
		String rol_id = "";
		for (int i = 0; i < mapList.size(); i++) {
			Map<String, Object> map = mapList.get(i);

			if (i == mapList.size() - 1) {
				rol_name += map.get("rolename");
				rol_id += map.get("id");
			} else {
				rol_name += map.get("rolename") + ",";
				rol_id += map.get("id") + ",";
			}
			// rol_name+=mapList.get(i);
		}
		m.put("user", user);
		m.put("department", Deplist);
		// m.put("roles", Rolelist);
		m.put("depart", depart);
		m.put("role", mapList);
		m.put("roles", rol_name);
		m.put("roles_id", rol_id);
		return new ModelAndView("lmPage/user/add", m);
	}

	/**
	 * 修改当前用户信息
	 * 
	 * @param reqeust
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "updateCurrentUserModel")
	public ModelAndView updateCurrentUserModel(HttpServletRequest reqeust) {
		TSUser user = PlatFormUtil.getSessionUser();
		if (null == user) {
			user = new TSUser();
		} else {
			user = userService.getEntity(TSUser.class, user.getId());
		}
		TSDepart depart = new TSDepart();
		if (null != user.getTSDepart()) {
			depart = userService
					.get(TSDepart.class, user.getTSDepart().getId());
		}
		List Deplist = userService.getList(TSDepart.class);
		String sql = "select  role.id,role.rolename from cms_role  role ,cms_user  users,cms_role_user  role_user  where role.id=role_user.roleid and users.id=role_user.userid and users.id='"
				+ user.getId() + "'";
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		mapList = userService.findForJdbc(sql);
		Map<String, Object> m = new HashMap<String, Object>();
		String rol_name = "";
		String rol_id = "";
		for (int i = 0; i < mapList.size(); i++) {
			Map<String, Object> map = mapList.get(i);

			if (i == mapList.size() - 1) {
				rol_name += map.get("rolename");
				rol_id += map.get("id");
			} else {
				rol_name += map.get("rolename") + ",";
				rol_id += map.get("id") + ",";
			}
		}
		m.put("user", user);
		m.put("department", Deplist);
		m.put("depart", depart);
		m.put("role", mapList);
		m.put("roles", rol_name);
		m.put("roles_id", rol_id);
		return new ModelAndView("lmPage/user/updateCurrentuserinfo", m);
	}

	/**
	 * 加载部门树
	 * 
	 * @return
	 */
	@RequestMapping(params = "departQuery")
	@ResponseBody
	public JSONArray departQuery(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		JSONArray jsonArray = userService.departQuery(userId);

		return jsonArray;
	}

	/**
	 * ztree 用户管理 角色
	 * 
	 * @param request
	 * @return json
	 * @author larry
	 */
	@RequestMapping(params = "loadRole")
	@ResponseBody
	public JSONArray loadRole(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		String userId = request.getParameter("userId");
		String id = request.getParameter("id");

		String sql = "select  role.id,role.rolename from cms_role  role ,cms_user  users,cms_role_user  role_user  where role.id=role_user.roleid and users.id=role_user.userid and users.id='"
				+ userId + "'";
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		mapList = userService.findForJdbc(sql);
		Map<String, Object> m = new HashMap<String, Object>();
		String[] rol_name = new String[mapList.size()];
		for (int i = 0; i < mapList.size(); i++) {
			Map<String, Object> map = mapList.get(i);
			rol_name[i] = (String) map.get("rolename");
			// rol_name+=mapList.get(i);
		}

		List<TSRole> Rolelist = userService.getList(TSRole.class);
		JSONArray jsonArray = new JSONArray();
		for (TSRole role : Rolelist) {
			json.put("id", role.getId());
			json.put("name", role.getRoleName());
			json.put("open", false);
			for (int i = 0; i < rol_name.length; i++) {
				if (role.getRoleName().equals(rol_name[i])) {
					json.put("checked", true);
					break;
				} else {
					json.put("checked", false);
				}
			}
			jsonArray.add(json);
		}

		return jsonArray;

	}

	/**
	 * ztree 用户管理 角色 (新增)
	 * 
	 * @param request
	 * @return json
	 * @author larry
	 */
	@RequestMapping(params = "loadAddRole")
	@ResponseBody
	public JSONArray loadAddRole(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		String id = request.getParameter("id");

		List<TSRole> Rolelist = userService.getList(TSRole.class);
		JSONArray jsonArray = new JSONArray();
		for (TSRole role : Rolelist) {
			json.put("id", role.getId());
			json.put("name", role.getRoleName());
			json.put("open", false);
			json.put("checked", false);
			jsonArray.add(json);
		}
		return jsonArray;
	}

	/**
	 * 校验用户名是否注册
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "checkUserName")
	@ResponseBody
	public String checkUserName(HttpServletRequest req) {
		String userName1 = req.getParameter("userName");
		JSONObject j = new JSONObject();
		String userName = userName1.trim();
		List<TSUser> userList = userService.findByProperty(TSUser.class,
				"userName", userName);
		if (userList.size() != 0) {
			j.accumulate("isSuccess", false);
			j.accumulate("msg", "该用户名已注册，请重新填写~");
		} else {
			j.accumulate("isSuccess", true);
		}
		String str = j.toString();
		return str;
	}
	/**
	 * 工作报表
	 * @author wy
	 * 2016/3/18
	 */
	@RequestMapping(params = "workStatement")
	public ModelAndView WorkStatement(HttpServletRequest reqeust) {
		TSUser user = PlatFormUtil.getSessionUser();
		if (null == user) {
			user = new TSUser();
		} else {
			user = userService.getEntity(TSUser.class, user.getId());
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("user", user);
		return new ModelAndView("lmPage/user/workStatement",m);
		
	}
	/**
	 * 加载统计图
	 * @param request
	 * @return
	 */
	@RequestMapping(params ="loadStat")
	public ModelAndView loadStat(HttpServletRequest request){
		//年
		String year = request.getParameter("year");
		//月
		String month = request.getParameter("month");
		//ContentCatEntity contentCat = statisticsService.get(ContentCatEntity.class, String.valueOf(contentCatId));
		Map<String , Object> m = new HashMap<String, Object>();
		m.put("searchYear", year);
		m.put("searchMonth", month);
		return new ModelAndView("lmPage/user/statementPage",m); 
	}
	/**
	 * 加载统计数据
	 * @param request
	 * @return
	 */
	@RequestMapping(params="loadDataStat")
	@ResponseBody
	public JSONArray loadDataStat(HttpServletRequest request){
		//年
		String searchYear = request.getParameter("searchYear");
		//月
		String searchMonth = request.getParameter("searchMonth");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = searchYear;
		String month = searchMonth;
		if(StringUtils.isEmpty(searchYear)){
			year = sdf.format(new Date());
		}
		JSONArray jsonArray = new JSONArray();
		//年统计
		jsonArray = yearStat(year,month);
		return jsonArray;
	}
	/**
	 * 按年、月、统计
	 * @param year
	 * @return
	 */
	public JSONArray yearStat(String year,String month){
		TSUser user = PlatFormUtil.getSessionUser();
		if (null == user) {
			user = new TSUser();
		} else {
			user = userService.getEntity(TSUser.class, user.getId());
		}
		JSONArray jsonArray = new JSONArray();
		//获取选中月的天数
		int days = 0;
		if(!month.equals("0")){
			String date = year+"/"+month;
			Calendar rightNow = Calendar.getInstance();
			SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM"); 
			try {
				rightNow.setTime(simpleDate.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		if(Integer.valueOf(month)<10&&Integer.valueOf(month)!=0){
			month = "0"+month;
		}
		//数据库
		String dbType = DBTypeUtil.getDBType();
		//选中年
		if(!year.equals("0")){
			//按月进行查询，显示天数
			jsonArray = this.days(days, year, month,dbType);
		//全部年
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			int yearV = Integer.valueOf(sdf.format(new Date()));
			for(int i=0;i<10;i++){
				JSONObject jsonObject = new JSONObject();
				int value = yearV-i;
				String sql = "",sql1 = "";
				if(month.equals("0")){  
					if("mysql".equals(dbType)){
						sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content where date_format(published,'%Y')='"+value+"' and cms_content.publishedbyid='"
				+ user.getId() + "'";
						sql1= "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y')='"+value+"'";
					}
					if("sqlserver".equals(dbType)){
						sql = "select count(*) counts,isnull(sum(pv),0) pv from cms_content where year(published)='"+value+"' and cms_content.publishedbyid='"
				+ user.getId() + "'";
						sql1= "select count(*) counts1 from cms_commentary where year(commentaryTime)='"+value+"'";
					}
					if("oracle".equals(dbType)){
						sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy')='"+value+"' and cms_content.publishedbyid='"
				+ user.getId() + "'";
						sql1= "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy')='"+value+"'";
					}
				}else{
					if("mysql".equals(dbType)){
						sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content where date_format(published,'%Y-%m')='"+value+"-"+month+"' and cms_content.publishedbyid='"
				+ user.getId() + "'";
						sql1 = "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y-%m')='"+value+"-"+month+"'";
					}
					if("sqlserver".equals(dbType)){
						sql = "select count(*) counts,isnull(sum(pv),0) pv from cms_content where year(published)='"+value+"' and month(published)='"+month+"' and cms_content.publishedbyid='"
				+ user.getId() + "'";
						sql1 = "select count(*) counts1 from cms_commentary where year(commentaryTime)='"+value+"' and month(commentaryTime)='"+month+"'";
					}
					if("oracle".equals(dbType)){
						sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy-MM')='"+value+"-"+month+"' and cms_content.publishedbyid='"
				+ user.getId() + "'";
						sql1 = "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy-MM')='"+value+"-"+month+"'";
					}
				}
				sql1 += " and contentId in (select id from cms_content where 1=1";
				
				
				sql1 += ")";
				List<Map<String,Object>> mapList1 = statisticsService.findForJdbc(sql);
				List<Map<String,Object>> mapList2 = statisticsService.findForJdbc(sql1);
				if(mapList1.size()!=0&&mapList2.size()!=0){
					jsonObject.put("count", mapList1.get(0).get("counts"));
					jsonObject.put("pv", mapList1.get(0).get("pv"));
					jsonObject.put("count1", mapList2.get(0).get("counts1"));
					jsonObject.put("date", value);
					jsonArray.add(jsonObject);
				}else{
					jsonObject.put("count", 0);
					jsonObject.put("pv", 0);
					jsonObject.put("count1", 0);
					jsonObject.put("date", value);
					jsonArray.add(jsonObject);
				}
			}
		}
		return jsonArray;
	}
	/**
	 * 按月进行查询，显示天数
	 * @param days
	 * @param year
	 * @param month
	 * @return
	 */
	public JSONArray days(int days,String year,String month,String dbType) {
		TSUser user = PlatFormUtil.getSessionUser();
		if (null == user) {
			user = new TSUser();
		} else {
			user = userService.getEntity(TSUser.class, user.getId());
		}
		JSONArray jsonArray = new JSONArray();
		
		if(!month.equals("0")){
			for(int i=1;i<days+1;i++){
				JSONObject jsonObject = new JSONObject();
				String sql = "",sql1 = "";
				if("mysql".equals(dbType)){
					sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content where date_format(published,'%Y-%m-%d')='"+year+"-"+month+"-"+i+"' and cms_content.publishedbyid='"
				+ user.getId() + "'";
					sql1 = "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y-%m-%d')='"+year+"-"+month+"-"+i+"'";
				}
				if("sqlserver".equals(dbType)){
					sql = "select count(*) counts,isnull(sum(pv),0) pv from cms_content where year(published)='"+year+"' and month(published)='"+month+"' and day(published)='"+i+"' and cms_content.publishedbyid='"
				+ user.getId() + "'";
					sql1 = "select count(*) counts1 from cms_commentary where year(commentaryTime)='"+year+"' and month(commentaryTime)='"+month+"' and day(commentaryTime)='"+i+"'";
				}
				if("oracle".equals(dbType)){
					sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy-MM-dd')='"+year+"-"+month+"-"+i+"' and cms_content.publishedbyid='"
				+ user.getId() + "'";
					sql1 = "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy-MM-dd')='"+year+"-"+month+"-"+i+"'";
				}
				if(i<10){
					if("mysql".equals(dbType)){
						sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content  where date_format(published,'%Y-%m-%d')='"+year+"-"+month+"-0"+i+"' and cms_content.publishedbyid='"
				+ user.getId() + "'";
						sql1 = "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y-%m-%d')='"+year+"-"+month+"-0"+i+"'";
					}
					if("oracle".equals(dbType)){
						sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy-MM-dd')='"+year+"-"+month+"-0"+i+"' and cms_content.publishedbyid='"
				+ user.getId() + "'";
						sql1 = "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy-MM-dd')='"+year+"-"+month+"-0"+i+"'";
					}
				}
				sql1 += " and contentId in (select id from cms_content where 1=1";
				
				sql1 += ")";
				List<Map<String,Object>> mapList1 = statisticsService.findForJdbc(sql);
				List<Map<String,Object>> mapList2 = statisticsService.findForJdbc(sql1);
				if(mapList1.size()!=0&&mapList2.size()!=0){
					jsonObject.put("count", mapList1.get(0).get("counts"));
					jsonObject.put("pv", mapList1.get(0).get("pv"));
					jsonObject.put("count1", mapList2.get(0).get("counts1"));
					jsonObject.put("date", i);
					jsonArray.add(jsonObject);
				}else{
					jsonObject.put("count", 0);
					jsonObject.put("pv", 0);
					jsonObject.put("count1", 0);
					jsonObject.put("date", i);
					jsonArray.add(jsonObject);
				}
			}
		}else{//按年查询，显示所有月
			for(int i=1; i<13; i++){
				JSONObject jsonObject = new JSONObject();
				String sql = "",sql1 = "";
				if("mysql".equals(dbType)){
					sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content where date_format(published,'%Y-%m')='"+year+"-"+i+"' and cms_content.publishedbyid='"
				+ user.getId() + "'";
					sql1 = "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y-%m')='"+year+"-"+i+"'";
				}
				if("sqlserver".equals(dbType)){
					sql = "select count(*) counts,isnull(sum(pv),0) pv from cms_content where year(published)='"+year+"' and month(published)='"+i+"' and cms_content.publishedbyid='"
				+ user.getId() + "'";
					sql1 = "select count(*) counts1 from cms_commentary where year(commentaryTime)='"+year+"' and month(commentaryTime)='"+i+"'";
				}
				if("oracle".equals(dbType)){
					sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy-MM')='"+year+"-"+i+"' and cms_content.publishedbyid='"
				+ user.getId() + "'";
					sql1 = "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy-MM')='"+year+"-"+i+"'";
				}
				if(i<10){
					if("mysql".equals(dbType)){
						sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content where date_format(published,'%Y-%m')='"+year+"-0"+i+"' and cms_content.publishedbyid='"
				+ user.getId() + "'";
						sql1 = "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y-%m')='"+year+"-0"+i+"'";
					}
					if("oracle".equals(dbType)){
						sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy-MM')='"+year+"-0"+i+"' and cms_content.publishedbyid='"
				+ user.getId() + "'";
						sql1 = "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy-MM')='"+year+"-0"+i+"'";
					}
				}
				sql1 += " and contentId in (select id from cms_content where 1=1";
				
				sql1 += ")";
				List<Map<String,Object>> mapList1 = statisticsService.findForJdbc(sql);
				List<Map<String,Object>> mapList2 = statisticsService.findForJdbc(sql1);
				if(mapList1.size()!=0&&mapList2.size()!=0){
					jsonObject.put("count", mapList1.get(0).get("counts"));
					jsonObject.put("pv", mapList1.get(0).get("pv"));
					jsonObject.put("count1", mapList2.get(0).get("counts1"));
					jsonObject.put("date", i);
					jsonArray.add(jsonObject);
				}else{
					jsonObject.put("count", 0);
					jsonObject.put("pv", 0);
					jsonObject.put("count1", 0);
					jsonObject.put("date", i);
					jsonArray.add(jsonObject);
				}
				
			}
		}
		return jsonArray;
	}
	
}

