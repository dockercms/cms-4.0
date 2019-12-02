package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: Tag标签管理
 * @author 
 * @date 2014-05-17 12:03:08
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_content_tag", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ContentTagEntity extends IdEntity implements java.io.Serializable {
	
	/**tag名称*/
	private java.lang.String tagName;
	/**被引用的次数*/
	private java.lang.Integer refCounter;
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
	 *@return: java.lang.String  tag名称
	 */
	@Column(name ="TAG_NAME",nullable=true,length=50)
	public java.lang.String getTagName(){
		return this.tagName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  tag名称
	 */
	public void setTagName(java.lang.String tagName){
		this.tagName = tagName;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  被引用的次数
	 */
	@Column(name ="REF_COUNTER",nullable=true,precision=10,scale=0)
	public java.lang.Integer getRefCounter(){
		return this.refCounter;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  被引用的次数
	 */
	public void setRefCounter(java.lang.Integer refCounter){
		this.refCounter = refCounter;
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
}
