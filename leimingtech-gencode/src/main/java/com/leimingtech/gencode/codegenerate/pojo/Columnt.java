package com.leimingtech.gencode.codegenerate.pojo;

public class Columnt {

	public static final String OPTION_REQUIRED = "required:true";
	public static final String OPTION_NUMBER_INSEX = "precision:2,groupSeparator:','";
	
	/**字段名[数据库表字段]*/
	private String fieldDbName;
	/**字段名[转换后]*/
	private String fieldName;
	/**字段注释*/
	private String filedComment = ""; 
	/**字段类型*/
	private String fieldType = "";
	/**字段类型*/
	private String classType = "";
	/**字段类型*/
	private String classType_row = "";
	/**样式选项*/
	private String optionType = "";
	/**字符串允许输入最大长度*/
	private String charmaxLength = "";
	/**精度*/
	private String precision;
	/**小数点*/
	private String scale;
	/**是否允许为空 Y/N*/
	private String nullable;

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getPrecision() {
		return precision;
	}

	public String getScale() {
		return scale;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFiledComment() {
//		if(filedComment.matches("[a-zA-Z]+")){
//			return filedComment;
//		}
//		if(filedComment!=null&&filedComment.length()>8){
//			return filedComment.substring(0, 8);
//		}
		return filedComment;
	}

	public void setFiledComment(String filedComment) {
		this.filedComment = filedComment;
	}

	public String getClassType_row() {
		if(classType!=null&&classType.indexOf("easyui-")>=0){
			return classType.replaceAll("easyui-", "");
		}
		return classType_row;
	}

	public void setClassType_row(String classType_row) {
		this.classType_row = classType_row;
	}

	public String getCharmaxLength() {
		if(charmaxLength==null||"0".equals(charmaxLength)){
			return "";
		}
		return charmaxLength;
	}

	public void setCharmaxLength(String charmaxLength) {
		this.charmaxLength = charmaxLength;
	}

	public String getFieldDbName() {
		return fieldDbName;
	}

	public void setFieldDbName(String fieldDbName) {
		this.fieldDbName = fieldDbName;
	}

}
