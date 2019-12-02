package com.leimingtech.gencode.codegenerate.generate;

import com.leimingtech.gencode.codegenerate.database.LmcmsReadTable;
import com.leimingtech.gencode.codegenerate.pojo.Columnt;
import com.leimingtech.gencode.codegenerate.pojo.CreateFileProperty;
import com.leimingtech.gencode.codegenerate.util.CodeDateUtils;
import com.leimingtech.gencode.codegenerate.util.CodeResourceUtil;
import com.leimingtech.gencode.codegenerate.util.NonceUtils;
import com.leimingtech.gencode.codegenerate.util.def.FtlDef;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;


/**
 * 自动生成Dao,Service,Action及Dao和Service测试类
 */
public class CodeGenerate implements ICallBack {
	private static final Log log = LogFactory.getLog(CodeGenerate.class);
	
	private static String entityPackage = "test";//包名（小写）
	private static String entityName = "Person";//实体名（首字母大写）
	private static String tableName = "person";	//表名
	private static String ftlDescription = "公告";//功能描述
	private static String primaryKeyPolicy = "uuid";//主键生成策略
	private static String sequenceCode = "";//主键序列号
	private static String[] foreignKeys;//子表：外键(中间逗号隔开)
	
	
	/**
	 * 数据库字段列表（除了主键OBID）
	 * 对应JSP
	 */
	private List<Columnt> originalColumns = new ArrayList<Columnt>();
	public static int FIELD_ROW_NUM = 1;//默认一行放几个字段
	private static CreateFileProperty createFileProperty = new CreateFileProperty();//生成选中文件
	static{
		createFileProperty.setActionFlag(true);
		createFileProperty.setServiceIFlag(true);
		createFileProperty.setJspFlag(true);
		createFileProperty.setServiceImplFlag(true);
		createFileProperty.setJspMode("01");
		createFileProperty.setPageFlag(true);
		createFileProperty.setEntityFlag(true);
	}
	
	
	public CodeGenerate(){
		
	}
	
	public CodeGenerate(String entityPackage,String entityName,String tableName,String ftlDescription,CreateFileProperty createFileProperty
			,int fieldRowNum,String primaryKeyPolicy,String sequenceCode){
		this.entityName = entityName;
		this.entityPackage = entityPackage;
		this.tableName = tableName;
		this.ftlDescription = ftlDescription;
		this.createFileProperty = createFileProperty;
		this.FIELD_ROW_NUM = fieldRowNum;
		this.primaryKeyPolicy = primaryKeyPolicy;
		this.sequenceCode = sequenceCode;
	}
	
	public CodeGenerate(String entityPackage,String entityName,String tableName,String ftlDescription,CreateFileProperty createFileProperty
			,String primaryKeyPolicy,String sequenceCode){
		this.entityName = entityName;
		this.entityPackage = entityPackage;
		this.tableName = tableName;
		this.ftlDescription = ftlDescription;
		this.createFileProperty = createFileProperty;
		this.primaryKeyPolicy = primaryKeyPolicy;
		this.sequenceCode = sequenceCode;
	}
	
	public CodeGenerate(String entityPackage,String entityName,String tableName,String ftlDescription,CreateFileProperty createFileProperty
			,String primaryKeyPolicy,String sequenceCode,String[] foreignKeys){
		this.entityName = entityName;
		this.entityPackage = entityPackage;
		this.tableName = tableName;
		this.ftlDescription = ftlDescription;
		this.createFileProperty = createFileProperty;
		this.primaryKeyPolicy = primaryKeyPolicy;
		this.sequenceCode = sequenceCode;
		this.foreignKeys = foreignKeys;
	}
	/**
	 * 数据库字段列表（除了主键OBID）
	 */
	private List<Columnt> columns = new ArrayList<Columnt>();
	private LmcmsReadTable dbFiledUtil = new LmcmsReadTable();
	/**
	 * entityPackage：Entity的上级包名 entityName：Entity的类名（注意第一个字母大写）
	 * serialVersionUID：随机生成的序列码
	 */
	public Map<String, Object> execute() {
		Map<String, Object> data = new HashMap<String, Object>();
		
		//填写页面文件
		data.put("webRootPackage", CodeResourceUtil.web_root_package);
		//填写业务包
		data.put("bussiPackage", CodeResourceUtil.bussiPackage);
		//填写包名（小写）
		data.put("entityPackage", entityPackage);
		//填写实体名（首字母大写）
		data.put("entityName", entityName);
		//填写表名
		data.put("tableName", tableName);
		//填写描述
		data.put("ftl_description", ftlDescription);
		//自定义主键
		data.put(FtlDef.LMCMS_TABLE_ID, CodeResourceUtil.LMCMS_GENERATE_TABLE_ID);
		//主键生成策略
		data.put(FtlDef.LMCMS_PRIMARY_KEY_POLICY, primaryKeyPolicy);
		data.put(FtlDef.LMCMS_SEQUENCE_CODE, sequenceCode);
		//创建时间
		data.put("ftl_create_time", CodeDateUtils.dateToString(new Date()));
		//外键
		data.put("foreignKeys", foreignKeys);
		
		
		//默认字段必须输入个数
		data.put(FtlDef.FIELD_REQUIRED_NAME, StringUtils.isNotEmpty(CodeResourceUtil.LMCMS_UI_FIELD_REQUIRED_NUM)?Integer.parseInt(CodeResourceUtil.LMCMS_UI_FIELD_REQUIRED_NUM):0-1);
		//默认生成几个查询条件
		data.put(FtlDef.SEARCH_FIELD_NUM,StringUtils.isNotEmpty(CodeResourceUtil.LMCMS_UI_FIELD_SEARCH_NUM)?Integer.parseInt(CodeResourceUtil.LMCMS_UI_FIELD_SEARCH_NUM):0-1);
		//默认一行显示几个字段
		data.put(FtlDef.FIELD_ROW_NAME, FIELD_ROW_NUM);
		
		try {
			//对应表实体字段[jsp]
			columns = dbFiledUtil.readTableColumn(tableName);
			data.put("columns",columns);
			
			//对应表实体字段[Entity]
			originalColumns = dbFiledUtil.readOriginalTableColumn(tableName);
			data.put("originalColumns",originalColumns);
			
			//表主键类型
			for(Columnt c:originalColumns){
				if(c.getFieldName().toLowerCase().equals(CodeResourceUtil.LMCMS_GENERATE_TABLE_ID.toLowerCase())){
					data.put("primary_key_type", c.getFieldType());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		long serialVersionUID = NonceUtils.randomLong()
				+ NonceUtils.currentMills();
		data.put("serialVersionUID", String.valueOf(serialVersionUID));
		return data;
	}

	public void generateToFile() {
		log.info("----lm---Code----Generation----[单表模型:"+tableName+"]------- 生成中。。。");

		CodeFactory codeFactory = new CodeFactory();
		codeFactory.setCallBack(new CodeGenerate());
		
		if("03".equals(createFileProperty.getJspMode())){
			//FIXME 冗余代码 2015年5月7日 17:47:32
			if(createFileProperty.isJspFlag()){
				codeFactory.invoke("onetomany/jspSubTemplate.ftl", "ftlList");//详细页面-Table风格
				codeFactory.invoke("lmone2one/lmListTemplate.ftl", "ftlList");//数据列表
			}
			if(createFileProperty.isActionFlag()){
				codeFactory.invoke("lmone2one/controllerTemplate.ftl", "controller");
			}
			if(createFileProperty.isEntityFlag()){
				//Entity实体生成
				codeFactory.invoke("lmone2one/entityTemplate.ftl", "entity");
			}
			
			if(createFileProperty.isServiceImplFlag()){
				codeFactory.invoke("lmone2one/serviceImplTemplate.ftl", "serviceImpl");
			}
			if(createFileProperty.isServiceIFlag()){
				codeFactory.invoke("lmone2one/serviceITemplate.ftl", "service");
			}
		}
		
		if("01".equals(createFileProperty.getJspMode())){
			if(createFileProperty.isJspFlag()){
				codeFactory.invoke("lmone2one/lmShowTemplate.ftl", "html");//详细页面-Table风格
				codeFactory.invoke("lmone2one/lmListTemplate.ftl", "ftlList");//数据列表
			}
			System.out.println("详细页面-Table风格");
			if(createFileProperty.isActionFlag()){
				codeFactory.invoke("lmone2one/controllerTemplate.ftl", "controller");
			}
			if(createFileProperty.isEntityFlag()){
				//Entity实体生成
				codeFactory.invoke("lmone2one/entityTemplate.ftl", "entity");
			}
			
			if(createFileProperty.isServiceImplFlag()){
				codeFactory.invoke("lmone2one/serviceImplTemplate.ftl", "serviceImpl");
			}
			if(createFileProperty.isServiceIFlag()){
				codeFactory.invoke("lmone2one/serviceITemplate.ftl", "service");
			}
		}
		//当选择树桩风格是使用下面的模版
		if("02".equals(createFileProperty.getJspMode())){
			System.out.println("详细页面-tree风格");
			if(createFileProperty.isJspFlag()){
				codeFactory.invoke("lmtreeone2many/lmShowModelTemplates.ftl", "show");//详细页面-tree风格
				codeFactory.invoke("lmtreeone2many/lmListTemplates.ftl", "ftlList");//数据列表
				codeFactory.invoke("lmtreeone2many/editModelTemplate.ftl", "html");//数据列表
			}
			if(createFileProperty.isActionFlag()){
				codeFactory.invoke("lmtreeone2many/controllerTemplate.ftl", "controller");
			}
			if(createFileProperty.isEntityFlag()){
				//Entity实体生成
				codeFactory.invoke("lmtreeone2many/entityTemplate.ftl", "entity");
			}
			
			if(createFileProperty.isServiceImplFlag()){
				codeFactory.invoke("lmtreeone2many/serviceImplTemplate.ftl", "serviceImpl");
			}
			if(createFileProperty.isServiceIFlag()){
				codeFactory.invoke("lmtreeone2many/serviceITemplate.ftl", "service");
			}
		}
		
		//当选择树桩风格是使用下面的模版
		if("04".equals(createFileProperty.getJspMode())){
			System.out.println("详细页面-tree风格2");
			if(createFileProperty.isJspFlag()){
				codeFactory.invoke("lmtreeone2many2/lmShowModelTemplates.ftl", "show");//详细页面-tree风格
				codeFactory.invoke("lmtreeone2many2/lmListTemplates.ftl", "ftlList");//数据列表
				codeFactory.invoke("lmtreeone2many2/editModelTemplate.ftl", "html");//添加页面
			}
			if(createFileProperty.isActionFlag()){
				codeFactory.invoke("lmtreeone2many2/controllerTemplate.ftl", "controller");
			}
			if(createFileProperty.isEntityFlag()){
				//Entity实体生成
				codeFactory.invoke("lmtreeone2many2/entityTemplate.ftl", "entity");
			}
			
			if(createFileProperty.isServiceImplFlag()){
				codeFactory.invoke("lmtreeone2many2/serviceImplTemplate.ftl", "serviceImpl");
			}
			if(createFileProperty.isServiceIFlag()){
				codeFactory.invoke("lmtreeone2many2/serviceITemplate.ftl", "service");
			}
		}
		log.info("----lm----Code----Generation-----[单表模型："+tableName+"]------ 生成完成。。。");
	}

	/*
	 * 参数为Entity类名(注意第一个字符大写)
	 */
	public static void main(String[] args) {
		System.out.println("----lm--------- Code------------- Generation -----[单表模型]------- 生成中。。。");
		new CodeGenerate().generateToFile();
		System.out.println("----lm--------- Code------------- Generation -----[单表模型]------- 生成完成。。。");
	}

}
