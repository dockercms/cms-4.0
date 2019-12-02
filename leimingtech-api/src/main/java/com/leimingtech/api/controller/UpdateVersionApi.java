package com.leimingtech.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.mobile.entity.updateversion.UpdateVersionEntity;
import com.leimingtech.mobile.service.updateversion.UpdateVersionServiceI;
/**
 * 版本更新接口
 * @author zhangyulong
 *
 */
@Controller
@RequestMapping(value = "/front/updateVersionApi")
public class UpdateVersionApi {
	
	@Autowired
	private UpdateVersionServiceI updateVersionService = null;
	/**
	 * 获取版本更新实体信息
	 * @param sysType 系统类型
	 * @return 版本更新实体
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(String sysType){
		try {
			UpdateVersionEntity entity = updateVersionService.findUniqueByProperty(UpdateVersionEntity.class, "sysType", sysType);
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			return "读取出错,请重新再试一次!";
		}
	}
}
