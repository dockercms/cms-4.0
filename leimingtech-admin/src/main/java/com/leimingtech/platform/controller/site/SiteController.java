package com.leimingtech.platform.controller.site;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.common.PlatformConstants;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.RoleSiteEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.IStatic;
import com.leimingtech.core.service.RoleSiteServiceI;
import com.leimingtech.core.service.SiteServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PinyinUtil;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.ResourceUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.core.util.SystemPath;
import com.leimingtech.platform.constants.CmsConstants;

/**
 * @Title: Controller
 * @Description: 站点管理
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-18 10:06:26
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/siteController")
public class SiteController extends BaseController {

	@Autowired
	private SiteServiceI siteService;
	/** 站点权限接口 */
	@Autowired
	private RoleSiteServiceI roleSiteService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private IStatic staticImpl;
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 站点管理列表页ftl
	 * 
	 * @param request
	 * @param
	 * @param
	 * @param
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(SiteEntity site, HttpServletRequest request) {
		// 获取站点管理列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = siteService.getPageList(site, param, pageSize, pageNo);

		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "siteController.do?table");
		return new ModelAndView("lmPage/site/siteList", m);
	}

	/**
	 * 站点管理添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new SiteEntity());
		return new ModelAndView("lmPage/site/site", m);
	}

	/**
	 * 站点管理更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		SiteEntity site = siteService.getSite(String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", site);
		return new ModelAndView("lmPage/site/site", m);
	}

	/**
	 * 站点管理保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(SiteEntity site, HttpServletRequest request) {
		Boolean isSuccess=false;
		JSONObject j = new JSONObject();
		String domain = request.getParameter("domain");
		List<SiteEntity> sitelist =siteService.siteList();
		boolean isHave=false;
		for(SiteEntity s :sitelist){
			if(s.getDomain().equals(domain)&&!s.getId().equals(site.getId())){
				message="域名不能重复";
				isHave=true;
				
				break;
			}
		}
		if (!isHave) {
			if (StringUtil.isNotEmpty(site.getId())) {
				message = "站点[" + site.getSiteName() + "]更新成功";
				SiteEntity t = siteService.getSite(site.getId());
				
				if (t.getIsMaster() != site.getIsMaster()) {
					//把当前操作的站点当作主站
					if(site.getIsMaster()==1){
						siteService.modifyMainSite();//修改其他站点为子站
					}else{
						//当前不可直接切换成子站
						site.setIsMaster(1);
					}
				}
				
				try {
					MyBeanUtils.copyBeanNotNull2Bean(site, t);
					siteService.saveOrUpdate(t);
					systemService.addLog(message, Globals.Log_Leavel_INFO,
							Globals.Log_Type_UPDATE);
					isSuccess=true;
				} catch (Exception e) {
					e.printStackTrace();
					message = "站点[" + site.getSiteName() + "]更新失败";
				}
			} else {
				message = "站点[" + site.getSiteName() + "]添加成功";
				
				SiteEntity mainSite = siteService.getMasterSite();
				if (mainSite == null) {
					site.setIsMaster(1);
				} else if (site.getIsMaster() == 1) {
					siteService.modifyMainSite();//修改其他站点为子站
				}
				site.setIsSwitch("0");   //默认静态
				site.setCreatedtime(new Date());//创建时间
				siteService.save(site);
				isSuccess=true;
				/*
				 * 默认去sysConfig.properties中lmcms.site.staticfiles.path指定配置路径  
				 * 没有配置的话就读取工程 WebRoot/wwwroot目录
				 */
				String separator = System.getProperty("file.separator");
				String siteFullPath = "";
				String staticFilesPath = ResourceUtil.getCMSStaticFilesPath();// cms站点静态资源存放路径
				if (StringUtils.isNotEmpty(staticFilesPath)) {
					siteFullPath = staticFilesPath + separator
							+ site.getSitePath() + separator;// 站点资源路径
				} else {
					String projectPath = SystemPath.getSysPath();
					siteFullPath = projectPath + CmsConstants.SITE_STORAGE_PATH
							+ separator + site.getSitePath() + separator;// 站点资源路径
				}

				try {
					FileUtils.forceMkdir(new File(siteFullPath));
					FileUtils.forceMkdir(new File(siteFullPath + "css"
							+ separator));
					FileUtils.forceMkdir(new File(siteFullPath + "images"
							+ separator));
					FileUtils.forceMkdir(new File(siteFullPath + "js"
							+ separator));
					FileUtils.forceMkdir(new File(siteFullPath + "template"
							+ separator));
					new File(siteFullPath + "template" + separator
							+ "index.html").createNewFile();
					FileUtils.forceMkdir(new File(siteFullPath + "upload"
							+ separator));
				} catch (IOException e) {
					e.printStackTrace();
				}
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_INSERT);
				
				message+="<br/><span style='color:red'>注意：新增站点需要手动配置Nginx方可正常访问！<br/>" +
						"Nginx配置用于域名转发到发布目录和文件资源目录<span>";
			}
			
		}
		return success(message).accumulate("toUrl", "siteController.do?table").toString();
	}

	/**
	 * 站点管理删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		List<RoleSiteEntity> rolesite=roleSiteService.getRoleEntity(String.valueOf(id));
		SiteEntity site = siteService.getSite(String.valueOf(id));
		
		message = "站点["+site.getSiteName()+"]删除成功";

		/*
		 * 默认去sysConfig.properties中lmcms.site.staticfiles.path指定配置路径  
		 * 没有配置的话就读取工程 WebRoot/wwwroot目录
		 */
		String separator = System.getProperty("file.separator");
		String siteFullPath = "";
		String staticFilesPath = ResourceUtil.getCMSStaticFilesPath();// cms站点静态资源存放路径
		if (StringUtils.isNotEmpty(staticFilesPath)) {
			siteFullPath = staticFilesPath + separator + site.getSitePath();// 站点资源路径
		} else {
			String projectPath = SystemPath.getSysPath();
			siteFullPath = projectPath + CmsConstants.SITE_STORAGE_PATH + separator + site.getSitePath();// 站点资源路径
		}
		
		// 删除站点模板文件
		try {
			File file = new File(siteFullPath);
			if (file.exists()) {
				FileUtils.deleteDirectory(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		siteService.delete(site);
		if(rolesite!=null && rolesite.size()>0){
			commonService.deleteAllEntitie(rolesite);
		}

		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "siteController.do?table");
		String str = j.toString();
		return str;
	}

	/**
	 * 验证站点路径是否存在
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkPath")
	@ResponseBody
	public Boolean checkPath(HttpServletRequest request) {
		String path = request.getParameter("path");
		return siteService.checkPath(path);
	}

	/**
	 * 将站点名称转化为简拼
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "pyChange")
	@ResponseBody
	public String pyChange(HttpServletRequest request) {
		String siteName = request.getParameter("sitename");
		try {
			siteName = new String(java.net.URLDecoder.decode(siteName, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		siteName = PinyinUtil.getPinYinHeadChar(siteName);// 首字母
		if (siteService.checkPath(siteName)) {
			siteName += RandomStringUtils.random(1, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		}
		JSONObject j = new JSONObject();
		j.accumulate("siteName", siteName);
		return j.toString();
	}
	
	/**
	 * 动静态切换
	 * 
	 * @return
	 */
	@RequestMapping(params = "toSwitchUrl")
	@ResponseBody
	public String toSwitchUrl(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		
		String id = request.getParameter("id");
		
		String isSwitch=request.getParameter("isSwitch"); //切换状态 1为动态0为静态
		
		SiteEntity site =siteService.getSite(id);
		
		try {
				if(isSwitch.equals("0")){
					site.setIsSwitch("1");//1表示动态0表示静态
					//更新session里面的站点			
					PlatFormUtil.getSessionSite().setIsSwitch("1");
					message="动态切换成功!";
				}else{
					site.setIsSwitch("0");//1表示动态0表示静态
					//更新session里面的站点			
					PlatFormUtil.getSessionSite().setIsSwitch("0");
					message="静态切换成功!";
				}
				siteService.saveOrUpdate(site);
				staticImpl.staticSite(site,-1);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
			
		} catch (Exception e) {
			e.printStackTrace();
			message="切换失败!";
		}

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "siteController.do?table");
		String str = j.toString();
		return str;
	}
	
}
