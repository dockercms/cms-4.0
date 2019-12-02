package com.leimingtech.core.util;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HTTP相关的操作
 * @author LKANG
 * @time 2014-07-08 16:18
 */

public class HttpUtil {

	/**
	 * 设置cookie
	 * @param response 应答
	 * @param cookieName cookie名
	 * @param cookieValue cookie值
	 * @param time cookie生存时间
	 */
	public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, int time) {
		try {
			if (cookieValue != null)
				cookieValue = URLEncoder.encode(cookieValue, "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(time);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * 根据cookie名称取得cookie的值
	 * @param HttpServletRequest request request对象
	 * @param name cookie名称
	 * @return string cookie的值 当取不到cookie的值时返回null
	 */
	public static String getCookieValue(HttpServletRequest request,	String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookieName.equals(cookies[i].getName())) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}
}
