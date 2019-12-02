package com.leimingtech.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 调查选项
 * @author zhangdaihao
 * @date 2014-05-05 16:45:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_survey_option", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SurveyOptionEntity extends IdEntity implements java.io.Serializable {
	
	/**调查ID*/
	private java.lang.String surveyid;
	/**选项名称*/
	private java.lang.String optionname;
	/**数据类型*/
	private java.lang.String dataType;
	/**图片*/
	private java.lang.String picture;
	/**说明*/
	private java.lang.String interpretation;
	/**排序*/
	private java.lang.String optionsort;
	/**选项内容*/
	private java.lang.String optionContent;
	
	/**数据类型*/
	private java.lang.String dataTypeStr;
	
	/**创建时间*/
	private java.util.Date createdtime;
	
	/**是否必选*/
	private java.lang.Integer isCheck;
	
	/**选项内容*/
	private List<SurveyOptionExtEntity> optionextList=new ArrayList<SurveyOptionExtEntity>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "optionsid")
	public List<SurveyOptionExtEntity> getOptionextList() {
		return optionextList;
	}

	public void setOptionextList(List<SurveyOptionExtEntity> optionextList) {
		this.optionextList = optionextList;
	}


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  调查ID
	 */
	@Column(name ="SURVEYID",nullable=true,length=32)
	public java.lang.String getSurveyid(){
		return this.surveyid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  调查ID
	 */
	public void setSurveyid(java.lang.String surveyid){
		this.surveyid = surveyid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  选项名称
	 */
	@Column(name ="OPTIONNAME",nullable=true,length=255)
	public java.lang.String getOptionname(){
		return this.optionname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  选项名称
	 */
	public void setOptionname(java.lang.String optionname){
		this.optionname = optionname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数据类型
	 */
	@Column(name ="DATA_TYPE",nullable=true,length=255)
	public java.lang.String getDataType(){
		return this.dataType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数据类型
	 */
	public void setDataType(java.lang.String dataType){
		this.dataType = dataType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片
	 */
	@Column(name ="PICTURE",nullable=true,length=255)
	public java.lang.String getPicture(){
		return this.picture;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片
	 */
	public void setPicture(java.lang.String picture){
		this.picture = picture;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  说明
	 */
	@Column(name ="INTERPRETATION",nullable=true,length=255)
	public java.lang.String getInterpretation(){
		return this.interpretation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  说明
	 */
	public void setInterpretation(java.lang.String interpretation){
		this.interpretation = interpretation;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  排序
	 */
	@Column(name ="OPTIONSORT",nullable=true,length=255)
	public java.lang.String getOptionsort(){
		return this.optionsort;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  排序
	 */
	public void setOptionsort(java.lang.String optionsort){
		this.optionsort = optionsort;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  选项内容
	 */
	@Column(name ="OPTIONCONTENT",nullable=true,length=4000)
	public java.lang.String getOptionContent(){
		return this.optionContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  选项内容
	 */
	public void setOptionContent(java.lang.String optionContent){
		this.optionContent = optionContent;
	}

	@Transient
	public java.lang.String getDataTypeStr() {
		return dataTypeStr;
	}

	public void setDataTypeStr(java.lang.String dataTypeStr) {
		this.dataTypeStr = dataTypeStr;
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
	@Column(name ="ISCHECK",nullable=true,precision=10,scale=0)
	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer 是否必选
	 */
	public void setIsCheck(java.lang.Integer isCheck){
		this.isCheck = isCheck;
	}

	public java.lang.Integer getIsCheck() {
		return isCheck;
	}

	
}
