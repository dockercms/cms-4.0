package com.leimingtech.wap.controller.wapexpand;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.WapexpandEntity;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.wap.service.wapexpand.WapexpandServiceI;

/**   
 * @Title: Controller
 * @Description: WAP配置项扩展表
 * @author 
 * @date 2014-06-25 18:14:41
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wapexpandController")
public class WapexpandController extends BaseController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(WapexpandController.class);

	@Autowired
	private WapexpandServiceI wapexpandService;
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
	 * WAP配置项扩展表保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(WapexpandEntity wapexpand, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String configid = request.getParameter("configid");
		String[] modelid = request.getParameterValues("modelid");
		String catalogids = request.getParameter("catalogids");
		
		try {
			List<WapexpandEntity> list = wapexpandService.findByProperty(WapexpandEntity.class, "configid", configid);
			if(list.size()>0){
			wapexpand = list.get(0);
			wapexpand.setCatalogids(catalogids);
			wapexpand.setModelids(StringUtils.join(modelid, ","));
			wapexpandService.saveOrUpdate(wapexpand);
			message = "WAP配置项扩展表["+wapexpand.getId()+"]更新成功";
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}else{
				wapexpand.setCatalogids(catalogids);
				wapexpand.setModelids(StringUtils.join(modelid, ","));
				wapexpandService.save(wapexpand);
				message = "WAP配置项扩展表["+wapexpand.getId()+"]更新成功";
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			message = "WAP配置项扩展表["+wapexpand.getId()+"]更新失败";
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "wapconfigController.do?update");
		String str = j.toString();
		return str;
	}
	
}
