package com.leimingtech.cms.service.impl.statistics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.comments.CommentsServiceI;
import com.leimingtech.cms.service.statistics.StatisticsServiceI;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.MessagesServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.core.util.DBTypeUtil;
@Service("statisticsService")
@Transactional
public class StatisticsServiceImpl extends CommonServiceImpl implements StatisticsServiceI{

	@Autowired
	private MessagesServiceI messageService;
	@Autowired
	private CommentsServiceI commentService;
	@Autowired
	private MemberServiceI memberService;
	/**
	 * 所有统计数据
	 * @param searchYear
	 * @param searchMonth
	 * @param staticsType
	 * @return 
	 */
	@Override
	public JSONArray statisticsData(String searchYear, String searchMonth,String staticsType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = searchYear;
		String month = searchMonth;
		if(StringUtils.isEmpty(searchYear)){
			year = sdf.format(new Date());
		}
		//年统计
		JSONArray jsonArray = yearStat(year,month,staticsType);
		return jsonArray;
	}

	/**
	 * 按年、月统计
	 * @param year
	 * @param month
	 * @param staticsType
	 * @return
	 */
	public JSONArray yearStat(String year,String month,String staticsType){
		JSONArray jsonArray = new JSONArray();
		//获取选中月的天数
		int days = 0;
		//数据库
		String dbType = DBTypeUtil.getDBType();
		if(!month.equals("0")){
			String date = year+"/"+month;
			Calendar rightNow = Calendar.getInstance();
			SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM"); 
			try {
				rightNow.setTime(simpleDate.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		if(Integer.valueOf(month)<10&&Integer.valueOf(month)!=0){
			month = "0"+month;
		}
		//选中年
		if(!year.equals("0")){
			//按月进行查询，显示天数
			jsonArray = this.days(days, year, month,staticsType,dbType);
		//全部年
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			int yearV = Integer.valueOf(sdf.format(new Date()));
			for(int i=0;i<10;i++){
				JSONObject jsonObject = new JSONObject();
				int value = yearV-i;
				String sql = yearSql(value,month,staticsType,dbType);
				List<Map<String,Object>> mapList1 = this.findForJdbc(sql);
				if(mapList1.size()!=0){
					jsonObject.put("count", mapList1.get(0).get("count"));
					jsonObject.put("date", value);
					jsonArray.add(jsonObject);
				}else{
					jsonObject.put("count", 0);
					jsonObject.put("date", value);
					jsonArray.add(jsonObject);
				}
			}
		}
		return jsonArray;
	}
	/**
	 * 会员、评论、留言年sql
	 * @param value
	 * @param month
	 * @param staticsType
	 * @return
	 */
	public String yearSql(int value,String month,String staticsType,String dbType){
		String sql = "";
		//会员统计
		if(staticsType.equals("member")){
			sql = memberService.yearSqlMember(value, month, staticsType, dbType);
		}
		//评论统计
		if(staticsType.equals("comment")){
			sql = commentService.yearSqlComments(value, month, staticsType, dbType);
		}
		//留言统计
		if(staticsType.equals("messages")){
			sql = messageService.yearSqlMessage(value, month, staticsType, dbType);
		}
		return sql;
	}
	/**
	 * 按月进行查询，显示天数
	 * @param days
	 * @param year
	 * @param month
	 * @param staticsType
	 * @return
	 */
	public JSONArray days(int days,String year,String month,String staticsType,String dbType) {
		JSONArray jsonArray = new JSONArray();
		if(!month.equals("0")){
			for(int i=1;i<days+1;i++){
				JSONObject jsonObject = new JSONObject();
				String sql = daySql(year,month,staticsType,i,dbType);
				List<Map<String,Object>> mapList1 = this.findForJdbc(sql);
				if(mapList1.size()!=0){
					jsonObject.put("count", mapList1.get(0).get("count"));
					jsonObject.put("date", i);
					jsonArray.add(jsonObject);
				}else{
					jsonObject.put("count", 0);
					jsonObject.put("date", i);
					jsonArray.add(jsonObject);
				}
			}
		}else{//按年查询，显示所有月
			for(int i=1; i<13; i++){
				JSONObject jsonObject = new JSONObject();
				String sql = monthSql(year,month,staticsType,i,dbType);
				List<Map<String,Object>> mapList1 = this.findForJdbc(sql);
				if(mapList1.size()!=0){
					jsonObject.put("count", mapList1.get(0).get("count"));
					jsonObject.put("date", i);
					jsonArray.add(jsonObject);
				}else{
					jsonObject.put("count", 0);
					jsonObject.put("date", i);
					jsonArray.add(jsonObject);
				}
				
			}
		}
		return jsonArray;
	}
	/**
	 * 会员、评论、留言天sql
	 * @param year
	 * @param month
	 * @param staticsType
	 * @param i
	 * @return
	 */
	public String daySql(String year,String month,String staticsType,int i,String dbType){
		String sql = "";
		if(staticsType.equals("member")){
			sql = memberService.daySqlMember(year, month, staticsType, i, dbType);
		}
		if(staticsType.equals("comment")){
			sql = commentService.daySqlComments(year, month, staticsType, i, dbType);
		}
		if(staticsType.equals("messages")){
			sql = messageService.daySqlMessage(year, month, staticsType, i, dbType);
		}
		return sql;
	}
	/**
	 * 会员、评论、留言月sql
	 * @param year
	 * @param month
	 * @param staticsType
	 * @param i
	 * @return
	 */
	public String monthSql(String year,String month,String staticsType,int i,String dbType){
		String sql = "";
		if(staticsType.equals("member")){
			sql = memberService.monthSqlMember(year, month, staticsType, i, dbType);
		}
		if(staticsType.equals("comment")){
			sql = commentService.monthSqlComments(year, month, staticsType, i, dbType);
		}
		if(staticsType.equals("messages")){
			sql = messageService.monthSqlMessage(year, month, staticsType, i, dbType);
		}
		return sql;
	}
}
