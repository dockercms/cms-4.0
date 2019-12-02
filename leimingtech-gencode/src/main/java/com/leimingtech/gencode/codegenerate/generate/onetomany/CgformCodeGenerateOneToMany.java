package com.leimingtech.gencode.codegenerate.generate.onetomany;

import com.leimingtech.gencode.codegenerate.database.LmcmsReadTable;
import com.leimingtech.gencode.codegenerate.generate.CgformCodeGenerate;
import com.leimingtech.gencode.codegenerate.generate.ICallBack;
import com.leimingtech.gencode.codegenerate.pojo.CreateFileProperty;
import com.leimingtech.gencode.codegenerate.pojo.onetomany.CodeParamEntity;
import com.leimingtech.gencode.codegenerate.pojo.onetomany.SubTableEntity;
import com.leimingtech.gencode.codegenerate.util.CodeDateUtils;
import com.leimingtech.gencode.codegenerate.util.CodeResourceUtil;
import com.leimingtech.gencode.codegenerate.util.NonceUtils;
import com.leimingtech.gencode.codegenerate.util.def.FtlDef;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeecgframework.web.cgform.entity.button.CgformButtonEntity;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;
import org.jeecgframework.web.cgform.entity.generate.GenerateEntity;

import java.io.IOException;
import java.util.*;


/**
 * 
 * @Title:CgformCodeGenerateOneToMany
 * @description:根据智能表单配置生成代码实现-一对多，一对一模型
 * @author 赵俊夫
 * @date Sep 23, 2013 7:04:21 PM
 * @version V1.0
 */
public class CgformCodeGenerateOneToMany implements ICallBack {
	private static final Log log = LogFactory.getLog(CgformCodeGenerateOneToMany.class);
	
	private  String entityPackage = "test";//包名（小写）
	private  String entityName = "Person";//实体名（首字母大写）
	private  String tableName = "person";	//表名
	private  String ftlDescription = "用户";//功能描述
	private  String primaryKeyPolicy = "uuid";//主键生成策略
	private  String sequenceCode = "";//主键序列号
	
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
	/**
	/**子表结构明細*/
	private List<SubTableEntity> subTabFtl = new ArrayList<SubTableEntity>();
	static{
		createFileProperty.setActionFlag(true);
		createFileProperty.setServiceIFlag(true);
		createFileProperty.setJspFlag(true);
		createFileProperty.setServiceImplFlag(true);
		createFileProperty.setPageFlag(true);
		createFileProperty.setEntityFlag(true);
	}
	
	
	public CgformCodeGenerateOneToMany(){
		
	}
	/**
	 * 智能表单配置
	 */
	private CodeParamEntity codeParamEntityIn;
	private GenerateEntity mainG;
	private Map<String, GenerateEntity> subsG;
	private List<SubTableEntity> subTabParamIn;
	
	public CgformCodeGenerateOneToMany(List<SubTableEntity> subTabParamIn, CodeParamEntity codeParamEntityIn,
			GenerateEntity mainG, Map<String, GenerateEntity> subsG) {
		this.entityName = codeParamEntityIn.getEntityName();
		this.entityPackage = codeParamEntityIn.getEntityPackage();
		this.tableName = codeParamEntityIn.getTableName();
		this.ftlDescription = codeParamEntityIn.getFtlDescription();
		this.subTabParam = codeParamEntityIn.getSubTabParam();
		this.ftl_mode = codeParamEntityIn.getFtl_mode();
		this.primaryKeyPolicy = "uuid";
		this.sequenceCode = codeParamEntityIn.getSequenceCode();
		this.subTabParamIn = subTabParamIn;
		this.mainG=mainG;
		this.subsG = subsG;
		this.codeParamEntityIn = codeParamEntityIn;
	}
		

	/**
	 * entityPackage：Entity的上级包名 entityName：Entity的类名（注意第一个字母大写）
	 * serialVersionUID：随机生成的序列码
	 */
	public Map<String, Object> execute() {
		Map<String, Object> data = new HashMap<String, Object>();
		
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
			//step.1 主表的配置
			Map<String,String> fieldMeta = new HashMap<String,String>();
			List<CgFormFieldEntity> columns = mainG.deepCopy().getCgFormHead().getColumns();
			//智能表单中的字段配置
			for(CgFormFieldEntity cf:columns){
				String type = cf.getType();
				if("string".equalsIgnoreCase(type)){
					cf.setType("java.lang.String");
				}else if("Date".equalsIgnoreCase(type)){
					cf.setType("java.util.Date");
				}else if("double".equalsIgnoreCase(type)){
					cf.setType("java.lang.Double");
				}else if("int".equalsIgnoreCase(type)){
					cf.setType("java.lang.Integer");
				}else if("BigDecimal".equalsIgnoreCase(type)){
					cf.setType("java.math.BigDecimal");
				}else if("Text".equalsIgnoreCase(type)){
					cf.setType("javax.xml.soap.Text");
				}else if("Blob".equalsIgnoreCase(type)){
					cf.setType("java.sql.Blob");
				}
				String fieldName = cf.getFieldName();
				String fieldNameV = LmcmsReadTable.formatField(fieldName);
				cf.setFieldName(fieldNameV);
				fieldMeta.put(fieldNameV, fieldName.toUpperCase());
			}
			List<CgFormFieldEntity> pageColumns = new ArrayList<CgFormFieldEntity>();
			for(CgFormFieldEntity cf:columns){
				if(StringUtils.isNotEmpty(cf.getIsShow()) && "Y".equalsIgnoreCase(cf.getIsShow())){
					pageColumns.add(cf);
				}
			}
			String[] subtables = mainG.getCgFormHead().getSubTableStr().split(",");
			//全部配置
			data.put("cgformConfig",this.mainG);
			data.put("fieldMeta",fieldMeta);
			data.put("columns",columns);
			data.put("pageColumns",pageColumns);
			data.put("buttons", this.mainG.getButtons()==null?new ArrayList<CgformButtonEntity>(0):this.mainG.getButtons() );
			data.put("buttonSqlMap",this.mainG.getButtonSqlMap()==null?new HashMap<String, String[]>(0):this.mainG.getButtonSqlMap());
			data.put("subtables",subtables);
			data.put("subTab", this.subTabParamIn);
			
			//step.2 子表的配置
			Map<String,List<CgFormFieldEntity>> subColumnsMap = new HashMap<String, List<CgFormFieldEntity>>(0);
			Map<String,List<CgFormFieldEntity>> subPageColumnsMap = new HashMap<String, List<CgFormFieldEntity>>(0);
			Map<String,String> subFieldMeta = new HashMap<String,String>(0);
			Map<String,String> subFieldMeta1 = new HashMap<String,String>(0);
			for(String key: subsG.keySet()){
				GenerateEntity subG = subsG.get(key);
				List<CgFormFieldEntity> subColumns = subG.deepCopy().getCgFormHead().getColumns();
				List<CgFormFieldEntity> subPageColumns = new ArrayList<CgFormFieldEntity>();
				for(CgFormFieldEntity cf:subColumns){
					String type = cf.getType();
					if("string".equalsIgnoreCase(type)){
						cf.setType("java.lang.String");
					}else if("Date".equalsIgnoreCase(type)){
						cf.setType("java.util.Date");
					}else if("double".equalsIgnoreCase(type)){
						cf.setType("java.lang.Double");
					}else if("int".equalsIgnoreCase(type)){
						cf.setType("java.lang.Integer");
					}else if("BigDecimal".equalsIgnoreCase(type)){
						cf.setType("java.math.BigDecimal");
					}else if("Text".equalsIgnoreCase(type)){
						cf.setType("javax.xml.soap.Text");
					}else if("Blob".equalsIgnoreCase(type)){
						cf.setType("java.sql.Blob");
					}
					String fieldName = cf.getFieldName();
					String fieldNameV = LmcmsReadTable.formatField(fieldName);
					cf.setFieldName(fieldNameV);
					subFieldMeta.put(fieldNameV, fieldName.toUpperCase());
					subFieldMeta1.put(fieldName.toUpperCase(),fieldNameV );
					if(StringUtils.isNotEmpty(cf.getIsShow()) && "Y".equalsIgnoreCase(cf.getIsShow())){
						subPageColumns.add(cf);
					}
					String mtable = cf.getMainTable();
					String mfiled = cf.getMainField();
					if(mtable!=null&&mtable.equalsIgnoreCase(mainG.getTableName())){
						data.put(key+"_fk", mfiled);
					}
					subColumnsMap.put(key, subColumns);
					subPageColumnsMap.put(key, subPageColumns);
				}
				data.put("subColumnsMap", subColumnsMap);
				data.put("subPageColumnsMap",subPageColumnsMap);
				data.put("subFieldMeta",subFieldMeta);
				data.put("subFieldMeta1",subFieldMeta1);
//				data.put("packageStyle", this.mainG.getPackageStyle());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long serialVersionUID = NonceUtils.randomLong()
				+ NonceUtils.currentMills();
		data.put("serialVersionUID", String.valueOf(serialVersionUID));
		return data;
	}

	public void generateToFile() throws TemplateException, IOException {
		CgformCodeFactoryOneToMany codeFactoryOneToMany = new CgformCodeFactoryOneToMany();
		codeFactoryOneToMany.setProjectPath(this.mainG.getProjectPath());
//		codeFactoryOneToMany.setPackageStyle(this.mainG.getPackageStyle());
		codeFactoryOneToMany.setCallBack(new CgformCodeGenerateOneToMany(this.subTabParamIn,this.codeParamEntityIn,this.mainG,this.subsG));

		if(createFileProperty.isJspFlag()){
			codeFactoryOneToMany.invoke("onetomany/cgform_jspListTemplate.ftl", "jspList");//一对多[列表页面]
			codeFactoryOneToMany.invoke("onetomany/cgform_jspTemplate_add.ftl", "jsp_add");//新增
			codeFactoryOneToMany.invoke("onetomany/cgform_jspTemplate_update.ftl", "jsp_update");//编辑
			codeFactoryOneToMany.invoke("onetomany/cgform_jsEnhanceTemplate.ftl", "js");//js增强-表单
			codeFactoryOneToMany.invoke("onetomany/cgform_jsListEnhanceTemplate.ftl", "jsList");//js增强-列表
		}
		if(createFileProperty.isServiceImplFlag()){
			codeFactoryOneToMany.invoke("onetomany/cgform_serviceImplTemplate.ftl", "serviceImpl");
		}
		if(createFileProperty.isServiceIFlag()){
			codeFactoryOneToMany.invoke("onetomany/cgform_serviceITemplate.ftl", "service");
		}
		if(createFileProperty.isActionFlag()){
			codeFactoryOneToMany.invoke("onetomany/cgform_controllerTemplate.ftl", "controller");
		}
		if(createFileProperty.isEntityFlag()){
			//Entity实体生成
			codeFactoryOneToMany.invoke("onetomany/cgform_entityTemplate.ftl", "entity");//采用替换方式
		}
		if(createFileProperty.isPageFlag()){
			//Page实体生成
			codeFactoryOneToMany.invoke("onetomany/cgform_pageTemplate.ftl", "page");//采用替换方式
		}
		
	}
	/**
	 * 一对多代码生成
	 * @param subsG 
	 * @param mainG 
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public static void oneToManyCreate(List<SubTableEntity> subTabParamIn,CodeParamEntity codeParamEntityIn, GenerateEntity mainG, Map<String, GenerateEntity> subsG) throws TemplateException, IOException{
		log.info("----lmcms----Code-----Generation-----[一对多数据模型："+codeParamEntityIn.getTableName()+"]------- 生成中。。。");
		//[1].子表代码生成
		CreateFileProperty subFileProperty = new CreateFileProperty();
		subFileProperty.setActionFlag(false);
		subFileProperty.setServiceIFlag(false);
		subFileProperty.setJspFlag(true);
		subFileProperty.setServiceImplFlag(false);
		subFileProperty.setPageFlag(false);
		subFileProperty.setEntityFlag(true);
		subFileProperty.setJspMode("03");//生成一对多明细页面
		
		for(SubTableEntity sub:subTabParamIn){
			String[] foreignKeys = sub.getForeignKeys();
			GenerateEntity subG = subsG.get(sub.getTableName());
			new CgformCodeGenerate(sub,subG,subFileProperty,"uuid",foreignKeys).generateToFile();
		}
		
		//[2].主表代码生成
		new CgformCodeGenerateOneToMany(subTabParamIn,codeParamEntityIn,mainG,subsG).generateToFile();
		log.info("----lmcms----Code----Generation------[一对多数据模型："+codeParamEntityIn.getTableName()+"]------ 生成完成。。。");
	}

}
