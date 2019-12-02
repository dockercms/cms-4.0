package com.leimingtech.weibo.controller.sinacontent;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import weibo4j.Timeline;
import weibo4j.http.ImageItem;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.common.PlatformConstants;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.ModelItemServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.weibo.entity.sinacontent.SinaContentEntity;
import com.leimingtech.weibo.entity.sinaweibo.SinaWeiboEntity;
import com.leimingtech.weibo.service.sinacontent.SinaContentServiceI;
import com.leimingtech.weibo.service.sinaweibo.SinaWeiboServiceI;

/**
 * @Title: Controller
 * @Description: 新浪微博内容
 * @author
 * @date 2015-12-05 13:56:58
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/sinaContentController")
public class SinaContentController extends BaseController {

	private String message;
	/** 新浪微博内容接口 */
	@Autowired
	private SinaContentServiceI sinaContentService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	@Autowired
	private ModelItemServiceI modelItemService;
	@Autowired
	private ContentsServiceI contentsService;
	/** 新浪微博接口 */
	@Autowired
	private SinaWeiboServiceI sinaWeiboService;
	/**
	 * 新浪微博内容列表
	 * 
	 * @param request
	 */
	@RequestMapping(params = "sinaContent")
	public ModelAndView sinaContent(SinaContentEntity sinaContent,
			HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = sinaContentService.getPageList(sinaContent,
				param, pageSize, pageNo);
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "sinaContentController.do?sinaContent");
		return new ModelAndView("weibo/sinacontent/sinaContentList", m);
	}

	/**
	 * 新浪微博内容添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("sinaContent", new SinaContentEntity());
		return new ModelAndView("weibo/sinacontent/sinaContent", m);
	}

	/**
	 * 新浪微博内容更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		SinaContentEntity sinaContent = sinaContentService
				.getEntity(java.lang.String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("sinaContent", sinaContent);
		return new ModelAndView("weibo/sinacontent/sinaContent", m);
	}

	/**
	 * 新浪微博内容保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(SinaContentEntity sinaContent, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;
		String siteid=PlatFormUtil.getSessionSite().getId();
		String code=sinaWeiboService.getWeiboinfo("accessToken", siteid);
		if(code==null){
		 message="请先配置微博公众号信息！";
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "sinaContentController.do?sinaContent");
		String str = j.toString();
		return str;
		}else{
		if (StringUtils.isNotEmpty(sinaContent.getId())) {
			SinaContentEntity t = sinaContentService.getEntity(sinaContent.getId());
			try {
				sinaContent.setCreatetime(new Date());
				MyBeanUtils.copyBeanNotNull2Bean(sinaContent, t);
				sinaContentService.saveOrUpdate(t);
				String imgurl=sinaContent.getSinaThumb();
				if(!imgurl.isEmpty()){
					try {
						String uploadFilesPath=CmsConstants.getUploadFilesPath(PlatFormUtil.getSessionSite());
						String imgpath=sinaContent.getSinaThumb();
						ImageItem pic = null;
						if(imgpath.contains("http")){
							byte[] content = readHttpImage(imgpath);
							pic = new ImageItem("pic", content);
						}else{
							byte[] content = readFileImage(uploadFilesPath+imgpath);
							pic = new ImageItem("pic", content);
						}
						String contentsina=sinaContent.getSinaDigest();
						String sinaStaus=StringEscapeUtils.unescapeHtml4(contentsina);
						String sinaUrl=PlatFormUtil.getCurrentSitedomain()+sinaContent.getSinaUrl();
						String s = java.net.URLEncoder.encode(sinaStaus+sinaUrl, "utf-8");
						String site=PlatFormUtil.getSessionSite().getId();
						SinaWeiboEntity sinaWeibo=sinaWeiboService.getSitEntity(site);
						String access_token =sinaWeibo.getAccesstoken();
						Timeline tm = new Timeline(access_token);
						Status status = tm.uploadStatus(s, pic);
						String retxt=status.getText();
						if(!retxt.isEmpty()){
							message = "新浪微博发布成功！";
						}else{
							isSuccess = false;
							message = "新浪微博发布失败！请检查公账号配置";
						}
					} catch (Exception e) {
						isSuccess = false;
						message = "新浪微博发布失败！请检查公账号配置";
						e.printStackTrace();
					}
				}else{
					String site=PlatFormUtil.getSessionSite().getId();
					SinaWeiboEntity sinaWeibo=sinaWeiboService.getSitEntity(site);
					String access_token =sinaWeibo.getAccesstoken();
					String content=sinaContent.getSinaDigest();
					String sinaStaus=StringEscapeUtils.unescapeHtml4(content);
					String sinaUrl=PlatFormUtil.getCurrentSitedomain()+sinaContent.getSinaUrl();
					Timeline tm = new Timeline(access_token);
					try {
						Status status = tm.updateStatus(sinaStaus+sinaUrl);
						String retxt=status.getId();
						if(!retxt.isEmpty()){
							message = "新浪微博发布成功！";
						}else{
							isSuccess = false;
							message = "新浪微博发布失败！请检查公账号配置";
						}
					} catch (WeiboException e) {
						isSuccess = false;
						message = "新浪微博发布失败！请检查公账号配置";
						e.printStackTrace();
					}	
				}
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "新浪微博更新失败";
				LogUtil.error(message, e);
				isSuccess = false;
			}
		} else {
			sinaContent.setCreatetime(new Date());
			sinaContentService.save(sinaContent);	
			String imgurl=sinaContent.getSinaThumb();
			if(!imgurl.isEmpty()){
				try {
					String uploadFilesPath=CmsConstants.getUploadFilesPath(PlatFormUtil.getSessionSite());
					String imgpath=sinaContent.getSinaThumb();
					ImageItem pic = null;
					if(imgpath.contains("http")){
						byte[] content = readHttpImage(imgpath);
						pic = new ImageItem("pic", content);
					}else{
						byte[] content = readFileImage(uploadFilesPath+imgpath);
						pic = new ImageItem("pic", content);
					}
					String contentsina=sinaContent.getSinaDigest();
					String sinaStaus=StringEscapeUtils.unescapeHtml4(contentsina);
					String sinaUrl=PlatFormUtil.getCurrentSitedomain()+sinaContent.getSinaUrl();
					String s = java.net.URLEncoder.encode(sinaStaus+sinaUrl, "utf-8");
					String site=PlatFormUtil.getSessionSite().getId();
					SinaWeiboEntity sinaWeibo=sinaWeiboService.getSitEntity(site);
					String access_token =sinaWeibo.getAccesstoken();
					Timeline tm = new Timeline(access_token);
					Status status = tm.uploadStatus(s, pic);
					String retxt=status.getText();
					if(!retxt.isEmpty()){
						message = "新浪微博发布成功！";
					}else{
						isSuccess = false;
						message = "新浪微博发布失败！请检查公账号配置";
					}
				} catch (Exception e) {
					isSuccess = false;
					message = "新浪微博发布失败！请检查公账号配置";
					e.printStackTrace();
				}
			}else{
				String site=PlatFormUtil.getSessionSite().getId();
				SinaWeiboEntity sinaWeibo=sinaWeiboService.getSitEntity(site);
				String access_token =sinaWeibo.getAccesstoken();
				String content=sinaContent.getSinaDigest();
				String sinaStaus=StringEscapeUtils.unescapeHtml4(content);
				String sinaUrl=PlatFormUtil.getCurrentSitedomain()+sinaContent.getSinaUrl();
				Timeline tm = new Timeline(access_token);
				try {
					Status status = tm.updateStatus(sinaStaus+sinaUrl);
					String retxt=status.getId();
					if(!retxt.isEmpty()){
						message = "新浪微博发布成功！";
					}else{
						isSuccess = false;
						message = "新浪微博发布失败！请检查公账号配置";
					}
				} catch (WeiboException e) {
					isSuccess = false;
					message = "新浪微博发布失败！请检查公账号配置";
					e.printStackTrace();
				}	
			}
			
			LogUtil.info(message);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "sinaContentController.do?sinaContent");
		String str = j.toString();
		return str;
		}
	}

	
	public static byte[] readFileImage(String filename) throws IOException {
		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				new FileInputStream(filename));
		int len = bufferedInputStream.available();
		byte[] bytes = new byte[len];
		int r = bufferedInputStream.read(bytes);
		if (len != r) {
			bytes = null;
			throw new IOException("读取文件不正确");
		}
		bufferedInputStream.close();
		return bytes;
	}
	
	public static byte[] readHttpImage(String http) throws IOException {
		URL	url = new URL(http);//filePath是网络的地址
    	URLConnection conn = url.openConnection();
        InputStream inStream = conn.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inStream);
		int len = bufferedInputStream.available();
		byte[] bytes = new byte[len];
		int r = bufferedInputStream.read(bytes);
		if (len != r) {
			bytes = null;
			throw new IOException("读取文件不正确");
		}
		bufferedInputStream.close();
		return bytes;
	}
	
	
	
	/**
	 * 新浪微博内容删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		SinaContentEntity sinaContent = sinaContentService
				.getEntity(java.lang.String.valueOf(id));
		message = "新浪微博内容【" + sinaContent.getId() + "】删除成功";
		sinaContentService.delete(sinaContent);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "sinaContentController.do?sinaContent");
		String str = j.toString();
		return str;
	}

	/**
	 * 相关搜索弹出框
	 * 
	 * @return
	 * @author zhangxiaoqiang
	 */
	@RequestMapping(params = "correlationDialog")
	public ModelAndView correlationDialog(ContentsEntity contents,
			String title, String classify1, HttpServletRequest request) {

		// 获取当前毫秒数
		String temporary = request.getParameter("temporary");

		// 获取内容列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class, pageSize,
				pageNo, "", "");
		if (!"0".equals(classify1)) {
			cq.eq("classify", classify1);
		}
		cq.eq("constants", "50");
		cq.addOrder("created", SortDirection.desc);
		cq.addOrder("published", SortDirection.desc);
		cq.add();
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, contents, param);
		// 排序条件
		PageList pageList = this.contentsService.getPageList(cq, true);
		List<ContentsEntity> testList = pageList.getResultList();
		for (ContentsEntity content : testList) {
			ContentCatEntity contentCat = contentsService.get(
					ContentCatEntity.class, content.getCatid());
			if (contentCat != null) {
				content.setCatName(contentCat.getName());
			}
		}
		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("temporary", temporary);
		m.put("classify1", classify1);
		m.put("title", title);
		m.put("actionUrl",
				"sinaContentController.do?correlationDialog&temporary="
						+ temporary);
		return new ModelAndView("weibo/sinacontent/attachArticleList", m);
	}

	/**
	 * 新浪微博内容删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "correlation")
	@ResponseBody
	public String correlation(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("radStr");
		ContentsEntity content = contentsService.getEntity(
				ContentsEntity.class, id);

		j.accumulate("isSuccess", true);

		j.accumulate("content", content);
		String str = j.toString();
		return str;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
