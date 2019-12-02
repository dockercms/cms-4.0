package com.leimingtech.mobile.entity.feedback;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 意见反馈
 * @author 
 * @date 2014-08-25 09:58:34
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cm_feedback", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class FeedbackEntity extends IdEntity implements java.io.Serializable {
	 
	/**意见反馈内容*/
	private java.lang.String content;
	/**邮箱*/
	private java.lang.String email;
	/**应用版本*/
	private java.lang.String version;
	/**系统*/
	private java.lang.String system;
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
	 *@return: java.lang.String  意见反馈内容
	 */
	@Column(name ="CONTENT",nullable=false,length=255)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  意见反馈内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮箱
	 */
	@Column(name ="EMAIL",nullable=true,length=30)
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
	 *@return: java.lang.String  应用版本
	 */
	@Column(name ="VERSION",nullable=true,length=20)
	public java.lang.String getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  应用版本
	 */
	public void setVersion(java.lang.String version){
		this.version = version;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  系统
	 */
	@Column(name ="SYSTEM",nullable=true,length=30)
	public java.lang.String getSystem(){
		return this.system;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  系统
	 */
	public void setSystem(java.lang.String system){
		this.system = system;
	}
}
