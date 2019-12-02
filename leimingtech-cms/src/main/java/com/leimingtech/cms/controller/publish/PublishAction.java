package com.leimingtech.cms.controller.publish;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.service.ContentCatServiceI;
import com.leimingtech.core.service.IStatic;
import com.leimingtech.core.service.StaticWapHtmlServiceI;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.PlatFormUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * CMS发布
 * 
 * @author liuzhen 2014年5月14日 14:25:27
 * 
 */
@Controller
@RequestMapping("/publishAct")
public class PublishAction extends BaseController {
	private String message;

	@Autowired
	private IStatic staticMng;

	@Autowired
	private StaticWapHtmlServiceI staticWapHtmlService;// 发布wap页面接口

	@Autowired
	private ContentCatServiceI contentCatService;

	/**
	 * 快速发布栏目
	 * 
	 * @param request
	 * @param catalogid
	 *            栏目id
	 * @return
	 */
	@RequestMapping(params = "publishCatalog")
	@ResponseBody
	public String publishCatalog(HttpServletRequest request, String catalogid) {
		request.setAttribute("static2Html",true);//主动静态化标识
		JSONObject j = new JSONObject();
		if (catalogid != null) {
			ContentCatEntity catalog = contentCatService.getEntity(catalogid);

			if (catalog != null) {
				long curTime = System.currentTimeMillis();
				LogUtil.info("栏目《" + catalog.getName() + "》开始发布");
				try {
					staticMng.staticCatalog(PlatFormUtil.getSessionSite(),
							catalogid);
					staticWapHtmlService.staticWapCatalog(catalogid);
					message = "栏目《" + catalog.getName() + "》发布成功！";
					j.accumulate("isSuccess", true);
				} catch (Exception e) {
					e.printStackTrace();
					message = "栏目发布失败！原因：" + e.getMessage();
					j.accumulate("isSuccess", false);
				} finally {
					LogUtil.info("栏目《" + catalog.getName() + "》发布结束\t总耗时："
							+ (System.currentTimeMillis() - curTime) + "毫秒");
				}
			} else {
				message = "栏目不存在，发布失败!";
				j.accumulate("isSuccess", false);
			}
		} else {
			message = "栏目不存在，发布失败!";
			j.accumulate("isSuccess", false);
		}

		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	/**
	 * 发布全部栏目
	 * 
	 * @param request
	 * @param catalogid
	 *            栏目id
	 * @return
	 */
	@RequestMapping(params = "publishCatalogAndAllContent")
	@ResponseBody
	public String publishCatalogAndAllContent(HttpServletRequest request,
			String catalogid) {
		request.setAttribute("static2Html",true);//主动静态化标识
		JSONObject j = new JSONObject();
		if (catalogid != null) {
			ContentCatEntity catalog = contentCatService.getEntity(catalogid);

			if (catalog != null) {
				long curTime = System.currentTimeMillis();
				LogUtil.info("栏目《" + catalog.getName() + "》开始发布");
				try {
					staticMng.staticCatalog(PlatFormUtil.getSessionSite(),
							catalogid);
					staticWapHtmlService.staticWapCatalog(catalogid, -1);
					message = "栏目《" + catalog.getName() + "》发布成功！";
					j.accumulate("isSuccess", true);
				} catch (Exception e) {
					e.printStackTrace();
					message = "栏目发布失败！原因：" + e.getMessage();
					j.accumulate("isSuccess", false);
				} finally {
					LogUtil.info("栏目《" + catalog.getName() + "》发布结束\t总耗时："
							+ (System.currentTimeMillis() - curTime) + "毫秒");
				}
			} else {
				message = "栏目不存在，发布失败!";
				j.accumulate("isSuccess", false);
			}
		} else {
			message = "栏目不存在，发布失败!";
			j.accumulate("isSuccess", false);
		}

		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	/**
	 * 发布文章
	 * 
	 * @param request
	 * @param idsStr
	 *            文章id
	 * @return
	 */
	@RequestMapping(params = "publishArticle")
	@ResponseBody
	public String publishArticle(HttpServletRequest request, String idsStr,
			String catalogid) {
		ContentCatEntity contentCat=this.contentCatService.getEntity(catalogid);
		request.setAttribute("static2Html",true);//主动静态化标识
		JSONObject j = new JSONObject();
		if (StringUtils.isNotEmpty(idsStr)) {
			String[] tempArray = idsStr.split(",");
			String[] ids = new String[tempArray.length];
			for (int i = 0; i < tempArray.length; i++) {
				ids[i] = String.valueOf(tempArray[i]);
			}
			try {
				staticMng.staticContents(PlatFormUtil.getSessionSite(),
						contentCat, ids);
				staticWapHtmlService.staticWapArticle(ids);
				message = "发布" + ids.length + "篇文章成功！";
				j.accumulate("isSuccess", true);
			} catch (Exception e) {
				e.printStackTrace();
				message = "文章发布失败！原因：" + e.getMessage();
				j.accumulate("isSuccess", false);
			}
		} else {
			message = "文章不存在，发布失败!";
			j.accumulate("isSuccess", false);
		}

		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	/**
	 * 快速发布站点
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "publishSite")
	@ResponseBody
	public String publishSite(HttpServletRequest request) {
		request.setAttribute("static2Html",true);//主动静态化标识
		JSONObject j = new JSONObject();
		SiteEntity site = PlatFormUtil.getSessionSite();

		long curTime = System.currentTimeMillis();
		LogUtil.info("站点《" + site.getSiteName() + "》开始发布");
		try {
			staticMng.staticSite(site);
			staticWapHtmlService.staticWapSite(site);
			message = "站点《" + site.getSiteName() + "》发布成功！";
			j.accumulate("isSuccess", true);
		} catch (Exception e) {
			e.printStackTrace();
			message = "站点《" + site.getSiteName() + "》发布失败！原因：" + e.getMessage();
			j.accumulate("isSuccess", false);
		} finally {
			LogUtil.info("站点《" + site.getSiteName() + "》发布结束\t总耗时："
					+ (System.currentTimeMillis() - curTime) + "毫秒");
		}

		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	/**
	 * 发布全站信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "publishSiteAllInfo")
	@ResponseBody
	public String publishSiteAllInfo(HttpServletRequest request) {
		request.setAttribute("static2Html",true);//主动静态化标识
		JSONObject j = new JSONObject();

		SiteEntity site = PlatFormUtil.getSessionSite();

		long curTime = System.currentTimeMillis();
		LogUtil.info("站点《" + site.getSiteName() + "》开始发布");
		try {
			staticMng.staticSite(site, -1);
			staticWapHtmlService.staticWapSite(site, -1);
			message = "站点《" + site.getSiteName() + "》发布成功！";
			j.accumulate("isSuccess", true);
		} catch (Exception e) {
			e.printStackTrace();
			message = "站点《" + site.getSiteName() + "》发布失败！原因：" + e.getMessage();
			j.accumulate("isSuccess", false);
		} finally {
			LogUtil.info("站点《" + site.getSiteName() + "》发布结束\t总耗时："
					+ (System.currentTimeMillis() - curTime) + "毫秒");
		}

		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	/**
	 * 快速发布首页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "publishIndex")
	@ResponseBody
	public String publishIndex(HttpServletRequest request) {
		request.setAttribute("static2Html",true);//主动静态化标识
		JSONObject j = new JSONObject();
		long curTime = System.currentTimeMillis();
		LogUtil.info("首页开始发布");
		try {
			SiteEntity site = PlatFormUtil.getSessionSite();
			staticMng.staticIndex(site);
			staticWapHtmlService.staticWapIndex(site);
			message = "首页发布成功！";
			j.accumulate("isSuccess", true);
		} catch (Exception e) {
			e.printStackTrace();
			message = "首页发布失败！原因：" + e.getMessage();
			j.accumulate("isSuccess", false);
		}finally {
			LogUtil.info("首页发布结束\t总耗时："
					+ (System.currentTimeMillis() - curTime) + "毫秒");
		}

		j.accumulate("msg", message);
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
