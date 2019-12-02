package com.leimingtech.gencode.codegenerate.generate;

import com.leimingtech.gencode.codegenerate.util.CodeResourceUtil;
import com.leimingtech.gencode.codegenerate.util.CodeStringUtils;
import freemarker.template.Configuration;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Locale;

/**
 * 
 * @Title:BaseCodeFactory
 * @description:将代码生成工厂的公用方法抽离出来
 * @author 赵俊夫
 * @date 2014-2-17 下午9:03:28
 * @version V1.0
 */
public class BaseCodeFactory {
	//代码包结构风格：project-按技术架构分层,service-按业务模块分层
	protected String packageStyle;
	/**
	 * 生成Java类.
	 */
	public enum CodeType {
		serviceImpl("ServiceImpl"),
		dao("Dao"), 
		service("ServiceI"), 
		controller("Controller"), 
		page("Page"), 
		entity("Entity"),
		jsp(""),
		jsp_add(""),
		jsp_update(""),
		js(""),
		jsList("List"),
		jspList("List"),
		jspSubList("SubList");
		
		
		private String type;

		CodeType(String type) {
			this.type = type;
		}

		public String getValue() {
			return type;
		}
	}
	/**
	 * 获取模板引擎的配置
	 * 这里改用ClassForTemplateLoading,用于同时支持文件目录和jar包两种模板存放方式
	 * @return
	 * @throws IOException
	 */
	public Configuration getConfiguration() throws IOException {
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(this.getClass(), CodeResourceUtil.FREEMARKER_CLASSPATH);
		cfg.setLocale(Locale.CHINA);
		cfg.setDefaultEncoding("UTF-8");
		return cfg;
	}
	/*
	 * 得到生成代码文件的绝对路径
	 * 按业务模块分包风格
	 */
	public String getCodePathServiceStyle(String path,String type, String entityPackage,
			String entityName) {
		StringBuilder str = new StringBuilder();
		if (StringUtils.isNotBlank(type)) {
			String codeType = Enum.valueOf(CodeType.class, type).getValue();
			str.append(path);
				if("jsp".equals(type)||"jspList".equals(type)||"js".equals(type)||"jsList".equals(type)
						||"jsp_add".equals(type)||"jsp_update".equals(type)){
					str.append(CodeResourceUtil.JSPPATH);
				}else{
					str.append(CodeResourceUtil.CODEPATH);
				}
				str.append(StringUtils.lowerCase(entityPackage));
				str.append("/");
				if ("Action".equalsIgnoreCase(codeType)) {
					str.append(StringUtils.lowerCase("action"));
				}else if ("ServiceImpl".equalsIgnoreCase(codeType)) {
					str.append(StringUtils.lowerCase("service/impl"));
				}else if ("ServiceI".equalsIgnoreCase(codeType)) {
					str.append(StringUtils.lowerCase("service"));
				}else if ("List".equalsIgnoreCase(codeType)) {
					//设置为空
				}else {	
					str.append(StringUtils.lowerCase(codeType));
				}
				str.append("/");
				
				
				if("jsp".equals(type)||"jspList".equals(type)){
					String jspName = StringUtils.capitalize(entityName);
					//首字母小写
					str.append(CodeStringUtils.getInitialSmall(jspName));
					str.append(codeType);
					str.append(".jsp");
				}else if("jsp_add".equals(type)||"jspList_add".equals(type)){
					String jsName = StringUtils.capitalize(entityName);
					//首字母小写
					str.append(CodeStringUtils.getInitialSmall(jsName));
					str.append(codeType);
					str.append("-add.jsp");
				}else if("jsp_update".equals(type)||"jspList_update".equals(type)){
					String jsName = StringUtils.capitalize(entityName);
					//首字母小写
					str.append(CodeStringUtils.getInitialSmall(jsName));
					str.append(codeType);
					str.append("-update.jsp");
				}else if("js".equals(type)||"jsList".equals(type)){
					String jsName = StringUtils.capitalize(entityName);
					//首字母小写
					str.append(CodeStringUtils.getInitialSmall(jsName));
					str.append(codeType);
					str.append(".js");
				}else{
					str.append(StringUtils.capitalize(entityName));
					str.append(codeType);
					str.append(".java");
				}
		} else {
			throw new IllegalArgumentException("type is null");
		}
		return str.toString();
	}
	/*
	 * 得到生成代码文件的绝对路径
	 * 按技术分层模块分包风格
	 */
	public String getCodePathProjectStyle(String path,String type, String entityPackage,
			String entityName) {
		StringBuilder str = new StringBuilder();
		if (StringUtils.isNotBlank(type)) {
			String codeType = Enum.valueOf(CodeType.class, type).getValue();
			str.append(path);
				if("jsp".equals(type)||"jspList".equals(type)||"js".equals(type)||"jsList".equals(type)
						||"jsp_add".equals(type)||"jsp_update".equals(type)){
					str.append(CodeResourceUtil.JSPPATH);
				}else{
					str.append(CodeResourceUtil.CODEPATH);
				}
				if ("Action".equalsIgnoreCase(codeType)) {
					str.append(StringUtils.lowerCase("action"));
				}else if ("ServiceImpl".equalsIgnoreCase(codeType)) {
					str.append(StringUtils.lowerCase("service/impl"));
				}else if ("ServiceI".equalsIgnoreCase(codeType)) {
					str.append(StringUtils.lowerCase("service"));
				}else if ("List".equalsIgnoreCase(codeType)) {
					//设置为空
				}else {	
					str.append(StringUtils.lowerCase(codeType));
				}
				str.append("/");
				str.append(StringUtils.lowerCase(entityPackage));
				str.append("/");
				
				if("jsp".equals(type)||"jspList".equals(type)){
					String jspName = StringUtils.capitalize(entityName);
					//首字母小写
					str.append(CodeStringUtils.getInitialSmall(jspName));
					str.append(codeType);
					str.append(".jsp");
				}else if("jsp_add".equals(type)||"jspList_add".equals(type)){
					String jsName = StringUtils.capitalize(entityName);
					//首字母小写
					str.append(CodeStringUtils.getInitialSmall(jsName));
					str.append(codeType);
					str.append("-add.jsp");
				}else if("jsp_update".equals(type)||"jspList_update".equals(type)){
					String jsName = StringUtils.capitalize(entityName);
					//首字母小写
					str.append(CodeStringUtils.getInitialSmall(jsName));
					str.append(codeType);
					str.append("-update.jsp");
				}else if("js".equals(type)||"jsList".equals(type)){
					String jsName = StringUtils.capitalize(entityName);
					//首字母小写
					str.append(CodeStringUtils.getInitialSmall(jsName));
					str.append(codeType);
					str.append(".js");
				}else{
					str.append(StringUtils.capitalize(entityName));
					str.append(codeType);
					str.append(".java");
				}
		} else {
			throw new IllegalArgumentException("type is null");
		}
		return str.toString();
	}
	public String getPackageStyle() {
		return packageStyle;
	}
	public void setPackageStyle(String packageStyle) {
		this.packageStyle = packageStyle;
	}
	
}
