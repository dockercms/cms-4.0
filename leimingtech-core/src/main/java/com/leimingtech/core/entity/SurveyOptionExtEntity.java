package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 调查选项内容
 * @author zhangdaihao
 * @date 2014-05-05 17:45:52
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_survey_option_ext", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SurveyOptionExtEntity extends IdEntity implements java.io.Serializable {
	
	/**选项ID*/
	private java.lang.String optionsid;
	/**选项名称*/
	private java.lang.String extOptionname;
	/**选项图片*/
	private java.lang.String extOptionpicture;
	/**数据类型*/
	private java.lang.String extDatatype;
	/**排序*/
	private java.lang.String extSort;
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
	 *@return: java.lang.String  选项ID
	 */
	@Column(name ="OPTIONSID",nullable=true,length=255)
	public java.lang.String getOptionsid(){
		return this.optionsid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  选项ID
	 */
	public void setOptionsid(java.lang.String optionsid){
		this.optionsid = optionsid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  选项名称
	 */
	@Column(name ="EXT_OPTIONNAME",nullable=true,length=255)
	public java.lang.String getExtOptionname(){
		return this.extOptionname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  选项名称
	 */
	public void setExtOptionname(java.lang.String extOptionname){
		this.extOptionname = extOptionname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  选项图片
	 */
	@Column(name ="EXT_OPTIONPICTURE",nullable=true,length=255)
	public java.lang.String getExtOptionpicture(){
		return this.extOptionpicture;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  选项图片
	 */
	public void setExtOptionpicture(java.lang.String extOptionpicture){
		this.extOptionpicture = extOptionpicture;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数据类型
	 */
	@Column(name ="EXT_DATATYPE",nullable=true,length=255)
	public java.lang.String getExtDatatype(){
		return this.extDatatype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数据类型
	 */
	public void setExtDatatype(java.lang.String extDatatype){
		this.extDatatype = extDatatype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  排序
	 */
	@Column(name ="EXT_SORT",nullable=true,length=255)
	public java.lang.String getExtSort(){
		return this.extSort;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  排序
	 */
	public void setExtSort(java.lang.String extSort){
		this.extSort = extSort;
	}
}
