package com.leimingtech.platform.entity.note;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 短信平台
 * @author zhangdaihao
 * @date 2014-04-03 16:59:28
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_note", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class NoteEntity extends IdEntity implements java.io.Serializable {
	 
	/**userId*/
	private java.lang.String userId;
	/**pid*/
	private java.lang.String pid;
	/**标记*/
	private java.lang.String token;
	/**短信平台手机号*/
	private java.lang.String notePhone;
	/**短信平台账号*/
	private java.lang.String onteAccount;
	/**短信平台密码*/
	private java.lang.String notePassword;
	/**短信平台状态*/
	private java.lang.String noteStauts;
	/**创建时间*/
	private java.util.Date createdtime;
	
	
	
	 
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  uid
	 */
	@Column(name ="USERID",nullable=true,length=32)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  uid
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  pid
	 */
	@Column(name ="PID",nullable=true,length=250)
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
	 *@return: java.lang.String  短信平台手机号
	 */
	@Column(name ="NOTE_PHONE",nullable=true,length=250)
	public java.lang.String getNotePhone(){
		return this.notePhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  短信平台手机号
	 */
	public void setNotePhone(java.lang.String notePhone){
		this.notePhone = notePhone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  短信平台账号
	 */
	@Column(name ="ONTE_ACCOUNT",nullable=true,length=250)
	public java.lang.String getOnteAccount(){
		return this.onteAccount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  短信平台账号
	 */
	public void setOnteAccount(java.lang.String onteAccount){
		this.onteAccount = onteAccount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  短信平台密码
	 */
	@Column(name ="NOTE_PASSWORD",nullable=true,length=250)
	public java.lang.String getNotePassword(){
		return this.notePassword;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  短信平台密码
	 */
	public void setNotePassword(java.lang.String notePassword){
		this.notePassword = notePassword;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  短信平台状态
	 */
	@Column(name ="NOTE_STAUTS",nullable=true,length=250)
	public java.lang.String getNoteStauts(){
		return this.noteStauts;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  短信平台状态
	 */
	public void setNoteStauts(java.lang.String noteStauts){
		this.noteStauts = noteStauts;
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
