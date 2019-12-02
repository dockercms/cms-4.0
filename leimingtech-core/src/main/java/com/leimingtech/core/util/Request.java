package com.leimingtech.core.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lkang
 * @date 2014-06-24 上午11:30
 * @version V1.0
 */
public interface Request {

	/**
	 * 
	 * @param uri
	 * @param httpResponse
	 * @param httpRequest
	 */
	public Response execute(String uri, HttpServletResponse httpResponse, HttpServletRequest httpRequest);

	/**
	 * 
	 * @param uri
	 */
	public Response execute(String uri);

	/**
	 * 设置请求时的参数
	 * 
	 * @param params
	 */
	public void setExecuteParams(Map<String, String> params);

}