package com.leimingtech.gencode.codegenerate.util;

import org.apache.commons.lang.StringUtils;

import java.util.List;

public class CodeStringUtils {
	
	/**
	 * 封装查询条件
	 * @param val
	 * @return
	 */
	public static String getStringSplit(String[] val){
		StringBuffer sqlStr = new StringBuffer();
		for(String s:val){
			if(StringUtils.isNotBlank(s)){
				sqlStr.append(",");
				sqlStr.append("'");
				sqlStr.append(s.trim());
				sqlStr.append("'");
			}
		}
		return sqlStr.toString().substring(1);
	}
	/**
	 * 字符串首字母变小写
	 */
	public static String getInitialSmall(String str) {
		if(StringUtils.isNotBlank(str)){
			str = str.substring(0, 1).toLowerCase() + str.substring(1);
		}
		return str;
	}
	
	/**
	 * 判断如果字段为空，则返回0
	 */
	public static Integer getIntegerNotNull(Integer t){
		if(t==null){
			return 0;
		}
		return t;
	}
	
	
	/**
	 * 判断元素是否在数组内
	 * 
	 * @param substring
	 * @param source
	 * @return
	 */
	public static boolean isIn(String substring, String[] source) {
		if (source == null || source.length == 0) {
			return false;
		}
		for (int i = 0; i < source.length; i++) {
			String aSource = source[i];
			if (aSource.equals(substring)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断元素是否在数组内
	 * 
	 * @param substring
	 * @param source
	 * @return
	 */
	public static boolean isIn(String substring, List<String> ls) {
		String[] source = new String[]{};
		if(ls!=null){
			source = (String[]) ls.toArray();
		}
	
		if (source == null || source.length == 0) {
			return false;
		}
		for (int i = 0; i < source.length; i++) {
			String aSource = source[i];
			if (aSource.equals(substring)) {
				return true;
			}
		}
		return false;
	}
}
