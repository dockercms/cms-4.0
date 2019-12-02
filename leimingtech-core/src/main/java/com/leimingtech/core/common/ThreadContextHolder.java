package com.leimingtech.core.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leimingtech.core.service.WebSessionContext;
import com.leimingtech.core.service.impl.WebSessioncontextImpl;

/**
 * 使用threadLocal来存储session
 * @author LAKNG
 * 2014-08-20 09:50
 */
public class ThreadContextHolder {
	@SuppressWarnings("rawtypes")
	private static ThreadLocal<WebSessionContext> SessionContextThreadLocalHolder = new ThreadLocal<WebSessionContext>();
	private static ThreadLocal<HttpServletRequest> HttpRequestThreadLocalHolder = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> HttpResponseThreadLocalHolder = new ThreadLocal<HttpServletResponse>();
	
	public static void setHttpRequest(HttpServletRequest request){
		HttpRequestThreadLocalHolder.set(request);
	}
	
	public static HttpServletRequest getHttpRequest(){
		return HttpRequestThreadLocalHolder.get();
	}
	
	public static void remove(){
		SessionContextThreadLocalHolder.remove();
		HttpRequestThreadLocalHolder.remove();
		HttpResponseThreadLocalHolder.remove();
	}
	
	public static void setHttpResponse(HttpServletResponse response){
		HttpResponseThreadLocalHolder.set(response);
	}
	
	public static HttpServletResponse getHttpResponse(){
		return HttpResponseThreadLocalHolder.get();
	}
	
	public static void setSessionContext(WebSessionContext context){
		SessionContextThreadLocalHolder.set(context);
	}
	
	public static WebSessionContext getSessionContext(){
		WebSessionContext context = SessionContextThreadLocalHolder.get();
		if(null == context){
			SessionContextThreadLocalHolder.set(new WebSessioncontextImpl());
		}
		return SessionContextThreadLocalHolder.get();
	}
	
	public static void destorySessionContext(){
		WebSessionContext context = SessionContextThreadLocalHolder.get();
		if(null != context){
			context.destory();
		}
	}
	
	
}
