package com.leimingtech.api.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.util.StringUtil;

@Controller
@RequestMapping("/front/editMobileApi")
public class editMobileApi extends BaseController{

	@Autowired
	private MemberServiceI memberService;
	
	/**
	 * 编辑
	 * @param request
	 * @return json字符串
	 */
	@RequestMapping(value = "/updateEdit")
	@ResponseBody
	public JSONObject updateEdit(HttpServletRequest request) {
		
		//用户id
		String userId = request.getParameter("userId");
		//用户密码
		String password = request.getParameter("password");
		//用户名
		String userName = request.getParameter("userName");
		String all = request.getParameter("all");
		
		JSONObject json = new JSONObject();
		//判断是否为含有文件的表单提交，为true表示已经配置了multipart/form-data
		if (ServletFileUpload.isMultipartContent(request)) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 得到上传的图片数据
			MultipartFile portrait = multipartRequest.getFile("portrait");
			
			if(StringUtil.isNotEmpty(portrait)){
				json = memberService.editData(all, portrait,userId);
			}
		}
		if(StringUtil.isNotEmpty(userName)){
			json = memberService.editUserName(all, userId, userName);
		}
		if(StringUtil.isNotEmpty(password)){
			json = memberService.editPwd(all, userId, password);
		}
		return json;
	}
}
