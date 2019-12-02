package com.leimingtech.platform.entity.mail;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;


/**   
 * @Title: Entity
 * @Description: 邮件平台
 * @author zhangdaihao
 * @date 2014-04-03 17:26:27
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_mail", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class MailEntity extends IdEntity implements java.io.Serializable {
	 
	/**userId*/
	private java.lang.String userId;
	/**pid*/
	private java.lang.String pid;
	/**标记*/
	private java.lang.String token;
	/**邮件地址*/
	private java.lang.String mailPhone;
	/**SMTP账号*/
	private java.lang.String smtpAccount;
	/**SMTP密码*/
	private java.lang.String smtpPassword;
	/**邮件平台状态*/
	private java.lang.String mailStauts;
	/**创建时间*/
	private java.util.Date createdtime;
	
	 
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  uid
	 */
	@Column(name ="USERID",nullable=true,length=32)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  uid
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  pid
	 */
	@Column(name ="PID",nullable=true,length=32)
	public java.lang.String getPid(){
		return this.pid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  pid
	 */
	public void setPid(java.lang.String pid){
		this.pid = pid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标记
	 */
	@Column(name ="TOKEN",nullable=true,length=250)
	public java.lang.String getToken(){
		return this.token;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标记
	 */
	public void setToken(java.lang.String token){
		this.token = token;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮件地址
	 */
	@Column(name ="MAIL_PHONE",nullable=true,length=250)
	public java.lang.String getMailPhone(){
		return this.mailPhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮件地址
	 */
	public void setMailPhone(java.lang.String mailPhone){
		this.mailPhone = mailPhone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  SMTP账号
	 */
	@Column(name ="SMTP_ACCOUNT",nullable=true,length=250)
	public java.lang.String getSmtpAccount(){
		return this.smtpAccount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  SMTP账号
	 */
	public void setSmtpAccount(java.lang.String smtpAccount){
		this.smtpAccount = smtpAccount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  SMTP密码
	 */
	@Column(name ="SMTP_PASSWORD",nullable=true,length=250)
	public java.lang.String getSmtpPassword(){
		return this.smtpPassword;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  SMTP密码
	 */
	public void setSmtpPassword(java.lang.String smtpPassword){
		this.smtpPassword = smtpPassword;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮件平台状态
	 */
	@Column(name ="MAIL_STAUTS",nullable=true,length=250)
	public java.lang.String getMailStauts(){
		return this.mailStauts;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮件平台状态
	 */
	public void setMailStauts(java.lang.String mailStauts){
		this.mailStauts = mailStauts;
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
