package com.leimingtech.platform.controller.file;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.FileUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.platform.constants.CmsConstants;
import com.leimingtech.platform.entity.test.CustomEntity;
import com.leimingtech.platform.service.test.CustomServiceI;
import com.leimingtech.platform.tpl.TplManager;
import com.leimingtech.platform.tpl.Zipper;
import com.leimingtech.platform.tpl.Zipper.FileEntry;

/**
 * @Title: Controller
 * @Description: test
 * @author zhangdaihao modify by wanghao 20140402
 * @date 2014-04-17
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/fileController")
public class fileController extends BaseController {
	private static final Logger logger = Logger.getLogger(fileController.class);
	private String message = null;

	@Autowired
	private CustomServiceI customService;
	@Autowired
	private TplManager tplManager;

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
	@RequestMapping(params = "file")
	public ModelAndView file(HttpServletRequest request) {
		SiteEntity site = PlatFormUtil.getSessionSite();
		String temfiles = CmsConstants.getSitePath(site); // 模板路径
		File file = new File(temfiles);
		File[] child = file.listFiles(new FileFilter() {
			public boolean accept(File file) {
				// 过滤.svn文件
				if (!file.getName().endsWith(".svn")) {
					return true;
				}
				return false;
			}
		});
		// --------------------------------------全部加载----------------------------------------------------------

		/*
		 * JSONArray jArray = new JSONArray();
		 * 
		 * JSONObject jstreeData = new JSONObject(); jstreeData.accumulate("id",
		 * "-1"); jstreeData.accumulate("text", "文件");
		 * jstreeData.accumulate("state", new
		 * JSONObject().accumulate("selected", true).accumulate("opened",
		 * true)); if (child != null) { JSONArray jChildArray = new JSONArray();
		 * jChildArray = this.setChildInfo(child);
		 * jstreeData.accumulate("children", jChildArray); }
		 * jArray.add(jstreeData);
		 */

		// ------------------------------------------------------------------------------------------------
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (child != null) {
			for (File f : child) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fileName", f.getName());
				map.put("filePath", f.getAbsolutePath());
				
				if (f.isDirectory()) {
					map.put("file", "1"); // 是文件夹
				} else {
					map.put("file", "0"); // 是文件
				}
				
				if (f.isDirectory()) {
					List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
					File[] childFile = f.listFiles();
					for (int i = 0; i < childFile.length; i++) {
						Map<String, Object> map2 = new HashMap<String, Object>();
						map2.put("fileName", childFile[i].getName());
						map2.put("filePath", childFile[i].getAbsolutePath());
						childList.add(map2);
					}
					map.put("customs", childList);
				} else {
					map.put("customs", new ArrayList<Map<String, Object>>());
				}
				list.add(map);
			}
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list", list);

		try {
			m.put("temfiles", URLEncoder.encode(temfiles,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ModelAndView("lmPage/file/fileList", m);
	}

	// ---------------------------------
	// 递归菜单子节点数据-------------------------------------------------------------------------------------------

	/*
	 * private JSONArray setChildInfo(File[] files) { JSONArray jArray = new
	 * JSONArray(); for (File file : files) { JSONObject jChildObject = new
	 * JSONObject(); jChildObject.accumulate("id", file.getAbsolutePath());
	 * jChildObject.accumulate("text", file.getName()); File[] childFiles =
	 * file.listFiles(); if (childFiles != null && childFiles.length > 0) {
	 * JSONArray jChildArray = new JSONArray(); jChildArray =
	 * this.setChildInfo(childFiles); jChildObject.accumulate("children",
	 * jChildArray); } jArray.add(jChildObject); }
	 * 
	 * return jArray; }
	 */
	// ----------------------------------------------------------------------------------------------------------------------------
	/**
	 * 加载下级
	 * 
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "load")
	@ResponseBody
	public JSONArray load(HttpServletRequest request) {
		String id = request.getParameter("id");
		File file = new File(id);
		File[] child = file.listFiles(new FileFilter() {
			public boolean accept(File file) {
				// 过滤.svn文件
				if (!file.getName().endsWith(".svn")) {
					return true;
				}
				return false;
			}
		});
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray jsonArray = new JSONArray();
		for (File f : child) {
			JSONObject json = new JSONObject();
			if (f.isDirectory()) {
				json.put("text",
						"<img alt=\"\" src=\"media/image/file/folder.gif\">"
								+ f.getName());
				json.put("value", f.getAbsolutePath());
				json.put("leaf", false);
				json.put("expanded", false);
				json.put("cls", "folder");
				json.put("id", f.getAbsolutePath());
				json.put("href",
						"fileController.do?load&id=" + f.getAbsolutePath());
				json.put("data-role", "branch");
				json.put("children", new JSONArray());
			} else {
				json.put("text",
						"<i class=\"icon-file-text\" style=\"width: 1em;margin-right: 4px;\"></i>"
								+ f.getName());
				json.put("value", f.getAbsolutePath());
				json.put("leaf", true);
				json.put("href", "javascript:void(0);");
				json.put("data-role", "leaf");
				json.put("id", f.getAbsolutePath());
				json.put("checked", false);
			}
			jsonArray.add(json);
		}
		return jsonArray;
	}

	/**
	 * 加载下级地区
	 * 
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loads")
	@ResponseBody
	public String loads(HttpServletRequest request) {
		String id = request.getParameter("id");
		CriteriaQuery cq = new CriteriaQuery(CustomEntity.class);
		cq.eq("custom.id", id);
		cq.add();
		List<CustomEntity> list = customService.getListByCriteriaQuery(cq,
				false);
		JSONArray jsonArray = new JSONArray();
		for (CustomEntity custom : list) {
			JSONObject json = new JSONObject();
			if (custom.getCustoms() != null && custom.getCustoms().size() > 0) {
				json.put("text", custom.getCustomname());
				json.put("value", custom.getId());
				json.put("leaf", false);
				json.put("expanded", false);
				json.put("cls", "folder");
				json.put("id", custom.getId());
				json.put("href", "fileController.do?load&id=" + custom.getId());
				json.put("data-role", "branch");
				json.put("children", new JSONArray());
			} else {
				json.put("text", custom.getCustomname());
				json.put("value", custom.getId());
				json.put("leaf", true);
				json.put("href", "javascript:void(0);");
				json.put("data-role", "leaf");
				json.put("id", custom.getId());
				json.put("checked", false);
			}
			jsonArray.add(json);
		}
		return jsonArray.toString();
	}

	/**
	 * 修改模板页
	 * 
	 * @param test
	 * @param request
	 * @return
	 */

	@RequestMapping(params = "editModelPage")
	public ModelAndView editModelPage(HttpServletRequest request) {
		String filePath = request.getParameter("filePath"); // 文件路径
		String fileName = request.getParameter("fileName"); // 文件名称
		String source = getSource(filePath);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("fileName", fileName);
		m.put("filePath", filePath);
		m.put("source", StringEscapeUtils.unescapeHtml4(source));
		return new ModelAndView("lmPage/file/editFileSource", m);
	}

	/**
	 * 添加模板页
	 * 
	 * @param test
	 * @param request
	 * @return
	 */

	@RequestMapping(params = "addSource")
	public ModelAndView addSource(HttpServletRequest request) {
		String filePathId = request.getParameter("filePathId"); // 文件路径
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("filePath", filePathId);
		return new ModelAndView("lmPage/file/addFileSource", m);
	}

	/**
	 * 模板内容
	 * 
	 * @param test
	 * @param request
	 * @return
	 */

	@RequestMapping(params = "showTemplate")
	public ModelAndView showTemplate(HttpServletRequest request) {

		String filePath = request.getParameter("filePath"); // 文件名称
		String source = getSource(filePath);

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("source", source);
		return new ModelAndView("lmPage/file/showTemplate", m);
	}

	// 获取根据路径文件的内容
	public String getSource(String filePath) {
		File file = new File(filePath);
		if (file.isDirectory()) {
			return null;
		}
		try {
			return FileUtils.readFileToString(file, "utf-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// 修改模板
	@RequestMapping(params = "updateSource")
	@ResponseBody
	public String updateSource(HttpServletRequest request)
			throws UnsupportedEncodingException {
		JSONObject j = new JSONObject();
		String newSource = request.getParameter("source");
		String filePath = request.getParameter("filePath"); // 文件名称
		try {
			File file = new File(filePath);
			FileUtils.writeStringToFile(file, newSource, "utf-8");
			message = "修改成功";
		} catch (Exception e) {
			message = "修改失败!";
			e.printStackTrace();
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	// 新建模板
	@RequestMapping(params = "saveSource")
	@ResponseBody
	public String saveSource(HttpServletRequest request)
			throws UnsupportedEncodingException {
		JSONObject j = new JSONObject();
		String filePath = request.getParameter("filePath"); // 路径
		String source = request.getParameter("source"); // 模板内容
		String filename = request.getParameter("filename"); // 名称
		boolean isSuccess = false;
		try {
			String name = filePath + "\\" + filename + ".html";
			tplManager.save(name, source, false);
			message = "创建成功";
			isSuccess = true;
		} catch (Exception e) {
			message = "创建失败";
			e.printStackTrace();
			isSuccess = false;
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("filePath", filePath);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	/**
	 * 新建目录
	 * 
	 * @param files
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(params = "saveCatalog")
	@ResponseBody
	public String saveCatalog(HttpServletRequest request)
			throws UnsupportedEncodingException {
		JSONObject j = new JSONObject();
		boolean isSuccess = false;
		String filePathId = request.getParameter("filePathId"); // 路径
		String catalog = request.getParameter("catalog"); // 目录名

		try {
			tplManager.save(filePathId + "\\\\" + catalog, null, true);
			message = "创建成功";
			isSuccess = true;
		} catch (Exception e) {
			message = "创建失败";
			isSuccess = false;
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("filePathId", filePathId);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	/**
	 * 上传文件
	 * 
	 * @param files
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(params = "uploadFile")
	@ResponseBody
	public String uploadFile(@RequestParam MultipartFile files,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		JSONObject j = new JSONObject();
		boolean isSuccess = false;
		String filePathId = request.getParameter("filePathId");
		try {
			tplManager.save(filePathId, files);
			message = "上传成功!";
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			message = "上传失败!";
			isSuccess = false;
		}

		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("filePathId", filePathId);
		String str = j.toString();
		return str;
	}

	/**
	 * 删除文件
	 * 
	 * @param custom
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delOnly")
	@ResponseBody
	public String delOnly(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JSONObject j = new JSONObject();
		String filePathId = request.getParameter("path"); // 路径
		String parentPath = request.getParameter("parentPath"); // 父路径
		boolean isSuccess = false;
		try {
			tplManager.delete(new String[] { filePathId });
			message = "删除成功!";
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			message = "删除失败!";
			isSuccess = false;
		}

		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("parentPath", parentPath);
		String str = j.toString();
		return str;
	}

	/**
	 * 删除选项文件
	 * 
	 * @param custom
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delcheckbox")
	@ResponseBody
	public String delcheckbox(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JSONObject j = new JSONObject();
		String filePathId = request.getParameter("strChebox"); // 路径
		String parentPath = request.getParameter("parentPath"); // 父路径
		String[] path = filePathId.split(",");
		boolean isSuccess = false;
		try {
			tplManager.delete(path);
			message = "删除成功!";
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}

		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("parentPath", parentPath);
		String str = j.toString();
		return str;
	}

	/**
	 * 重命名
	 * 
	 * @param test
	 * @param request
	 * @return
	 */

	@RequestMapping(params = "rename")
	public ModelAndView rename(HttpServletRequest request) {
		String path = request.getParameter("path"); // 文件路径
		String fileName = request.getParameter("fileName"); // 文件名称
		String parentPath = request.getParameter("parentPath"); // 父路径
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("filePath", path);
		m.put("fileName", fileName);
		m.put("parentPath", parentPath);
		return new ModelAndView("lmPage/file/reName", m);
	}

	/**
	 * 重命名保存
	 * 
	 * @param custom
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveName")
	@ResponseBody
	public String saveName(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JSONObject j = new JSONObject();
		String filename = request.getParameter("newfilename"); // 名称
		String filePath = request.getParameter("filePath"); // 路径
		String parentPath = request.getParameter("parentPath"); // 父路径
		String newPath = parentPath + "\\\\" + filename;
		boolean isSuccess = false;
		try {
			tplManager.rename(filePath, newPath);
			message = "重命名成功!";
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			message = "重命名失败!";
			isSuccess = false;
		}

		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("parentPath", parentPath);
		String str = j.toString();
		return str;
	}

	/**
	 * 下载页面
	 * 
	 * @param custom
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "download")
	public void download(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String filePath = request.getParameter("path"); // 路径
		try {
			com.leimingtech.core.util.FileUtils.download(filePath,
					response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 打包下载
	 * 
	 * @param custom
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "downFileZip")
	public void downFileZip(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String parentPath = request.getParameter("parentPath"); // 路径
		String parentPathName = request.getParameter("parentPathName");// 名称

		List<FileEntry> fileEntrys = tplManager.export(parentPath);
		response.setContentType("application/x-download;charset=UTF-8");
		response.addHeader("Content-disposition", "filename=" + parentPathName
				+ ".zip");
		try {
			// 模板一般都在windows下编辑，所以默认编码为GBK
			Zipper.zip(response.getOutputStream(), fileEntrys, "GBK");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(params = "update")
	@ResponseBody
	public String update(CustomEntity custom, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (custom.getCustom() != null && custom.getCustom().getId().equals("")) {
			custom.setCustom(null);
		}
		if (StringUtil.isNotEmpty(custom.getId())) {
			message = "菜单信息[" + custom.getCustomname() + "]更新成功";
			CustomEntity t = customService.get(CustomEntity.class,
					custom.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(custom, t);
				customService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "菜单信息[" + custom.getCustomname() + "]更新失败";
			}
		} else {
			message = "菜单信息[" + custom.getCustomname() + "]添加成功";
			if (custom.getCustom() == null) {
				custom.setSustomlevel(0);
			} else {
				custom.setSustomlevel(1);
			}
			custom.setCreatedtime(new Date());// 创建时间
			customService.save(custom);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	/**
	 * 加载下级
	 * 
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "Table")
	public ModelAndView Table(HttpServletRequest request) {
		String id = request.getParameter("id");
		File file = new File(id);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
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
		if (child != null) {
			for (int i = 0; i < child.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fileName", child[i].getName());
				map.put("lastModifiedDate", sdf.format(child[i].lastModified()));
				map.put("size", FileUtil.FormetFileSize(child[i].length()));

				map.put("path",
						child[i].getAbsolutePath().replaceAll("\\\\", "/"));
				map.put("ico", com.leimingtech.core.util.FileUtils
						.getSuffix(child[i].getName()));// 文件图标
				if (child[i].isDirectory()) {
					map.put("file", "1"); // 是文件夹
				} else if (com.leimingtech.core.util.FileUtils
						.isPicture(child[i].getName())) {
					map.put("file", "2"); // 是图片
				} else {
					map.put("file", "0"); // 是文件
				}
				list.add(map);
			}
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list", list);
		m.put("parentPathName", file.getName()); // 父类文件名
		m.put("parentPath", id.replaceAll("\\\\", "/"));

		return new ModelAndView("lmPage/file/file", m);

	}

	/**
	 * 地域删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		CustomEntity custom = customService.getEntity(CustomEntity.class, id);
		message = "文档信息[" + custom.getCustomname() + "]删除成功";
		customService.delete(custom);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "fileController.do?custom");
		String str = j.toString();
		return str;
	}

}
