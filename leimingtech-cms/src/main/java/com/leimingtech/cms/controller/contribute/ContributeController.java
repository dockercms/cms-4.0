package com.leimingtech.cms.controller.contribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.leimingtech.cms.service.accessory.ContentAccessoryServiceI;
import com.leimingtech.core.entity.ContentAccessoryEntity;
import com.leimingtech.core.entity.member.MemberEntity;
import com.leimingtech.core.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.controller.contents.ContentClassify;
import com.leimingtech.cms.service.ContributeServiceI;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ArticleEntity;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.util.PlatFormUtil;

/**
 * 会员中心投搞
 * @author LKANG
 * 2014-08-13 10:55:00
 */
@Controller
@RequestMapping("/member/contributeController")
public class ContributeController extends BaseController {
	@Autowired
	private ContentCatServiceI contentCatService; // 栏目实现类
	@Autowired
	private ArticleServiceI articleService;       // 文章内容实现类
	@Autowired
	private ContentsServiceI contentsService;     // 文章实现类
	
	@Autowired
	private MemberServiceI memberMng;             // 会员实现类
	@Autowired
	private ContributeServiceI contributeServiceImpl;
	@Autowired
	private MemberServiceI memberService;
	@Autowired
	private MemberDepartServiceI memberDepartService;

	@Autowired
	private ContentAccessoryServiceI contentAccessoryService;

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	/**
	 * 添加投搞页面
	 * @return
	 */
	@RequestMapping(params = "addContribute")
	public ModelAndView addContribute(HttpServletRequest request) {
		Map<String, Object> m = new HashMap<String, Object>();
		String id = request.getParameter("id");
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(id)){
			map = contributeServiceImpl.getContentById(String.valueOf(id));
		}
		List<ContentCatEntity> content=contentCatService.findByQueryString("from ContentCatEntity where iscontribute=1");
		m.put("content", content);

		if(!map.isEmpty()){
			List<ContentAccessoryEntity> accessoryList =contentAccessoryService.findByContentId(id);
			m.put("accessoryList", accessoryList);
		}

		m.put("contents", map);
		m.put("sitePath", memberMng.getSitePath());
		m.put("site",  PlatFormUtil.getFrontSessionSite());
		return new ModelAndView("member/addcontribute", m);
	}
	
	
	/**
	 * 加载栏目树
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "contentCatTree")
	@ResponseBody
	public JSONArray contentCatTree(HttpServletRequest request) {
		String catid = request.getParameter("catid");
		JSONArray jsonArray = new JSONArray();
		String[] models = {ContentClassify.CONTENT_ARTICLE_STR};
		jsonArray = contentCatService.getContentCatForJsonArray(models, 1, catid, "1");
		return jsonArray;
	}
	
	/**
	 * 保存投搞
	 * @param content
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveContribute")
	@ResponseBody
	public String saveContribute(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		  
		String randCode = session.getAttribute("randCode").toString();
		String code = request.getParameter("valicode");
		String contentCatId = request.getParameter("contentCatId");
		try {
			if (StringUtils.isNotEmpty(contentCatId)) {
				if (randCode.equalsIgnoreCase(code)) {
					message = "投搞成功";
					Map<String, String> map = requestMap(request);
					contributeServiceImpl.saveContribute(map);
					return success(message).accumulate("toUrl",
							"contributeController.do?contentsList").toString();
				} else {
					message = "验证码错误";
					return error(message).toString();
				}

			} else {
				message = "没有选择栏目";
				return error(message).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "投搞失败";
			return error(message).toString();
		}
	} 
	
	/**
	 * 获取投搞列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "contentsList")
	public ModelAndView contentsList(ContentsEntity contens, HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageIndex")) ? 1 : Integer.valueOf(request.getParameter("pageIndex"));
		Map map = request.getParameterMap();
		MemberEntity member = memberMng.getSessionMember();
		
				
		PageList list = contentsService.contentsList(pageSize, pageNo, map, member.getId(), contens);
		List<ContentsEntity> resultList = list.getResultList();
		int pageCount = (int)Math.ceil((double)list.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", map);
		m.put("pageList", resultList);
		m.put("pageNo", list.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		
		m.put("countNum", list.getCount());
		m.put("searchUrl", "contributeController.do?contentsList");
		m.put("sitePath", memberMng.getSitePath());
		m.put("site",  PlatFormUtil.getFrontSessionSite());
		return new ModelAndView("member/contributelist", m);
	}
	
	/**
	 * 投稿删除
	 * @return
	 */
	@RequestMapping(params = "delContribute")
	@ResponseBody
	public String delContribute(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		try {
			message = "删除成功";
			ArticleEntity article = articleService.getArticleById(id);
			articleService.deleArticle(article);
			contentsService.delContentsById(id);
			j.accumulate("isSuccess", true);
			j.accumulate("msg", message);
			j.accumulate("toUrl", "messagesController.do?messageslist");
		} catch (Exception e) {
			e.printStackTrace();
			message = "删除失败";
			j.accumulate("isSuccess", false);
			j.accumulate("msg", message);
			j.accumulate("toUrl", "messagesController.do?messageslist");
		}
		String str = j.toString();
		return str;
	}
}



