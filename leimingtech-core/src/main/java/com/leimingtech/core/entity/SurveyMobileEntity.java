package com.leimingtech.core.entity;

import java.util.ArrayList;
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
 * @Description: 调查
 * @author zhangdaihao
 * @date 2014-05-05 12:01:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cm_survey", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SurveyMobileEntity extends IdEntity implements java.io.Serializable {
	 
	/**内容ID*/
	private java.lang.String contentid;
	/**自定义模板*/
	private java.lang.String custom;
	/**页面背景图*/
	private java.lang.String pagebackground;
	/**接收人*/
	private java.lang.String recipient;
	/**开始时间*/
	private java.util.Date surveystarttime;
	/**结束时间*/
	private java.util.Date surveyendtime;
	/**人数限制*/
	private java.lang.String surveypeolimit;
	/**IP限制*/
	private java.lang.String surveyiplimit;
	/**是否会员*/
	private java.lang.String isvip;
	/**站点id*/
	private java.lang.String siteid;
	/**调查PCID*/
	private java.lang.String surveyid;
	
	/** 调查选项 */
	private List<SurveyOptionEntity> optionList = new ArrayList<SurveyOptionEntity>();
	

	/**创建时间*/
	private java.util.Date createdtime;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "surveyid")
	public List<SurveyOptionEntity> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<SurveyOptionEntity> optionList) {
		this.optionList = optionList;
	}

	 
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容ID
	 */
	@Column(name ="CONTENTID",nullable=true,precision=7,scale=0)
	public java.lang.String getContentid(){
		return this.contentid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容ID
	 */
	public void setContentid(java.lang.String contentid){
		this.contentid = contentid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  自定义模板
	 */
	@Column(name ="CUSTOM",nullable=true,length=255)
	public java.lang.String getCustom(){
		return this.custom;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  自定义模板
	 */
	public void setCustom(java.lang.String custom){
		this.custom = custom;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  页面背景图
	 */
	@Column(name ="PAGEBACKGROUND",nullable=true,length=1000)
	public java.lang.String getPagebackground(){
		return this.pagebackground;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  页面背景图
	 */
	public void setPagebackground(java.lang.String pagebackground){
		this.pagebackground = pagebackground;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  接收人
	 */
	@Column(name ="RECIPIENT",nullable=true,length=255)
	public java.lang.String getRecipient(){
		return this.recipient;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接收人
	 */
	public void setRecipient(java.lang.String recipient){
		this.recipient = recipient;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  开始时间
	 */
	@Column(name ="SURVEYSTARTTIME",nullable=true)
	public java.util.Date getSurveystarttime(){
		return this.surveystarttime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  开始时间
	 */
	public void setSurveystarttime(java.util.Date surveystarttime){
		this.surveystarttime = surveystarttime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结束时间
	 */
	@Column(name ="SURVEYENDTIME",nullable=true)
	public java.util.Date getSurveyendtime(){
		return this.surveyendtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  结束时间
	 */
	public void setSurveyendtime(java.util.Date surveyendtime){
		this.surveyendtime = surveyendtime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  人数限制
	 */
	@Column(name ="SURVEYPEOLIMIT",nullable=true,length=255)
	public java.lang.String getSurveypeolimit(){
		return this.surveypeolimit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  人数限制
	 */
	public void setSurveypeolimit(java.lang.String surveypeolimit){
		this.surveypeolimit = surveypeolimit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  IP限制
	 */
	@Column(name ="SURVEYIPLIMIT",nullable=true,length=255)
	public java.lang.String getSurveyiplimit(){
		return this.surveyiplimit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  IP限制
	 */
	public void setSurveyiplimit(java.lang.String surveyiplimit){
		this.surveyiplimit = surveyiplimit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否会员
	 */
	@Column(name ="ISVIP",nullable=true,length=255)
	public java.lang.String getIsvip(){
		return this.isvip;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否会员
	 */
	public void setIsvip(java.lang.String isvip){
		this.isvip = isvip;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  站点id
	 */
	@Column(name ="SITE_ID",nullable=true,precision=32,scale=0)
	public java.lang.String getSiteid() {
		return siteid;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点id
	 */
	public void setSiteid(java.lang.String siteid) {
		this.siteid = siteid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  调查PCID
	 */
	@Column(name ="SURVEYID",nullable=true,precision=11,scale=0)
	public java.lang.String getSurveyid(){
		return this.surveyid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  调查PCID
	 */
	public void setSurveyid(java.lang.String surveyid){
		this.surveyid = surveyid;
	}
	/**
	 *方法: 取得java.lang.Date
	 *@return: java.lang.Date  创建时间
	 */
	@Column(name = "CREATEDTIME", nullable = true)
	public java.util.Date getCreatedtime() {
		return createdtime;
	}
	/**
	 *方法: 设置java.lang.Date
	 *@return: java.lang.Date  创建时间
	 */
	public void setCreatedtime(java.util.Date createdtime) {
		this.createdtime = createdtime;
	}
}
