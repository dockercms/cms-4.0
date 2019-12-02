package com.leimingtech.cms.entity.friendlink;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 友情链接
 * @author zhangdaihao
 * @date 2014-04-18 13:34:56
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_friendlink", schema = "")
@SuppressWarnings("serial")
public class FriendLinkEntity extends IdEntity implements java.io.Serializable {

	/**点站id*/
	private java.lang.String siteId;
	/**网站名称*/
	private java.lang.String siteName;
	/**网站地址*/
	private java.lang.String domain;
	/**图标*/
	private java.lang.String logo;
	/**站长邮箱*/
	private java.lang.String siteEmail;
	/**描述*/
	private java.lang.String description;
	/**点击次数*/
	private java.lang.Integer views;
	/**是否显示*/
	private java.lang.String isEnabled;
	/**排列顺序*/
	private java.lang.Integer priority;
	
	/**接链类别*/
	private FriendLinkCtgEntity friendLinkCtg;
	/**创建时间*/
	private java.util.Date createdtime;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GID")
	public FriendLinkCtgEntity getFriendLinkCtg() {
		return friendLinkCtg;
	}

	public void setFriendLinkCtg(FriendLinkCtgEntity friendLinkCtg) {
		this.friendLinkCtg = friendLinkCtg;
	}
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  点站id
	 */
	@Column(name ="SITE_ID",nullable=false,length=32)
	public java.lang.String getSiteId(){
		return this.siteId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  点站id
	 */
	public void setSiteId(java.lang.String siteId){
		this.siteId = siteId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  网站名称
	 */
	@Column(name ="SITE_NAME",nullable=false,length=150)
	public java.lang.String getSiteName(){
		return this.siteName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  网站名称
	 */
	public void setSiteName(java.lang.String siteName){
		this.siteName = siteName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  网站地址
	 */
	@Column(name ="DOMAIN",nullable=false,length=255)
	public java.lang.String getDomain(){
		return this.domain;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  网站地址
	 */
	public void setDomain(java.lang.String domain){
		this.domain = domain;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图标
	 */
	@Column(name ="LOGO",nullable=true,length=150)
	public java.lang.String getLogo(){
		return this.logo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图标
	 */
	public void setLogo(java.lang.String logo){
		this.logo = logo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  站长邮箱
	 */
	@Column(name ="SITE_EMAIL",nullable=true,length=100)
	public java.lang.String getSiteEmail(){
		return this.siteEmail;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站长邮箱
	 */
	public void setSiteEmail(java.lang.String siteEmail){
		this.siteEmail = siteEmail;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  描述
	 */
	@Column(name ="DESCRIPTION",nullable=true,length=255)
	public java.lang.String getDescription(){
		return this.description;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  描述
	 */
	public void setDescription(java.lang.String description){
		this.description = description;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  点击次数
	 */
	@Column(name ="VIEWS",nullable=false,precision=10,scale=0)
	public java.lang.Integer getViews(){
		return this.views;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  点击次数
	 */
	public void setViews(java.lang.Integer views){
		this.views = views;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否显示
	 */
	@Column(name ="IS_ENABLED",nullable=false,length=1)
	public java.lang.String getIsEnabled(){
		return this.isEnabled;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否显示
	 */
	public void setIsEnabled(java.lang.String isEnabled){
		this.isEnabled = isEnabled;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排列顺序
	 */
	@Column(name ="PRIORITY",nullable=false,precision=10,scale=0)
	public java.lang.Integer getPriority(){
		return this.priority;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排列顺序
	 */
	public void setPriority(java.lang.Integer priority){
		this.priority = priority;
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
