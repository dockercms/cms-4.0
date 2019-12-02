package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 链接
 * @author zhangdaihao
 * @date 2014-04-28 14:06:30
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_link", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ContentLinkEntity extends IdEntity implements java.io.Serializable {

	/**内容ID*/
	private java.lang.String contentid;
	/**链接名称*/
	private java.lang.String linkname;
	/**链接url*/
	private java.lang.String linkurl;
	/**备注*/
	private java.lang.String linkremark;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容ID
	 */
	@Column(name ="CONTENTID",nullable=true,length=32)
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
	 *@return: java.lang.String  链接名称
	 */
	@Column(name ="LINKNAME",nullable=true,length=255)
	public java.lang.String getLinkname(){
		return this.linkname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  链接名称
	 */
	public void setLinkname(java.lang.String linkname){
		this.linkname = linkname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  链接url
	 */
	@Column(name ="LINKURL",nullable=true,length=4000)
	public java.lang.String getLinkurl(){
		return this.linkurl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  链接url
	 */
	public void setLinkurl(java.lang.String linkurl){
		this.linkurl = linkurl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="LINKREMARK",nullable=true,length=1000)
	public java.lang.String getLinkremark(){
		return this.linkremark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setLinkremark(java.lang.String linkremark){
		this.linkremark = linkremark;
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
