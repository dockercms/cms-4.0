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
import com.leimingtech.core.entity.ArticleEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.service.ArticleServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.StringUtil;

/**
 * 文章切面类
 * @author zhangxiaoqiang
 *
 */
@Aspect
@Component
public class ArticleInterceptor{
	@Autowired
	private SystemService systemService;
	@Autowired
	private ArticleServiceI articleService;
	
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
	 * 保存内容之后，保存文章
	 * @param map
	 */
	@After("myMethod()&&args(map,..)")
	public void after(Map<String,Object> map){
		ContentsEntity contents = (ContentsEntity) map.get("contents");
		ArticleEntity article = (ArticleEntity) map.get("article");
		HttpServletRequest request = ContextHolderUtils.getRequest();
		//内容id
		String contentsId = request.getParameter("contentsId");
		if(StringUtil.isNotEmpty(contentsId)){
			contents= articleService.get(ContentsEntity.class, String.valueOf(contentsId));
		}
		String classify = contents.getClassify();
		if(ContentClassify.CONTENT_ARTICLE.equals(classify)){
			articleService.saveArticle(contents, article);
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