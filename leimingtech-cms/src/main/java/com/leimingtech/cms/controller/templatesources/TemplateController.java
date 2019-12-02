package com.leimingtech.cms.controller.templatesources;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.AttachPictureEntity;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.FileUtil;



/**
 * @Title: templateController
 * @Description: 模板管理
 * @author linjm
 * @date 2014-04-29
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/templateController")
public class TemplateController extends BaseController {

	private static final Logger logger = Logger.getLogger(TemplateController.class);

	private String message = null;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Autowired
	private SystemService systemService;

	/**
	 * 列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "templateList")
	public ModelAndView TemplateList(HttpServletRequest request) {
		// 子文件
		String filePath = request.getParameter("filePath");

		String siteTemplatePath = CmsConstants.getSiteTemplatePath(ContextHolderUtils.getSession());// 站点模板资源路径

		File file = new File(siteTemplatePath);
		if (StringUtils.isNotEmpty(filePath)) {
			file = new File(siteTemplatePath + filePath);
		}
		File[] child = file.listFiles(new FileFilter() {
			public boolean accept(File file) {
				// 过滤.svn文件
				if (!file.getName().endsWith(".svn")) {
					return true;
				}
				return false;
			}
		});
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < child.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isDirectory", child[i].isDirectory());
			map.put("fileName", child[i].getName());
			map.put("lastModifiedDate", sdf.format(child[i].lastModified()));
			map.put("size",
					child[i].isDirectory() ? FileUtil.FormetFileSize(FileUtil.getFileSize(child[i])) : FileUtil.FormetFileSize(child[i]
							.length()));
			String path = child[i].getAbsolutePath().replace("\\", "/");
			path = path.substring(path.indexOf("template/") + 9, path.length());
			map.put("filePath", path);
			list.add(map);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list", list);
		m.put("navigation", getFileNavigation(filePath, false));
		// m.put("root",
		// CmsConstants.rootPath(ContextHolderUtils.getSession()));
		return new ModelAndView("cms/templatesources/templateList", m);
	}

	/**
	 * 获取文件导航条
	 * 
	 * @param filepath
	 * @param boolean true为弹出框，false 不是弹出框
	 * @return String
	 */
	public String getFileNavigation(String filepath, boolean isDialog) {
		if (StringUtils.isEmpty(filepath)) {
			return "";
		}
		String[] pathname = filepath.split("/");
		StringBuilder sb = new StringBuilder();
		String path = "";
		for (int i = 0; i < pathname.length; i++) {
			path += pathname[i] + "/";
			if (isDialog) {
				// 弹出框调用的切换方法
				sb.append("<a href=\"javaScript:changePageSub('templateController.do?templateList&filePath=" + path + "');\">"
						+ pathname[i] + "> </a>");
			} else {
				sb.append("<a href=\"javaScript:changePage('templateController.do?templateList&filePath=" + path + "');\">" + pathname[i]
						+ "> </a>");
			}
		}
		return sb.toString();
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String filepath = request.getParameter("filepath");
		File file = new File(CmsConstants.getSiteTemplatePath(ContextHolderUtils.getSession()) + filepath);
		// 不管路径是文件还是文件夹，都删掉
		boolean isDelete = FileUtil.delete(file);
		if (isDelete) {
			message = "文件删除成功";
		} else {
			message = "文件删除失败";
		}
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "templateController.do?templateList");
		String str = j.toString();
		return str;
	}

	/**
	 * 弹出框新建文件夹
	 * 
	 * @return
	 */
	@RequestMapping(params = "pageModel")
	public ModelAndView pageModel(HttpServletRequest request) {
		String fileName = request.getParameter("filename");
		Map<String, Object> m = new HashMap<String, Object>();
		return new ModelAndView("cms/templatesources/template_add", m);
	}

	/**
	 * 保存新建文件夹
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addFolder")
	@ResponseBody
	public String makeFile(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String fileName = request.getParameter("filename");
		File file = new File(CmsConstants.getSiteTemplatePath(ContextHolderUtils.getSession()) + fileName);
		message = "新建文件失败";
		if (file.exists()) {
			message = "文件已存在";
		} else {
			file.mkdir();
			message = "新建文件成功";
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "templateController.do?templateList");
		String str = j.toString();
		return str;
	}

	/**
	 * 模板列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "tableDialog")
	public ModelAndView tableDialog(AttachPictureEntity attachPicture, HttpServletRequest request) {
		// 子文件
		String filePath = request.getParameter("filePath");
		// 栏目选择模型
		String model = request.getParameter("model");
		if (model == null) {
			model = "-1";
		}
		// 模板根目录
		String absolutePath = CmsConstants.getSiteTemplatePath(ContextHolderUtils.getSession());
		File file = new File(absolutePath);
		if (StringUtils.isNotEmpty(filePath)) {
			file = new File(absolutePath + "/" + filePath);
		}
		File[] child = file.listFiles(new FileFilter() {
			public boolean accept(File file) {
				// 过滤.svn文件
				if (!file.getName().endsWith(".svn")) {
					return true;
				}
				return false;
			}
		});
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (child != null && child.length > 0) {
			for (int i = 0; i < child.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("isDirectory", child[i].isDirectory());
				map.put("fileName", child[i].getName());
				map.put("lastModifiedDate", sdf.format(child[i].lastModified()));
				map.put("size",
						child[i].isDirectory() ? FileUtil.FormetFileSize(FileUtil.getFileSize(child[i])) : FileUtil.FormetFileSize(child[i]
								.length()));
				String path = child[i].getAbsolutePath().replace("\\", "/");
				path = path.substring(path.indexOf("template/") + 9, path.length());
				map.put("filePath", path);
				list.add(map);
			}
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list", list);
		m.put("navigation", getFileNavigation(filePath, true));
		// m.put("root",
		// CmsConstants.rootPath(ContextHolderUtils.getSession()));
		m.put("model", model);
		return new ModelAndView("cms/templatesources/template_dialog_List", m);
	}

	/**
	 * 弹出框删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "dialogdel")
	@ResponseBody
	public String dialogDel(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String filepath = request.getParameter("filepath");
		File file = new File(CmsConstants.getSiteTemplatePath(ContextHolderUtils.getSession()) + filepath);
		// 不管路径是文件还是文件夹，都删掉
		boolean isDelete = FileUtil.delete(file);
		if (isDelete) {
			message = "文件删除成功";
		} else {
			message = "文件删除失败";
		}
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "templateController.do?tableDialog");
		String str = j.toString();
		return str;
	}

	/**
	 * 弹出框新建文件夹
	 * 
	 * @return
	 */
	@RequestMapping(params = "uploadTemplate")
	public ModelAndView uploadTemplate(HttpServletRequest request) {
		String fileName = request.getParameter("filename");
		Map<String, Object> m = new HashMap<String, Object>();
		return new ModelAndView("cms/templatesources/template_upload", m);
	}
}
