package com.leimingtech.platform.core.interceptors;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.core.entity.log.SmsLogEntity;
import com.leimingtech.core.service.SystemService;

/*
 *面向切面编程, 动态代理. Aspect声明切面, Component初始化.
 */
@Aspect
@Component
public class LogsInterceptor
{
	@Autowired
	private SystemService systemService;
	
	//这个可用来替代以后重复出现的. 直接在后面的Before("myMethod()")就行了.
	@Pointcut("execution(public * com.leimingtech.platform.controller..*.*(..))")
	public void myMethod(){
//		System.out.println("myMethod");
		
	};
	
	//下面用到的是织入点语法, 看文档里面有. 就是指定在该方法前执行
	//@Before("execution(public void com.dao.impl.UserDAOImp.save(com.model.User))")
	//记住下面这种通用的, *表示所有
	@Before("myMethod()&&args(smslog,request,..)")
	public void beforeMethod(SmsLogEntity smslog,HttpServletRequest request)
	{
//		systemService.addLog("切面编程，执行之前插入日志"+System.currentTimeMillis(), Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
//		System.out.println("save start......");
//		System.out.println(smslog);
	}
	//正常执行完后
	@After("myMethod()")
	public void after(JoinPoint joinPoint)
	{
//		systemService.addLog("切面编程，执行之后插入日志"+System.currentTimeMillis(), Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
//		System.out.println(joinPoint.getArgs().length);
//		System.out.println(joinPoint.getArgs());
//		System.out.println("after ......");
	}
	//正常执行完后
	@AfterReturning("myMethod()")
	public void afterReturnning()
	{
//		systemService.addLog("切面编程，执行之后插入日志"+System.currentTimeMillis(), Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
//		System.out.println("after return ......");
	}
	
	//抛出异常时才调用
	@AfterThrowing("myMethod()")
	public void afterThrowing()
	{
//		systemService.addLog("切面编程，截获异常后插入日志"+System.currentTimeMillis(), Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
//		System.out.println("after throwing......");
	}
	
	//环绕, 这个特殊点.
//	@Around("myMethod()")
//	public void aroundMethod(ProceedingJoinPoint pjp) throws Throwable
//	{
//		//加逻辑的时候, 不要依赖执行的的先后顺序
//		System.out.println("method around start!");
//		pjp.proceed();
//		System.out.println("method around end!");
//	}
}