package com.leimingtech.core.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间操作工具
 * 
 * @company 雷铭智信
 * @author 谢进伟
 * @DateTime 2015-4-15 下午12:46:56
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 时间格式化
	 * 
	 * @param date
	 *            需要格式化的时间对象
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static String defaultFormat(Date date) {
		try {
			String pattern = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 时间格式化
	 * 
	 * @param date
	 *            需要格式化的时间对象
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static String format(Date date , String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @return 获取当前月第一天
	 */
	public static String getFirstMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH , 0);
		c.set(Calendar.DAY_OF_MONTH , 1);// 设置为1号,当前日期既为本月第一天
		String first = format.format(c.getTime());
		return first;
	}
	
	/**
	 * @return 获取当前月最后一天
	 */
	public static String getEndMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH , ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		return last;
	}
	
	/**
	 * 根据开始日期 ，需要的工作日天数 ，计算工作截止日期，并返回截止日期
	 * 
	 * @param startDate
	 *            开始日期
	 * @param workDay
	 *            工作日天数
	 * @return Date类型
	 * @createTime 2014-2-14
	 * @author larry
	 * @throws ParseException
	 */
	public static String getWorkDay(String dstr , String workDay) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date startDate = sdf.parse(dstr);
			Calendar c1 = Calendar.getInstance();
			c1.setTime(startDate);
			c1.set(Calendar.DATE , c1.get(Calendar.DATE) + Integer.valueOf(workDay));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.format(c1.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 解析时间系统参数参数
	 * 
	 * @param timeStr
	 *            订单超时取消时长(超过该时长订单自动取消),单位：h->小时,m->分钟,s->秒。如：12h表示12小时、12
	 *            h3m4s表示12小时3分钟4秒,以此类推即可
	 * @return 解析后的总毫秒数
	 */
	public static long getMillisecond(String timeStr) {
		String hour = DateUtils.getBeforeIntegerValue(timeStr , 'h');
		String minute = DateUtils.getBeforeIntegerValue(timeStr , 'm');
		String second = DateUtils.getBeforeIntegerValue(timeStr , 's');
		long time = Integer.parseInt(hour) * 60 * 60 * 1000L + Integer.parseInt(minute) * 60 * 1000L + Integer.parseInt(second) * 1000L;
		return time;
	}
	
	/**
	 * 获取某一个字符串中指定字符之前的数字
	 * 
	 * @param str
	 *            需要检测的字符串
	 * @param c
	 *            字符
	 * @return
	 */
	public static String getBeforeIntegerValue(String str , char c) {
		StringBuffer resultStr = new StringBuffer();
		int indexOf = str.indexOf(c);
		if(indexOf > -1) {
			String substring = str.substring(0 , indexOf);
			for(int i = indexOf - 1 ; i >= 0 ; i--) {
				if((substring.charAt(i) + "").matches("^\\d$")) {
					resultStr.append(substring.charAt(i));
				} else {
					break;
				}
			}
		} else {
			resultStr.append("0");
		}
		return resultStr.reverse().toString();
	}
}
