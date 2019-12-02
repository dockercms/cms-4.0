package com.leimingtech.cms.controller.videoalburm;

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

import com.leimingtech.cms.entity.VideoalburmEntity;
import com.leimingtech.cms.service.videoalburm.VideoalburmServiceI;
import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.ContentMobileClassify;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.VideoalburmarticleEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ArticleServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.VideoalburmarticleServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**
 * @Title: Controller
 * @Description: 视频专辑
 * @author LKANG
 * @date 2014-07-09 18:45:33
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/videoalburmController")
public class VideoalburmController extends BaseController {

	@Autowired
	private VideoalburmServiceI videoalburmService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ArticleServiceI articleService;
	@Autowired
	private VideoalburmarticleServiceI videoalburmarticleService;
	@Autowired
	private ContentsServiceI contentsService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 视频专辑列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "videoalburm")
	public ModelAndView videoalburm(VideoalburmEntity videoalburm, HttpServletRequest request) {
		// 获取视频专辑列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(VideoalburmEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, videoalburm, param);
		// 排序条件
		cq.addOrder("createdate", SortDirection.desc);
		cq.add();
		PageList pageList = this.videoalburmService.getPageList(cq, true);
		List<VideoalburmEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", resultList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "videoalburmController.do?videoalburm");
		return new ModelAndView("cms/videoalburm/videoalburmList", m);
	}

	/**
	 * 视频专辑添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("videoalburm", new VideoalburmEntity());
		return new ModelAndView("cms/videoalburm/videoalburm", m);
	}

	/**
	 * 视频专辑更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		VideoalburmEntity videoalburm = videoalburmService.getEntity(VideoalburmEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("videoalburm", videoalburm);
		return new ModelAndView("cms/videoalburm/videoalburm", m);
	}

	/**
	 * 视频专辑保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(VideoalburmEntity videoalburm, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(videoalburm.getId())) {
			message = "视频专辑[" + videoalburm.getName() + "]更新成功";
			VideoalburmEntity t = videoalburmService.get(VideoalburmEntity.class, videoalburm.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(videoalburm, t);
				videoalburmService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "视频专辑[" + videoalburm.getName() + "]更新失败";
			}
		} else {
			message = "视频专辑[" + videoalburm.getName() + "]添加成功";
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			videoalburm.setCreatedate(new Date());
			videoalburm.setSiteId(site.getId());
			videoalburmService.save(videoalburm);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "videoalburmController.do?videoalburm");
		String str = j.toString();
		return str;
	}

	/**
	 * 视频专辑删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		VideoalburmEntity videoalburm = videoalburmService.getEntity(VideoalburmEntity.class, String.valueOf(id));
		message = "视频专辑[" + videoalburm.getName() + "]删除成功";
		videoalburmService.delete(videoalburm);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "videoalburmController.do?videoalburm");
		String str = j.toString();
		return str;
	}

	/**
	 * 视频专辑视频管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "addVideo")
	public ModelAndView addVideo(HttpServletRequest reqeust) {

		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		String siteid = site.getId();
		String classify = ContentMobileClassify.CONTENT_VIDEO;
		String id = reqeust.getParameter("id"); // 视频专辑ID

		String searchname = reqeust.getParameter("name");
		StringBuffer sql = new StringBuffer();
		sql.append(" select c.title, c.published, cc.name, cv.id alburmarticleid from cms_content c ");
		sql.append(" left join cms_content_cat cc on c.catid = cc.id ");
		sql.append(" left join cms_video_alburm_article cv on cv.articleid = c.id ");
		sql.append(" where c.classify = " + classify + " and c.site_id = " + siteid);
		sql.append(" and cv.alburmid = '" + id + "'");
		if (StringUtils.isNotBlank(searchname)) {
			sql.append(" and c.title like '%" + searchname + "%'");
		}
		List<Map<String, Object>> list = videoalburmService.findForJdbc(sql.toString());
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", list);
		m.put("searchname", searchname);
		m.put("classify", classify);
		m.put("id", id);
		m.put("actionUrl", "videoalburmController.do?videoalburm");
		return new ModelAndView("cms/videoalburm/alburmManagementList", m);
	}

	/**
	 * 选取内容
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "selectArticle")
	public ModelAndView selectArticle(ContentsEntity contens, HttpServletRequest request) {
		String classify = request.getParameter("classify");
		String id = request.getParameter("id");

		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		Map params = new HashMap(request.getParameterMap());
		params.remove("id");
		contens.setId(null);
		PageList pageList = contentsService.contentsList(pageSize, pageNo, params, "-1", contens);

		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}

		Map<String, Object> m = new HashMap<String, Object>();

		m.put("searchMap", params);
		m.put("contentlist", pageList.getResultList());
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		m.put("countNum", pageList.getCount());

		m.put("classify", classify);
		m.put("id", id);
		m.put("actionUrl", "videoalburmController.do?selectArticle");
		return new ModelAndView("cms/videoalburm/selectArticle", m);
	}

	/**
	 * 选取内容保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "saveSelectArticle")
	@ResponseBody
	public String saveSelectArticle(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		String chestr = request.getParameter("chestr");
		chestr = chestr.substring(0, chestr.length() - 1);
		String[] articles = chestr.split(",");
		for (int i = 0; i < articles.length; i++) {
			String articleid = articles[i];
			List<VideoalburmarticleEntity> VideoList=videoalburmarticleService.findByProperty(VideoalburmarticleEntity.class, "articleid", articleid);
			if(VideoList.size()<=0){
			VideoalburmarticleEntity alburmarticle = new VideoalburmarticleEntity();
			alburmarticle.setAlburmid(id);
			alburmarticle.setArticleid(articleid);
			alburmarticle.setCreatedtime(new Date());// 创建时间
			try {
				message = "视频专辑关联文章[" + alburmarticle.getId() + "]添加成功";

				videoalburmarticleService.save(alburmarticle);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "视频专辑关联文章[" + alburmarticle.getId() + "]更新失败";
			}
		  }
		}
		;
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "videoalburmController.do?addVideo&id=" + id);
		String str = j.toString();
		return str;
	}

	/**
	 * 视频专辑列表页弹出框
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "videoalburmWin")
	public ModelAndView videoalburmWin(VideoalburmEntity videoalburm, HttpServletRequest request) {
		// 获取视频专辑列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		PageList pageList = videoalburmService.videoAblurmPageList(pageSize, pageNo, param, videoalburm);
		List<VideoalburmEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", resultList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "videoalburmController.do?videoalburmWin");
		return new ModelAndView("cms/videoalburm/videoAlburmWin", m);
	}

}
