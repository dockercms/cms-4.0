package com.leimingtech.mobile.entity.updateversion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 版本更新
 * @author 
 * @date 2014-09-01 17:50:24
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cm_update_version", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class UpdateVersionEntity extends IdEntity implements java.io.Serializable {
	 
	/**系统类型*/
	private java.lang.String sysType;
	/**版本号*/
	private java.lang.String version;
	/**更新说明*/
	private java.lang.String account;
	/**下载地址*/
	private java.lang.String downUrl;
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
	 *@return: java.lang.Integer  系统类型
	 */
	@Column(name ="SYS_TYPE",nullable=true,length=20)
	public java.lang.String getSysType(){
		return this.sysType;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  系统类型
	 */
	public void setSysType(java.lang.String sysType){
		this.sysType = sysType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  版本号
	 */
	@Column(name ="VERSION",nullable=true,length=20)
	public java.lang.String getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  版本号
	 */
	public void setVersion(java.lang.String version){
		this.version = version;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新说明
	 */
	@Column(name ="ACCOUNT",nullable=true,length=3000)
	public java.lang.String getAccount(){
		return this.account;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新说明
	 */
	public void setAccount(java.lang.String account){
		this.account = account;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  下载地址
	 */
	@Column(name ="DOWN_URL",nullable=true,length=200)
	public java.lang.String getDownUrl(){
		return this.downUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  下载地址
	 */
	public void setDownUrl(java.lang.String downUrl){
		this.downUrl = downUrl;
	}
}
