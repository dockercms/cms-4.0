package com.leimingtech.wechat.controller.fileupload;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.date.DateUtils;

/**
 * 微信相关文件上传
 * 
 * @author 谢进伟
 * 
 * @date 2015-8-13 下午10:23:43
 */
@Controller
@RequestMapping("/wechatFileUploadController")
public class WechatFileUploadController {
	
	@RequestMapping(params = "showUploadFilePage")
	public ModelAndView showUploadFilePage(HttpServletRequest request , HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		String title = request.getParameter("title");// 上传文件资源名称
		String isSingle = request.getParameter("isSingle");// 是否只允许单文件上传
		String isPureUploadPicture = request.getParameter("isPureUploadPicture");// 是否只允许上传纯图片
		String saveFolder = request.getParameter("saveFolder");// 上传文件所在服务器端存储的文件夹相对路径
		String responseDomId = request.getParameter("responseDomId");// 文件上传后,接收文件上传成功后所形成的网络路径dom元素Id
		mav.addObject("title" , title);
		mav.addObject("isSingle" , isSingle);
		mav.addObject("isPureUploadPicture" , isPureUploadPicture);
		mav.addObject("saveFolder" , saveFolder);
		mav.addObject("responseDomId" , responseDomId);
		mav.setViewName("wechat/fileUpload/uploadFile");
		return mav;
	}
	
	@RequestMapping(params = "fileUpload")
	@ResponseBody
	public JSONObject fileUpload(HttpServletRequest request , HttpServletResponse response) {
		Map<String , String> jsonMap = new HashMap<String , String>();
		try {
			String saveFolder = request.getParameter("saveFolder");// 上传文件所在服务器端存储的文件夹相对路径
			if(StringUtils.isEmpty(saveFolder)) {
				saveFolder = "/upload/wechat/temp/";
			}
			if(ServletFileUpload.isMultipartContent(request)) {
				String rootPath = request.getSession().getServletContext().getRealPath("/");
				final String relativeSaveFolder = saveFolder + DateUtils.format(new Date() , "yyyyMMdd");
				File saveFileFolder = new File(rootPath + File.separator + relativeSaveFolder);
				if(!saveFileFolder.exists()) {
					saveFileFolder.mkdirs();
				}
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
				MultiValueMap<String , MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();
				List<MultipartFile> multipartFiles = multiFileMap.get("file");
				List<String> fileNetPath = new ArrayList<String>();
				String prefix = PlatFormUtil.getDomail();
				for(MultipartFile multipartFile : multipartFiles) {
					final String originalFilename = multipartFile.getOriginalFilename();
					final String suffix = StringUtils.substring(originalFilename , originalFilename.lastIndexOf("."));
					final long nanoTime = System.nanoTime();
					final String filePath = saveFileFolder.getPath() + File.separator + nanoTime + suffix;
					final String relativePath = relativeSaveFolder + "/" + nanoTime + suffix;
					File saveFile = new File(filePath);
					// 上传文件
					FileUtils.copyInputStreamToFile(multipartFile.getInputStream() , saveFile);
					fileNetPath.add(prefix + "/" + relativePath);
					break;
				}
				if(fileNetPath.size() > 0) {
					jsonMap.put("path" , StringUtils.join(fileNetPath.toArray() , ","));
				}
				jsonMap.put("resultCode" , "1");
				jsonMap.put("resultMsg" , "文件上传成功!");
			} else {
				jsonMap.put("resultCode" , "0");
				jsonMap.put("resultMsg" , "上传参数错误,无法读取上传文件!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("resultCode" , "0");
			jsonMap.put("resultMsg" , "服务器端异常,请稍后再试!");
		}
		return JSONObject.fromObject(jsonMap);
	}
}
