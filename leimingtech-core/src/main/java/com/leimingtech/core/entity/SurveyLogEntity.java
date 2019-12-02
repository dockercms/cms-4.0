package com.leimingtech.core.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 调查日志
 * @author 
 * @date 2014-06-11 09:12:26
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_survey_log", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SurveyLogEntity extends IdEntity implements java.io.Serializable {
	
	/**调查id*/
	private java.lang.String surveyid;
	/**投票时间*/
	private java.util.Date created;
	/**投票人*/
	private java.lang.String createdby;
	/**ip*/
	private java.lang.String ip;
	/**国家*/
	private java.lang.String country;
	/**省*/
	private java.lang.String province;
	/**市*/
	private java.lang.String city;
	/**区县*/
	private java.lang.String county;
	/**选项结果*/
	private List<SurveyLogDataEntity> surveyLogDataList;
	

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "logid")
	public List<SurveyLogDataEntity> getSurveyLogDataList() {
		return surveyLogDataList;
	}

	public void setSurveyLogDataList(List<SurveyLogDataEntity> surveyLogDataList) {
		this.surveyLogDataList = surveyLogDataList;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  调查id
	 */
	@Column(name ="SURVEYID",nullable=true,precision=10,scale=0)
	public java.lang.String getSurveyid(){
		return this.surveyid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  调查id
	 */
	public void setSurveyid(java.lang.String surveyid){
		this.surveyid = surveyid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  投票时间
	 */
	@Column(name ="CREATED",nullable=true)
	public java.util.Date getCreated(){
		return this.created;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  投票时间
	 */
	public void setCreated(java.util.Date created){
		this.created = created;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  投票人
	 */
	@Column(name ="CREATEDBY",nullable=true,length=255)
	public java.lang.String getCreatedby(){
		return this.createdby;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  投票人
	 */
	public void setCreatedby(java.lang.String createdby){
		this.createdby = createdby;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ip
	 */
	@Column(name ="IP",nullable=true,length=15)
	public java.lang.String getIp(){
		return this.ip;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  ip
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
	/**
	 *方法: 取得java.lang.Date
	 *@return: java.lang.Date  创建时间
	 */
	
}
