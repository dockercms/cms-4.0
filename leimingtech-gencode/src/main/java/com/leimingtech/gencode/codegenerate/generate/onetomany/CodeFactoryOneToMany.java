package com.leimingtech.gencode.codegenerate.generate.onetomany;

import com.leimingtech.gencode.codegenerate.generate.BaseCodeFactory;
import com.leimingtech.gencode.codegenerate.generate.ICallBack;
import com.leimingtech.gencode.codegenerate.util.CodeResourceUtil;
import com.leimingtech.gencode.codegenerate.util.CodeStringUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CodeFactoryOneToMany extends BaseCodeFactory{
	private ICallBack callBack;

	/**
	 * 生成Java类.
	 */
	public enum CodeType {serviceImpl("ServiceImpl"),
		dao("Dao"), service("ServiceI"), controller("Controller"), page("Page"), entity("Entity"),jsp(""),jspList("List"),jspSubList("SubList");
		private String type;

		CodeType(String type) {
			this.type = type;
		}

		public String getValue() {
			return type;
		}
	}


	/*
	 * 生成Java文件
	 * 
	 * @Params templateFileName是模板文件名
	 * 
	 * @Params type是生成类的类型，只能是现有枚举类中类型
	 * 
	 * @Params data是数据
	 */
	@SuppressWarnings("unchecked")
	public void generateFile(String templateFileName, String type, Map data) {
		try {
			String entityPackage = data.get("entityPackage").toString();
			String entityName = data.get("entityName").toString();
			String fileNamePath = getCodePath(type, entityPackage, entityName);
			String fileDir = StringUtils.substringBeforeLast(fileNamePath, "/");
			Template template = getConfiguration()
					.getTemplate(templateFileName);
			org.apache.commons.io.FileUtils.forceMkdir(new File(fileDir + "/"));
			Writer out = new OutputStreamWriter(new FileOutputStream(
					fileNamePath), CodeResourceUtil.SYSTEM_ENCODING);
			template.process(data, out);
			out.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 得到项目代码的绝对路径，到src
	 */
	public String getProjectPath() {
		// String path = getClass().getProtectionDomain().getCodeSource()
		// .getLocation().getPath();
		// path = StringUtils.substringBefore(path, "target");
		// path = path.substring(1);
		String path = System.getProperty("user.dir").replace("\\", "/") + "/";
		return path;
	}

	
	/*
	 * 得到Class目录
	 */
	public String getClassPath() {
		String path = Thread.currentThread().getContextClassLoader().getResource("./").getPath();
		return path;
	}
	
	
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getContextClassLoader().getResource("./").getPath());
	}
	/*
	 * 得到模板文件的绝对根目录
	 */
	public String getTemplatePath() {
		String path = getClassPath() + CodeResourceUtil.TEMPLATEPATH;
		return path;
	}

	/*
	 * 得到生成代码文件的绝对路径
	 */
	public String getCodePath(String type, String entityPackage,
			String entityName) {
		String path = getProjectPath();
		StringBuilder str = new StringBuilder();
		if (StringUtils.isNotBlank(type)) {
			String codeType = Enum.valueOf(CodeType.class, type).getValue();
			str.append(path);
				if("jsp".equals(type)||"jspList".equals(type)){
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

	public void invoke(String templateFileName, String type) {
		Map<String, Object> data = new HashMap<String, Object>();
		data = callBack.execute();
		generateFile(templateFileName, type, data);
	}

	public ICallBack getCallBack() {
		return callBack;
	}

	public void setCallBack(ICallBack callBack) {
		this.callBack = callBack;
	}

}
