package com.leimingtech.cms.controller.contents;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.service.ConstantsServiceI;

/**   
 * @Title: Controller
 * @Description: 内容基类
 * @author zhangdaihao modify by wanghao 20140429
 * @date 2014-04-21 15:05:15
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/contentsbaseController")
public class ContentsbaseController extends BaseController {

	@Autowired
	private ConstantsServiceI constantsService;
	
	protected void enterworkflow(ContentsEntity contents,String contentCatId){
		ContentCatEntity concat = constantsService.get(ContentCatEntity.class, contentCatId);
		constantsService.enterworkflow(contents, concat);
	}
	
	/**
	 * 内容送审
	 * 
	 * @return
	 */
	@RequestMapping(params = "songshen")
	@ResponseBody
	public String songshen(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		String token = request.getParameter("token");
		String contentCatId = request.getParameter("contentCatId");
		JSONObject j = new JSONObject();
		String str = constantsService.songshen(ids,token);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", str);
		j.accumulate("toUrl", "contentsController.do?table&tab=all&contentCatId="+contentCatId);
		String jsonStr = j.toString();
		return jsonStr;
	}
	
	/**
	 * 内容审核
	 * 
	 * @return
	 */
	@RequestMapping(params = "contentsAudit")
	@ResponseBody
	public String contentsAudit(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		String result = request.getParameter("result");
		String contentCatId = request.getParameter("contentCatId");
		JSONObject j = new JSONObject();
		String str = constantsService.contentsAudit(ids, result);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", str);
		j.accumulate("toUrl", "contentsController.do?table&tab=all&contentCatId="+contentCatId);
		String jsonStr = j.toString();
		return jsonStr;
	}
	/**
	 * 下线、回收站
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "downContent")
	@ResponseBody
	public String downContent(HttpServletRequest request) {
		String result = request.getParameter("result");
		String contentsId = request.getParameter("id");
		String contentCatId = request.getParameter("contentCatId");
		JSONObject j = new JSONObject();
		String str = constantsService.downContent(contentsId,result);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", str);
		j.accumulate("toUrl", "contentsController.do?table&tab=all&contentCatId="+contentCatId);
		String jsonStr = j.toString();
		return jsonStr;
	}
	/**
	 * 生成内容二维码
	 * @param request
	 */
	@RequestMapping(params = "enCode")
	@ResponseBody
	public String enCode(HttpServletRequest request){
		String contentId = request.getParameter("contentId");
		String msg = "";
		JSONObject j = new JSONObject();
		boolean successFlag=false;
		try {
			constantsService.encode(contentId);
			msg = "已生成二维码";
			successFlag=true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "生成失败！";
		}
		j.accumulate("isSuccess", successFlag);
		j.accumulate("msg", msg);
		String jsonStr = j.toString();
		return jsonStr;
	}
}
