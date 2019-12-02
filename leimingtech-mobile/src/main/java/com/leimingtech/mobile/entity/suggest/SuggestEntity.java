package com.leimingtech.mobile.entity.suggest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 意见反馈表
 * @author 
 * @date 2014-07-25 10:18:04
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cm_suggest", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SuggestEntity extends IdEntity implements java.io.Serializable {
	 
	/**用户id*/
	private java.lang.String userId;
	/**反馈意见内容*/
	private java.lang.String suggestContent;
	/**反馈类别*/
	private java.lang.String suggestType;
	/**邮箱*/
	private java.lang.String email;
	/**电话*/
	private java.lang.String tel;
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
	 *@return: java.lang.String  用户id
	 */
	@Column(name ="USER_ID",nullable=true,precision=10,scale=0)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户id
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  反馈意见内容
	 */
	@Column(name ="SUGGEST_CONTENT",nullable=true)
	public java.lang.String getSuggestContent(){
		return this.suggestContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  反馈意见内容
	 */
	public void setSuggestContent(java.lang.String suggestContent){
		this.suggestContent = suggestContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  反馈类别
	 */
	@Column(name ="SUGGEST_TYPE",nullable=true,length=32)
	public java.lang.String getSuggestType(){
		return this.suggestType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  反馈类别
	 */
	public void setSuggestType(java.lang.String suggestType){
		this.suggestType = suggestType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮箱
	 */
	@Column(name ="EMAIL",nullable=true,length=255)
	public java.lang.String getEmail(){
		return this.email;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮箱
	 */
	public void setEmail(java.lang.String email){
		this.email = email;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
	@Column(name ="TEL",nullable=true,length=32)
	public java.lang.String getTel(){
		return this.tel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setTel(java.lang.String tel){
		this.tel = tel;
	}
}
