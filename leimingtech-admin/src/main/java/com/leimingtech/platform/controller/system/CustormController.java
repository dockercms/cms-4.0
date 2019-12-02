package com.leimingtech.platform.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.json.AjaxJson;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ComboTree;
import com.leimingtech.core.entity.ComboTreeModel;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.TSCustom;
import com.leimingtech.core.entity.TSIcon;
import com.leimingtech.core.entity.TreeGrid;
import com.leimingtech.core.entity.TreeGridModel;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.platform.entity.TSTerritory;
import com.leimingtech.platform.service.test.CustomServiceI;


/**
 * 地域处理类
 * @author wushu
 */
@Controller
@RequestMapping("/custormController")
public class CustormController extends BaseController {
	
	private String message = null;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private CustomServiceI customService;

	/**
	 * 地域列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "custorm")
	public ModelAndView function(HttpServletRequest request) {
		CriteriaQuery cq = new CriteriaQuery(TSCustom.class);
		cq.eq("sustomLevel", Short.valueOf("0"));
		cq.add();
		List<TSCustom> list = customService.getListByCriteriaQuery(cq, false);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list", list);
		return new ModelAndView("lmPage/test/custormList",m);
	}
	
	/**
	 * 加载下级地区
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loadCustorm")
	@ResponseBody
	public JSONArray loadMenu(HttpServletRequest request) {
		String id = request.getParameter("id");
		CriteriaQuery cq = new CriteriaQuery(TSCustom.class);
		cq.eq("TSCustom.id", id);
		cq.add();
		List<TSCustom> list = customService.getListByCriteriaQuery(cq, false);
		JSONArray jsonArray = new JSONArray();
		for(TSCustom custom : list){
			JSONObject json = new JSONObject();
			if(custom.getTSCustoms() != null && custom.getTSCustoms().size() > 0){
				String s="<leimingtech class=\"leimingtech_limengbo_float_right\"  onclick=\"del('custormController.do?del', '"+custom.getId()+"')\">删除</leimingtech>";
                  s+="<leimingtech class=\"leimingtech_limengbo_float_right\"  onclick=\"toEditModel('custormController.do?editModelPage&selectId=&id="+custom.getId()+"')\">修改</leimingtech>";
      				s+="<leimingtech class=\"leimingtech_limengbo_float_right\" onclick=\"toEditModel('custormController.do?editModelPage&selectId=&parentId="+custom.getId()+"')\">添加</leimingtech>";
				json.put("text", custom.getCustomName()+s);
				json.put("value", custom.getId());
				json.put("leaf", false);
				json.put("expanded", false);
				json.put("cls", "folder");
				json.put("id", custom.getId());
				json.put("href", "custormController.do?loadCustorm&id=" + custom.getId());
				json.put("data-role", "branch");
				json.put("children", new JSONArray());
			}else{
				String s="<leimingtech class=\"leimingtech_limengbo_float_right\" onclick=\"del('custormController.do?del', '"+custom.getId()+"')\">删除</leimingtech>";
                s+="<leimingtech class=\"leimingtech_limengbo_float_right\"  onclick=\"toEditModel('custormController.do?editModelPage&selectId=&id="+custom.getId()+"')\">修改</leimingtech>";
    				s+="<leimingtech class=\"leimingtech_limengbo_float_right\" onclick=\"toEditModel('custormController.do?editModelPage&selectId=&parentId="+custom.getId()+"')\">添加</leimingtech>";
				json.put("text", "<i class=\"icon-file-text\" style=\"width: 1em;margin-right: 4px;\"></i>"+custom.getCustomName()+s);
				json.put("value", custom.getId());
				json.put("leaf", true);
				json.put("href", "javascript:void(0);");
				json.put("data-role", "leaf");
				json.put("id", custom.getId());
				json.put("checked", false);
			}
			jsonArray.add(json);
		}
		return jsonArray;
	}
	
	/**
	 * 加载下级地区
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loadCustoms")
	@ResponseBody
	public String loadMenus(HttpServletRequest request) {
		String id = request.getParameter("id");
		CriteriaQuery cq = new CriteriaQuery(TSCustom.class);
		cq.eq("TSTerritory.id", id);
		cq.add();
		List<TSCustom> list = customService.getListByCriteriaQuery(cq, false);
		JSONArray jsonArray = new JSONArray();
		for(TSCustom custom : list){
			JSONObject json = new JSONObject();
			if(custom.getTSCustoms() != null && custom.getTSCustoms().size() > 0){
				json.put("text", custom.getCustomName());
				json.put("value", custom.getId());
				json.put("leaf", false);
				json.put("expanded", false);
				json.put("cls", "folder");
				json.put("id", custom.getId());
				json.put("href", "territoryController.do?loadTerritory&id=" + custom.getId());
				json.put("data-role", "branch");
				json.put("children", new JSONArray());
			}else{
				json.put("text", custom.getCustomName());
				json.put("value", custom.getId());
				json.put("leaf", true);
				json.put("href", "javascript:void(0);");
				json.put("data-role", "leaf");
				json.put("id", custom.getId());
				json.put("checked", false);
			}
			jsonArray.add(json);
		}
		return jsonArray.toString();
	}
	
	/**
	 * 加载内容属性
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loadAttr")
	@ResponseBody
	public JSONArray loadAttr(ContentsEntity contents,HttpServletRequest request) {
		String contentsId = request.getParameter("contentsId");
		String id = request.getParameter("id");
		CriteriaQuery cq = new CriteriaQuery(TSCustom.class);
		cq.eq("TSCustom.id", id);
		cq.add();
		List<TSCustom> list = customService.getListByCriteriaQuery(cq, false);
		if(StringUtil.isNotEmpty(contentsId)){
			contents = customService.get(ContentsEntity.class, String.valueOf(contentsId));
		}
		JSONArray jsonArray = new JSONArray();
		for(TSCustom custom : list){
			JSONObject json = new JSONObject();
			if(custom.getSustomLevel()==0){
				json.put("id", custom.getId());
				json.put("name", custom.getCustomName());
				json.put("children", getChildren(custom,contents));
				if(StringUtil.isNotEmpty(contents.getAttribute())){
					String[] strs = contents.getAttribute().split(",");
					for(int a=0;a<strs.length;a++){
						if(strs[a].equals(custom.getCustomName())){
							json.put("checked", true);
						}
					}
				}else{
					json.put("checked", false);
				}
				jsonArray.add(json);
			}
		}
		return jsonArray;
	}
	/**
	 * 自定义属性子节点
	 * @param custom
	 * @param contents
	 * @return
	 */
	public JSONArray getChildren(TSCustom custom,ContentsEntity contents){
		JSONArray jsonArray = new JSONArray();
		JSONObject json = new JSONObject();
		if(custom.getTSCustoms()==null||custom.getTSCustoms().size()==0){
			return jsonArray;
		}
		for(TSCustom custom1 : custom.getTSCustoms()){
			json.put("id", custom1.getId());
			json.put("name", custom1.getCustomName());
			json.put("children", getChildren(custom1,contents));
			json.put("checked", false);
			if(StringUtil.isNotEmpty(contents.getAttribute())){
				String[] strs = contents.getAttribute().split(",");
				for(int a=0;a<strs.length;a++){
					if(strs[a].equals(custom1.getCustomName())){
						json.put("checked", true);
					}
				}
			}else{
				json.put("checked", false);
			}
			jsonArray.add(json);
		}
		return jsonArray;
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
		TSCustom custom = null;
		if(id != null && !"".equals(id)){
			custom = customService.getEntity(TSCustom.class, id);
		}else{
			String parentId = request.getParameter("parentId");
			custom = new TSCustom();
			TSCustom parentCustom = new TSCustom();
			parentCustom.setId(parentId);
			custom.setTSCustom(parentCustom);
		}
		List<TSIcon> iconList = customService.loadAll(TSIcon.class);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("territory", custom);
		m.put("iconList", iconList);
		m.put("selectId", selectId);
		return new ModelAndView("lmPage/test/custormEdit", m);
	}
	
	@RequestMapping(params = "update")
	@ResponseBody
	public String update(TSCustom custom, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if(custom.getTSCustom() != null && custom.getTSCustom().getId().equals("")){
			custom.setTSCustom(null);
		}
		if (StringUtil.isNotEmpty(custom.getId())) {
			message = "自定义属性["+custom.getCustomName()+"]修改成功";
			TSCustom t = customService.get(TSCustom.class, custom.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(custom, t);
				customService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "自定义属性["+custom.getCustomName()+"]修改失败";
			}
		} else {
			message = "自定义属性["+custom.getCustomName()+"]添加成功";
			if(custom.getTSCustom() == null){
				
				custom.setSustomLevel(Short.valueOf("0"));
			}else{
//				Short level =(short)(territory.getTSTerritory().getTerritoryLevel()+1);
//				territory.setTerritoryLevel(level);
				custom.setSustomLevel(Short.valueOf("1"));
			}
		    custom.setCreatedtime(new Date());//创建时间
			customService.save(custom);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "custormController.do?custorm");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 加载下级
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "custormtable")
	public ModelAndView menuTable(HttpServletRequest request) {
		String id = request.getParameter("id");
		TSCustom parentCustom = null;
		if(id != null && "".equals(id)){
			//点击顶级菜单
			parentCustom = new TSCustom();
			parentCustom.setId("");
			parentCustom.setCustomName("顶级菜单");
			parentCustom.setSustomSort("0");
		}else{
			parentCustom = customService.getEntity(TSCustom.class, id);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("parentFunction", parentCustom);
		m.put("list", parentCustom.getTSCustom());
		m.put("selectId", id);
		//三级级联菜单第一级
		CriteriaQuery cq = new CriteriaQuery(TSCustom.class);
		cq.eq("territoryLevel", Short.valueOf("1"));
		cq.add();
		List<TSCustom> list = customService.getListByCriteriaQuery(cq, false);
		m.put("list_test", list);
		return new ModelAndView("lmPage/test/customTable", m);
	}
	
	/**
	 * 地域列表
	 */
	@RequestMapping(params = "territoryGrid")
	@ResponseBody
	public List<TreeGrid> territoryGrid(HttpServletRequest request, TreeGrid treegrid) {

		CriteriaQuery cq = new CriteriaQuery(TSTerritory.class);
		if (treegrid.getId() != null) {
			cq.eq("TSTerritory.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.eq("TSTerritory.id","1");//这个是全国最高级
		}
	
	cq.addOrder("territorySort", SortDirection.asc);
	cq.add();
	List<TSTerritory> territoryList = customService.getListByCriteriaQuery(cq, false);
	List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
	TreeGridModel treeGridModel = new TreeGridModel();
	treeGridModel.setIcon("");
	treeGridModel.setTextField("territoryName");
	treeGridModel.setParentText("TSTerritory_territoryName");
	treeGridModel.setParentId("TSTerritory_id");
	treeGridModel.setSrc("territoryCode");
	treeGridModel.setIdField("id");
	treeGridModel.setChildList("TSTerritorys");
	treeGridModel.setOrder("territorySort");
	treeGrids = customService.treegrid(territoryList, treeGridModel);
	return treeGrids;
	}
	/**
	 *地域列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TSTerritory territory, HttpServletRequest req) {
		String functionid = req.getParameter("id");
		if (functionid != null) {
			territory = customService.getEntity(TSTerritory.class, functionid);
			req.setAttribute("territory", territory);
		}
		if(territory.getTSTerritory()!=null && territory.getTSTerritory().getId()!=null){
			territory.setTSTerritory((TSTerritory)customService.getEntity(TSTerritory.class, territory.getTSTerritory().getId()));
			req.setAttribute("territory", territory);
		}
		return new ModelAndView("system/territory/territory");
	}
	/**
	 * 地域父级下拉菜单
	 */
	@RequestMapping(params = "setPTerritory")
	@ResponseBody
	public List<ComboTree> setPTerritory(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TSTerritory.class);
		if (comboTree.getId() != null) {
			cq.eq("TSTerritory.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("TSTerritory");
		}
		cq.add();
		List<TSTerritory> territoryList = customService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "territoryName", "TSTerritorys");
		comboTrees = customService.ComboTree(territoryList, comboTreeModel, null);
		return comboTrees;
	}
	/**
	 * 地域保存
	 */
	@RequestMapping(params = "saveTerritory")
	@ResponseBody
	public AjaxJson saveTerritory(TSTerritory territory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String functionOrder = territory.getTerritorySort();
		if(StringUtils.isEmpty(functionOrder)){
			territory.setTerritorySort("0");
		}
		if (territory.getTSTerritory().getId().equals("")) {
			territory.setTSTerritory(null);
		}else{
			TSTerritory parent = customService.getEntity(TSTerritory.class, territory.getTSTerritory().getId());
			territory.setTerritoryLevel(Short.valueOf(parent.getTerritoryLevel()+1+""));
		}
		if (StringUtil.isNotEmpty(territory.getId())) {
			message = "地域: " + territory.getTerritoryName() + "被更新成功";
			customService.saveOrUpdate(territory);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} else {
			territory.setTerritorySort(territory.getTerritorySort());
			message = "地域: " + territory.getTerritoryName() + "被添加成功";
			territory.setCreatedtime(new Date());//创建时间
			customService.save(territory);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);

		}
		
		return j;
	}

	/**
	 * 地域删除
	 * 
	 * @param ids
	 * @return
	 *//*
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TSTerritory territory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		territory = systemService.getEntity(TSTerritory.class, territory.getId());
		message = "地域: " + territory.getTerritoryName() + "被删除成功";
		systemService.delete(territory);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		return j;
	}
	*/
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		TSCustom custom = customService.getEntity(TSCustom.class, id);
		message = "自定义属性["+custom.getCustomName()+"]删除成功";
		customService.delete(custom);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "custormController.do?custorm");
		String str = j.toString();
		return str;
	}

}
