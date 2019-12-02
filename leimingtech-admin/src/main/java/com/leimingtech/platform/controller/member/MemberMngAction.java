package com.leimingtech.platform.controller.member;


import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.*;
import com.leimingtech.core.entity.member.MemberEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.UserService;
import com.leimingtech.core.service.depart.DepartServiceI;
import com.leimingtech.core.util.PasswordUtil;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.core.util.oConvertUtils;
import com.leimingtech.platform.controller.DepartController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 会员管理
 * 
 * @author liuzhen 2014年3月28日 16:06:31
 * 
 */
@Controller
@RequestMapping("/memberMngAction")
public class MemberMngAction extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DepartController.class);

	private UserService userService;

	@Autowired
	private SystemService systemService;
	@Autowired
	private DepartServiceI departService;
	private String message;

	/**
	 * 获取部门列表信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request, TSDepart depart) {
		String id = request.getParameter("id");
		TSDepart parentDepart = null;
		List<MemberEntity> memberList=new ArrayList<MemberEntity>();
		if(StringUtils.isEmpty(id)||"-1".equals(id)){
			//点击顶级菜单
			parentDepart = new TSDepart();
			parentDepart.setId(null);
			parentDepart.setDepartname("顶级菜单");

		}else{
			parentDepart = departService.findUniqueByProperty(TSDepart.class, "id", id);
			List<MemberDepart> memberDepartListList = userService.findByProperty(MemberDepart.class, "depart.id",id );
			if(memberDepartListList.size()!=0){
				for(MemberDepart memberDepart:memberDepartListList){
					memberList.add(memberDepart.getMember());
				}
			}

		}
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("jstreeData", departService.getTreeJson(id).toString());
			m.put("parentDepart", depart);
			m.put("memberList", memberList);
			m.put("selectId", id);

		return new ModelAndView("lmPage/memberDepart/depart_list",m);
	}

	/**
	 * 保存会员部门关联关系
	 * @param request
	 * @param departId
     * @return
     */
	@RequestMapping(params = "saveMemberDepart")
	@ResponseBody
	public String saveMemberDepart(HttpServletRequest request,String departId) {
		
		String[] checkOnes=request.getParameterValues("checkOne");
		
		TSDepart depart = userService.getEntity(TSDepart.class,departId);
		JSONObject j = new JSONObject();
		try {
			message = "《"+depart.getDepartname()+"》关联用户成功";
			departService.saveDepartMember(departId, checkOnes);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} catch (Exception e) {
			message = "更新失败，原因："+e.getMessage();
			e.printStackTrace();
			return error(message).toString();
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "memberMngAction.do?list&id="+departId);
		String str = j.toString();
		return str;
	}
	@RequestMapping(params = "delMemberDepart")
	@ResponseBody
	public String delMemberDepart(HttpServletRequest req,String memberId,String departId) {

		JSONObject j = new JSONObject();
		userService.executeSql("delete FROM cms_member_depart where memberId=? and departId=? ",memberId,departId);
		message = "删除成功";
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "memberMngAction.do?list&id="+departId);
		String str = j.toString();
		return str;
	}
	/**
	 * 会员列表页
	 * @param request
	 * @return
     */
	@RequestMapping(params = "findMemberPage")
	public ModelAndView findMemberPage(MemberEntity member,HttpServletRequest request) {
		//获取请求组类型的ID
		int pageSize = org.apache.commons.lang3.StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = org.apache.commons.lang3.StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		String departId = request.getParameter("departId");
		String mIds="";
		if(departId!=null){
			List<MemberDepart> mDepartList = userService.findByProperty(MemberDepart.class,"depart.id",departId);
			if(mDepartList.size()!=0){
				for(MemberDepart memberDepart:mDepartList){
					mIds+=memberDepart.getMember().getId()+",";
				}
			}
		}
		CriteriaQuery cq = new CriteriaQuery(MemberEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq,member, param);
		// 排序条件
		cq.addOrder("createtime", SortDirection.desc);

		cq.add();
		PageList pageList = userService.getPageList(cq, true);
		List<MemberEntity> memberList = pageList.getResultList();
		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("mIds", mIds);
		m.put("pageCount", pageCount);
		m.put("searchMap", param);
		m.put("memberList", memberList);
		m.put("departId", departId);
		m.put("actionUrl", "memberMngAction.do?findMemberPage");
		return new ModelAndView("lmPage/memberDepart/memberList",m);
	}
	
	/**
	 * 方法描述:  成员列表dataGrid
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid 
	 * 返回类型： void
	 */
	@RequestMapping(params = "userList")
	public ModelAndView userList(TSUser user,HttpServletRequest request, HttpServletResponse response) {
		
		TSUser loginuser = PlatFormUtil.getSessionUser();
		HttpSession session = ContextHolderUtils.getSession();
		String departid = oConvertUtils.getString(request.getParameter("departid"));
		// 登陆者的权限
		if (loginuser.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(new RedirectView("loginAction.do?login"));
		}
		// 获取部门列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(TSUser.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, user, param);
		
		if (!StringUtil.isEmpty(departid)) {
			DetachedCriteria dc = cq.getDetachedCriteria();
			DetachedCriteria dcDepart = dc.createCriteria("TSDepart");
			dcDepart.add(Restrictions.eq("id", departid));
		}
		Short[] userstate = new Short[] { Globals.User_Normal, Globals.User_ADMIN };
		cq.in("status", userstate);
		cq.add();
		PageList pageList = departService.getPageList(cq, true);
		List<TSUser> userList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("userList", userList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "departAction.do?userList");
		return new ModelAndView("lmPage/memberDepart/depart_userList",m);
	}
	

	

	
	protected void saveRoleUser(TSUser user, String roleidstr) {
		String[] roleids = roleidstr.split(",");
		for (int i = 0; i < roleids.length; i++) {
			TSRoleUser rUser = new TSRoleUser();
			TSRole role = departService.getEntity(TSRole.class, roleids[i]);
			rUser.setTSRole(role);
			rUser.setTSUser(user);
			rUser.setCreatedtime(new Date());//创建时间
			departService.save(rUser);
		}
	}

	
	public void delRoleUser(TSUser user) {
		// 同步删除用户角色关联表
		List<TSRoleUser> roleUserList = departService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
		if (roleUserList.size() >= 1) {
			for (TSRoleUser tRoleUser : roleUserList) {
				departService.delete(tRoleUser);
			}
		}
	}

	/**
	 * 加载下级菜单
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loadMenu")
	@ResponseBody
	public JSONArray loadMenu(HttpServletRequest request) {
		String id = request.getParameter("id");
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
		cq.eq("TSPDepart.id", id);
		cq.add();
		List<TSDepart> departList = departService.getListByCriteriaQuery(cq, false);
		JSONArray jsonArray = new JSONArray();
		for(TSDepart depart : departList){
			JSONObject json = new JSONObject();
			if(depart.getTSDeparts() != null && depart.getTSDeparts().size() > 0){
				json.put("text", depart.getDepartname());
				json.put("value", depart.getId());
				json.put("leaf", false);
				json.put("expanded", false);
				json.put("cls", "folder");
				json.put("id", depart.getId());
				json.put("href", "departAction.do?loadMenu&id=" + depart.getId());
				json.put("data-role", "branch");
				json.put("children", new JSONArray());
			}else{
				json.put("text", "<i class=\"icon-file-text\" style=\"width: 1em;margin-right: 4px;\"></i>"+depart.getDepartname());
				json.put("value", depart.getId());
				json.put("leaf", true);
				json.put("href", "javascript:void(0);");
				json.put("data-role", "leaf");
				json.put("id", depart.getId());
				json.put("checked", false);
			}
			jsonArray.add(json);
		}
		return jsonArray;
	}
	
	/**
	 * 加载下级菜单
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "menuTable")
	public ModelAndView menuTable(HttpServletRequest request) {
		String id = request.getParameter("id");
		TSDepart parentDepart  =  null;
		List<MemberEntity> memberList= new ArrayList<MemberEntity>();
		if(StringUtils.isEmpty(id)||"-1".equals(id)){
			//点击顶级菜单
			parentDepart = new TSDepart();
			parentDepart.setId(null);
			parentDepart.setDepartname("顶级菜单");


		}else{
			List<MemberDepart> memberDepartList = departService.findByProperty(MemberDepart.class, "depart.id",id );
			if(memberDepartList.size()!=0){
				for(MemberDepart memberDepart:memberDepartList){
					memberList.add(memberDepart.getMember());
				}
			}
		}
		parentDepart = departService.findUniqueByProperty(TSDepart.class, "id", id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("parentDepart", parentDepart);
		m.put("memberList", memberList);
		m.put("selectId", id);
		return new ModelAndView("lmPage/memberDepart/menuTable", m);
	}
	
	@RequestMapping(params = "editModelPage")
	public ModelAndView editModelPage(HttpServletRequest request){
		String id = request.getParameter("id");
		String selectId = request.getParameter("selectId");
		
		List<TSDepart> departList = departService.loadAll(TSDepart.class);
		Map<String, Object> m = new HashMap<String, Object>();
		TSDepart depart = null;
		if(StringUtil.isNotEmpty(id)){
			depart = departService.getEntity(TSDepart.class, id);
			if(StringUtil.isNotEmpty(depart.getTSPDepart())){
				m.put("selectId",depart.getTSPDepart().getId());
			}else{
				m.put("selectId","");
			}
		}else{
			depart = new TSDepart();
			m.put("selectId", selectId);
		}
		m.put("depart", depart);
		m.put("departList", departList);
		return new ModelAndView("lmPage/memberDepart/depart_saveOrUpdate", m);
	}
	/**
	 * ztree 机构管理 角色菜单
	 *
	 * @param request
	 * @return json
	 * @author larry
	 */
	@RequestMapping(params = "loadRole")
	@ResponseBody
	public JSONArray loadRole(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		String departId = request.getParameter("departId");

		String sql = "select  role.id,role.rolename from cms_role as role ,cms_depart as depart,cms_role_depart as role_depart  where role.id=role_depart.roleid and depart.id=role_depart.departid and depart.id='"
				+ departId + "'";
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		mapList = userService.findForJdbc(sql);
		String[] rol_name = new String[mapList.size()];
		for (int i = 0; i < mapList.size(); i++) {
			Map<String, Object> map = mapList.get(i);
			rol_name[i] = (String) map.get("rolename");
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
	 * 添加或修改部门信息
	 *
	 * @param icon
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request, TSDepart depart) {
		// 设置上级部门
		String pid = request.getParameter("TSPDepart.id");
		String roleid = oConvertUtils.getString(request.getParameter("roleid"));
		if (StringUtil.isEmpty(pid)) {
			depart.setTSPDepart(null);

		}
		TSDepart d=departService.findUniqueByProperty(TSDepart.class, "departname", pid);
		if(d!=null){
			depart.setTSPDepart(d);
		}else{
			depart.setTSPDepart(null);
		}
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(depart.getId())) {
			message = "部门【" + depart.getDepartname() + "】被更新成功";

			userService.saveOrUpdate(depart);
			j.accumulate("isSuccess", true);
			List<TSRoleDepart> ru = userService.findByProperty(
					TSRoleDepart.class, "depart.id", depart.getId());
			userService.deleteAllEntitie(ru);
			if (StringUtil.isNotEmpty(roleid)) {
				saveRoleDepart(depart, roleid);
			}
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			if (depart.getTSPDepart() != null
					&& StringUtils.isNotEmpty(depart.getId() + "")) {
				j.accumulate("toUrl", "memberMngAction.do?list&id=" + depart.getId());
			}else{
				j.accumulate("toUrl", "memberMngAction.do?list&id=" + depart.getId());
			}
		} else {
			message = "部门【" + depart.getDepartname() + "】被添加成功";
			depart.setCreatedtime(new Date());//创建时间
			userService.save(depart);

			j.accumulate("isSuccess", true);

			if (StringUtil.isNotEmpty(roleid)) {
				saveRoleDepart(depart, roleid);
			}
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
			j.accumulate("toUrl", "memberMngAction.do?list&id=" + depart.getId());
		}
		j.accumulate("msg", message);

		String str = j.toString();
		return str;
	}
	protected void saveRoleDepart(TSDepart depart, String roleidstr) {
		String[] roleids = roleidstr.split(",");
		for (int i = 0; i < roleids.length; i++) {
			TSRoleDepart rDepart = new TSRoleDepart();
			TSRole role = userService.getEntity(TSRole.class, roleids[i]);
			rDepart.setRole(role);
			rDepart.setDepart(depart);
			userService.save(rDepart);
		}
	}

	/**
	 * 父级权限列表
	 * 
	 * @param role
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 * @return
	 */
	@RequestMapping(params = "getDepartTree")
	@ResponseBody
	public JSONArray getDepartTree(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
		if(null != request.getParameter("selfId")){
			cq.notEq("id", request.getParameter("selfId"));
		}
		if (comboTree.getId() != null) {
			cq.eq("TSPDepart.id", comboTree.getId());
		}
		cq.add();
		List<TSDepart> departsList = departService.getListByCriteriaQuery(cq, false);
		JSONArray jsonArray = new JSONArray();
		for(TSDepart depart : departsList){
			JSONObject json = new JSONObject();
			if(depart.getTSDeparts()!= null && depart.getTSDeparts().size() > 0){
				json.put("name", depart.getDepartname());
				if(depart.getTSPDepart()==null){
					json.put("pId", "0");
				}else{
					json.put("pId", depart.getTSPDepart().getId());
				}
				json.put("id", depart.getId());
				json.put("open", true);
			}else{
				json.put("name", depart.getDepartname());
				if(depart.getTSPDepart()==null){
					json.put("pId", "0");
				}else{
					json.put("pId", depart.getTSPDepart().getId());
				}
				json.put("id", depart.getId());
			}
			jsonArray.add(json);
		}
		return jsonArray;

	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
/*-------左边菜单树型展示----------开始-------*/
	/**
	 * 左边树展示
	 * @param requset
	 * @return
	 */
	@RequestMapping(params="istree")
	public ModelAndView deparTree(HttpServletRequest requset){
		String istree = requset.getParameter("istree");
		List<TSDepart> departList = departService.loadAll(TSDepart.class);
//		TSDepart t = new TSDepart();
//		t.getTSDeparts();
		Map<String ,Object> m = new HashMap<String ,Object>();
		m.put("list", departList);
		return new ModelAndView("lmPage/main/leftdepartree" ,m);
	}
	
	
	/**
	 * 左边树展示
	 * @param requset
	 * @return
	 */
	@RequestMapping(params="list4tree")
	public ModelAndView list4Tree(TSUser user,HttpServletRequest request, HttpServletResponse response){
		TSUser loginuser = PlatFormUtil.getSessionUser();
		HttpSession session = ContextHolderUtils.getSession();
		String departid = oConvertUtils.getString(request.getParameter("departid"));
		// 登陆者的权限
		if (loginuser.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(new RedirectView("loginAction.do?login"));
		}
		// 获取部门列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(TSUser.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, user, param);
		
		if (!StringUtil.isEmpty(departid)) {
			DetachedCriteria dc = cq.getDetachedCriteria();
			DetachedCriteria dcDepart = dc.createCriteria("TSDepart");
			dcDepart.add(Restrictions.eq("id", departid));
		}
		Short[] userstate = new Short[] { Globals.User_Normal, Globals.User_ADMIN };
		cq.in("status", userstate);
		cq.add();
		PageList pageList = departService.getPageList(cq, true);
		List<TSUser> userList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("departid", departid);
		m.put("userList", userList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "departAction.do?list4tree");
		return new ModelAndView("lmPage/depart2/list4Tree" ,m);
	}
	
	@RequestMapping(params="addOrUpdate4tree")
	public ModelAndView add4tree(HttpServletRequest request, HttpServletResponse response){
		String departid = request.getParameter("departid");
		String id = request.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		TSDepart depart = departService.get(TSDepart.class, departid);
		List<TSRole> roleList = departService.loadAll(TSRole.class);
		String userRole=null;
		TSUser user = null;
		if (StringUtil.isNotEmpty(id)) {
			user = departService.getEntity(TSUser.class, id);
			m.put("departid", user.getTSDepart().getId());
			List<TSRoleUser> rUsers = departService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			userRole=rUsers.get(0).getTSRole().getId();
		} else {
			user = new TSUser();
			m.put("departid", departid);
		}
		m.put("user", user);
		m.put("roleList", roleList);
		m.put("depart", depart);
		m.put("userRole", userRole);
		return new ModelAndView("lmPage/depart2/tree_user_saveOrUpdate",m);
	}
	
	
	/**
	 * 用户录入或修改
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "treeSaveOrUpdateUser")
	@ResponseBody
	public String treeSaveOrUpdateUser(HttpServletRequest req, TSUser user) {
		JSONObject j = new JSONObject();
		// 得到用户的角色
		try {
			String roleid = oConvertUtils.getString(req.getParameter("roleid"));
			String password = oConvertUtils.getString(req.getParameter("password"));
			if (StringUtil.isNotEmpty(user.getId())) {
				TSUser users = departService.getEntity(TSUser.class, user.getId());
				users.setEmail(user.getEmail());
				users.setOfficePhone(user.getOfficePhone());
				users.setMobilePhone(user.getMobilePhone());
				users.setTSDepart(user.getTSDepart());
				users.setRealName(user.getRealName());
				users.setStatus(Globals.User_Normal);
				users.setActivitiSync(user.getActivitiSync());
				departService.updateEntitie(users);
				List<TSRoleUser> ru = departService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
				departService.deleteAllEntitie(ru);
				message = "用户【" + users.getUserName() + "】更新成功";
				if (StringUtil.isNotEmpty(roleid)) {
					saveRoleUser(users, roleid);
				}
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} else {
				TSUser users = departService.findUniqueByProperty(TSUser.class, "userName",user.getUserName());
				if (users != null) {
					message = "用户【" + users.getUserName() + "】已经存在";
				} else {
					user.setPassword(PasswordUtil.encrypt(user.getUserName(), password, PasswordUtil.getStaticSalt()));
					if (user.getTSDepart().equals("")) {
						user.setTSDepart(null);
					}
					user.setStatus(Globals.User_Normal);
					user.setCreatedtime(new Date());//创建时间
					departService.save(user);
					message = "用户【" + user.getUserName() + "】添加成功";
					if (StringUtil.isNotEmpty(roleid)) {
						saveRoleUser(user, roleid);
					}
					systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
					
				}

			}
		} catch (Exception e) {
			message = "保存异常操作失败";
			e.printStackTrace();
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "departAction.do?list4tree");
		String str = j.toString();
		return str;
	}
	
	
	/**
	 * 用户信息删除
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "delTreeUser")
	@ResponseBody
	public String delTreeUser(TSUser user, HttpServletRequest req) {
		JSONObject j = new JSONObject();
		if("admin".equals(user.getUserName())){
			message = "超级管理员[admin]不可删除";
			j.accumulate("isSuccess", false);
		}
		user = departService.getEntity(TSUser.class, user.getId());
		List<TSRoleUser> roleUser = departService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
		if (!user.getStatus().equals(Globals.User_ADMIN)) {
			if (roleUser.size()>0) {
				// 删除用户时先删除用户和角色关系表
				delRoleUser(user);
				userService.delete(user);
				message = "用户【" + user.getUserName() + "】删除成功";
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
			} else {
				userService.delete(user);
				message = "用户【" + user.getUserName() + "】删除成功";
			}
			j.accumulate("isSuccess", true);
		} else {
			message = "超级管理员不可删除";
		}
		j.accumulate("msg", message);
		j.accumulate("toUrl", "departAction.do?list4tree");
		String str = j.toString();
		return str;
	}
/*-------左边菜单树型展示----------结束-------*/
}
