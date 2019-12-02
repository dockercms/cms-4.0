package com.leimingtech.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.MessagesServiceI;

@Service("messagesService")
@Transactional
public class MessagesServiceImpl extends CommonServiceImpl implements MessagesServiceI {

	@Override
	public String yearSqlMessage(int value, String month, String staticsType,String dbType) {
		String sql = "select count(id) count from cms_message ";
		if("0".equals(month)){
			if("mysql".equals(dbType)){
				sql += "where date_format(createdtime,'%Y')='"+value+"'";
			}
			if("sqlserver".equals(dbType)){
				sql += "where year(createdtime)='"+value+"'";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(createdtime,'yyyy')='"+value+"'";
			}
		}else{
			if("mysql".equals(dbType)){
				sql += "where date_format(createdtime,'%Y-%m')= '"+value+"-"+month+"' ";
			}
			if("sqlserver".equals(dbType)){
				sql += "where year(createdtime)='"+value+"' and month(createdtime)='"+month+"'";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(createdtime,'yyyy-MM')='"+value+"-"+month+"' ";
			}
		}
		return sql;
	}

	@Override
	public String monthSqlMessage(String year, String month, String staticsType,int i, String dbType) {
		String sql = "select count(id) count from cms_message ";
		if("sqlserver".equals(dbType)){
			sql += "where year(createdtime)='"+year+"' and month(createdtime)='"+i+"' ";
		}
		if(i<10){
			if("mysql".equals(dbType)){
				sql += "where date_format(createdtime,'%Y-%m')='"+year+"-0"+i+"' ";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(createdtime,'yyyy-MM')='"+year+"-0"+i+"' ";
			}
		}else{
			if("mysql".equals(dbType)){
				sql += "where date_format(createdtime,'%Y-%m')='"+year+"-"+i+"' ";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(createdtime,'yyyy-MM')='"+year+"-"+i+"' ";
			}
		}
		return sql;
	}
	
	@Override
	public String daySqlMessage(String year, String month, String staticsType, int i,String dbType) {
		String sql = "select count(id) count from cms_message ";
		if("sqlserver".equals(dbType)){
			sql += "where CONVERT(varchar(100), createdtime, 23)='"+year+"-"+month+"-"+i+"' ";
		}
		if(i<10){
			if("mysql".equals(dbType)){
				sql += "where date_format(createdtime,'%Y-%m-%d')= '"+year+"-"+month+"-0"+i+"' ";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(createdtime,'yyyy-MM-dd')='"+year+"-"+month+"-0"+i+"' ";
			}
		}else{
			if("mysql".equals(dbType)){
				sql += "where date_format(createdtime,'%Y-%m-%d')= '"+year+"-"+month+"-"+i+"' ";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(createdtime,'yyyy-MM-dd')='"+year+"-"+month+"-"+i+"' ";
			}
		}
		return sql;
	}

	
	
}