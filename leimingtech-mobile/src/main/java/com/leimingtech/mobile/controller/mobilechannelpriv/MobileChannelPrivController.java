package com.leimingtech.mobile.controller.mobilechannelpriv;
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
import com.leimingtech.core.entity.MobileChannelPrivEntity;
import com.leimingtech.core.entity.TSRole;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.RoleServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.mobile.service.mobilechannelpriv.MobileChannelPrivServiceI;

/**   
 * @Title: Controller
 * @Description: 移动栏目权限表
 * @author 
 * @date 2015-01-06 16:37:38
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/mobileChannelPrivController")
public class MobileChannelPrivController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MobileChannelPrivController.class);

	@Autowired
	private MobileChannelPrivServiceI mobileChannelPrivService;
	@Autowired
	private SystemService systemService;
	
	/**角色管理接口*/
	@Autowired
	private RoleServiceI roleService;
	private String message; 
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 移动栏目权限表列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "mobileChannelPriv")
	public ModelAndView mobileChannelPriv(MobileChannelPrivEntity mobileChannelPriv, HttpServletRequest request) {
		//获取移动栏目权限表列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(MobileChannelPrivEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, mobileChannelPriv, param);
		//排序条件
		cq.addOrder("createdtime",SortDirection.desc );
		cq.add();
		PageList pageList = this.mobileChannelPrivService.getPageList(cq, true);
		List<MobileChannelPrivEntity> resultList = pageList.getResultList();
		
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
		m.put("actionUrl", "mobileChannelPrivController.do?mobileChannelPriv");
		return new ModelAndView("mobile/mobilechannelpriv/mobileChannelPrivList", m);
	}

	/**
	 * 移动栏目权限表添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("mobileChannelPriv", new MobileChannelPrivEntity());
		return new ModelAndView("mobile/mobilechannelpriv/mobileChannelPriv", m);
	}
	
	/**
	 * 移动栏目权限表更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		MobileChannelPrivEntity mobileChannelPriv = mobileChannelPrivService.getEntity(MobileChannelPrivEntity.class,  id );
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("mobileChannelPriv", mobileChannelPriv);
		return new ModelAndView("mobile/mobilechannelpriv/mobileChannelPriv", m);
	}

	/**
	 * 移动栏目权限表保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(MobileChannelPrivEntity mobileChannelPriv, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(mobileChannelPriv.getId())) {
			message = "移动栏目权限【id="+mobileChannelPriv.getId()+"】更新成功";
			MobileChannelPrivEntity t = mobileChannelPrivService.get(MobileChannelPrivEntity.class, mobileChannelPriv.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(mobileChannelPriv, t);
				mobileChannelPrivService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "移动栏目权限【id="+mobileChannelPriv.getId()+"】更新失败";
			}
		} else {
			message = "移动栏目权限【id="+mobileChannelPriv.getId()+"】添加成功";
			mobileChannelPriv.setCreatedtime(new Date());//创建时间
			mobileChannelPrivService.save(mobileChannelPriv);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "mobileChannelPrivController.do?mobileChannelPriv");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 移动栏目权限表删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		MobileChannelPrivEntity mobileChannelPriv = mobileChannelPrivService.getEntity(MobileChannelPrivEntity.class,  id );
		message = "移动栏目权限【id="+mobileChannelPriv.getId()+"】删除成功";
		mobileChannelPrivService.delete(mobileChannelPriv);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "mobileChannelPrivController.do?mobileChannelPriv");
		String str = j.toString();
		return str;
	}
	/**
	 * 移动栏目权限设置跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "mobileChannelPrivView")
	public ModelAndView mobileChannelPrivView(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		TSRole role = mobileChannelPrivService.getEntity(TSRole.class, roleId);
		request.setAttribute("role", role);
		request.setAttribute("roleJson", mobileChannelPrivService.loadMobileChannelTree(roleId).toString());
		return new ModelAndView("mobile/mobilechannelpriv/mobileChannelSet");
	}
	
	/**
	 * 保存已勾选移动栏目权限
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveMobileChannelPriv")
	@ResponseBody
	public String saveMobileChannelPriv(HttpServletRequest request){
		String roleId = request.getParameter("roleId");
		String funVal = request.getParameter("funVal");
		JSONObject j = new JSONObject();
		TSRole role = roleService.getEntity(roleId);
		try {
			message = "移动栏目角色权限【"+role.getRoleName()+"】更新成功";
			mobileChannelPrivService.saveMobileChannelPriv(roleId, funVal);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			message+="<br/><span style='color:red'>注意：变更权限需要重新登录才会生效！</span>";
		} catch (Exception e) {
			message = "权限更新失败，原因："+e.getMessage();
			e.printStackTrace();
			return error(message).toString();
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "roleController.do?role");
		String str = j.toString();
		return str;
	}
}
