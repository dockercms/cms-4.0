package com.leimingtech.gencode.codegenerate.pojo.onetomany;


import com.leimingtech.gencode.codegenerate.pojo.Columnt;

import java.util.List;


/**
 * 子表实体
 * @author 00704873
 *
 */
public class SubTableEntity {

	/**子表: 包名*/
	private String entityPackage;
	/**子表：表名*/
	private String tableName;
	/**子表：实体名*/
	private String entityName;
	/**主键生成策略*/
	private String primaryKeyPolicy;
	/**主键序列号*/
	private String sequenceCode;
	/**子表：表注释*/
	private String ftlDescription;
	/**子表：外键(中间逗号隔开)*/
	private String[] foreignKeys;
	/**子表：字段明细*/
	private List<Columnt> subColums;
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
	public List<Columnt> getSubColums() {
		return subColums;
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
	public void setSubColums(List<Columnt> subColums) {
		this.subColums = subColums;
	}
	public String[] getForeignKeys() {
		return foreignKeys;
	}
	public void setForeignKeys(String[] foreignKeys) {
		this.foreignKeys = foreignKeys;
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
