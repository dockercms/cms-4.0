package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 文章附件表
 * @author 
 * @date 2014-08-20 09:58:36
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_content_accessory", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ContentAccessoryEntity extends IdEntity implements java.io.Serializable {
	
	/**内容id*/
	private java.lang.String contentId;
	/**附件名称*/
	private java.lang.String accessoryName;
	/**附件地址*/
	private java.lang.String accessoryUrl;
	/**附件分类*/
	private java.lang.String accessoryType;
	/**附件备注*/
	private java.lang.String accessoryRemark;
	/**附件下载量*/
	private java.lang.Integer accessoryDownload;
	/**站点id*/
	private java.lang.String siteId;
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
	 *@return: java.lang.String  内容id
	 */
	@Column(name ="CONTENT_ID",nullable=true,length=32)
	public java.lang.String getContentId(){
		return this.contentId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容id
	 */
	public void setContentId(java.lang.String contentId){
		this.contentId = contentId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附件名称
	 */
	@Column(name ="ACCESSORY_NAME",nullable=true,length=255)
	public java.lang.String getAccessoryName(){
		return this.accessoryName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附件名称
	 */
	public void setAccessoryName(java.lang.String accessoryName){
		this.accessoryName = accessoryName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附件地址
	 */
	@Column(name ="ACCESSORY_URL",nullable=true,length=255)
	public java.lang.String getAccessoryUrl(){
		return this.accessoryUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附件地址
	 */
	public void setAccessoryUrl(java.lang.String accessoryUrl){
		this.accessoryUrl = accessoryUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附件分类
	 */
	@Column(name ="ACCESSORY_TYPE",nullable=true,length=255)
	public java.lang.String getAccessoryType(){
		return this.accessoryType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附件分类
	 */
	public void setAccessoryType(java.lang.String accessoryType){
		this.accessoryType = accessoryType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附件备注
	 */
	@Column(name ="ACCESSORY_REMARK",nullable=true,length=255)
	public java.lang.String getAccessoryRemark(){
		return this.accessoryRemark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附件备注
	 */
	public void setAccessoryRemark(java.lang.String accessoryRemark){
		this.accessoryRemark = accessoryRemark;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  附件下载量
	 */
	@Column(name ="ACCESSORY_DOWNLOAD",nullable=true,precision=10,scale=0)
	public java.lang.Integer getAccessoryDownload(){
		return this.accessoryDownload;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  附件下载量
	 */
	public void setAccessoryDownload(java.lang.Integer accessoryDownload){
		this.accessoryDownload = accessoryDownload;
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
}
