package com.leimingtech.cms.controller.lucene;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.cms.service.LuceneServiceI;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.util.PlatFormUtil;

/**   
 * @Title: Controller
 * @Description: 教育局
 * @author linjm
 * @date 2014-04-03 18:28:48
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/luceneController")
public class LuceneController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LuceneController.class);

	@Autowired
	private LuceneServiceI luceneServiceImpl;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@RequestMapping(params = "creatIndex")
	@ResponseBody
	public String creatIndex(HttpServletRequest reqeust){
		JSONObject j = new JSONObject();
		try {
			luceneServiceImpl.creatIndex(PlatFormUtil.getSessionSite());
			message = "索引建立成功";
		} catch (Exception e) {
			message = "索引建立失败";
			e.printStackTrace();
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "luceneController.do?table");
		String str = j.toString();
		return str;
	}
}
