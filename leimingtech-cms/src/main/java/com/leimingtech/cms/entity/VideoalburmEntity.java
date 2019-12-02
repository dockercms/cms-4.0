package com.leimingtech.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 视频专辑
 * @author 
 * @date 2014-07-09 18:45:34
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_video_alburm", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class VideoalburmEntity extends IdEntity implements java.io.Serializable {
	
	/**专辑名称*/
	private java.lang.String name;
	/**排序方式，1-正序，2-倒序*/
	private java.lang.String sorttype;
	/**创建时间*/
	private java.util.Date createdate;
	/**站点id*/
	private java.lang.String siteId;
	/**关联文章---未使用，废弃*/
	private java.lang.String relatearticle;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专辑名称
	 */
	@Column(name ="NAME",nullable=true,length=225)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专辑名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  排序方式
	 */
	@Column(name ="SORTTYPE",nullable=true,length=225)
	public java.lang.String getSorttype(){
		return this.sorttype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  排序方式
	 */
	public void setSorttype(java.lang.String sorttype){
		this.sorttype = sorttype;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATEDATE",nullable=true)
	public java.util.Date getCreatedate(){
		return this.createdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreatedate(java.util.Date createdate){
		this.createdate = createdate;
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
	 *@return: java.lang.String  关联文章的ID
	 */
	@Column(name ="RELATEARTICLE",nullable=true,length=500)
	public java.lang.String getRelatearticle() {
		return relatearticle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联文章的ID
	 */
	public void setRelatearticle(java.lang.String relatearticle) {
		this.relatearticle = relatearticle;
	}
	
	
}
