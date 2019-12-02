package com.leimingtech.core.entity.thirdlogin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;


/**   
 * @Title: Entity
 * @Description: 第三方登录配置
 * @author 
 * @date 2014-05-22 15:51:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_third_login", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ThirdloginEntity extends IdEntity  implements java.io.Serializable {
	 
	private java.lang.String name;
	/**是否开启*/
	private java.lang.String isopen;
	/**id*/
	private java.lang.String appid;
	/**key*/
	private java.lang.String appkey;
	/**回调地址*/
	private java.lang.String returnurl;
	/**授权请求地址*/
	private java.lang.String authorizeurl;
	/**创建时间*/
	private java.util.Date createdtime;
	
	
	 
	 
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  name
	 */
	@Column(name ="NAME",nullable=true,length=255)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  name
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否开启
	 */
	@Column(name ="ISOPEN",nullable=true,length=100)
	public java.lang.String getIsopen(){
		return this.isopen;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否开启
	 */
	public void setIsopen(java.lang.String isopen){
		this.isopen = isopen;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	@Column(name ="APPID",nullable=true,length=255)
	public java.lang.String getAppid(){
		return this.appid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setAppid(java.lang.String appid){
		this.appid = appid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  key
	 */
	@Column(name ="APPKEY",nullable=true,length=255)
	public java.lang.String getAppkey(){
		return this.appkey;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  key
	 */
	public void setAppkey(java.lang.String appkey){
		this.appkey = appkey;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  回调地址
	 */
	@Column(name ="RETURNURL",nullable=true,length=255)
	public java.lang.String getReturnurl(){
		return this.returnurl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  回调地址
	 */
	public void setReturnurl(java.lang.String returnurl){
		this.returnurl = returnurl;
	}

	/**
	 * 方法：取得java.lang.String
	 * @return: java.lang.String 授权请求地址
	 */
	@Column(name ="AUTHORIZEURL",nullable=true,length=255)
	public java.lang.String getAuthorizeurl() {
		return authorizeurl;
	}

	/**
	 * 方法：配置java.lang.String
	 * @return: java.lang.String 授权请求地址
	 */
	public void setAuthorizeurl(java.lang.String authorizeurl) {
		this.authorizeurl = authorizeurl;
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
