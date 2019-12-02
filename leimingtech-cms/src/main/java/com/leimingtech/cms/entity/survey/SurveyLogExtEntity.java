package com.leimingtech.cms.entity.survey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 调查日志选项文本
 * @author 
 * @date 2014-06-16 15:28:18
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_survey_log_ext", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SurveyLogExtEntity extends IdEntity implements java.io.Serializable {
	
	/**投票日志id*/
	private String logid;
	/**选项*/
	private java.lang.String optionid;
	/**文本内容*/
	private java.lang.String ext;
	/**ip地址*/
	private java.lang.String ip;
	/**国家*/
	private java.lang.String country;
	/**省*/
	private java.lang.String province;
	/**市*/
	private java.lang.String city;
	/**区县*/
	private java.lang.String county;
	/**创建时间*/
	private java.util.Date createdtime;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建时间
	 */
	@Column(name = "CREATEDTIME", nullable = true)
	public java.util.Date getCreatedtime() {
		return createdtime;
	}
	/**
	 *方法: 设置java.lang.String
	 *@return: java.lang.String  创建时间
	 */
	public void setCreatedtime(java.util.Date createdtime) {
		this.createdtime = createdtime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  投票日志id
	 */
	@Column(name ="LOGID",nullable=true,length=32)
	public java.lang.String getLogid(){
		return this.logid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  投票日志id
	 */
	public void setLogid(java.lang.String logid){
		this.logid = logid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  选项
	 */
	@Column(name ="OPTIONID",nullable=true,length=32)
	public java.lang.String getOptionid(){
		return this.optionid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  选项
	 */
	public void setOptionid(java.lang.String optionid){
		this.optionid = optionid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文本内容
	 */
	@Column(name ="EXT",nullable=true,length=65535)
	public java.lang.String getExt(){
		return this.ext;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文本内容
	 */
	public void setExt(java.lang.String ext){
		this.ext = ext;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ip地址
	 */
	@Column(name ="IP",nullable=true,length=15)
	public java.lang.String getIp(){
		return this.ip;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  ip地址
	 */
	public void setIp(java.lang.String ip){
		this.ip = ip;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  国家
	 */
	@Column(name ="COUNTRY",nullable=true,length=255)
	public java.lang.String getCountry(){
		return this.country;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  国家
	 */
	public void setCountry(java.lang.String country){
		this.country = country;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  省
	 */
	@Column(name ="PROVINCE",nullable=true,length=255)
	public java.lang.String getProvince(){
		return this.province;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  省
	 */
	public void setProvince(java.lang.String province){
		this.province = province;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  市
	 */
	@Column(name ="CITY",nullable=true,length=255)
	public java.lang.String getCity(){
		return this.city;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  市
	 */
	public void setCity(java.lang.String city){
		this.city = city;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  区县
	 */
	@Column(name ="COUNTY",nullable=true,length=255)
	public java.lang.String getCounty(){
		return this.county;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  区县
	 */
	public void setCounty(java.lang.String county){
		this.county = county;
	}
}
