package com.leimingtech.cms.entity.cmskeyword;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: cms关键词管理
 * @author zhangdaihao
 * @date 2014-04-21 14:28:42
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_cmskeyword", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CmsKeywordEntity extends IdEntity implements java.io.Serializable {
	
	/**站点ID*/
	private java.lang.String siteId;
	/**名称*/
	private java.lang.String keywordName;
	/**链接*/
	private java.lang.String url;
	/**是否禁用 1禁用 0启用*/
	private java.lang.Integer isDisabled;
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
	 *@return: java.lang.Integer  站点ID
	 */
	@Column(name ="SITE_ID",nullable=true,length=32)
	public java.lang.String getSiteId(){
		return this.siteId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点ID
	 */
	public void setSiteId(java.lang.String siteId){
		this.siteId = siteId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="KEYWORD_NAME",nullable=true,length=100)
	public java.lang.String getKeywordName(){
		return this.keywordName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setKeywordName(java.lang.String keywordName){
		this.keywordName = keywordName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  链接
	 */
	@Column(name ="URL",nullable=true,length=255)
	public java.lang.String getUrl(){
		return this.url;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  链接
	 */
	public void setUrl(java.lang.String url){
		this.url = url;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否禁用
	 */
	@Column(name ="IS_DISABLED",nullable=true,precision=3,scale=0)
	public java.lang.Integer getIsDisabled(){
		return this.isDisabled;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否禁用
	 */
	public void setIsDisabled(java.lang.Integer isDisabled){
		this.isDisabled = isDisabled;
	}
}
