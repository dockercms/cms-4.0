package com.leimingtech.cms.entity.sensitivity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 敏感词管理
 * @author zhangdaihao
 * @date 2014-04-21 14:49:00
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_sensitivity", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SensitivityEntity extends IdEntity implements java.io.Serializable {
	
	/**敏感词*/
	private java.lang.String search;
	/**替换词*/
	private java.lang.String replacement;
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
	 *@return: java.lang.String  敏感词
	 */
	@Column(name ="SEARCH",nullable=true,length=255)
	public java.lang.String getSearch(){
		return this.search;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  敏感词
	 */
	public void setSearch(java.lang.String search){
		this.search = search;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  替换词
	 */
	@Column(name ="REPLACEMENT",nullable=true,length=255)
	public java.lang.String getReplacement(){
		return this.replacement;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  替换词
	 */
	public void setReplacement(java.lang.String replacement){
		this.replacement = replacement;
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
