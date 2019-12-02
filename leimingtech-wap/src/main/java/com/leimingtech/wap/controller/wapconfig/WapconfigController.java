package com.leimingtech.wap.controller.wapconfig;

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.WapconfigEntity;
import com.leimingtech.core.entity.WapexpandEntity;
import com.leimingtech.core.entity.model.ModelsEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.ModelManageServiceI;
import com.leimingtech.core.service.ModelsServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.WapconfigServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**   
 * @Title: Controller
 * @Description: WAP配置
 * @author 
 * @date 2014-06-25 16:03:03
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wapconfigController")
public class WapconfigController extends BaseController {

	private String message;
	@Autowired
	private WapconfigServiceI wapconfigService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ModelManageServiceI modelManageService;
	
	@Autowired
	private ModelsServiceI modelsService;
	
	
	/**
	 * WAP配置更新
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		HttpSession session = ContextHolderUtils.getSession();
		SiteEntity site = ClientManager.getInstance().getClient(session.getId()).getSite();
		// 获取WAP配置
		List<WapconfigEntity> list = wapconfigService.findByProperty(WapconfigEntity.class, "siteid", site.getId());
		WapconfigEntity wapconfig = null;
		if(list != null && list.size()>0){
			wapconfig = list.get(0);
		}else{
			wapconfig = new WapconfigEntity();
		}
		// 模型列表
		List<ModelsEntity> modelList = this.modelsService.getAllOpenModels();
		
		// 选中的栏目
		List<WapexpandEntity> wapexpandlist = wapconfigService.findByProperty(WapexpandEntity.class, "configid", wapconfig.getId() + "");
		WapexpandEntity wapexpand = null;
		if(wapexpandlist != null && wapexpandlist.size()>0){
			wapexpand = wapexpandlist.get(0);
		}else{
			wapexpand = new WapexpandEntity();
		}
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("wapconfig", wapconfig);
		m.put("modelList", modelList);
		m.put("wapexpand", wapexpand);
		return new ModelAndView("wap/wapconfig/wapconfig", m);
	}

	/**
	 * WAP配置保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(WapconfigEntity wapconfig, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtils.isNotEmpty(wapconfig.getId())) {
			message = "WAP配置["+wapconfig.getWapname()+"]更新成功";
			WapconfigEntity t = wapconfigService.get(WapconfigEntity.class, wapconfig.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(wapconfig, t);
				wapconfigService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "WAP配置["+wapconfig.getWapname()+"]更新失败";
			}
		} else {
			message = "WAP配置["+wapconfig.getWapname()+"]添加成功";
			wapconfig.setCreatedtime(new Date());//创建时间
            wapconfig.setSiteid(PlatFormUtil.getSessionSiteId());
			wapconfigService.save(wapconfig);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "wapconfigController.do?update");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 加载加载栏目树JSON结构
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loadCatalogTree")
	@ResponseBody
	public JSONArray loadCatalogTree(HttpServletRequest request) {
		String wapconfigid = request.getParameter("wapid");

		String siteid = PlatFormUtil.getSessionSiteId();
		CriteriaQuery cq = new CriteriaQuery(ContentCatEntity.class);
		cq.eq("siteid", siteid);
		
		cq.addOrder("created", SortDirection.desc);
		cq.addOrder("sort", SortDirection.desc);
		cq.add();
		// 栏目集合
		List<ContentCatEntity> list = wapconfigService.getListByCriteriaQuery(cq, false);
		
		// 选中的栏目
		WapexpandEntity wapexpand  = wapconfigService.findUniqueByProperty(WapexpandEntity.class, "configid", wapconfigid);
		
		JSONArray jsonArray = new JSONArray();
		for(ContentCatEntity contentcat : list){
			JSONObject json = new JSONObject();
			//父节点
			if(contentcat.getLevels()==0){
				json.put("id", contentcat.getId());
				json.put("name", contentcat.getName());
				json.put("open", false);
				json.put("children", getChildren(contentcat, wapexpand)); // 子节点
                String catalogids =null;
				if(wapexpand!=null){
                   catalogids = wapexpand.getCatalogids();
                }
				if(!StringUtils.isEmpty(catalogids)){
					String[] ids = catalogids.split(",");
					for (int i = 0; i < ids.length; i++) {
						if(ids[i].equals(contentcat.getId().toString())){
							json.put("checked", true);
							break;
						}else{
							json.put("checked", false);
						}
					}
				}
				jsonArray.add(json);
			}
		}
		return jsonArray;
	}
	
	/**
	 * 子节点下含有节点
	 * @param set
	 * @param tsfunction
	 * @param roleId
	 * @return
	 */
	public JSONArray getChildren(ContentCatEntity contentcat, WapexpandEntity wapexpand){
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		if(contentcat.getContentCats()==null||contentcat.getContentCats().size()==0){
			return jsonArray;
		}
		
		for(ContentCatEntity contentcat1 : contentcat.getContentCats()){
			json.put("id", contentcat1.getId());
			json.put("name", contentcat1.getName());
			json.put("children", this.getChildren(contentcat1, wapexpand));
			
			if(null != wapexpand){
				String catalogids = wapexpand.getCatalogids();
				if(!StringUtils.isEmpty(catalogids)){
					String[] ids = catalogids.split(",");
					for (int i = 0; i < ids.length; i++) {
						if(ids[i].equals(contentcat1.getId().toString())){
							json.put("checked", true);
							break;
						}else{
							json.put("checked", false);
						}
					}
				}
			}
			jsonArray.add(json);
		}
		return jsonArray;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	
	}
	
}
