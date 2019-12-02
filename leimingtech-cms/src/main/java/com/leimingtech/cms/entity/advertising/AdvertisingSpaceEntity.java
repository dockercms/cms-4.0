package com.leimingtech.cms.entity.advertising;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 广告栏位
 * @author zhangdaihao
 * @date 2014-04-16 17:27:52
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_advertising_space", schema = "")
@SuppressWarnings("serial")
public class AdvertisingSpaceEntity extends IdEntity implements java.io.Serializable {
	
	/**属所网站id*/
	private java.lang.String siteId;
	/**名称*/
	private java.lang.String adName;
	/**描述*/
	private java.lang.String description;
	/**是否启用 (1启用、0关闭)*/
	private java.lang.String isEnabled;
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
	 *@return: java.lang.String  属所网站id
	 */
	@Column(name ="SITE_ID",nullable=true,length=32)
	public java.lang.String getSiteId(){
		return this.siteId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  属所网站id
	 */
	public void setSiteId(java.lang.String siteId){
		this.siteId = siteId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="AD_NAME",nullable=true,length=100)
	public java.lang.String getAdName(){
		return this.adName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setAdName(java.lang.String adName){
		this.adName = adName;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否启用 (1启用、0关闭)
	 */
	@Column(name ="IS_ENABLED",nullable=true,length=1)
	public java.lang.String getIsEnabled(){
		return this.isEnabled;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否启用 (1启用、0关闭)
	 */
	public void setIsEnabled(java.lang.String isEnabled){
		this.isEnabled = isEnabled;
	}
}
