package com.leimingtech.cms.interceptors;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.core.common.ContentClassify;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.VoteEntity;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.VoteServiceI;
import com.leimingtech.core.util.StringUtil;

/**
 * 投票切面类
 * @author zhangxiaoqiang
 *
 */
@Aspect
@Component
public class VoteInterceptor{
	@Autowired
	private SystemService systemService;
	@Autowired
	private VoteServiceI voteService;
	
	@Pointcut("execution(public * com.leimingtech.core.service.ContentsServiceI.saveContent(..))")
	public void myMethod(){};
	/**
	 * 下面用到的是织入点语法, 看文档里面有. 就是指定在该方法前执行
	 * 记住下面这种通用的, *表示所有
	 * @param map
	 */
	@Before("myMethod()&&args(map,..)")
	public void beforeMethod(Map<String,Object> map){
		
	}
	/**
	 * 正常执行完后
	 * 保存内容之后，保存投票
	 * @param map
	 */
	@After("myMethod()&&args(map,..)")
	public void after(Map<String,Object> map){
		ContentsEntity contents = (ContentsEntity) map.get("contents");
		VoteEntity vote = (VoteEntity) map.get("vote");
		HttpServletRequest request = ContextHolderUtils.getRequest();
		//内容id
		String contentsId = request.getParameter("contentsId");
		if(StringUtil.isNotEmpty(contentsId)){
			contents= voteService.get(ContentsEntity.class, String.valueOf(contentsId));
		}
		String classify = contents.getClassify();
		if(ContentClassify.CONTENT_VOTE.equals(classify)){
			voteService.saveVote(contents, vote);
		}
	}
	/**
	 * 正常执行完后
	 */
	@AfterReturning("myMethod()")
	public void afterReturnning(){
		
	}
	/**
	 * 抛出异常时才调用
	 */
	@AfterThrowing("myMethod()")
	public void afterThrowing(){
		
	}
	
}