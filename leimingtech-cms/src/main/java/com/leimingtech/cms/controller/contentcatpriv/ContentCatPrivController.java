package com.leimingtech.cms.controller.contentcatpriv;
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

import com.leimingtech.cms.service.contentcatpriv.ContentCatPrivServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentCatPrivEntity;
import com.leimingtech.core.entity.TSRole;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.RoleServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: PC栏目权限表
 * @author 
 * @date 2015-01-06 16:34:37
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/contentCatPrivController")
public class ContentCatPrivController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ContentCatPrivController.class);

	@Autowired
	private ContentCatPrivServiceI contentCatPrivService;
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
	 * PC栏目权限表列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "contentCatPriv")
	public ModelAndView contentCatPriv(ContentCatPrivEntity contentCatPriv, HttpServletRequest request) {
		//获取PC栏目权限表列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(ContentCatPrivEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, contentCatPriv, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.contentCatPrivService.getPageList(cq, true);
		List<ContentCatPrivEntity> resultList = pageList.getResultList();
		
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
		m.put("actionUrl", "contentCatPrivController.do?contentCatPriv");
		return new ModelAndView("cms/contentcatpriv/contentCatPrivList", m);
	}

	/**
	 * PC栏目权限表添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("contentCatPriv", new ContentCatPrivEntity());
		return new ModelAndView("cms/contentcatpriv/contentCatPriv", m);
	}
	
	/**
	 * PC栏目权限表更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		ContentCatPrivEntity contentCatPriv = contentCatPrivService.getEntity(ContentCatPrivEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("contentCatPriv", contentCatPriv);
		return new ModelAndView("cms/contentcatpriv/contentCatPriv", m);
	}

	/**
	 * PC栏目权限表保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(ContentCatPrivEntity contentCatPriv, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(contentCatPriv.getId())) {
			message = "PC栏目权限表【"+contentCatPriv.getContentCatEntity()+"】更新成功";
			ContentCatPrivEntity t = contentCatPrivService.get(ContentCatPrivEntity.class, contentCatPriv.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(contentCatPriv, t);
				contentCatPrivService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "PC栏目权限表更新失败";
			}
		} else {
			message = "PC栏目权限表【"+contentCatPriv.getContentCatEntity()+"】添加成功";
			contentCatPriv.setCreatedtime(new Date());//创建时间
			contentCatPrivService.save(contentCatPriv);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentCatPrivController.do?contentCatPriv");
		String str = j.toString();
		return str;
	}
	
	/**
	 * PC栏目权限表删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		ContentCatPrivEntity contentCatPriv = contentCatPrivService.getEntity(ContentCatPrivEntity.class, String.valueOf(id));
		message = "PC栏目权限表【"+contentCatPriv.getContentCatEntity()+"】删除成功";
		contentCatPrivService.delete(contentCatPriv);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentCatPrivController.do?contentCatPriv");
		String str = j.toString();
		return str;
	}
	
	/**
	 * PC栏目权限设置跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "contentCatPrivView")
	public ModelAndView contentCatPrivView(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		TSRole role = contentCatPrivService.getEntity(TSRole.class, roleId);
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("role", role);
		m.put("roleJson", contentCatPrivService.loadContentCatTree(roleId).toString());
		return new ModelAndView("cms/contentcatpriv/contentCatSet",m);
	}
	
	/**
	 * 保存已勾选PC栏目权限
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "savecontentCatPriv")
	@ResponseBody
	public String savecontentCatPriv(HttpServletRequest request){
		String roleId = request.getParameter("roleId");
		String funVal = request.getParameter("funVal");
		TSRole role = roleService.getEntity(roleId);
		JSONObject j = new JSONObject();
		try {
			message = "角色权限《"+role.getRoleName()+"》更新成功";
			contentCatPrivService.saveContentCatPriv(roleId, funVal);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			message+="<br/><span style='color:red'>注意：变更权限需要重新登录才会生效！</span>";
		} catch (Exception e) {
			message = "权限更新失败，原因："+e.getMessage();
			e.printStackTrace();
			return error(message).toString();
		}
		return success(message).accumulate("toUrl", "roleController.do?role").toString();
	}
}
