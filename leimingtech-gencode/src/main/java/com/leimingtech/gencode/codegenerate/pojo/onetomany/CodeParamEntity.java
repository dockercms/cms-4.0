package com.leimingtech.gencode.codegenerate.pojo.onetomany;

import java.util.List;


/**
 * 代码生成器[主表参数]
 * @author 00704873
 *
 */
public class CodeParamEntity {

	/**主表: 包名*/
	private String entityPackage;
	/**主表：表名*/
	private String tableName;
	/**主表：实体名*/
	private String entityName;
	/**主表：表注释*/
	private String ftlDescription;
	/**主键生成策略*/
	private String primaryKeyPolicy;
	/**主键序列号*/
	private String sequenceCode;
	
	/**
	 * A：一个页面展现子表
	 * B:采用Tab方式展现子表
	 */
	private String ftl_mode = "A";
	
	/**主表：子表参数*/
	List<SubTableEntity> subTabParam;
	
	public List<SubTableEntity> getSubTabParam() {
		return subTabParam;
	}
	public void setSubTabParam(List<SubTableEntity> subTabParam) {
		this.subTabParam = subTabParam;
	}
	public String getEntityPackage() {
		return entityPackage;
	}
	public String getTableName() {
		return tableName;
	}
	public String getEntityName() {
		return entityName;
	}
	public String getFtlDescription() {
		return ftlDescription;
	}
	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public void setFtlDescription(String ftlDescription) {
		this.ftlDescription = ftlDescription;
	}
	public String getFtl_mode() {
		return ftl_mode;
	}
	public void setFtl_mode(String ftl_mode) {
		this.ftl_mode = ftl_mode;
	}
	public String getPrimaryKeyPolicy() {
		return primaryKeyPolicy;
	}
	public String getSequenceCode() {
		return sequenceCode;
	}
	public void setPrimaryKeyPolicy(String primaryKeyPolicy) {
		this.primaryKeyPolicy = primaryKeyPolicy;
	}
	public void setSequenceCode(String sequenceCode) {
		this.sequenceCode = sequenceCode;
	}

}
