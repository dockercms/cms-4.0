package com.leimingtech.cms.controller.dynamic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.controller.contents.ContentsbaseController;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.ContentStatus;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.TemplateData;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.SystemService;

import freemarker.template.TemplateException;

@Controller
@RequestMapping("/front/newsListController")
public class NewsListController extends ContentsbaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(NewsListController.class);
	@Autowired
	private SystemService systemService;
	private String message;
	@Autowired
	private CommonService commonService;
	@Autowired
	private MemberServiceI memberMng;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 新闻列表页模板
	 * 
	 * @throws IOException
	 * @throws TemplateException
	 */

	@RequestMapping(params = "newsList")
	public ModelAndView newsList(HttpServletRequest request,HttpServletResponse response)
			throws IOException, TemplateException {
		// 新闻列表页模板
		String catId = request.getParameter("catId");
		ContentCatEntity cat = commonService.get(ContentCatEntity.class, catId);
		SiteEntity site = commonService.get(SiteEntity.class,cat.getSiteid());
		if(site.getIsSwitch().equals("0")){
			response.sendRedirect("http://"+site.getDomain()+cat.getStaticUrl());
			return null;
		}else{
			List<String> list = (List<String>) request.getSession().getAttribute("channel");
			if (list == null || list.size() == 0 || !list.contains(catId)) {
				return new ModelAndView("lmPage/main/noAuth");
			}
			Map<String, Object> m = new HashMap<String, Object>();

			Integer pagesize = cat.getPagesize() == 0 ? 10 : cat.getPagesize();
			int PageIndex = StringUtils.isEmpty(request.getParameter("PageIndex")) ? 1
					: Integer.valueOf(request.getParameter("PageIndex"));

//			List<String> catalogids = new ArrayList<String>();

			CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class);

//			catalogids = getChildidList(catalogids, cat,list);
//			if (catalogids != null && catalogids.size() > 0) {
//				cq.in("catid", toStringArray(catalogids));
				cq.eq("catid", catId);
//			} else {
//				cq.eq("catid", -1 + "");
//
//			}
			cq.eq("siteid", site.getId());

			Date date = new Date();
			cq.or(Restrictions.isNull("noted"), Restrictions.ge("noted", date));

			cq.le("published", date);
			cq.in("constants", new String[] { ContentStatus.CONTENT_COMMITTED,
					ContentStatus.CONTENT_PUBLISHED });
			cq.addOrder("published", SortDirection.desc);
			cq.addOrder("id", SortDirection.desc);
			cq.add();
			List<ContentsEntity> contentList = commonService
					.getListByCriteriaQuery(cq, false);

			Integer pagecount = contentList.size() / pagesize;
			if (contentList.size() % pagesize != 0) {
				pagecount++;
			}

			// 文章
			String templateFilepath = "";
			if (StringUtils.isNotEmpty(cat.getIndexTemplate())) {
				templateFilepath = cat.getIndexTemplate();
			} else {
				templateFilepath = cat.getListModel();
			}
			String fileName[] = templateFilepath.split("\\.");

			String sitePath = site.getSitePath();

			TemplateData t = new TemplateData();
			t.setFirstFileName("newsListController.do?newsList&catId=" + catId);

			t.setPageSize(pagesize);
			t.setPageCount(pagecount);
			t.setTotal(contentList.size());
			t.setPageIndex(PageIndex);
			if (PageIndex == 2) {
				t.setDynamicPreviousPage("newsListController.do?newsList&catId="
						+ catId);
			}
			if (PageIndex > 2) {
				t.setDynamicPreviousPage("newsListController.do?newsList&catId="
						+ catId + "&PageIndex=" + (PageIndex - 1));
			}
			if (PageIndex >= 1 && PageIndex < pagecount) {
				t.setDynamicNextPage("newsListController.do?newsList&catId="
						+ catId + "&PageIndex=" + (PageIndex + 1));

			}
			if (PageIndex == pagecount) {
				t.setDynamicNextPage("newsListController.do?newsList&catId="
						+ catId + "&PageIndex=" + (pagecount));

			}
			t.setDynamicLastPage("newsListController.do?newsList&catId=" + catId
					+ "&PageIndex=" + (pagecount));
			String pagebar = t.getDynamicPageBar(catId);
			m.put("catalogidF", catId);
			m.put("catalogF", cat);
			m.put("sitePath", sitePath);
			m.put("pagebar", pagebar);
			m.put("pageindexF", PageIndex);
			m.put("site",site);

			return new ModelAndView("wwwroot/" + sitePath + "/template/"
					+ fileName[0], m);
		}
		
			
		
		
	}

	private List<String> getChildidList(List<String> idList,
			ContentCatEntity contentCat, List<String> list) {
		if (contentCat != null&&list.contains(contentCat.getId())) {
			idList.add(contentCat.getId());
			List<ContentCatEntity> contentCatList = commonService
					.findByProperty(ContentCatEntity.class, "contentCat.id",
							contentCat.getId());
			if (contentCatList != null && contentCatList.size() > 0) {
				for (ContentCatEntity contentCatEntity : contentCatList) {
					idList = getChildidList(idList, contentCatEntity,list);
				}
			}
		}
		return idList;
	}

	private String[] toStringArray(List<String> args) {
		if (args != null && args.size() == 0)
			return null;

		String[] intArray = new String[args.size()];
		for (int i = 0; i < args.size(); i++) {
			intArray[i] = args.get(i);
		}
		return intArray;
	}
}
