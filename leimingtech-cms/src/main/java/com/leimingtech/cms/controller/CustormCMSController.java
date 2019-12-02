package com.leimingtech.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.TSCustom;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.StringUtil;

@Controller
@RequestMapping("/custormCMSController")
public class CustormCMSController extends BaseController {
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private ContentsServiceI contentsService;

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
		List<TSCustom> list = contentsService.getListByCriteriaQuery(cq, false);
		if(StringUtil.isNotEmpty(contentsId)){
			contents = contentsService.get(ContentsEntity.class, String.valueOf(contentsId));
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
	
}
