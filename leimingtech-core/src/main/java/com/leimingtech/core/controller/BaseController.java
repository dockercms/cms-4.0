package com.leimingtech.core.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.leimingtech.core.util.oConvertUtils;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.leimingtech.core.util.date.DateUtils;

/**
 * 基础控制器，其他控制器需集成此控制器获得initBinder自动转换的功能
 * 
 * @author
 * 
 */
@Controller
@RequestMapping("/baseController")
public class BaseController {

	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
//		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
//			@Override
//			public String getAsText() {
//				Object value = getValue();
//				return value != null ? DateUtils.formatDateTime((Date)value) : "";
//			}
		});
	}

	/**
	 * 获取request中所有的参数并添加到map中,用于多参数传递
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> requestMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();

		Enumeration<String> name = request.getParameterNames();
		while (name.hasMoreElements()) {
			String paramName = (String) name.nextElement();
			String paramValue = request.getParameter(paramName);
			map.put(paramName, oConvertUtils.getString(paramValue,""));

		}
		return map;
	}

	public JSONObject error(String msg) {
		return new JSONObject().accumulate("isSuccess", false).accumulate(
				"msg", msg);
	}

	public JSONObject error() {
		return error("操作失败");
	}

	public JSONObject success(String msg) {
		return new JSONObject().accumulate("isSuccess", true).accumulate("msg",
				msg);
	}

	public JSONObject success() {
		return success("操作成功");
	}
}
