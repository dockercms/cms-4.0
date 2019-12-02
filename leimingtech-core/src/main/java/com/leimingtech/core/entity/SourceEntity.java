package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 来源管理
 * @author 
 * @date 2014-05-17 12:28:28
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_source", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SourceEntity extends IdEntity implements java.io.Serializable {
	
	/**源来名称*/
	private java.lang.String name;
	/**logo*/
	private java.lang.String logo;
	/**址地*/
	private java.lang.String url;
	/**不详*/
	private java.lang.String initials;
	/**载量装*/
	private java.lang.Integer count;
	/**排序值*/
	private java.lang.Integer sort;
	/**站点id*/
	private java.lang.String siteId;
	/**英文名称*/
	private java.lang.String enname;
	/**中文全拼*/
	private java.lang.String cnname;
	/**中文简拼*/
	private java.lang.String simplename;
	/**创建时间*/
	private java.util.Date createdtime;
	
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源来名称
	 */
	@Column(name ="NAME",nullable=true,length=40)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源来名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  logo
	 */
	@Column(name ="LOGO",nullable=true,length=100)
	public java.lang.String getLogo(){
		return this.logo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  logo
	 */
	public void setLogo(java.lang.String logo){
		this.logo = logo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  址地
	 */
	@Column(name ="URL",nullable=true,length=100)
	public java.lang.String getUrl(){
		return this.url;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  址地
	 */
	public void setUrl(java.lang.String url){
		this.url = url;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  不详
	 */
	@Column(name ="INITIALS",nullable=true,length=10)
	public java.lang.String getInitials(){
		return this.initials;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  不详
	 */
	public void setInitials(java.lang.String initials){
		this.initials = initials;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  载量装
	 */
	@Column(name ="COUNT",nullable=true,precision=7,scale=0)
	public java.lang.Integer getCount(){
		return this.count;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  载量装
	 */
	public void setCount(java.lang.Integer count){
		this.count = count;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序值
	 */
	@Column(name ="SORT",nullable=true,precision=10,scale=0)
	public java.lang.Integer getSort(){
		return this.sort;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序值
	 */
	public void setSort(java.lang.Integer sort){
		this.sort = sort;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  站点id
	 */
	@Column(name ="SITE_ID",nullable=true,length=32)
	public java.lang.String getSiteId(){
		return this.siteId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点id
	 */
	public void setSiteId(java.lang.String siteId){
		this.siteId = siteId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  英文名称
	 */
	@Column(name ="ENNAME",nullable=true,length=32)
	public java.lang.String getEnname(){
		return this.enname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  英文名称
	 */
	public void setEnname(java.lang.String enname){
		this.enname = enname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  中文全拼
	 */
	@Column(name ="CNNAME",nullable=true,length=32)
	public java.lang.String getCnname(){
		return this.cnname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  中文全拼
	 */
	public void setCnname(java.lang.String cnname){
		this.cnname = cnname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  中文简拼
	 */
	@Column(name ="SIMPLENAME",nullable=true,length=32)
	public java.lang.String getSimplename(){
		return this.simplename;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  中文简拼
	 */
	public void setSimplename(java.lang.String simplename){
		this.simplename = simplename;
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
