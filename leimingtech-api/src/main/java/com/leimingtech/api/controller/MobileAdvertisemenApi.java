package com.leimingtech.api.controller;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.mobile.entity.advertisemen.AdvertisemenStartingEntity;
import com.leimingtech.mobile.service.advertisemen.AdvertisemenStartingServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 移动平台的广告接口
 * 
 * @author zhangyulong
 * 
 */
@Controller
@RequestMapping(value = "/front/mobileAdvertisemenApi")
public class MobileAdvertisemenApi extends BaseController {
	private String error = "请求数据出错，请重试！";
	@Autowired
	private AdvertisemenStartingServiceI advertisemenStartingService = null;


	/**
	 * 获取所有的启动画面广告
	 * 
	 * @return 启动画面广告实体集合的JSON
	 */
	@RequestMapping(value = "/getListStarting")
	@ResponseBody
	public List getListAdvertisemenStarting() {
		List list = null;
		try {
			list = advertisemenStartingService.getList(AdvertisemenStartingEntity.class);
		} catch (Exception e) {// 请求出错 返回错误信息
			e.printStackTrace();
			list = new ArrayList<String>();
			list.add(error);
		}
		return list;
	}

	/**
	 * 获取分页的启动画面广告
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            页码尺寸
	 * @return 启动画面广告实体集合的JSON
	 */
	// @SuppressWarnings("null")
	// @RequestMapping(value =
	// "/getListStartingByPage/pageNo/{pageNo}/pageSize/{pageSize}")
	// public Map<String, Object>
	// getListAdvertisemenStarting(@PathVariable("pageNo") Integer pageNo,
	// @PathVariable("pageSize") Integer pageSize) {
	@RequestMapping(value = "/getListStartingByPage")
	@ResponseBody
	public Map<String, Object> getListAdvertisemenStarting(Integer pageNo, Integer pageSize) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = this.advertisemenStartingService.getListByPage(pageNo, pageSize);
		} catch (Exception e) { // 请求出错 返回错误信息
			e.printStackTrace();
			;
			map.put("error", error);
		}
		return map;
	}

	/**
	 * 获取启动画面广告根据ID
	 * 
	 * @param ID
	 *            启动画面广告实体类的ID
	 * @return 启动画面广告实体类的JSON
	 */
	// @RequestMapping(value = "/getStarting/{id}")
	// public Object getAdvertisemenStarting(@PathVariable("id") Integer id) {
	@RequestMapping(value = "/getStarting")
	@ResponseBody
	public Object getAdvertisemenStarting(String id, HttpServletRequest request) {
		AdvertisemenStartingEntity bean = null;
		try {
			bean = advertisemenStartingService.get(AdvertisemenStartingEntity.class, id);
			String imgUrl = bean.getImgUrl();
//			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+request.getContextPath() + imgUrl;
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + imgUrl;
			bean.setImgUrl(basePath);
			//System.out.println(basePath);

		} catch (Exception e) {// 请求出错 返回错误信息
			e.printStackTrace();
			return error;
		}
		return bean;
	}


}
