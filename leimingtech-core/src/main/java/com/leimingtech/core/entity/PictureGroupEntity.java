package com.leimingtech.core.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 组图
 * @author zhangdaihao
 * @date 2014-04-23 16:13:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_picture_group", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class PictureGroupEntity extends IdEntity implements java.io.Serializable {
	
	/**内容ID*/
	private String contentid;
	/**图片名*/
	private java.lang.String image;
	/**备注*/
	private java.lang.String remark;
	/**url*/
	private java.lang.String url;
	/**url*/
	private java.lang.String urlThumb;
	/**排序*/
	private java.lang.Integer sort;
	/**站点id*/
	private java.lang.String siteid;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  内容ID
	 */
	@Column(name ="CONTENTID",nullable=true,length=32)
	public java.lang.String getContentid(){
		return this.contentid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  内容ID
	 */
	public void setContentid(java.lang.String contentid){
		this.contentid = contentid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片名
	 */
	@Column(name ="IMAGE",nullable=true,length=255)
	public java.lang.String getImage(){
		return this.image;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片名
	 */
	public void setImage(java.lang.String image){
		this.image = image;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=255)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  url
	 */
	@Column(name ="URL",nullable=true)
	public java.lang.String getUrl(){
		return this.url;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  url
	 */
	public void setUrl(java.lang.String url){
		this.url = url;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  urlThumb
	 */
	@Column(name ="URL_THUMB",nullable=true,length=255)
	public java.lang.String getUrlThumb(){
		return this.urlThumb;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  urlThumb
	 */
	public void setUrlThumb(java.lang.String urlThumb){
		this.urlThumb = urlThumb;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序
	 */
	@Column(name ="SORT",nullable=true,precision=3,scale=0)
	public java.lang.Integer getSort(){
		return this.sort;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序
	 */
	public void setSort(java.lang.Integer sort){
		this.sort = sort;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  站点id
	 */
	@Column(name ="SITE_ID",nullable=true,length=32)
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
}
