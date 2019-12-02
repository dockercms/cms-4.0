package com.leimingtech.core.util.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {

	private ExceptionUtils(){
		//no instance
	}
	
	
	/**
	 * 如果目标为空则抛出异常
	 * @author 苍鹰
	 * 2009-11-3
	 * @param target
	 * @param errorMessage
	 */
	public static void throwIfNull(Object target,String errorMessage){
		if(target==null){
			throw new BusinessException(errorMessage);
		}
	}
	
	/**
	 * 如果目标为空则抛出异常
	 * 本方法空指针安全
	 * 2009-11-3
	 * @param target
	 * @param errorMessage
	 */
	public static void throwIfEmpty(String target,String errorMessage)
	{
		if(target==null || target.equals("")){
			throw new BusinessException(errorMessage);
		}
	}
	
	/**
	 * 返回错误信息字符串
	 * 
	 * @param ex
	 *            Exception
	 * @return 错误信息字符串
	 */
	public static String getExceptionMessage(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}
	
}
