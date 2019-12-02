package com.leimingtech.api.controller;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.entity.BeanApi;
import com.leimingtech.mobile.entity.sysiostoken.SysIosTokenEntity;
import com.leimingtech.mobile.service.sysiostoken.SysIosTokenServiceI;

/**
 * 获取IOS设备的token
 * 
 * @author zhangyulong
 * 
 */
@Controller
@RequestMapping(value = "/front/SysToKenApi")
public class SysToKenApi {

	@Autowired
	SysIosTokenServiceI tokenService = null;

	/**
	 * 
	 * @param entity
	 *            SysIsoTokenEntity实体
	 * @return 成功 或 失败
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JSONObject Save(SysIosTokenEntity entity) {
		BeanApi beanApi = new BeanApi();
		JSONObject json = new JSONObject();
		SysIosTokenEntity tokenEntity = null;
		String token = entity.getToken();
		// 判断token长度是否合法
		if (token.length() != 64) {
			beanApi.setResultCode("0");
			beanApi.setResultMsg("错误:token长度异常");
			beanApi.setItems(null);
			json = json.fromObject(beanApi, getJsonConfig());
		}
		if (entity.getId() != null) {
			beanApi.setResultCode("0");
			beanApi.setResultMsg("错误:ID必须为空");
			beanApi.setItems(null);
			json = json.fromObject(beanApi, getJsonConfig());

		}
		try {
			// 获取当前TOKEN实体
			tokenEntity = tokenService.findUniqueByProperty(
					SysIosTokenEntity.class, "token", entity.getToken());

			// 判断token值是否存在 ,不存在保存实体
			if (tokenEntity == null) {
				tokenService.save(entity);
				beanApi.setResultCode("1");
				beanApi.setResultMsg("成功");
				beanApi.setItems(null);
				json = json.fromObject(beanApi, getJsonConfig());

			} else {
				beanApi.setResultCode("0");
				beanApi.setResultMsg("已存在");
				beanApi.setItems(null);
				json = json.fromObject(beanApi, getJsonConfig());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		beanApi.setResultCode("0");
		beanApi.setResultMsg("录入失败,请重新操作");
		beanApi.setItems(null);
		json = json.fromObject(beanApi, getJsonConfig());
		return json;

	}

	private JsonConfig getJsonConfig() {
		return com.leimingtech.core.util.json.JsonConfig.getInstance();
	}
}
