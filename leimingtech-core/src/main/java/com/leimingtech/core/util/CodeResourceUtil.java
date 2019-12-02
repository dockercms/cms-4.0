package com.leimingtech.core.util;



import java.util.ResourceBundle;


/**
 * 项目参数工具类
 * 
 * 
 */
public class CodeResourceUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("lmcms/lmcms_database");
	private static final ResourceBundle bundlePath = java.util.ResourceBundle.getBundle("lmcms/lmcms_config");
	
	//------------------------------------------------------------------------------------------------
	/**数据库连接：驱动*/
	public static  String DIVER_NAME = "com.mysql.jdbc.Driver";
	/**数据库连接：数据库URL*/
	public static  String URL = "jdbc:mysql://localhost:3306/sys?useUnicode=true&characterEncoding=UTF-8";
	/**数据库连接：账号*/
	public static  String USERNAME = "root";
	/**数据库连接：密码*/
	public static  String PASSWORD = "root";
	/**数据库名*/
	public static  String DATABASE_NAME = "sys";
	
	/**
	 * 数据库类型
	 */
	public static String DATABASE_TYPE = "mysql";
	/**默认前4个字段必须输入*/
	public static String LMCMS_UI_FIELD_REQUIRED_NUM = "4";
	/**默认页面生成4个查询字段*/
	public static String LMCMS_UI_FIELD_SEARCH_NUM = "3";
	
	
	//----------------------------------------------------------------------------------------------
	/**
	 * 各种包路径
	 */
	/**项目路径-用于web项目*/
	public static String project_path = "c:/workspace/lmcms";
	/**web根目录*/
	public static String web_root_package = "WebRoot";
	/**src根目录*/
	public static  String source_root_package = "src";
	/**业务包：不要随意改*/
	public static  String bussiPackage = "sun";
	/**业务包：不要随意改*/
	public static  String entity_package = "entity";
	/**业务包：不要随意改*/
	public static  String page_package = "page";
	/**数据库字段是否安装驼峰写法转换*/
	public static  boolean LMCMS_FILED_CONVERT = true;
	/**代码生成模板的默认路径(基于class)*/
	public static String FREEMARKER_CLASSPATH = "/lmcms/template";
	/**代码生成包风格*/
	public static String PACKAGE_SERVICE_STYLE = "service";
	public static String PACKAGE_PROJECT_STYLE = "project";
	//----------------------------------------------------------------------------------------------
	/**
	 * 代码生成路径
	 */
	/**entity 生成路径*/
	public static  String ENTITY_URL;
	/**Page 生成路径*/
	public static  String PAGE_URL;
	/**entity*/
	public static  String ENTITY_URL_INX;
	/**page*/
	public static  String PAGE_URL_INX;
	/**模板路径*/
	public static  String TEMPLATEPATH;
	/**Java文件生成路径*/
	public static  String CODEPATH;
	/**JSP文件生成路径*/
	public static  String JSPPATH;
	/**自定义主键*/
	public static  String LMCMS_GENERATE_TABLE_ID;
	/**自定义页面过滤字段*/
	public static  String LMCMS_GENERATE_UI_FILTER_FIELDS;
	/**编码格式*/
	public static  String SYSTEM_ENCODING;
	//----------------------------------------------------------------------------------------------
	static{
		DIVER_NAME = getDIVER_NAME();
		URL = getURL();
		USERNAME = getUSERNAME();
		PASSWORD = getPASSWORD();
		DATABASE_NAME = getDATABASE_NAME();
		LMCMS_FILED_CONVERT = getLMCMS_FILED_CONVERT();
		
		SYSTEM_ENCODING=getSYSTEM_ENCODING();
		TEMPLATEPATH =getTEMPLATEPATH();
		source_root_package=getSourceRootPackage();
		web_root_package=getWebRootPackage();
		bussiPackage = getBussiPackage();
		//entity_package=getEntityPackage();
		//page_package =getPagePackage();

		//自定义主键
		LMCMS_GENERATE_TABLE_ID =getLmcms_generate_table_id();
		//自定义页面过滤字段
		LMCMS_GENERATE_UI_FILTER_FIELDS =getLmcms_generate_ui_filter_fields();
		
		//自定义JSP页面，默认前几个字段为必须输入
		//LMCMS_UI_FIELD_REQUIRED_NUM =getLmcms_ui_field_required_num();
		//自定义JSP页面，默认生成几个查询条件
		LMCMS_UI_FIELD_SEARCH_NUM =getLmcms_ui_search_filed_num();
		
		
		
		//设置数据库类型
		if(URL.indexOf("mysql")>=0||URL.indexOf("MYSQL")>=0){
			DATABASE_TYPE = ConvertDef.DATABASE_TYPE_MYSQL;
		}else if(URL.indexOf("oracle")>=0||URL.indexOf("ORACLE")>=0){
			DATABASE_TYPE = ConvertDef.DATABASE_TYPE_ORACLE;
		}else if(URL.indexOf("postgresql")>=0||URL.indexOf("POSTGRESQL")>=0){
			DATABASE_TYPE = ConvertDef.DATABASE_TYPE_postgresql;
		}  // 添加sqlserver支持
		else if(URL.indexOf("sqlserver") >= 0 || URL.indexOf("sqlserver") >= 0){
			DATABASE_TYPE = ConvertDef.DATABASE_TYPE_SQL_SERVER;
		}
		
		
		//----------------------------------------------------------------------------------------------
		source_root_package = source_root_package.replace(".", "/");
		web_root_package = web_root_package.replace(".", "/");
		String bussiPackageUrl = bussiPackage.replace(".", "/");
		//entity 生成路径
		ENTITY_URL = source_root_package+"/"+bussiPackageUrl+"/"+entity_package+"/";
		//Page 生成路径
		PAGE_URL = source_root_package+"/"+bussiPackageUrl+"/"+page_package+"/";
		//entity
		ENTITY_URL_INX = bussiPackage+"."+entity_package+".";
		//page
		PAGE_URL_INX = bussiPackage+"."+page_package+".";
		//Java文件生成路径
		CODEPATH = source_root_package+"/"+bussiPackageUrl+"/";
		//JSP文件生成路径
//		JSPPATH = web_root_package+"/"+"webpage"+"/"+bussiPackageUrl+"/";
		JSPPATH = web_root_package+"/";
		
		//----------------------------------------------------------------------------------------------
	}
	
	
	
	private void ResourceUtil() {
	}


	/**
	 *DIVER_NAME
	 * 
	 * @return
	 */
	public static final String getDIVER_NAME() {
		return bundle.getString("diver_name");
	}

	/**
	 * URL
	 * 
	 * @return
	 */
	public static final String getURL() {
		return bundle.getString("url");
	}

	/**
	 * USERNAME
	 * 
	 * @return
	 */
	public static final String getUSERNAME() {
		return bundle.getString("username");
	}

	/**
	 * PASSWORD
	 * 
	 * @return
	 */
	public static final String getPASSWORD() {
		return bundle.getString("password");
	}

	/**
	 *DATABASE_NAME
	 * 
	 * @return
	 */
	public static final String getDATABASE_NAME() {
		return bundle.getString("database_name");
	}
	
	
	/**
	 *DATABASE_NAME
	 * 
	 * @return
	 */
	public static final boolean getLMCMS_FILED_CONVERT() {
		String s = bundlePath.getString("lmcms_filed_convert");
		if(s.toString().equals("false")){
			return false;
		}else {
			return true;
		}
	}
	
	
	private static String getBussiPackage() {
		return bundlePath.getString("bussi_package");
	}
	
	/**
	 *ENTITY_URL
	 * 
	 * @return
	 */
	public static final String getEntityPackage() {
		return bundlePath.getString("entity_package");
	}
	/**
	 *PAGE_URL
	 * 
	 * @return
	 */
	public static final String getPagePackage() {
		return bundlePath.getString("page_package");
	}
	
	/**
	 *TEMPLATEPATH
	 * 
	 * @return
	 */
	public static final String getTEMPLATEPATH() {
//		String keyValue ="";
//		try {
//			keyValue = new String(bundlePath.getString("templatepath").getBytes("ISO-8859-1"), "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}  
//		return keyValue;

		return bundlePath.getString("templatepath");
	}

	
	/**
	 *CODEPATH
	 * 
	 * @return
	 */
	public static final String getSourceRootPackage() {
		return bundlePath.getString("source_root_package");
	}
	
	/**
	 *JSPPATH
	 * 
	 * @return
	 */
	public static final String getWebRootPackage() {
		return bundlePath.getString("webroot_package");
	}
	/**
	 *SYSTEM_ENCODING
	 * 
	 * @return
	 */
	public static final String getSYSTEM_ENCODING() {
		return bundlePath.getString("system_encoding");
	}
	/**
	 *lmcms_generate_table_id
	 * 
	 * @return
	 */
	public static final String getLmcms_generate_table_id() {
		return bundlePath.getString("lmcms_generate_table_id");
	}
	/**
	 *lmcms_generate_ui_filter_fields
	 * 
	 * @return
	 */
	public static final String getLmcms_generate_ui_filter_fields() {
		return bundlePath.getString("ui_filter_fields");
	}
	/**
	 *lmcms_generate_ui_filter_fields
	 * 
	 * @return
	 */
	public static final String getLmcms_ui_search_filed_num() {
		return bundlePath.getString("lmcms_ui_search_filed_num");
	}
	/**
	 *lmcms_generate_ui_filter_fields
	 * 
	 * @return
	 */
	public static final String getLmcms_ui_field_required_num() {
		return bundlePath.getString("lmcms_ui_field_required_num");
	}


	public static String getProject_path() {
		String projp = bundlePath.getString("project_path");
		if(projp!=null && !"".equals(projp)){
			project_path = projp;
		}
		return project_path;
	}


	public static void setProject_path(String project_path) {
		CodeResourceUtil.project_path = project_path;
	}
	}

