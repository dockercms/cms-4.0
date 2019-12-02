package com.leimingtech.cms.controller.acquisition;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.entity.acquisition.AcquisitionContentEntity;
import com.leimingtech.cms.service.acquisition.AcquisitionContentServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ArticleEntity;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.TSUser;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ContentCatServiceI;
import com.leimingtech.core.service.IStatic;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.PlatFormUtil;

/**
 * @Title: Controller
 * @Description: 采集信息
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-16 11:37:23
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/acquisitionContentController")
public class AcquisitionContentController extends BaseController {
	@Autowired
	private AcquisitionContentServiceI acquisitionContentService;
	@Autowired
	private SystemService systemService;
	/** 栏目管理接口 */
	@Autowired
	private ContentCatServiceI contentCatService;

	private String message;

	@Autowired
	private IStatic staticImpl;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 采集信息列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(AcquisitionContentEntity acquisitionContent,
			HttpServletRequest request) {
		// 获取采集信息列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 20
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(AcquisitionContentEntity.class,
				pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, acquisitionContent, param);
		// 排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.acquisitionContentService
				.getPageList(cq, true);
		List<AcquisitionContentEntity> testList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "acquisitionContentController.do?table");
		return new ModelAndView("cms/acquisition/acquisitionContentList", m);
	}

	/**
	 * 采集信息删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		AcquisitionContentEntity acquisitionContent = acquisitionContentService
				.getEntity(AcquisitionContentEntity.class, String.valueOf(id));
		message = "采集信息【" + acquisitionContent.getTitle() + "】删除成功";
		acquisitionContentService.delete(acquisitionContent);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "acquisitionContentController.do?table");
		String str = j.toString();
		return str;
	}

	/**
	 * 复制到内容页面
	 * 
	 * @param ids
	 * @param requestz
	 * @return
	 */
	@RequestMapping(params = "copy")
	public ModelAndView copy(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("ids", ids);
		m.put("contentCattreeJsonData", contentCatService
				.getContentCatTreeData().toString());
		return new ModelAndView("cms/acquisition/contentCatTree", m);
	}

	/**
	 * 复制到内容
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "toContents")
	@ResponseBody
	public String toContents(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		String contentCatId = request.getParameter("funVal");

		JSONObject j = new JSONObject();
		String[] contentsids = ids.split(",");
		String message = "";
		boolean isSuccess = false;

		ContentCatEntity catalog = null;
		if (StringUtils.isNotBlank(contentCatId)) {
			catalog = acquisitionContentService.getEntity(
					ContentCatEntity.class, contentCatId);
		}

		if (catalog == null) {
			message = "栏目已经不存在";
		} else {
			String siteid = PlatFormUtil.getSessionSiteId();// 站点id

			TSUser user = PlatFormUtil.getSessionUser();
			String username = user.getUserName();// 数据创建人

			if (StringUtils.isNotEmpty(user.getRealName())) {
				username = user.getRealName();
			}

			try {
				for (String contentsid : contentsids) {
					if (StringUtils.isBlank(contentsid))
						continue;

					AcquisitionContentEntity acq = acquisitionContentService
							.get(AcquisitionContentEntity.class, contentsid);
					ContentsEntity c = new ContentsEntity();

					c.setModelid("1");
					c.setClassify("1");
					c.setTitle(acq.getTitle());
					c.setSubtitle(acq.getShortTitle());
					c.setAuthor(acq.getAuthor());
					c.setSourceid(acq.getOrigin());
					c.setSiteid(siteid);
					c.setPathids(catalog.getPathids());
					c.setCatid(contentCatId);
					c.setWeight(0);
					c.setStatus("10");
					c.setCatName(catalog.getName());
					c.setCreated(new Date());
					c.setCreatedby(username);
					c.setConstants("10");
					c.setAllowcomment("true");
					acquisitionContentService.saveOrUpdate(c);
					staticImpl.staticContentActOnPreview(c);

					// 复制文章
					ArticleEntity article = new ArticleEntity();
					article.setContent(c.getId());
					article.setSiteid(siteid);
					article.setContent(acq.getTxt());
					acquisitionContentService.saveOrUpdate(article);
				}
				message = "复制成功";
				isSuccess = true;
			} catch (Exception e) {
				e.printStackTrace();
				message = "复制失败";
			}
		}

		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "acquisitionContentController.do?table");
		String str = j.toString();
		return str;
	}

}
