package com.leimingtech.platform.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.TSFunction;
import com.leimingtech.core.entity.TSIcon;
import com.leimingtech.core.entity.TSUser;
import com.leimingtech.core.entity.UploadFile;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.core.util.oConvertUtils;
import com.leimingtech.platform.service.IconService;

/**
 * 图标管理
 * 
 * @author liuzhen 2014-3-26下午02:38:37
 * 
 */
@Controller
@RequestMapping("/iconAction")
public class IconAction {
	@Autowired
	private IconService iconService;

	/**
	 * 获取图标信息列表
	 * 
	 * @param icon
	 *            图标信息
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(TSIcon icon, HttpServletRequest request) {
		TSUser user = PlatFormUtil.getSessionUser();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(new RedirectView("loginAction.do?login"));
		}
		// 获取图标列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		CriteriaQuery cq = new CriteriaQuery(TSIcon.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, icon, param);
		// 排序条件
		cq.addOrder("createdtime",SortDirection.desc);

		cq.add();
		PageList pageList = iconService.getPageList(cq, true);
		List<TSIcon> iconList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("iconList", iconList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "iconAction.do?list");
		return new ModelAndView("lmPage/icon/icon_list", m);
	}

	/**
	 * 跳转添加或修改图标页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addOrUpdate")
	public ModelAndView addOrUpdate(HttpServletRequest request) {
		String id = request.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		TSIcon icon = null;
		if (StringUtil.isNotEmpty(id)) {
			icon = iconService.getEntity(TSIcon.class, id);
		} else {
			icon = new TSIcon();
		}
		m.put("icon", icon);
		return new ModelAndView("lmPage/icon/icon_saveOrUpdate", m);
	}
	/**
	 * 添加或修改图标
	 * 
	 * @param icon
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "newSaveOrUpdate")
	@ResponseBody
	public String NewSaveOrUpdate(HttpServletRequest request) {
		
		JSONObject j = new JSONObject();
		Short iconType = oConvertUtils.getShort(request.getParameter("iconType"));
		String iconName = request.getParameter("iconName");
		String iconPath = request.getParameter("iconPath");
//		String prefixName = request.getParameter("prefixName");
		String iconClass = request.getParameter("iconClass");
		String id = request.getParameter("id");
//		icon.setId(id);
		TSIcon icon = new TSIcon();
		icon.setIconName(iconName);
		icon.setIconType(iconType);
//		icon.setIconPath(iconPath);
		icon.setIconClas(iconClass);
		icon.setExtend("png");
//		if(prefixName.length()>0&&prefixName.contains(".")){
//			icon.setIconClas(prefixName.substring(0, prefixName.lastIndexOf(".")));
//		}else{
//			j.accumulate("msg", "文件上传出错");
//			j.accumulate("toUrl", "iconAction.do?list");
//			String str = j.toString();
//			return str;
//		}
		try {
			if (StringUtil.isNotEmpty(id)) {
//				icon = systemService.get(TSIcon.class, id);
				icon.setId(id);
//				icon.setIconName(iconName);
//				icon.setIconType(iconType);
				iconService.saveOrUpdate(icon);
			} else {
				icon.setCreatedtime(new Date());//创建时间
				iconService.save(icon);
			}
			message = "操作成功";
			j.accumulate("isSuccess", true);
		} catch (Exception e) {
			message = "图标信息更新失败";
			j.accumulate("isSuccess", false);
		}

		j.accumulate("msg", message);
		j.accumulate("toUrl", "iconAction.do?list");
		String str = j.toString();
		return str;
	}
	/**
	 * 添加或修改图标
	 * 
	 * @param icon
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request) {
		TSIcon icon = new TSIcon();
		JSONObject j = new JSONObject();
		Short iconType = oConvertUtils.getShort(request.getParameter("iconType"));
		String iconName = oConvertUtils.getString(request.getParameter("iconName"));
		String id = request.getParameter("id");
		icon.setId(id);
		icon.setIconName(iconName);
		icon.setIconType(iconType);
		// uploadFile.setBasePath("images/accordion");
		UploadFile uploadFile = new UploadFile(request, icon);
		uploadFile.setCusPath("plug-in/accordion/images");
		uploadFile.setExtend("extend");
		uploadFile.setTitleField("iconclas");
		uploadFile.setRealPath("iconPath");
		uploadFile.setObject(icon);
		uploadFile.setByteField("iconContent");
		uploadFile.setRename(false);
		try {
			int fileSize = uploadFile.getMultipartRequest().getFileMap().size();
			if (fileSize > 0) {
				iconService.uploadFile(uploadFile);
			} else {
				if (StringUtil.isNotEmpty(id)) {
					icon = iconService.get(TSIcon.class, id);
					icon.setIconName(iconName);
					icon.setIconType(iconType);
					iconService.saveOrUpdate(icon);
				} else {
					icon.setCreatedtime(new Date());
					iconService.save(icon);
				}
			}
			message = "图标信息更新成功";
			j.accumulate("isSuccess", true);
		} catch (Exception e) {
			message = "图标信息更新失败";
			j.accumulate("isSuccess", false);
		}

		j.accumulate("msg", message);
		j.accumulate("toUrl", "iconAction.do?list");
		String str = j.toString();
		return str;
	}

	/**
	 * 删除图标信息
	 * @param icon
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(TSIcon icon, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		icon = iconService.getEntity(TSIcon.class, icon.getId());
		boolean isPermit = isPermitDel(icon);

		if (isPermit) {
			iconService.delete(icon);

			message = "图标: " + icon.getIconName() + "被删除成功。";

			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
			j.accumulate("isSuccess", true);
			j.accumulate("msg", message);
			j.accumulate("toUrl", "iconAction.do?list");
			String str = j.toString();
			return str;
		}

		message = "图标: " + icon.getIconName() + "正在使用，不允许删除。";
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "iconAction.do?list");
		String str = j.toString();
		return str;
	}

	/**
	 * 检查是否允许删除该图标。
	 * 
	 * @param icon
	 *            图标。
	 * @return true允许；false不允许；
	 */
	private boolean isPermitDel(TSIcon icon) {
		List<TSFunction> functions = iconService.findByProperty(TSFunction.class, "TSIcon.id", icon.getId());
		if (functions == null || functions.isEmpty()) {
			return true;
		}
		return false;
	}

	public SystemService systemService;

	private String message;

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
}
