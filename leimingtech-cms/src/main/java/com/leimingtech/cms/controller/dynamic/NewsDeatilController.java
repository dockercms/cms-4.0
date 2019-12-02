package com.leimingtech.cms.controller.dynamic;

import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.*;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.IContentTagMng;
import com.leimingtech.core.service.MemberDepartServiceI;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.ResourceUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/front/newsDeatilController")
public class NewsDeatilController extends BaseController {
	@Autowired
	private CommonService commonService;
	@Autowired
	private IContentTagMng contentMng;
	/**会员管理接口*/
	@Autowired
	private MemberServiceI memberService;
	/**会员所属部门管理接口*/
	@Autowired
	private MemberDepartServiceI memberDepartService;

	/**
	 * 新闻详情页模板
	 * @throws IOException 
	 */

	@RequestMapping(params = "newsDeatil")
	public ModelAndView newsDeatil(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// 新闻详情页模板
		String contentId = request.getParameter("contentId"); // 内容id
	
		int pageIndex = StringUtils.isEmpty(request.getParameter("PageIndex")) ? 1
				: Integer.valueOf(request.getParameter("PageIndex"));

		ContentsEntity contents = commonService.get(ContentsEntity.class,
				contentId);
		SiteEntity site = commonService.get(SiteEntity.class,
				contents.getSiteid());
		ContentCatEntity cat = commonService.get(ContentCatEntity.class,
				contents.getCatid());
		if (site.getIsSwitch().equals("0")) {
			//当前站点为静态站直接跳转到静态页面
			response.sendRedirect("http://" + site.getDomain());
			return null;
		}

		List<String> list = (List<String>) request.getSession().getAttribute(
				"channel");
		if (list == null || list.size() == 0 || !list.contains(cat.getId())) {
			//此栏目没有授权不允许访问
			response.sendRedirect("http://" + site.getDomain());
			return null;
		}

		Map<String, Object> data = contentMng.getContent(contents);

		List<ContentAccessoryEntity> accessoryList = commonService
				.findByProperty(ContentAccessoryEntity.class, "contentId",
						contentId);
		String uploadPath = ResourceUtil.getCMSUploadFilesPath();

		String sitePath = site.getSitePath();
		String templatePath = contentMng.getContentTemplatePath(contents,cat);
		String fileName[] = templatePath.split("\\.");
		data.put("newTag", new TagCreator());
		data.put("contentidF", contentId);
		data.put("accessoryList", accessoryList);
		data.put("uploadPath", uploadPath);
		data.put("contentF", contents);
		ArticleEntity art = commonService.findUniqueByProperty(
				ArticleEntity.class, "contentid", contentId);
		if (art != null && StringUtils.isNotEmpty(art.getContent())) {
			String[] datapage = art.getContent().split(
					CmsConstants.CONTENT_PAGE_CODE);
			if (datapage.length > 1) {
				/**
				 * 如果文章有分页,进行分页
				 */
				TemplateData td = new TemplateData();
				td.setPageCount(datapage.length);

				td.setTotal(datapage.length);

				td.setPageIndex(pageIndex);
				td.setFirstFileName("newsDeatilController.do?newsDeatil&contentId="
						+ contentId);
				td.setOtherFileName("newsDeatilController.do?newsDeatil&contentId="
						+ contentId + "&PageIndex=");
				if (td.getPageIndex() == 2) {
					td.setDynamicPreviousPage("newsDeatilController.do?newsDeatil&contentId="
							+ contentId);
				}
				if (td.getPageIndex() > 2) {
					td.setDynamicPreviousPage("newsDeatilController.do?newsDeatil&contentId="
							+ contentId
							+ "&PageIndex="
							+ (td.getPageIndex() - 1));
				}
				if (td.getPageIndex() >= 1
						&& td.getPageIndex() < td.getPageCount()) {
					td.setDynamicNextPage("newsDeatilController.do?newsDeatil&contentId="
							+ contentId
							+ "&PageIndex="
							+ (td.getPageIndex() + 1));

				}
				if (td.getPageIndex() == td.getPageCount()) {
					td.setDynamicNextPage("newsDeatilController.do?newsDeatil&contentId="
							+ contentId + "&PageIndex=" + (td.getPageCount()));
				}
				ArticleEntity a = new ArticleEntity();
				try {
					MyBeanUtils.copyBeanNotNull2Bean(art, a);
				} catch (Exception e) {
					e.printStackTrace();
				}

				a.setContent(datapage[pageIndex - 1]);

				data.put("articleDataF", a);
				String html = td.getDynamicPageBreakBar(1);
				data.put("PageBreakBar", html);
			}
		}
		data.put("catalogF", cat);
		data.put("sitePath", sitePath);
		data.put("site", site);

		return new ModelAndView("wwwroot/" + sitePath + "/template/"
				+ fileName[0], data);
	}
	
	
	/**
	 * 文章内容附件下载
	 * @param request
	 * @param response
	 * @throws IOException
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
}
