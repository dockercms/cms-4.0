package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 访谈
 * @author zhangdaihao
 * @date 2014-05-08 11:26:03
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_interview", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class InterviewEntity extends IdEntity implements java.io.Serializable {
	
	/**内容ID*/
	private String contentid;
	/**期号*/
	private java.lang.String interviewIssue;
	/**访谈状态*/
	private java.lang.String interviewState;
	/**访谈介绍*/
	private java.lang.String interviewIntroduce;
	/**访谈方式*/
	private java.lang.String interviewWay;
	/**访谈图片*/
	private java.lang.String interviewPicture;
	/**访谈开始时间*/
	private java.util.Date interviewStarttime;
	/**访谈结束时间*/
	private java.util.Date interviewEndtime;
	/**访谈地点*/
	private java.lang.String interviewPlace;
	/**主持人*/
	private java.lang.String interviewCompere;
	/**自定义模板*/
	private java.lang.String customModel;
	/**网友发言*/
	private java.lang.String netfriendSpeak;
	/**游客发言*/
	private java.lang.String visitorSpeak;
	/**发言审核*/
	private java.lang.String speakCheck;
	/**发言开始时间*/
	private java.util.Date speakStarttime;
	/**发言结束时间*/
	private java.util.Date speakEndtime;
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
	 *@return: java.lang.String  内容ID
	 */
	@Column(name ="CONTENTID",nullable=true,precision=7,scale=0)
	public java.lang.String getContentid(){
		return this.contentid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容ID
	 */
	public void setContentid(java.lang.String contentid){
		this.contentid = contentid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  期号
	 */
	@Column(name ="INTERVIEW_ISSUE",nullable=true,length=255)
	public java.lang.String getInterviewIssue(){
		return this.interviewIssue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  期号
	 */
	public void setInterviewIssue(java.lang.String interviewIssue){
		this.interviewIssue = interviewIssue;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  访谈状态
	 */
	@Column(name ="INTERVIEW_STATE",nullable=true,length=255)
	public java.lang.String getInterviewState(){
		return this.interviewState;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  访谈状态
	 */
	public void setInterviewState(java.lang.String interviewState){
		this.interviewState = interviewState;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  访谈介绍
	 */
	@Column(name ="INTERVIEW_INTRODUCE",nullable=true,length=255)
	public java.lang.String getInterviewIntroduce(){
		return this.interviewIntroduce;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  访谈介绍
	 */
	public void setInterviewIntroduce(java.lang.String interviewIntroduce){
		this.interviewIntroduce = interviewIntroduce;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  访谈方式
	 */
	@Column(name ="INTERVIEW_WAY",nullable=true,length=255)
	public java.lang.String getInterviewWay(){
		return this.interviewWay;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  访谈方式
	 */
	public void setInterviewWay(java.lang.String interviewWay){
		this.interviewWay = interviewWay;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  访谈图片
	 */
	@Column(name ="INTERVIEW_PICTURE",nullable=true,length=255)
	public java.lang.String getInterviewPicture(){
		return this.interviewPicture;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  访谈图片
	 */
	public void setInterviewPicture(java.lang.String interviewPicture){
		this.interviewPicture = interviewPicture;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  访谈开始时间
	 */
	@Column(name ="INTERVIEW_STARTTIME",nullable=true)
	public java.util.Date getInterviewStarttime(){
		return this.interviewStarttime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  访谈开始时间
	 */
	public void setInterviewStarttime(java.util.Date interviewStarttime){
		this.interviewStarttime = interviewStarttime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  访谈结束时间
	 */
	@Column(name ="INTERVIEW_ENDTIME",nullable=true)
	public java.util.Date getInterviewEndtime(){
		return this.interviewEndtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  访谈结束时间
	 */
	public void setInterviewEndtime(java.util.Date interviewEndtime){
		this.interviewEndtime = interviewEndtime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  访谈地点
	 */
	@Column(name ="INTERVIEW_PLACE",nullable=true,length=255)
	public java.lang.String getInterviewPlace(){
		return this.interviewPlace;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  访谈地点
	 */
	public void setInterviewPlace(java.lang.String interviewPlace){
		this.interviewPlace = interviewPlace;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主持人
	 */
	@Column(name ="INTERVIEW_COMPERE",nullable=true,length=255)
	public java.lang.String getInterviewCompere(){
		return this.interviewCompere;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主持人
	 */
	public void setInterviewCompere(java.lang.String interviewCompere){
		this.interviewCompere = interviewCompere;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  自定义模板
	 */
	@Column(name ="CUSTOM_MODEL",nullable=true,length=255)
	public java.lang.String getCustomModel(){
		return this.customModel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  自定义模板
	 */
	public void setCustomModel(java.lang.String customModel){
		this.customModel = customModel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  网友发言
	 */
	@Column(name ="NETFRIEND_SPEAK",nullable=true,length=255)
	public java.lang.String getNetfriendSpeak(){
		return this.netfriendSpeak;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  网友发言
	 */
	public void setNetfriendSpeak(java.lang.String netfriendSpeak){
		this.netfriendSpeak = netfriendSpeak;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  游客发言
	 */
	@Column(name ="VISITOR_SPEAK",nullable=true,length=255)
	public java.lang.String getVisitorSpeak(){
		return this.visitorSpeak;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  游客发言
	 */
	public void setVisitorSpeak(java.lang.String visitorSpeak){
		this.visitorSpeak = visitorSpeak;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发言审核
	 */
	@Column(name ="SPEAK_CHECK",nullable=true,length=255)
	public java.lang.String getSpeakCheck(){
		return this.speakCheck;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发言审核
	 */
	public void setSpeakCheck(java.lang.String speakCheck){
		this.speakCheck = speakCheck;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发言开始时间
	 */
	@Column(name ="SPEAK_STARTTIME",nullable=true)
	public java.util.Date getSpeakStarttime(){
		return this.speakStarttime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发言开始时间
	 */
	public void setSpeakStarttime(java.util.Date speakStarttime){
		this.speakStarttime = speakStarttime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发言结束时间
	 */
	@Column(name ="SPEAK_ENDTIME",nullable=true)
	public java.util.Date getSpeakEndtime(){
		return this.speakEndtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发言结束时间
	 */
	public void setSpeakEndtime(java.util.Date speakEndtime){
		this.speakEndtime = speakEndtime;
	}
}
