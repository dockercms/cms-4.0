package com.leimingtech.gencode.codegenerate.pojo;

import com.leimingtech.gencode.codegenerate.util.def.ConvertDef;
import org.apache.commons.lang.StringUtils;


public class TableConvert {

	/**
	 * 返回字段是否为空属性
	 * @param nullable
	 * @return
	 */
	public static String getNullAble(String nullable){
		if("YES".equals(nullable)||"yes".equals(nullable)||"y".equals(nullable)||"Y".equals(nullable)||"f".equals(nullable)){
			return ConvertDef.FIELD_NULL_ABLE_Y;
		}
		if("NO".equals(nullable)||"N".equals(nullable)||"no".equals(nullable)||"n".equals(nullable)||"t".equals(nullable)){
			return ConvertDef.FIELD_NULL_ABLE_N;
		}
		return null;
	}
	
	/**
	 * 返回字段是否为空属性
	 * @param nullable
	 * @return
	 */
	public static String getNullString(String nullable){
		if(StringUtils.isBlank(nullable)){
			return "";
		}else{
			return nullable;
		}
	}
	
	
	
	//传入字符追加单引号
	public static String getV(String s){
		return "'"+s+"'";
	}
}
