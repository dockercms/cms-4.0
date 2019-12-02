package com.leimingtech.gencode.codegenerate.util;

import java.io.File;

public class LmcmsCodeDeleteUtil {

	private static String buss_package = CodeResourceUtil.bussiPackage;
	private static final String src_url = "src/"+buss_package;
	private static final String web_url = "WebRoot/"+buss_package;
	
	private static final String action_inx = "action";
	private static final String entity_inx = "entity";
	private static final String page_inx = "page";
	private static final String service_inx = "service";
	private static final String impl_inx = "impl";
	
	
	private static String action_url;
	private static String entity_url;
	private static String page_url;
	private static String service_url;
	private static String service_impl_url;
	private static String jsp_url;
	private static String jsp_add_url;
	private static String jsp_edit_url;

    public static void  init(String gn_package,String name){
		action_url = src_url+"/"+action_inx+"/"+gn_package+"/"+name+"Action.java";
		entity_url = src_url+"/"+entity_inx+"/"+gn_package+"/"+name+"Entity.java";
		page_url = src_url+"/"+page_inx+"/"+gn_package+"/"+name+"Page.java";
		service_url = src_url+"/"+service_inx+"/"+gn_package+"/"+name+"ServiceI.java";
		service_impl_url = src_url+"/"+service_inx+"/"+impl_inx+"/"+gn_package+"/"+name+"ServiceImpl.java";
		jsp_url = web_url+"/"+gn_package+"/"+name+".jsp";
		jsp_add_url = web_url+"/"+gn_package+"/"+name+"-main-add.jsp";
		jsp_edit_url = web_url+"/"+gn_package+"/"+name+"-main-edit.jsp";
		
		
		//获取项目路径
		String path = getProjectPath();
		action_url = path+ "/"+action_url ;
		entity_url = path+ "/"+entity_url ;
		page_url = path+ "/"+page_url ;
		service_url = path+ "/"+service_url ;
		service_impl_url = path+ "/"+service_impl_url ;
		jsp_url  = path+ "/"+jsp_url ;
		jsp_add_url = path+ "/"+jsp_add_url ;
		jsp_edit_url = path+ "/"+jsp_edit_url ;
	}
	
	
	public static void main(String[] args) {
		
		String name = "Company";
		String subPackage = "test";
		delCode(subPackage,name);
				
	}
	
	
	public static void delCode(String subPackage,String codeName){
		//初始化Code路径
		init(subPackage,codeName);
		
		//删除CODE文件
		delete(action_url);
		delete(entity_url);
		delete(page_url);
		delete(service_url);
		delete(service_impl_url);
		delete(jsp_edit_url);
		delete(jsp_url);
		delete(jsp_add_url);
		
		System.out.println("--------------------删除动作结束-----------------------");
	}
	
	/*
	 * 得到项目代码的绝对路径，到src
	 */
	public static String getProjectPath() {
		String path = System.getProperty("user.dir").replace("\\", "/") + "/";
		return path;
	}
	
	
	/**
	 * 删除指定的文件
	 * 
	 * @param strFileName
	 *            指定绝对路径的文件名
	 * @return 如果删除成功true否则false
	 */
	public static boolean delete(String strFileName) {
		File fileDelete = new File(strFileName);

		if (!fileDelete.exists() || !fileDelete.isFile()) {
			//System.err.println("错误: " + strFileName + "不存在!");
			return false;
		}

		System.out.println("--------成功删除文件---------"+strFileName);
		return fileDelete.delete();
	}
}
