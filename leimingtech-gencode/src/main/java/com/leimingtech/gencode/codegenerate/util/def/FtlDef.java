package com.leimingtech.gencode.codegenerate.util.def;

public final class FtlDef {
	private FtlDef() {
	}
	/**默认必须输入字段个数：参数*/
	public static String FIELD_REQUIRED_NAME ="field_required_num";
	/**默认一行显示几个字段：参数*/
	public static String FIELD_ROW_NAME ="field_row_num";
	
	/**主键字段*/
	public static String LMCMS_TABLE_ID = "lmcms_table_id";
	/**生成几个查询条件*/
	public static String SEARCH_FIELD_NUM = "search_field_num";
	
	/**主键生成策略*/
	public static String LMCMS_PRIMARY_KEY_POLICY = "lmcms_primary_key_policy";
	/**Oracle序列名称*/
	public static String LMCMS_SEQUENCE_CODE = "lmcms_sequence_code";
}
