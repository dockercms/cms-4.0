package com.leimingtech.cms.controller.lucene;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.leimingtech.core.util.oConvertUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.service.LuceneServiceI;
import com.leimingtech.cms.service.catalog.CatalogServiceI;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.NewsIndexEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.VideoSourcesEntity;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.SiteServiceI;
import com.leimingtech.core.service.VideoSourcesServiceI;
import com.leimingtech.core.util.PlatFormUtil;

/**
 * @author :linjm linjianmao@gmail.com
 * @version :2014-4-15下午05:20:07 description :
 **/

@Controller
@RequestMapping("/front/frontController")
public class FrontController extends BaseController {

	@Autowired
	private LuceneServiceI luceneServiceImpl;
	@Autowired
	private VideoSourcesServiceI videoSourcesService;
	@Autowired
	private CatalogServiceI catalogService;

	@Autowired
	private SiteServiceI siteService;// 站点管理

	@Autowired
	private MemberServiceI memberMng;
	@Autowired
	private ContentsServiceI contentsService;

	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	@RequestMapping(params = "search")
	public ModelAndView Search(HttpServletRequest request) {
		String keyWord = request.getParameter("keyword");
		keyWord = StringEscapeUtils.escapeHtml4(oConvertUtils.getString(keyWord, ""));
		String siteid = request.getParameter("siteid");
		String catid = request.getParameter("catid") == null ? "" : request.getParameter("catid");
		String classify = request.getParameter("classify") == null ? "" : request.getParameter("classify");

		SiteEntity site = null;

		if (StringUtils.isNotEmpty(siteid)) {
			site = siteService.getSite(String.valueOf(siteid));
		}

		if (site == null) {
			site =PlatFormUtil.getFrontSessionSite();
		}

		// 获取页码数
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		List<NewsIndexEntity> newslist = new ArrayList<NewsIndexEntity>();
		Map<String, Object> newsMap = new HashMap<String, Object>();
		long start = 0;
		long end = 0;
		Integer totalSize = 0;
		if (!StringUtils.isEmpty(keyWord)) {
			try {
				keyWord = URLDecoder.decode(keyWord, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			start = System.currentTimeMillis();
			Map params = new HashMap();
			params.put("keyword", keyWord);
			params.put("catid", catid);
			params.put("classify", classify);
			params.put("pageNO", pageNo);
			params.put("pageSize", pageSize);
			try {
				newsMap = luceneServiceImpl.searchIndex(site, params);// 检索后返回的数据
				newslist = (List<NewsIndexEntity>) newsMap.get("newslist");// 当前页条数
				totalSize = (Integer) newsMap.get("totalSize");// 总条数
			} catch (Exception e) {
				e.printStackTrace();
			}
			end = System.currentTimeMillis();
		}

		int pageCount = (int) Math.ceil((double) totalSize / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}

		List<ContentCatEntity> catList = catalogService.catalogList(siteid);


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("site", site);
		map.put("keyword", keyWord);
		map.put("searchtime", (end - start));
		map.put("pageList", newslist);
		map.put("pageNo", pageNo);
		map.put("totalSize", totalSize);
		map.put("pageCount", pageCount);
		map.put("sitePath", memberMng.getSitePath());
		map.put("domain", PlatFormUtil.getSessionSite().getDomain());
		map.put("classify", classify);
		map.put("catid", catid);
		map.put("catList", catList);

		return new ModelAndView("lucene/results", map);
	}

	/**
	 * 根据类型查询
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	@RequestMapping(params = "searchType")
	public ModelAndView searchType(HttpServletRequest request) {
		String keyWord = request.getParameter("keyword");

		keyWord = StringEscapeUtils.escapeHtml4(oConvertUtils.getString(keyWord, ""));

		String istype = request.getParameter("istype");//搜索的类型,1:标题,2:正文
		String siteid = request.getParameter("siteid");
		String catid = request.getParameter("catid") == null ? "" : request.getParameter("catid");
		String classify = request.getParameter("classify") == null ? "" : request.getParameter("classify");

		SiteEntity site = null;

		if (StringUtils.isNotEmpty(siteid)) {
			site = siteService.getSite(String.valueOf(siteid));
		}

		if (site == null) {
			site=PlatFormUtil.getFrontSessionSite();
		}

		// 获取页码数
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		List<NewsIndexEntity> newslist = new ArrayList<NewsIndexEntity>();
		Map<String, Object> newsMap = new HashMap<String, Object>();
		long start = 0;
		long end = 0;
		Integer totalSize = 0;
		try {
			keyWord = URLDecoder.decode(keyWord, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		start = System.currentTimeMillis();
		Map params = new HashMap();
		params.put("keyword", keyWord);
		params.put("istype", istype);
		params.put("catid", catid);
		params.put("classify", classify);
		params.put("pageNO", pageNo);
		params.put("pageSize", pageSize);
		try {
			newsMap = luceneServiceImpl.searchIndex(site, params);// 检索后返回的数据
			newslist = (List<NewsIndexEntity>) newsMap.get("newslist");// 当前页条数
			totalSize = (Integer) newsMap.get("totalSize");// 总条数
		} catch (Exception e) {
			e.printStackTrace();
		}
		end = System.currentTimeMillis();

		int pageCount = (int) Math.ceil((double) totalSize / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}

		List<ContentCatEntity> catList = catalogService.catalogList(siteid);


		SiteEntity siteMaster=PlatFormUtil.getFrontSessionSite();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("site", site);
		map.put("keyword", keyWord);
		map.put("searchtime", (end - start));
		map.put("pageList", newslist);
		map.put("pageNo", pageNo);
		map.put("pageCount", pageCount);
		map.put("sitePath", siteMaster.getSitePath()); //主站的路径地址
		map.put("domain", PlatFormUtil.getSessionSite().getDomain());
		map.put("classify", classify);
		map.put("catid", catid);
		map.put("catList", catList);
		map.put("istype", istype);

		if(siteid==null || siteid==""){
			return new ModelAndView("lucene/results_gov",map);
		}else{
			return new ModelAndView("lucene/results_main", map);
		}
	}

	/**
	 * 前台视频播放
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "videoPlayer")
	public ModelAndView videoPlayer(HttpServletRequest request) {
		String id = request.getParameter("id");
		VideoSourcesEntity v = videoSourcesService.singleResult("from VideoSourcesEntity where id=" + id);
		Map<String, String> m = new HashMap<String, String>();
		m.put("uri", v.getLocalpath());
		m.put("defaultimage", v.getDefaultimage());
		m.put("videoName", v.getVideoname());
		return new ModelAndView("plug-in/ckplayer/player", m);
	}

	@RequestMapping(params = "creatIndex")
	@ResponseBody
	public String creatIndex(HttpServletRequest reqeust) {
		String message = null;
		JSONObject j = new JSONObject();
		try {
			luceneServiceImpl.creatIndex(PlatFormUtil.getSessionSite());
			message = "索引建立成功";
		} catch (Exception e) {
			message = "索引建立失败";
			e.printStackTrace();
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "luceneController.do?table");
		String str = j.toString();
		return str;
	}

	/**
	 * wap--端搜索--
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "wapSearch")
	@ResponseBody
	public String wapSearch(HttpServletRequest request) {
		String keyWord = request.getParameter("keyword");

		String siteid = request.getParameter("siteid");
		String catid = request.getParameter("catid") == null ? "" : request.getParameter("catid");
		String classify = request.getParameter("classify") == null ? "" : request.getParameter("classify");

		SiteEntity site = null;

		if (StringUtils.isNotEmpty(siteid)) {
			site = siteService.getSite(String.valueOf(siteid));
		}

		if (site == null) {
			List<SiteEntity> siteList = siteService.siteList();
			if (siteList != null && siteList.size() > 0) {
				site = siteList.get(0);
			} else {
				site = new SiteEntity();
			}
		}

		// 获取页码数
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 3 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		List<NewsIndexEntity> newslist = new ArrayList<NewsIndexEntity>();
		Map<String, Object> newsMap = new HashMap<String, Object>();
		long start = 0;
		long end = 0;
		Integer totalSize = 0;
		if (!StringUtils.isEmpty(keyWord)) {
			try {
				keyWord = URLDecoder.decode(keyWord, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			start = System.currentTimeMillis();
			Map params = new HashMap();
			params.put("keyword", keyWord);
			params.put("catid", catid);
			params.put("classify", classify);
			params.put("pageNO", pageNo);
			params.put("pageSize", pageSize);
			try {
				newsMap = luceneServiceImpl.searchIndex(site, params);// 检索后返回的数据
				newslist = (List<NewsIndexEntity>) newsMap.get("newslist");// 当前页条数
				totalSize = (Integer) newsMap.get("totalSize");// 总条数
			} catch (Exception e) {
				e.printStackTrace();
			}
			end = System.currentTimeMillis();
		}

		int pageCount = (int) Math.ceil((double) totalSize / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}

		List<ContentCatEntity> catList = catalogService.catalogList(siteid);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyWord);
		map.put("searchtime", (end - start));
		map.put("pageList", newslist);
		map.put("pageNo", pageNo);
		map.put("pageCount", pageCount);
		map.put("sitePath", memberMng.getSitePath());
		map.put("domain", PlatFormUtil.getSessionSite().getDomain());
		map.put("classify", classify);
		map.put("catid", catid);
		map.put("catList", catList);

		JSONArray jArray = new JSONArray();

		for (NewsIndexEntity n : newslist) {
			JSONObject jObject = new JSONObject();
			jObject.accumulate("title", n.getTitle());
			jObject.accumulate("wapurl", n.getWapurl());
			jArray.add(jObject);
		}

		return jArray.toString();
	}

	@RequestMapping(params = "mysearch")
	public ModelAndView MySearch(HttpServletRequest request) {
		String keyWord = request.getParameter("keyword");
		keyWord = StringEscapeUtils.escapeHtml4(oConvertUtils.getString(keyWord, ""));
		String siteid = request.getParameter("siteid");

		String catid = request.getParameter("catid") == null ? "" : request.getParameter("catid");
		String classify = request.getParameter("classify") == null ? "" : request.getParameter("classify");

		SiteEntity site = null;

		if (StringUtils.isNotEmpty(siteid)) {
			site = siteService.getSite(String.valueOf(siteid));
		}

		if (site == null) {
			site =PlatFormUtil.getFrontSessionSite();
		}

		// 获取页码数
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		List<NewsIndexEntity> newslist = new ArrayList<NewsIndexEntity>();
		Map<String, Object> newsMap = new HashMap<String, Object>();
		long start = 0;
		long end = 0;
		Integer totalSize = 0;
		if (!StringUtils.isEmpty(keyWord)) {
			try {
				keyWord = URLDecoder.decode(keyWord, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			start = System.currentTimeMillis();
			Map params = new HashMap();
			params.put("keyword", keyWord);
			params.put("pageNO", pageNo);
			params.put("pageSize", pageSize);
			params.put("catid", catid);
			params.put("classify", classify);
			try {
				newsMap = luceneServiceImpl.searchIndex(site, params);// 检索后返回的数据
				newslist = (List<NewsIndexEntity>) newsMap.get("newslist");// 当前页条数
				totalSize = (Integer) newsMap.get("totalSize");// 总条数
			} catch (Exception e) {
				e.printStackTrace();
			}
			end = System.currentTimeMillis();
		}

		int pageCount = (int) Math.ceil((double) totalSize / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("site", site);
		map.put("keyword", keyWord);
		map.put("searchtime", (end - start));
		map.put("pageList", newslist);
		map.put("pageNo", pageNo);
		map.put("pageCount", pageCount);
		map.put("sitePath", memberMng.getSitePath());
		map.put("domain", PlatFormUtil.getSessionSite().getDomain());
		return new ModelAndView("lucene/myresults", map);
	}

}
