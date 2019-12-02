package com.leimingtech.gencode.codegenerate.generate;

import com.leimingtech.gencode.codegenerate.util.CodeResourceUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Title:CgformCodeFactory
 * @description:根据cgform配置生成代码-单表
 * @author 赵俊夫
 * @date Sep 8, 2013 8:22:56 PM
 * @version V1.0
 */
public class CgformCodeFactory extends BaseCodeFactory{
	private ICallBack callBack;
	private String projectPath;


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
	public void generateFile(String templateFileName, String type, Map data) throws TemplateException, IOException {
		try {
			String entityPackage = data.get("entityPackage").toString();
			String entityName = data.get("entityName").toString();
			String fileNamePath = getCodePath(type, entityPackage, entityName);
			String fileDir = StringUtils.substringBeforeLast(fileNamePath, "/");
			Template template = getConfiguration().getTemplate(templateFileName);
			org.apache.commons.io.FileUtils.forceMkdir(new File(fileDir + "/"));
			Writer out = new OutputStreamWriter(new FileOutputStream(
					fileNamePath), CodeResourceUtil.SYSTEM_ENCODING);
			template.process(data, out);
			out.close();
		} catch (TemplateException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * 得到项目代码的绝对路径，到src
	 */
	public  String getProjectPath() {
		// String path = getClass().getProtectionDomain().getCodeSource()
		// .getLocation().getPath();
		// path = StringUtils.substringBefore(path, "target");
		// path = path.substring(1);
//		String path = getClassPath().replace("/classes", "").replace("/WEB-INF", "").replace("/WebRoot", "");
//		if(this.getProjectPath()!=null && !"".equals(this.getProjectPath())){
//			return this.getProjectPath();
//		}
//		String path = CodeResourceUtil.getProject_path()+"/";;
		
		return this.projectPath;
	}

	
	/*
	 * 得到Class目录
	 */
	public String getClassPath() {
		String path = this.getClass().getResource("/").getPath();
		return path;
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
		String codePath = "";
		if(packageStyle!=null&&CodeResourceUtil.PACKAGE_SERVICE_STYLE.equals(packageStyle)){
			codePath = getCodePathServiceStyle(path, type, entityPackage, entityName);
		}else{
			//如果是为空默认按技术架构分层
			codePath = getCodePathProjectStyle(path, type, entityPackage, entityName);
		}
		return codePath;
	}

	public void invoke(String templateFileName, String type) throws TemplateException, IOException {
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

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

}
