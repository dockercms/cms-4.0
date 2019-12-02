package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 访谈嘉宾
 * @author zhangdaihao
 * @date 2014-05-08 11:20:13
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_interview_guest", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class InterviewGuestEntity extends IdEntity implements java.io.Serializable {
	
	/**访谈ID*/
	private java.lang.String interviewid;
	/**嘉宾姓名*/
	private java.lang.String guestName;
	/**嘉宾介绍网址*/
	private java.lang.String guestUrl;
	/**嘉宾图片*/
	private java.lang.String guestPicture;
	/**嘉宾介绍*/
	private java.lang.String guestIntroduce;
	/**备注*/
	private java.lang.String guestToken;
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
	 *@return: java.lang.String  访谈ID
	 */
	@Column(name ="INTERVIEWID",nullable=true,precision=10,scale=0)
	public java.lang.String getInterviewid(){
		return this.interviewid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  访谈ID
	 */
	public void setInterviewid(java.lang.String interviewid){
		this.interviewid = interviewid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  嘉宾姓名
	 */
	@Column(name ="GUEST_NAME",nullable=true,length=255)
	public java.lang.String getGuestName(){
		return this.guestName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  嘉宾姓名
	 */
	public void setGuestName(java.lang.String guestName){
		this.guestName = guestName;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  嘉宾介绍网址
	 */
	public void setGuestUrl(java.lang.String guestUrl){
		this.guestUrl = guestUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  嘉宾介绍网址
	 */
	@Column(name ="GUEST_URL",nullable=true,length=255)
	public java.lang.String getGuestUrl(){
		return this.guestUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  嘉宾图片
	 */
	public void setGuestPicture(java.lang.String guestPicture){
		this.guestPicture = guestPicture;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  嘉宾图片
	 */
	@Column(name ="GUEST_PICTURE",nullable=true,length=255)
	public java.lang.String getGuestPicture(){
		return this.guestPicture;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  嘉宾介绍
	 */
	@Column(name ="GUEST_INTRODUCE",nullable=true,length=255)
	public java.lang.String getGuestIntroduce(){
		return this.guestIntroduce;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  嘉宾介绍
	 */
	public void setGuestIntroduce(java.lang.String guestIntroduce){
		this.guestIntroduce = guestIntroduce;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="GUEST_TOKEN",nullable=true,length=255)
	public java.lang.String getGuestToken(){
		return this.guestToken;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setGuestToken(java.lang.String guestToken){
		this.guestToken = guestToken;
	}
}
