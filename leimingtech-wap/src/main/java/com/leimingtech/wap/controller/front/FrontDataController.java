package com.leimingtech.wap.controller.front;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.WapManager;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.wap.service.front.FrontService;
/**   
 * @Title: Controller
 * @Description: WAP页面控制器
 * @author 
 * @date 2015年6月18日09:17:16
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/front/frontDataController")
public class FrontDataController extends BaseController {

	private String message;
	@Autowired
	private FrontService frontService;
	@Autowired
	private ContentsServiceI contentsService;
	/**
	 * 加载某个栏目下的新闻数据
	 * 响应ajax请求
	 * @return
	 */
	@RequestMapping(params = "loadData")
	@ResponseBody
	public String loadData(HttpServletRequest reqeust){
		String catId = reqeust.getParameter("contentCatId");
		int pageSize = StringUtils.isEmpty(reqeust.getParameter("pageSize")) ?20: Integer.valueOf(reqeust.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(reqeust.getParameter("pageNo")) ? 1 : Integer.valueOf(reqeust.getParameter("pageNo")); 
		//System.out.println("FrontDataController.loadData()-------------------pageNo="+pageNo+"-----------------------"+this.frontService.getFrontData(catId,pageSize, pageNo));
		return this.frontService.getFrontData(catId,pageSize, pageNo);
	}
	/**
	 * wap上开启的栏目
	 * 响应ajax请求
	 * @return
	 */
	@RequestMapping(params = "catalog")
	@ResponseBody
	public String catalog(HttpServletRequest reqeust){
		SiteEntity site = PlatFormUtil.getSessionSite();//当前站点
		WapManager wapmanager = new WapManager(site);
		 
		String siteid = site.getId();
		Set<String> catalogsSet = wapmanager.getCatalogs();
		String[] catalogids = new String[catalogsSet.size()];
		catalogids = catalogsSet.toArray(catalogids);

		List<ContentCatEntity> allOpenCatalog = new ArrayList<ContentCatEntity>();

		if (catalogids != null && catalogids.length > 0) {
			// 查出开启的栏目
			CriteriaQuery cqCatalog = new CriteriaQuery(ContentCatEntity.class);
			cqCatalog.eq("siteid", siteid);
			cqCatalog.eq("iscatend", 1);
			cqCatalog.eq("isshow", "1");
			cqCatalog.in("id", catalogids);
			cqCatalog.eq("disabled", 0);
			cqCatalog.addOrder("sort", SortDirection.desc);
			cqCatalog.addOrder("id", SortDirection.desc);
			cqCatalog.add();
			allOpenCatalog = contentsService.getListByCriteriaQuery(cqCatalog, false);
		}
		
		JSONArray jArray = new JSONArray();
		for (ContentCatEntity c : allOpenCatalog){
			JSONObject jObject = new JSONObject();
			jObject.accumulate("id", c.getId());
			jObject.accumulate("name", c.getName());
			jArray.add(jObject);
		}
		return jArray.toString();
		 
	}
	
	
}
