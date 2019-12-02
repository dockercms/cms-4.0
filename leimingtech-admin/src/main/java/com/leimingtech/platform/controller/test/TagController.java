package com.leimingtech.platform.controller.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.service.SystemService;


/**
 * @ClassName: demoController
 * @Description: 标签示例
 * @author linjm
 */
@Controller
@RequestMapping("/tagController")
public class TagController extends BaseController {
	private static final Logger logger = Logger.getLogger(TagController.class);
	private SystemService systemService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	/**
	 *数据字典下拉
	 */
	@RequestMapping(params = "dictSelect")
	public ModelAndView dictSelect(HttpServletRequest request) {
		TSTypegroup typeSexGroup = TSTypegroup.allTypeGroups.get("sex");//sex 为字典编码
		List<TSType> typeSexlist = TSTypegroup.allTypes.get("sex");//sex 为字典编码
		
		TSTypegroup typeFieldGroup = TSTypegroup.allTypeGroups.get("fieldtype");//fieldtype 为字典编码
		List<TSType> typeFieldlist = TSTypegroup.allTypes.get("fieldtype");//fieldtype 为字典编码
		Map<String, Object> m = new HashMap<String, Object>();
		
		m.put("typeSexGroup", typeSexGroup);
		m.put("typeSexlist", typeSexlist);
		
		m.put("typeFieldGroup", typeFieldGroup);
		m.put("typeFieldlist", typeFieldlist);
		return new ModelAndView("lmPage/demo/tagDemo", m);
	}
	
}
