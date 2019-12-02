package com.leimingtech.gencode.codegenerate.database;

import com.leimingtech.gencode.codegenerate.pojo.Columnt;
import com.leimingtech.gencode.codegenerate.pojo.TableConvert;
import com.leimingtech.gencode.codegenerate.util.CodeResourceUtil;
import com.leimingtech.gencode.codegenerate.util.CodeStringUtils;
import com.leimingtech.gencode.codegenerate.util.def.ConvertDef;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 取生成页面需要字段（过滤建表模板的默认字段）
 * @author 00704873
 *
 */
public class LmcmsReadTable {
	private static final long serialVersionUID = -5324160085184088010L;

	private Connection conn;
	private Statement stmt;
	private String sql;
	private ResultSet rs;
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		try {
			List<Columnt> cls = new LmcmsReadTable().readTableColumn("person");
			for(Columnt c:cls){
				System.out.println(c.getFieldName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(ArrayUtils.toString(new LmcmsReadTable().readAllTableNames()));
		//System.out.println(formatField("t60_gbuy_module"));
	}
	/**
	 * 读取数据库中所有的表
	 * @return
	 * @throws SQLException 
	 */
	public List<String> readAllTableNames() throws SQLException{
		List<String> tableNames = new ArrayList<String>(0);
		try {
			Class.forName(CodeResourceUtil.DIVER_NAME);
			conn = DriverManager.getConnection(CodeResourceUtil.URL, CodeResourceUtil.USERNAME, CodeResourceUtil.PASSWORD);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY); // 创建可滚动的,只读的结果集
			//[DB SQL]
			//mysql
			if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_MYSQL)){
				sql =  MessageFormat.format(ConvertDef.mysql_db_sql_queryName, TableConvert.getV(CodeResourceUtil.DATABASE_NAME));
			}
			//oracle
			if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_ORACLE)){
				sql = ConvertDef.oracle_db_sql_queryName;
			}
			//postgresql
			if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_postgresql)){
				sql = ConvertDef.PostgreSQL_db_sql_queryName;
			}
			//sqlserver
			if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_SQL_SERVER)){
				sql = ConvertDef.sqlserver_db_sql_queryName;
			}
			//---------------------------------------------------------------------------------------
			//log.info("--------------sql-------------"+sql);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				String tableName = rs.getString(1);
				tableNames.add(tableName);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (stmt != null) {
					stmt.close();
					stmt = null;
					System.gc();
				}
				if (conn != null) {
					conn.close();
					conn = null;
					System.gc();
				}
			} catch (SQLException e) {
				throw e;
			}
		}
		return tableNames;
	}
	/**
	 * 方法:读取表字段信息
	 * 【对应JSP页面-前台页面模板】
	 * @param tableName
	 */
	public List<Columnt> readTableColumn(String tableName) throws Exception {
		List<Columnt> columntList = new ArrayList<Columnt>();
		try {
			Class.forName(CodeResourceUtil.DIVER_NAME);
			conn = DriverManager.getConnection(CodeResourceUtil.URL, CodeResourceUtil.USERNAME, CodeResourceUtil.PASSWORD);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY); // 创建可滚动的,只读的结果集
			//---------------------------------------------------------------------------------------
			//[DB SQL]
			//mysql
			if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_MYSQL)){
				sql = MessageFormat.format(ConvertDef.mysql_db_sql,TableConvert.getV(tableName.toUpperCase()),TableConvert.getV(CodeResourceUtil.DATABASE_NAME));
			}
			//oracle
			if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_ORACLE)){
				sql = MessageFormat.format(ConvertDef.oracle_db_sql,TableConvert.getV(tableName.toUpperCase()));
			}
			//postgresql
			if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_postgresql)){
				sql = MessageFormat.format(ConvertDef.PostgreSQL_db_sql,TableConvert.getV(tableName.toLowerCase()));
			}
			//sqlserver
			if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_SQL_SERVER)){
				sql = MessageFormat.format(ConvertDef.sqlserver_db_sql,TableConvert.getV(tableName.toLowerCase()));
			}
			//---------------------------------------------------------------------------------------
			//log.info("--------------sql-------------"+sql);
			rs = stmt.executeQuery(sql);
			rs.last(); // 把指针指向结果集的最后
			int fieldNum = rs.getRow(); // 取得最后一条结果的行号,作为类的属性个数
			int n = fieldNum;
			
			if (n > 0) { // 判断数据表中是否存在字段
				Columnt columnt = new Columnt();
				//----begin--------------是否转换字段可配置------------------------------------
				if(CodeResourceUtil.LMCMS_FILED_CONVERT){
					columnt.setFieldName(formatField(rs.getString(1).toLowerCase()));
				}else{
					columnt.setFieldName(rs.getString(1).toLowerCase());
				}
				//--end----------------------------------------------------
				//直接保存表字段名字
				columnt.setFieldDbName(rs.getString(1).toUpperCase());
				columnt.setFieldType(formatField(rs.getString(2).toLowerCase()));
				//精度-小数点
				columnt.setPrecision(rs.getString(4));
				columnt.setScale(rs.getString(5));
				columnt.setCharmaxLength(rs.getString(6));
				columnt.setNullable(TableConvert.getNullAble(rs.getString(7)));
				//加载字段页面样式
				formatFieldClassType(columnt);
				columnt.setFiledComment(StringUtils.isBlank(rs.getString(3))?columnt.getFieldName():rs.getString(3));
				
				//过滤建表模板中字段
				//System.out.println("columnt.getFieldName() -------------"+columnt.getFieldName());
				
				//
				//页面生成过滤字段
				String[] ui_filter_fields = new String[]{};
				if(CodeResourceUtil.LMCMS_GENERATE_UI_FILTER_FIELDS!=null){
					ui_filter_fields = CodeResourceUtil.LMCMS_GENERATE_UI_FILTER_FIELDS.toLowerCase().split(",");
				}
				
				if(CodeResourceUtil.LMCMS_GENERATE_TABLE_ID.equals(columnt.getFieldName())
						|| CodeStringUtils.isIn(columnt.getFieldDbName().toLowerCase(),ui_filter_fields)){
				}
				else{
					columntList.add(columnt);
				}
				while (rs.previous()) {
					Columnt po = new Columnt();
					//----begin--------------是否转换字段可配置------------------------------------
					if(CodeResourceUtil.LMCMS_FILED_CONVERT){
						po.setFieldName(formatField(rs.getString(1).toLowerCase()));
					}else{
						po.setFieldName(rs.getString(1).toLowerCase());
					}
					//--end----------------------------------------------------
					//直接保存表字段名字
					po.setFieldDbName(rs.getString(1).toUpperCase());
					//System.out.println("columnt.getFieldName() -------------"+po.getFieldName());
					if(CodeResourceUtil.LMCMS_GENERATE_TABLE_ID.equals(po.getFieldName())
							||CodeStringUtils.isIn(po.getFieldDbName().toLowerCase(),ui_filter_fields)){
						continue;
					}
					po.setFieldType(formatField(rs.getString(2).toLowerCase()));
					//System.out.println("-----po.setFieldType------------"+po.getFieldType());
					//精度-小数点
					po.setPrecision(rs.getString(4));
					po.setScale(rs.getString(5));
					po.setCharmaxLength(rs.getString(6));
					po.setNullable(TableConvert.getNullAble(rs.getString(7)));
					//加载字段页面样式
					formatFieldClassType(po);
					po.setFiledComment(StringUtils.isBlank(rs.getString(3))?po.getFieldName():rs.getString(3));
					columntList.add(po);
				}
			} else {
				throw new Exception("该表不存在或者表中没有字段");
			}
			
			//System.out.println("读取表成功");
		} catch (ClassNotFoundException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
					stmt = null;
					System.gc();
				}
				if (conn != null) {
					conn.close();
					conn = null;
					System.gc();
				}
			} catch (SQLException e) {
				throw e;
			}
		}
		
		
		//纠正顺序
		List<Columnt> rsList = new ArrayList<Columnt>();
			for(int i = columntList.size() - 1;i >= 0;i--){
			Columnt ch = columntList.get(i);
			rsList.add(ch);
		}
		return rsList;
	}
	
	
	/**
	 * 方法:读取表字段信息
	 * 【对应Entity,Page-后台代码】
	 * 
	 * @param tableName
	 */
	public List<Columnt> readOriginalTableColumn(String tableName) throws Exception {
		List<Columnt> columntList = new ArrayList<Columnt>();
		try {
			Class.forName(CodeResourceUtil.DIVER_NAME);
			conn = DriverManager.getConnection(CodeResourceUtil.URL, CodeResourceUtil.USERNAME, CodeResourceUtil.PASSWORD);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY); // 创建可滚动的,只读的结果集
			//---------------------------------------------------------------------------------------
			//[DB SQL]
			//mysql
			if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_MYSQL)){
				sql = MessageFormat.format(ConvertDef.mysql_db_sql,TableConvert.getV(tableName.toUpperCase()),TableConvert.getV(CodeResourceUtil.DATABASE_NAME));
			}
			//oracle
			if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_ORACLE)){
				sql = MessageFormat.format(ConvertDef.oracle_db_sql,TableConvert.getV(tableName.toUpperCase()));
			}
			//postgresql
			if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_postgresql)){
				sql = MessageFormat.format(ConvertDef.PostgreSQL_db_sql,TableConvert.getV(tableName.toLowerCase()));
			}//sqlserver
			if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_SQL_SERVER)){
				sql = MessageFormat.format(ConvertDef.sqlserver_db_sql,TableConvert.getV(tableName.toLowerCase()));
			}
			//---------------------------------------------------------------------------------------
			rs = stmt.executeQuery(sql);
			rs.last(); // 把指针指向结果集的最后
			int fieldNum = rs.getRow(); // 取得最后一条结果的行号,作为类的属性个数
			int n = fieldNum;
			
			if (n > 0) { // 判断数据表中是否存在字段
				Columnt columnt = new Columnt();
				//----begin--------------是否转换字段可配置------------------------------------
				if(CodeResourceUtil.LMCMS_FILED_CONVERT){
					columnt.setFieldName(formatField(rs.getString(1).toLowerCase()));
				}else{
					columnt.setFieldName(rs.getString(1).toLowerCase());
				}
				//--end----------------------------------------------------
				//直接保存表字段名字
				columnt.setFieldDbName(rs.getString(1).toUpperCase());
				//精度-小数点
				columnt.setPrecision(TableConvert.getNullString(rs.getString(4)));
				columnt.setScale(TableConvert.getNullString(rs.getString(5)));
				columnt.setCharmaxLength(TableConvert.getNullString(rs.getString(6)));
				columnt.setNullable(TableConvert.getNullAble(rs.getString(7)));
				// 把数据库字段类型转换成java数据类型
				columnt.setFieldType(formatDataType(rs.getString(2).toLowerCase(),columnt.getPrecision(),columnt.getScale()));
				//加载字段页面样式
				formatFieldClassType(columnt);
				columnt.setFiledComment(StringUtils.isBlank(rs.getString(3))?columnt.getFieldName():rs.getString(3));
				
				//过滤建表模板中字段
				//System.out.println("columnt.getFieldName() -------------"+columnt.getFieldName());
				columntList.add(columnt);
				while (rs.previous()) {
					Columnt po = new Columnt();
					//----begin--------------是否转换字段可配置------------------------------------
					if(CodeResourceUtil.LMCMS_FILED_CONVERT){
						po.setFieldName(formatField(rs.getString(1).toLowerCase()));
					}else{
						po.setFieldName(rs.getString(1).toLowerCase());
					}
					//--end----------------------------------------------------
					//直接保存表字段名字
					po.setFieldDbName(rs.getString(1).toUpperCase());
					//精度-小数点
					po.setPrecision(TableConvert.getNullString(rs.getString(4)));
					po.setScale(TableConvert.getNullString(rs.getString(5)));
					po.setCharmaxLength(TableConvert.getNullString(rs.getString(6)));
					po.setNullable(TableConvert.getNullAble(rs.getString(7)));
					// 把数据库字段类型转换成java数据类型
					po.setFieldType(formatDataType(rs.getString(2).toLowerCase(),po.getPrecision(),po.getScale()));
					//加载字段页面样式
					formatFieldClassType(po);
					po.setFiledComment(StringUtils.isBlank(rs.getString(3))?po.getFieldName():rs.getString(3));
					columntList.add(po);
				}
			} else {
				throw new Exception("该表不存在或者表中没有字段");
			}
			
			//System.out.println("读取表成功");
		} catch (ClassNotFoundException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
					stmt = null;
					System.gc();
				}
				if (conn != null) {
					conn.close();
					conn = null;
					System.gc();
				}
			} catch (SQLException e) {
				throw e;
			}
		}
		
		
		//纠正顺序
		List<Columnt> rsList = new ArrayList<Columnt>();
			for(int i = columntList.size() - 1;i >= 0;i--){
			Columnt ch = columntList.get(i);
			rsList.add(ch);
		}
		return rsList;
	}
	
	/**
	 * 把数据库字段格式成java变量名[头字母小写]
	 * 
	 * @param field
	 * @return
	 */
	public static String formatField(String field) {
		String[] strs = field.split("_");
		field = "";
		for (int m = 0, length = strs.length; m < length; m++) {
			if (m > 0) {
				String tempStr = strs[m].toLowerCase();
				tempStr = tempStr.substring(0, 1).toUpperCase()
						+ tempStr.substring(1, tempStr.length());
				field += tempStr;
			} else {
				field += strs[m].toLowerCase();
			}
		}
		return field;
	}

	/**
	 * 把数据库字段格式成java变量名[头字母大写]
	 * 
	 * @param field
	 * @return
	 */
	public static String formatFieldCapital(String field) {
		String[] strs = field.split("_");
		field = "";
		for (int m = 0, length = strs.length; m < length; m++) {
			if (m > 0) {
				String tempStr = strs[m].toLowerCase();
				tempStr = tempStr.substring(0, 1).toUpperCase()
						+ tempStr.substring(1, tempStr.length());
				field += tempStr;
			} else {
				field += strs[m].toLowerCase();
			}
		}
		field = field.substring(0, 1).toUpperCase()+field.substring(1);
		return field;
	}
	
	/**
	 * 判断表在数据库中是否存在
	 * @param tableName
	 * @return
	 */
	public boolean checkTableExist(String tableName){
		try {
			System.out.println("数据库驱动: "+CodeResourceUtil.DIVER_NAME);
			Class.forName(CodeResourceUtil.DIVER_NAME);
			conn = DriverManager.getConnection(CodeResourceUtil.URL, CodeResourceUtil.USERNAME, CodeResourceUtil.PASSWORD);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY); // 创建可滚动的,只读的结果集
			
			//---------------------------------------------------------------------------------------
			//mysql
			if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_MYSQL)){
				sql = "select column_name,data_type,column_comment,0,0 from information_schema.columns"+
						" where table_name = '"+tableName.toUpperCase()+"'"
						+" and table_schema = '"+CodeResourceUtil.DATABASE_NAME+"'"; // 表名
			}
			//oracle
			if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_ORACLE)){
			sql = "select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment"
				+ " from user_tab_cols colstable "
				+ " inner join user_col_comments commentstable "
				+ " on colstable.column_name = commentstable.column_name "
				+ " where colstable.table_name = commentstable.table_name "
				+ " and colstable.table_name = '"
				+ tableName.toUpperCase()
				+ "'"; // 表名
			}
			//postgresql
			if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_postgresql)){
				sql = MessageFormat.format(ConvertDef.PostgreSQL_db_sql,TableConvert.getV(tableName.toLowerCase()));
			}//sqlserver
			if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_SQL_SERVER)){
				sql = MessageFormat.format(ConvertDef.sqlserver_db_sql,TableConvert.getV(tableName.toLowerCase()));
			}
			//---------------------------------------------------------------------------------------
			
			rs = stmt.executeQuery(sql);
			rs.last(); // 把指针指向结果集的最后
			int fieldNum = rs.getRow(); // 取得最后一条结果的行号,作为类的属性个数
			if(fieldNum>0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * 根据数据库字段的类型，加载页面样式
	 * @param columnt
	 */
	private void formatFieldClassType(Columnt columnt) {
		String fieldType = columnt.getFieldType();
		String scale = columnt.getScale();
		
		columnt.setClassType("inputxt");
		//必须校验判断
		if(ConvertDef.FIELD_NULL_ABLE_N.equals(columnt.getNullable())){
			columnt.setOptionType("*");
		}
		if("datetime".equals(fieldType)||fieldType.contains("time")){
			columnt.setClassType("easyui-datetimebox");
		}else if("date".equals(fieldType)){
			columnt.setClassType("easyui-datebox");
		}else if(fieldType.contains("int")){
			columnt.setOptionType("n");
		}else if("number".equals(fieldType)){
			if(StringUtils.isNotBlank(scale)&&Integer.parseInt(scale)>0){
				columnt.setOptionType("d");
			}
		}else if("float".equals(fieldType)||"double".equals(fieldType)||"decimal".equals(fieldType)){
				columnt.setOptionType("d");
		}else if("numeric".equals(fieldType)){
			columnt.setOptionType("d");
		}
	}
	
	
	

	private String formatDataType(String dataType,String precision,String scale) {
		if (dataType.contains("char")||dataType.contains("text")) {
			dataType = "java.lang.String";
		} else if (dataType.contains("int")) {
			dataType = "java.lang.Integer";
		} else if (dataType.contains("float")) {
			dataType = "java.lang.Float";
		} else if (dataType.contains("double")) {
			dataType = "java.lang.Double";
		} else if (dataType.contains("number")) {
			if(StringUtils.isNotBlank(scale) && Integer.parseInt(scale)>0){
				dataType = "java.math.BigDecimal";
			}else if(StringUtils.isNotBlank(precision) && Integer.parseInt(precision)>10){
				dataType = "java.lang.Long";
			}else{
				dataType = "java.lang.Integer";
			}
		} else if (dataType.contains("decimal")) {
			dataType = "BigDecimal";
		}else if (dataType.contains("date")) {
			dataType = "java.util.Date";
		} else if (dataType.contains("time")) {
			//dataType = "java.sql.Timestamp";
			dataType = "java.util.Date";
		} else if (dataType.contains("blob")) {
			dataType = "byte[]";
		} else if (dataType.contains("clob")) {
			dataType = "java.sql.Clob";
		} else if (dataType.contains("numeric")) {
			dataType = "BigDecimal";
		} else {
			dataType = "java.lang.Object";
		}
		return dataType;
	}
}
