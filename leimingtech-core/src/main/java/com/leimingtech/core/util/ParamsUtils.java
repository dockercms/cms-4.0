package com.leimingtech.core.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ParamsUtils {

	/**
	 * 获取request中所有参数
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllParams(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter
				.hasNext();) {
			String name = iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		return params;
	}

	/**
	 * Map中是数据转为URL参数
	 * 
	 * @param m
	 * @return
	 * @author liuzhen
	 * @date 2016-8-9 下午4:19:45
	 */
	public static String toUrlParam(Map<String, String> m) {
		StringBuilder sb = new StringBuilder();
		for (String key : m.keySet()) {
			sb.append(key);
			sb.append('=');
			sb.append(m.get(key));
			sb.append('&');
		}
		return sb.substring(0, sb.length() - 1);
	}

	/**
	 * 获取url参数 以&连接
	 * 
	 * @param request
	 * @return
	 */
	public static String getUrlParams(HttpServletRequest request) {
		Map<String, String> params = getAllParams(request);
		String paramStr = toUrlParam(params);
		return paramStr;
	}
}
