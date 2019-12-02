package com.leimingtech.cms.controller.favorites;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.service.FavoritesServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.CollectFrontEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 收藏
 * @author 
 * @date 2014-08-11 17:56:59
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/favoritesController")
public class FavoritesController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FavoritesController.class);

	@Autowired
	private FavoritesServiceI favoritesService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 收藏列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "favorites")
	public ModelAndView favorites(CollectFrontEntity favorites, HttpServletRequest request) {
		//获取收藏列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(CollectFrontEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, favorites, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.favoritesService.getPageList(cq, true);
		List<CollectFrontEntity> resultList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", resultList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "favoritesController.do?favorites");
		return new ModelAndView("cms/favorites/favoritesList", m);
	}

	/**
	 * 收藏添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("favorites", new CollectFrontEntity());
		return new ModelAndView("cms/favorites/favorites", m);
	}
	
	/**
	 * 收藏更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		CollectFrontEntity favorites = favoritesService.getEntity(CollectFrontEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("favorites", favorites);
		return new ModelAndView("cms/favorites/favorites", m);
	}

	/**
	 * 收藏保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(CollectFrontEntity favorites, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(favorites.getId())) {
			message = "收藏["+favorites.getContent()+"]更新成功";
			CollectFrontEntity t = favoritesService.get(CollectFrontEntity.class, favorites.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(favorites, t);
				favoritesService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "收藏更新失败";
			}
		} else {
			message = "收藏["+favorites.getContent()+"]添加成功";
			favorites.setCreatedtime(new Date());//创建时间
			favoritesService.save(favorites);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "favoritesController.do?favorites");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 收藏删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		CollectFrontEntity favorites = favoritesService.getEntity(CollectFrontEntity.class, String.valueOf(id));
		message = "收藏["+favorites.getContent()+"]删除成功";
		favoritesService.delete(favorites);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "favoritesController.do?favorites");
		String str = j.toString();
		return str;
	}
}
