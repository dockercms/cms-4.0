package com.leimingtech.cms.service.impl.comments;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.comments.CommentsServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;

@Service("commentService")
@Transactional
public class CommentsServiceImpl extends CommonServiceImpl implements CommentsServiceI {

	@Override
	public String yearSqlComments(int value, String month, String staticsType,String dbType) {
		String sql = "select count(id) count from cms_commentary ";
		if("0".equals(month)){
			if("mysql".equals(dbType)){
				sql += "where date_format(commentaryTime,'%Y')= '"+value+"'";
			}
			if("sqlserver".equals(dbType)){
				sql += "where year(commentaryTime)='"+value+"'";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(commentaryTime,'yyyy')='"+value+"'";
			}
		}else{
			if("mysql".equals(dbType)){
				sql += "where date_format(commentaryTime,'%Y-%m')= '"+value+"-"+month+"' ";
			}
			if("sqlserver".equals(dbType)){
				sql += "where year(commentaryTime)='"+value+"' and month(commentaryTime)='"+month+"'";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(commentaryTime,'yyyy-MM')='"+value+"-"+month+"' ";
			}
		}
		return sql;
	}

	@Override
	public String monthSqlComments(String year, String month,String staticsType, int i, String dbType) {
		String sql = "select count(id) count from cms_commentary ";
		if("sqlserver".equals(dbType)){
			sql += "where year(commentaryTime)='"+year+"' and month(commentaryTime)='"+i+"' ";
		}
		if(i<10){
			sql = "select count(id) count from t_s_commentary ";
			if("mysql".equals(dbType)){
				sql += "where date_format(commentaryTime,'%Y-%m')='"+year+"-0"+i+"' ";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(commentaryTime,'yyyy-MM')='"+year+"-0"+i+"' ";
			}
		}else{
			if("mysql".equals(dbType)){
				sql += "where date_format(commentaryTime,'%Y-%m')='"+year+"-"+i+"' ";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(commentaryTime,'yyyy-MM')='"+year+"-"+i+"' ";
			}
		}
		return sql;
	}

	@Override
	public String daySqlComments(String year, String month, String staticsType,int i, String dbType) {
		String sql = "select count(id) count from cms_commentary ";
		if("sqlserver".equals(dbType)){
			sql += "where CONVERT(varchar(100), commentaryTime, 23)='"+year+"-"+month+"-"+i+"' ";
		}
		if(i<10){
			sql = "select count(id) count from cms_commentary ";
			if("mysql".equals(dbType)){
				sql += "where date_format(commentaryTime,'%Y-%m-%d')= '"+year+"-"+month+"-0"+i+"' ";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(commentaryTime,'yyyy-MM-dd')='"+year+"-"+month+"-0"+i+"' ";
			}
		}else{
			if("mysql".equals(dbType)){
				sql += "where date_format(commentaryTime,'%Y-%m-%d')= '"+year+"-"+month+"-"+i+"' ";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(commentaryTime,'yyyy-MM-dd')='"+year+"-"+month+"-"+i+"' ";
			}
		}
		return sql;
	}
	
}