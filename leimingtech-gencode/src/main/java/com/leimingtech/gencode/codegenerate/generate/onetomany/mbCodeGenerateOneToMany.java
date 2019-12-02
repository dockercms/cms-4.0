package com.leimingtech.gencode.codegenerate.generate.onetomany;

import com.leimingtech.gencode.codegenerate.database.LmcmsReadTable;
import com.leimingtech.gencode.codegenerate.generate.ICallBack;
import com.leimingtech.gencode.codegenerate.generate.mbCodeGenerate;
import com.leimingtech.gencode.codegenerate.pojo.Columnt;
import com.leimingtech.gencode.codegenerate.pojo.CreateFileProperty;
import com.leimingtech.gencode.codegenerate.pojo.onetomany.CodeParamEntity;
import com.leimingtech.gencode.codegenerate.pojo.onetomany.SubTableEntity;
import com.leimingtech.gencode.codegenerate.util.CodeDateUtils;
import com.leimingtech.gencode.codegenerate.util.CodeResourceUtil;
import com.leimingtech.gencode.codegenerate.util.NonceUtils;
import com.leimingtech.gencode.codegenerate.util.def.FtlDef;
import com.leimingtech.gencode.codegenerate.util.def.LmcmsKey;
import org.apache.commons.lang.StringUtils;

import java.util.*;


/**
 * 自动生成Dao,Service,Action及Dao和Service测试类
 */
public class mbCodeGenerateOneToMany implements ICallBack {
	
	private static String entityPackage = "test";//包名（小写）
	private static String entityName = "Person";//实体名（首字母大写）
	private static String tableName = "person";	//表名
	private static String ftlDescription = "用户";//功能描述
	private static String primaryKeyPolicy = "uuid";//主键生成策略
	private static String sequenceCode = "";//主键序列号

	/**
	 * A：一个页面展现子表
	 * B:采用Tab方式展现子表
	 */
	private static String ftl_mode;
	public static String FTL_MODE_A = "A";
	public static String FTL_MODE_B = "B";
	
	private static List<SubTableEntity> subTabParam = new ArrayList<SubTableEntity>();//子表集合
	private static CreateFileProperty createFileProperty = new CreateFileProperty();//生成选中文件
	public static int FIELD_ROW_NUM = 4;//默认一行放几个字段
	
	/**主表：字段明细*/
	private List<Columnt> mainColums = new ArrayList<Columnt>();
	/**
	 * 数据库字段列表（除了主键OBID）
	 * 对应JSP
	 */
	private List<Columnt> originalColumns = new ArrayList<Columnt>();
	/**子表结构明細*/
	private List<SubTableEntity> subTabFtl = new ArrayList<SubTableEntity>();
	/**读取表结构工具类*/
	private static LmcmsReadTable dbFiledUtil = new LmcmsReadTable();
	static{
		createFileProperty.setActionFlag(true);
		createFileProperty.setServiceIFlag(true);
		createFileProperty.setJspFlag(true);
		createFileProperty.setServiceImplFlag(true);
		createFileProperty.setPageFlag(false);
		createFileProperty.setEntityFlag(true);
	}
	
	
	public mbCodeGenerateOneToMany(){
		
	}
	
	public mbCodeGenerateOneToMany(String entityPackage,String entityName,String tableName,List<SubTableEntity> subTabParam,
			String ftlDescription,CreateFileProperty createFileProperty,String primaryKeyPolicy,String sequenceCode){
		this.entityName = entityName;
		this.entityPackage = entityPackage;
		this.tableName = tableName;
		this.ftlDescription = ftlDescription;
		this.createFileProperty = createFileProperty;
		this.subTabParam = subTabParam;
		this.primaryKeyPolicy = StringUtils.isNotBlank(primaryKeyPolicy)?primaryKeyPolicy:"uuid";
		this.sequenceCode = sequenceCode;
	}
	
	
	public mbCodeGenerateOneToMany(CodeParamEntity codeParamEntity){
		this.entityName = codeParamEntity.getEntityName();
		this.entityPackage = codeParamEntity.getEntityPackage();
		this.tableName = codeParamEntity.getTableName();
		this.ftlDescription = codeParamEntity.getFtlDescription();
		this.subTabParam = codeParamEntity.getSubTabParam();
		this.ftl_mode = codeParamEntity.getFtl_mode();
		this.primaryKeyPolicy = StringUtils.isNotBlank(codeParamEntity.getPrimaryKeyPolicy())?codeParamEntity.getPrimaryKeyPolicy():"uuid";
		this.sequenceCode = codeParamEntity.getSequenceCode();
	}

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
		data.put("lmcms_table_id", CodeResourceUtil.LMCMS_GENERATE_TABLE_ID);
		//主键生成策略
		data.put(FtlDef.LMCMS_PRIMARY_KEY_POLICY, primaryKeyPolicy);
		data.put(FtlDef.LMCMS_SEQUENCE_CODE, sequenceCode);
		data.put("ftl_create_time", CodeDateUtils.dateToString(new Date()));
		
		//默认字段必须输入个数
		data.put(FtlDef.FIELD_REQUIRED_NAME, StringUtils.isNotEmpty(CodeResourceUtil.LMCMS_UI_FIELD_REQUIRED_NUM)?Integer.parseInt(CodeResourceUtil.LMCMS_UI_FIELD_REQUIRED_NUM):0-1);
		//默认生成几个查询条件
		data.put(FtlDef.SEARCH_FIELD_NUM,StringUtils.isNotEmpty(CodeResourceUtil.LMCMS_UI_FIELD_SEARCH_NUM)?Integer.parseInt(CodeResourceUtil.LMCMS_UI_FIELD_SEARCH_NUM):0-1);
		//默认一行显示几个字段
		data.put(FtlDef.FIELD_ROW_NAME, FIELD_ROW_NUM);
		
		//对应表实体字段
		try {
			
			//主表字段明细[jsp]
			mainColums = dbFiledUtil.readTableColumn(tableName);
			data.put("mainColums",mainColums);
			data.put("columns",mainColums);
			
			//对应表实体字段[Entity]
			originalColumns = dbFiledUtil.readOriginalTableColumn(tableName);
			data.put("originalColumns",originalColumns);
			
			//表主键类型
			for(Columnt c:originalColumns){
				if(c.getFieldName().toLowerCase().equals(CodeResourceUtil.LMCMS_GENERATE_TABLE_ID.toLowerCase())){
					data.put("primary_key_type", c.getFieldType());
				}
			}
			
			subTabFtl.clear();
			for(SubTableEntity subTableEntity:subTabParam){
				SubTableEntity po = new SubTableEntity();
				List<Columnt> subColum = dbFiledUtil.readTableColumn(subTableEntity.getTableName());
				po.setSubColums(subColum);
				po.setEntityName(subTableEntity.getEntityName());
				po.setFtlDescription(subTableEntity.getFtlDescription());
				po.setTableName(subTableEntity.getTableName());
				po.setEntityPackage(subTableEntity.getEntityPackage());
				
				//转换外键为实体字段
				String[] fkeys = subTableEntity.getForeignKeys();
				List<String> foreignKeys = new ArrayList<String>();
				for(String key:fkeys){
					//---------------------------------------------------
					//判断是否转换字段标示
					if(CodeResourceUtil.LMCMS_FILED_CONVERT){
						foreignKeys.add(dbFiledUtil.formatFieldCapital(key));
					}else{
						String keyStr  = key.toLowerCase();
						String field = keyStr.substring(0, 1).toUpperCase()+keyStr.substring(1);
						foreignKeys.add(field);
					}
					//---------------------------------------------------
				}
				po.setForeignKeys(foreignKeys.toArray(new String[]{}));
				subTabFtl.add(po);
			}
			//子表结构
			data.put("subTab", subTabFtl);
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
		System.out.println("----lm---Code----Generation----[主表模型:"+tableName+"]------- 生成中。。。");
		mbCodeFactoryOneToMany codeFactoryOneToMany = new mbCodeFactoryOneToMany();
		codeFactoryOneToMany.setCallBack(new mbCodeGenerateOneToMany());
		
		if(createFileProperty.isJspFlag()){
			codeFactoryOneToMany.invoke("lmone2many/mb/jspListMbTemplate.ftl", "ftlList");//一对多[列表页面]
			codeFactoryOneToMany.invoke("lmone2many/mb/jspMbTemplate.ftl", "html");//编辑页面
		}
		if(createFileProperty.isServiceImplFlag()){
			codeFactoryOneToMany.invoke("lmone2many/mb/serviceImplMbTemplate.ftl", "serviceImpl");
		}
		if(createFileProperty.isServiceIFlag()){
			codeFactoryOneToMany.invoke("lmone2many/mb/serviceIMbTemplate.ftl", "service");
		}
		if(createFileProperty.isActionFlag()){
			codeFactoryOneToMany.invoke("lmone2many/mb/controllerMbTemplate.ftl", "controller");
		}
		if(createFileProperty.isEntityFlag()){
			//Entity实体生成
			codeFactoryOneToMany.invoke("lmone2many/mb/entityMbTemplate.ftl", "entity");//采用替换方式
		}
		if(createFileProperty.isPageFlag()){
			//Page实体生成
			codeFactoryOneToMany.invoke("lmone2many/mb/pageMbTemplate.ftl", "page");//采用替换方式
		}
		System.out.println("----lm----Code----Generation-----[主表模型："+tableName+"]------ 生成完成。。。");
	}

	/*
	 * 一对多代码生成器
	 */
	public static void main(String[] args) {
		//--------------------------------------------------------
		List<SubTableEntity> subTabParamIn = new ArrayList<SubTableEntity>();//子表集合
		//定义子表集合参数
		//[1].子表一
		SubTableEntity po = new SubTableEntity();
		//子表表名
		po.setTableName("lm_order_custom");
		//子表生成的实体名
		po.setEntityName("OrderCustom");
		//包
		po.setEntityPackage("order");
		//子表描述
		po.setFtlDescription("订单客户明细");
		//主键生成策略
		po.setPrimaryKeyPolicy(LmcmsKey.UUID);
		po.setSequenceCode(null);
		//子表与主表的外键字段
		po.setForeignKeys(new String[]{"GORDER_OBID","GO_ORDER_CODE"});
		subTabParamIn.add(po);
		//[2].子表二
		SubTableEntity po2 = new SubTableEntity();
		po2.setTableName("lm_order_product");
		po2.setEntityName("OrderProduct");
		po2.setEntityPackage("order");
		po2.setFtlDescription("订单产品明细");
		po2.setForeignKeys(new String[]{"GORDER_OBID","GO_ORDER_CODE"});
		//主键生成策略
		po2.setPrimaryKeyPolicy(LmcmsKey.UUID);
		po2.setSequenceCode(null);
		subTabParamIn.add(po2);
		
		
		//主表参数
		CodeParamEntity codeParamEntityIn = new CodeParamEntity();
		codeParamEntityIn.setTableName("lm_order_main");
		codeParamEntityIn.setEntityName("OrderMain");
		codeParamEntityIn.setEntityPackage("order");
		codeParamEntityIn.setFtlDescription("订单抬头");
		codeParamEntityIn.setFtl_mode(FTL_MODE_B);
		//主键生成策略
		codeParamEntityIn.setPrimaryKeyPolicy(LmcmsKey.UUID);
		//Oracle 序号名称
		codeParamEntityIn.setSequenceCode(null);
		codeParamEntityIn.setSubTabParam(subTabParamIn);
		//一对多代码生成
		mbCodeGenerateOneToMany.oneToManyCreate(subTabParamIn, codeParamEntityIn);
	}
	
	
	/**
	 * 一对多代码生成
	 */
	public static void oneToManyCreate(List<SubTableEntity> subTabParamIn, CodeParamEntity codeParamEntityIn){
		System.out.println("----lm----Code-----Generation-----[一对多数据模型："+codeParamEntityIn.getTableName()+"]------- 生成中。。。");
		//[1].子表代码生成
		CreateFileProperty subFileProperty = new CreateFileProperty();
		subFileProperty.setActionFlag(true);
		subFileProperty.setServiceIFlag(true);
		subFileProperty.setJspFlag(true);
		subFileProperty.setServiceImplFlag(true);
		subFileProperty.setPageFlag(false);
		subFileProperty.setEntityFlag(true);
		subFileProperty.setJspMode("03");//生成一对多明细页面
		
		
		for(SubTableEntity sub:subTabParamIn){
			//--------------------------------------------------------------------------
			//转换外键为实体字段
			String[] fkeys = sub.getForeignKeys();
			List<String> foreignKeys = new ArrayList<String>();
			for(String key:fkeys){
				//---------------------------------------------------
				//判断是否转换字段标示
				if(CodeResourceUtil.LMCMS_FILED_CONVERT){
					foreignKeys.add(dbFiledUtil.formatFieldCapital(key));
				}else{
					String keyStr  = key.toLowerCase();
					String field = keyStr.substring(0, 1).toUpperCase()+keyStr.substring(1);
					foreignKeys.add(field);
				}
				//---------------------------------------------------
			}
			//--------------------------------------------------------------------------
			
			new mbCodeGenerate(sub.getEntityPackage(),sub.getEntityName(),sub.getTableName(),sub.getFtlDescription(),subFileProperty,StringUtils.isNotBlank(sub.getPrimaryKeyPolicy())?sub.getPrimaryKeyPolicy():"uuid",sub.getSequenceCode(),foreignKeys.toArray(new String[]{}),codeParamEntityIn.getEntityName(),codeParamEntityIn.getEntityPackage(),codeParamEntityIn.getFtlDescription()).generateToFile();
		}
		
		//[2].主表代码生成
		new mbCodeGenerateOneToMany(codeParamEntityIn).generateToFile();
		System.out.println("----lm----Code----Generation------[一对多数据模型："+codeParamEntityIn.getTableName()+"]------ 生成完成。。。");
	}

}
