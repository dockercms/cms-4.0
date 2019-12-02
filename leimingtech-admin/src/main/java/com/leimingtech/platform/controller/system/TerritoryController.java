package com.leimingtech.platform.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.TSFunction;
import com.leimingtech.core.entity.TSIcon;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.platform.entity.TSTerritory;
import com.leimingtech.platform.service.territory.TerritoryServiceI;


/**
 * 地域处理类
 * @author wushu
 */
@Controller
@RequestMapping("/territoryController")
public class TerritoryController extends BaseController {
	
	private String message = null;
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private TerritoryServiceI territoryService;

	/**
	 * 地域列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "territory")
	public ModelAndView territory(HttpServletRequest request) {
		CriteriaQuery cq = new CriteriaQuery(TSTerritory.class);
		cq.eq("territoryLevel", Short.valueOf("2"));
		cq.add();
		List<TSFunction> list = territoryService.getListByCriteriaQuery(cq, false);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list", list);
		return new ModelAndView("lmPage/test/territoryList",m);
	}
	
	/**
	 * 加载下级
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loadTerritory")
	@ResponseBody
	public JSONArray loadMenu(HttpServletRequest request) {
		String id = request.getParameter("id");
		CriteriaQuery cq = new CriteriaQuery(TSTerritory.class);
		cq.eq("TSTerritory.id", id);
		cq.add();
		List<TSTerritory> list = territoryService.getListByCriteriaQuery(cq, false);
		JSONArray jsonArray = new JSONArray();
		for(TSTerritory territory : list){
			JSONObject json = new JSONObject();
			if(territory.getTSTerritorys() != null && territory.getTSTerritorys().size() > 0){
				json.put("text", territory.getTerritoryName());
				json.put("value", territory.getId());
				json.put("leaf", false);
				json.put("expanded", false);
				json.put("cls", "folder");
				json.put("id", territory.getId());
				json.put("href", "territoryController.do?loadTerritory&id=" + territory.getId());
				json.put("data-role", "branch");
				json.put("children", new JSONArray());
			}else{
				json.put("text", "<i class=\"icon-file-text\" style=\"width: 1em;margin-right: 4px;\"></i>"+territory.getTerritoryName());
				json.put("value", territory.getId());
				json.put("leaf", true);
				json.put("href", "javascript:void(0);");
				json.put("data-role", "leaf");
				json.put("id", territory.getId());
				json.put("checked", false);
			}
			jsonArray.add(json);
		}
		return jsonArray;
	}
	
	/**
	 * 加载下级
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loadTerritorys")
	@ResponseBody
	public String loadMenus(HttpServletRequest request) {
		String id = request.getParameter("id");
		CriteriaQuery cq = new CriteriaQuery(TSTerritory.class);
		cq.eq("TSTerritory.id", id);
		cq.add();
		List<TSTerritory> list = territoryService.getListByCriteriaQuery(cq, false);
		JSONArray jsonArray = new JSONArray();
		for(TSTerritory territory : list){
			JSONObject json = new JSONObject();
			if(territory.getTSTerritorys() != null && territory.getTSTerritorys().size() > 0){
				json.put("text", territory.getTerritoryName());
				json.put("value", territory.getId());
				json.put("leaf", false);
				json.put("expanded", false);
				json.put("cls", "folder");
				json.put("id", territory.getId());
				json.put("href", "territoryController.do?loadTerritory&id=" + territory.getId());
				json.put("data-role", "branch");
				json.put("children", new JSONArray());
			}else{
				json.put("text", territory.getTerritoryName());
				json.put("value", territory.getId());
				json.put("leaf", true);
				json.put("href", "javascript:void(0);");
				json.put("data-role", "leaf");
				json.put("id", territory.getId());
				json.put("checked", false);
			}
			jsonArray.add(json);
		}
		return jsonArray.toString();
	}
	
	/**
	 * 修改
	 * @param test
	 * @param request
	 * @return
	 */
	
	@RequestMapping(params = "editModelPage")
	public ModelAndView editModelPage(HttpServletRequest request){
		String id = request.getParameter("id");
		String selectId = request.getParameter("selectId");
		TSTerritory territory = null;
		if(id != null && !"".equals(id)){
			territory = territoryService.getEntity(TSTerritory.class, id);
		}else{
			String parentId = request.getParameter("parentId");
			territory = new TSTerritory();
			TSTerritory parentTerritory = new TSTerritory();
			parentTerritory.setId(parentId);
			territory.setTSTerritory(parentTerritory);
		}
		List<TSIcon> iconList = territoryService.loadAll(TSIcon.class);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("territory", territory);
		m.put("iconList", iconList);
		m.put("selectId", selectId);
		return new ModelAndView("lmPage/test/editModel", m);
	}
	
	@RequestMapping(params = "update")
	@ResponseBody
	public String update(TSTerritory territory, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if(territory.getTSTerritory() != null && territory.getTSTerritory().getId().equals("")){
			territory.setTSTerritory(null);
		}
		if (StringUtil.isNotEmpty(territory.getId())) {
			message = "地域信息["+territory.getTerritoryName()+"]更新成功";
			TSTerritory t = territoryService.get(TSTerritory.class, territory.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(territory, t);
				territoryService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "地域信息["+territory.getTerritoryName()+"]更新失败";
			}
		} else {
			message = "地域信息["+territory.getTerritoryName()+"]添加成功";
			if(territory.getTSTerritory() == null){
				TSTerritory p = new TSTerritory();
				p.setId("0");
				territory.setTSTerritory(p);
				territory.setTerritoryLevel(Short.valueOf("0"));
			}else{
				territory.setTerritoryLevel(Short.valueOf("1"));
			}
			territory.setTerritorySort("1");
			territory.setCreatedtime(new Date());//创建时间
			territoryService.save(territory);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "territoryController.do?territory");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 加载下级
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "territoryTable")
	public ModelAndView territoryTable(HttpServletRequest request) {
		String id = request.getParameter("id");
		TSTerritory parentFunction = null;
		if(id != null && "".equals(id)){
			//点击顶级菜单
			parentFunction = new TSTerritory();
			parentFunction.setId("");
			parentFunction.setTerritoryName("顶级菜单");
			parentFunction.setTerritorySort("0");
		}else{
			parentFunction = territoryService.getEntity(TSTerritory.class, id);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("parentFunction", parentFunction);
		m.put("list", parentFunction.getTSTerritorys());
		m.put("selectId", id);
		//三级级联菜单第一级
		CriteriaQuery cq = new CriteriaQuery(TSTerritory.class);
		cq.eq("territoryLevel", Short.valueOf("2"));
		cq.add();
		List<TSFunction> list = territoryService.getListByCriteriaQuery(cq, false);
		m.put("list_test", list);
		return new ModelAndView("lmPage/test/territoryTable", m);
	}
	

	/**
	 * 地域删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		TSTerritory territory = territoryService.getEntity(TSTerritory.class, id);
		message = "地域信息["+territory.getTerritoryName()+"]删除成功";
		territoryService.delete(territory);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "territoryController.do?territory");
		String str = j.toString();
		return str;
	}

}
