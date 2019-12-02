package com.leimingtech.mobile.entity.comment;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**
 * @Title: Entity
 * @Description: 移动内容评价表
 * @author
 * @date 2014-07-23 18:35:53
 * @version V1.0
 *
 */
@Entity
@Table(name = "cm_comments", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CommentMobileEntity extends IdEntity implements java.io.Serializable {

	/**内容ID*/
	private java.lang.String contentsId;
	/**评价类型*/
	private java.lang.String commentType;
	/**作者ID*/
	private java.lang.String userId;
	/**作者*/
	private java.lang.String userName;
	/**联系方式*/
	private java.lang.String contact;
	/**创建时间*/
	private java.util.Date time;
	/**最后回复时间*/
	private java.util.Date lastreply;
	/**回复者名称*/
	private java.lang.String replyName;
	/**标题*/
	private java.lang.String title;
	/**内容*/
	private java.lang.String comments;
	/**ip*/
	private java.lang.String ip;
	/**display*/
	private java.lang.String display;
	/**p_index*/
	private java.lang.Integer pIndex;
	/**disabled*/
	private java.lang.String disabled;
	/**评分*/
	private java.lang.Integer grade;
	/**图片*/
	private java.lang.String img;

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
	 *@return: java.lang.Integer  内容ID
	 */
	@Column(name ="CONTENTS_ID",nullable=true,length=32)
	public java.lang.String getContentsId(){
		return this.contentsId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  内容ID
	 */
	public void setContentsId(java.lang.String contentsId){
		this.contentsId = contentsId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  评价类型
	 */
	@Column(name ="COMMENT_TYPE",nullable=true,length=50)
	public java.lang.String getCommentType(){
		return this.commentType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  评价类型
	 */
	public void setCommentType(java.lang.String commentType){
		this.commentType = commentType;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  作者ID
	 */
	@Column(name ="USER_ID",nullable=true,length=32)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  作者ID
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  作者
	 */
	@Column(name ="USER_NAME",nullable=true,length=100)
	public java.lang.String getUserName(){
		return this.userName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  作者
	 */
	public void setUserName(java.lang.String userName){
		this.userName = userName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系方式
	 */
	@Column(name ="CONTACT",nullable=true,length=255)
	public java.lang.String getContact(){
		return this.contact;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系方式
	 */
	public void setContact(java.lang.String contact){
		this.contact = contact;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="TIME",nullable=true)
	public java.util.Date getTime(){
		return this.time;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setTime(java.util.Date time){
		this.time = time;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  最后回复时间
	 */
	@Column(name ="LASTREPLY",nullable=true)
	public java.util.Date getLastreply(){
		return this.lastreply;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  最后回复时间
	 */
	public void setLastreply(java.util.Date lastreply){
		this.lastreply = lastreply;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  回复者名称
	 */
	@Column(name ="REPLY_NAME",nullable=true,length=100)
	public java.lang.String getReplyName(){
		return this.replyName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  回复者名称
	 */
	public void setReplyName(java.lang.String replyName){
		this.replyName = replyName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题
	 */
	@Column(name ="TITLE",nullable=true,length=255)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容
	 */
	@Column(name ="COMMENTS",nullable=true)
	public java.lang.String getComments(){
		return this.comments;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容
	 */
	public void setComments(java.lang.String comments){
		this.comments = comments;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ip
	 */
	@Column(name ="IP",nullable=true,length=15)
	public java.lang.String getIp(){
		return this.ip;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  ip
	 */
	public void setIp(java.lang.String ip){
		this.ip = ip;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  display
	 */
	@Column(name ="DISPLAY",nullable=true,length=255)
	public java.lang.String getDisplay(){
		return this.display;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  display
	 */
	public void setDisplay(java.lang.String display){
		this.display = display;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  p_index
	 */
	@Column(name ="P_INDEX",nullable=true,precision=3,scale=0)
	public java.lang.Integer getPIndex(){
		return this.pIndex;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  p_index
	 */
	public void setPIndex(java.lang.Integer pIndex){
		this.pIndex = pIndex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  disabled
	 */
	@Column(name ="DISABLED",nullable=true,length=255)
	public java.lang.String getDisabled(){
		return this.disabled;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  disabled
	 */
	public void setDisabled(java.lang.String disabled){
		this.disabled = disabled;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  评分
	 */
	@Column(name ="GRADE",nullable=true,precision=10,scale=0)
	public java.lang.Integer getGrade(){
		return this.grade;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  评分
	 */
	public void setGrade(java.lang.Integer grade){
		this.grade = grade;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片
	 */
	@Column(name ="IMG",nullable=true,length=255)
	public java.lang.String getImg(){
		return this.img;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片
	 */
	public void setImg(java.lang.String img){
		this.img = img;
	}
}
