package com.leimingtech.platform.core.interceptors;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.util.IpUtil;
import com.leimingtech.core.util.browser.BrowserUtils;
import com.leimingtech.core.util.date.DataUtils;
import com.leimingtech.platform.core.common.model.json.LogAnnotation;


/**
 * 
 * @author  
 *
 */
@Component
@Aspect
public class LogInterceptor {
	@Before("execution(public * com.leimingtech.platform.web.system.controller..*.*(..)) or  execution(public * com.leimingtech.cm.api..*.*(..)) or  execution(public * com.leimingtech.*.controller..*.*(..)) ")
	public void beforeMethod(JoinPoint joinPoint) throws Exception {
		String temp = joinPoint.getStaticPart().toShortString();
		String longTemp = joinPoint.getStaticPart().toLongString();
		joinPoint.getStaticPart().toString();
		String classType = joinPoint.getTarget().getClass().getName();
		String methodName = temp.substring(10, temp.length() - 1);
		Class<?> className = Class.forName(classType);
		Class[] args = new Class[joinPoint.getArgs().length];
		String[] sArgs = (longTemp.substring(longTemp.lastIndexOf("(") + 1,
				longTemp.length() - 2)).split(",");
		for (int i = 0; i < args.length; i++) {
			if (sArgs[i].endsWith("String[]")) {
				args[i] = Array.newInstance(Class.forName("java.lang.String"),
						1).getClass();
			} else if (sArgs[i].endsWith("Long[]")) {
				args[i] = Array.newInstance(Class.forName("java.lang.Long"), 1)
						.getClass();
			} else if (sArgs[i].indexOf(".") == -1) {
				if (sArgs[i].equals("int")) {
					args[i] = int.class;
				} else if (sArgs[i].equals("char")) {
					args[i] = char.class;
				} else if (sArgs[i].equals("float")) {
					args[i] = float.class;
				} else if (sArgs[i].equals("long")) {
					args[i] = long.class;
				}
			} else {
				args[i] = Class.forName(sArgs[i]);
			}
		}
//		Method method = className.getMethod(
//				methodName.substring(methodName.indexOf(".") + 1,
//						methodName.indexOf("(")), args);
		
		if("BaseController.initBinder(..)".equals(methodName)){
			return;
		}
		
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String broswer = BrowserUtils.checkBrowse(request);
		
		System.out.println("ip:"+IpUtil.getIpAddr(request)+" 浏览器："+broswer+" 当前时间："+DataUtils.date2Str(DataUtils.datetimeFormat)+" className:"+className+"methodName:"+methodName);
//		if (method.isAnnotationPresent(LogAnnotation.class)) {
//			LogAnnotation logAnnotation = method
//					.getAnnotation(LogAnnotation.class);
//			String operateModelNm = logAnnotation.operateModelNm();
//			String operateDescribe = logAnnotation.operateDescribe();
//			String operateFuncNm = logAnnotation.operateFuncNm();
//
//		}

	}
}