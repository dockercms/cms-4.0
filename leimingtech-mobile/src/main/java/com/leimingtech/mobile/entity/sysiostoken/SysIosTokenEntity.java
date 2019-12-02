package com.leimingtech.mobile.entity.sysiostoken;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 获取IOS设备的token
 * @author 
 * @date 2014-09-05 09:29:25
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cm_sys_iso_token", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SysIosTokenEntity extends IdEntity implements java.io.Serializable {
	 
	/**token*/
	private java.lang.String token;
	/**系统类型*/
	private java.lang.String sysType;
	/**手机品牌*/
	private java.lang.String phoneName;
	/**手机型号*/
	private java.lang.String phoneType;
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
	 *@return: java.lang.String  token
	 */
	@Column(name ="TOKEN",nullable=false,length=100)
	public java.lang.String getToken(){
		return this.token;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  token
	 */
	public void setToken(java.lang.String token){
		this.token = token;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  系统类型
	 */
	@Column(name ="SYS_TYPE",nullable=true,length=50)
	public java.lang.String getSysType(){
		return this.sysType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  系统类型
	 */
	public void setSysType(java.lang.String sysType){
		this.sysType = sysType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机品牌
	 */
	@Column(name ="PHONE_NAME",nullable=true,length=50)
	public java.lang.String getPhoneName(){
		return this.phoneName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机品牌
	 */
	public void setPhoneName(java.lang.String phoneName){
		this.phoneName = phoneName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机型号
	 */
	@Column(name ="PHONE_TYPE",nullable=true,length=50)
	public java.lang.String getPhoneType(){
		return this.phoneType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机型号
	 */
	public void setPhoneType(java.lang.String phoneType){
		this.phoneType = phoneType;
	}
}
