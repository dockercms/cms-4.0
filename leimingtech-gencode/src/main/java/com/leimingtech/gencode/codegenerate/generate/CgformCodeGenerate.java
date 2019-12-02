package com.leimingtech.gencode.codegenerate.generate;

import com.leimingtech.gencode.codegenerate.database.LmcmsReadTable;
import com.leimingtech.gencode.codegenerate.pojo.CreateFileProperty;
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
 * @Title:CgformCodeGenerate
 * @description:根据智能表单生成代码实现-单表模型
 * @author 赵俊夫
 * @date Sep 23, 2013 7:05:27 PM
 * @version V1.0
 */
public class CgformCodeGenerate implements ICallBack {
	private static final Log log = LogFactory.getLog(CgformCodeGenerate.class);
	
	private  String entityPackage = "test";//包名（小写）
	private  String entityName = "Person";//实体名（首字母大写）
	private  String tableName = "person";	//表名
	private  String ftlDescription = "公告";//功能描述
	private  String primaryKeyPolicy = "uuid";//主键生成策略
	private  String sequenceCode = "";//主键序列号
	private  String[] foreignKeys;//子表：外键(中间逗号隔开)
	public static int FIELD_ROW_NUM = 1;//默认一行放几个字段
	
	private SubTableEntity sub;
	private GenerateEntity subG;
	private CreateFileProperty subFileProperty;
	private String policy;
	private String[] array;
	private GenerateEntity cgformConfig;//智能表单配置
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
	public CgformCodeGenerate(){
	}
	/**
	 * 用于单表生成
	 * @param createFileProperty2
	 * @param generateEntity
	 */
	public CgformCodeGenerate(CreateFileProperty createFileProperty2,
			GenerateEntity generateEntity) {
		this.entityName = generateEntity.getEntityName();
		this.entityPackage = generateEntity.getEntityPackage();
		this.tableName = generateEntity.getTableName();
		this.ftlDescription = generateEntity.getFtlDescription();
		this.FIELD_ROW_NUM = 1;
		this.createFileProperty =createFileProperty2;
		this.createFileProperty.setJspMode("01");
		this.primaryKeyPolicy = generateEntity.getPrimaryKeyPolicy();
		this.sequenceCode = "";
		this.cgformConfig = generateEntity;
	}
	/**
	 * 用于一对多模型生成
	 * @param sub
	 * @param subG
	 * @param subFileProperty
	 * @param policy
	 * @param array
	 */
	public CgformCodeGenerate(SubTableEntity sub, GenerateEntity subG,
			CreateFileProperty subFileProperty, String policy, String[] array) {
		this.entityName = subG.getEntityName();
		this.entityPackage = subG.getEntityPackage();
		this.tableName = subG.getTableName();
		this.ftlDescription = subG.getFtlDescription();
		this.createFileProperty = subFileProperty;
		this.FIELD_ROW_NUM = 1;
		this.primaryKeyPolicy = policy;
		this.sequenceCode = "";
		this.cgformConfig = subG;
		this.foreignKeys = array;
		this.sub = sub;
		this.subG = subG;
		this.subFileProperty = subFileProperty;
		this.policy = policy;
	}
	/**
	 * entityPackage：Entity的上级包名 entityName：Entity的类名（注意第一个字母大写）
	 * serialVersionUID：随机生成的序列码
	 */
	public Map<String, Object> execute() {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String,String> fieldMeta = new HashMap<String,String>();
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
		data.put("foreignKeys", this.foreignKeys);
		
		//默认字段必须输入个数
		data.put(FtlDef.FIELD_REQUIRED_NAME, StringUtils.isNotEmpty(CodeResourceUtil.LMCMS_UI_FIELD_REQUIRED_NUM)?Integer.parseInt(CodeResourceUtil.LMCMS_UI_FIELD_REQUIRED_NUM):0-1);
		//默认生成几个查询条件
		data.put(FtlDef.SEARCH_FIELD_NUM,StringUtils.isNotEmpty(CodeResourceUtil.LMCMS_UI_FIELD_SEARCH_NUM)?Integer.parseInt(CodeResourceUtil.LMCMS_UI_FIELD_SEARCH_NUM):0-1);
		//默认一行显示几个字段
		data.put(FtlDef.FIELD_ROW_NAME, FIELD_ROW_NUM);
		try {
			List<CgFormFieldEntity> columns = cgformConfig.deepCopy().getCgFormHead().getColumns();
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
			//全部配置
			data.put("cgformConfig",this.cgformConfig);
			data.put("fieldMeta",fieldMeta);
			data.put("columns",columns);
			data.put("pageColumns",pageColumns);
			data.put("buttons", this.cgformConfig.getButtons()==null?new ArrayList<CgformButtonEntity>(0):this.cgformConfig.getButtons() );
			data.put("buttonSqlMap",this.cgformConfig.getButtonSqlMap()==null?new HashMap<String, String[]>(0):this.cgformConfig.getButtonSqlMap());
//			data.put("packageStyle", this.cgformConfig.getPackageStyle());
		} catch (Exception e) {
			e.printStackTrace();
		}
		long serialVersionUID = NonceUtils.randomLong()
				+ NonceUtils.currentMills();
		data.put("serialVersionUID", String.valueOf(serialVersionUID));
		return data;
	}

	public void generateToFile() throws TemplateException, IOException {
		log.info("----lmcms---Code----Generation----[单表模型:"+tableName+"]------- 生成中。。。");

		CgformCodeFactory codeFactory = new CgformCodeFactory();
		codeFactory.setProjectPath(this.cgformConfig.getProjectPath());
//		codeFactory.setPackageStyle(this.cgformConfig.getPackageStyle());
		if(cgformConfig.getCgFormHead().getJformType()==1){
			codeFactory.setCallBack(new CgformCodeGenerate(createFileProperty,cgformConfig));
		}else{
			codeFactory.setCallBack(new CgformCodeGenerate(sub,subG,subFileProperty,"uuid",foreignKeys));
		}
		
		if(createFileProperty.isJspFlag()){
			if("03".equals(createFileProperty.getJspMode())){
				codeFactory.invoke("onetomany/cgform_jspSubTemplate.ftl", "jspList");//详细页面-Table风格
			}else{
				if("01".equals(createFileProperty.getJspMode())){
					codeFactory.invoke("cgform_jspTableTemplate_add.ftl", "jsp_add");//详细页面-Table风格-新增页面
					codeFactory.invoke("cgform_jspTableTemplate_update.ftl", "jsp_update");//详细页面-Table风格-编辑页面
				}
				if("02".equals(createFileProperty.getJspMode())){
					codeFactory.invoke("cgform_jspDivTemplate_add.ftl", "jsp_add");//详细页面-DIV风格-新增页面
					codeFactory.invoke("cgform_jspDivTemplate_update.ftl", "jsp_update");//详细页面-DIV风格-编辑页面
				}
				codeFactory.invoke("cgform_jspListTemplate.ftl", "jspList");//数据列表
				codeFactory.invoke("cgform_jsListEnhanceTemplate.ftl", "jsList");//列表js增强
				codeFactory.invoke("cgform_jsEnhanceTemplate.ftl", "js");//表单js增强
			}
		}
		if(createFileProperty.isServiceImplFlag()){
			codeFactory.invoke("cgform_serviceImplTemplate.ftl", "serviceImpl");
		}
		if(createFileProperty.isServiceIFlag()){
			codeFactory.invoke("cgform_serviceITemplate.ftl", "service");
		}
		if(createFileProperty.isActionFlag()){
			codeFactory.invoke("cgform_controllerTemplate.ftl", "controller");
		}
		if(createFileProperty.isEntityFlag()){
			//Entity实体生成
			codeFactory.invoke("cgform_entityTemplate.ftl", "entity");
		}
		log.info("----lmcms----Code----Generation-----[单表模型："+tableName+"]------ 生成完成。。。");
	}
	public GenerateEntity getCgformConfig() {
		return cgformConfig;
	}

	public void setCgformConfig(GenerateEntity cgformConfig) {
		this.cgformConfig = cgformConfig;
	}

}
