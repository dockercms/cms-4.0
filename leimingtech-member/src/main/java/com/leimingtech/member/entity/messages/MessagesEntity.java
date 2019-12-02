package com.leimingtech.member.entity.messages;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 留言表
 * @author 
 * @date 2014-08-07 10:17:57
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_message", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class MessagesEntity extends IdEntity implements java.io.Serializable {

	/**主题*/
	private java.lang.String title;
	/**联系人*/
	private java.lang.String name;
	/**联系电话*/
	private java.lang.String phone;
	/**联系地址*/
	private java.lang.String address;
	/**邮箱*/
	private java.lang.String email;
	/**留言内容*/
	private java.lang.String content;
	/**回复*/
	private java.lang.String reply;
	/**添加时间*/
	private java.util.Date addTime;
	/**回复时间*/
	private java.util.Date replyTime;
	/**用户ID*/
	private java.lang.String memberid;
	/**站点id*/
	private java.lang.String siteId;
	/**审核状态*/
	private java.lang.String ischeck;

	/**创建时间*/
	private java.util.Date createdtime;
	
	/**回复人*/
	private java.lang.String replyUser;
	
	/**
	 * 回复状态
	 */
	private java.lang.String replyStatus;
	/**
	 * 回复部门
	 */
	private java.lang.String departId;
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  回复人
	 */
	@Column(name ="reply_user",nullable=true,length=50)
	public java.lang.String getReplyUser() {
		return this.replyUser;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  回复人
	 */
	public void setReplyUser(java.lang.String replyUser) {
		this.replyUser = replyUser;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主题
	 */
	@Column(name ="TITLE",nullable=true,length=100)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人
	 */
	@Column(name ="NAME",nullable=true,length=50)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  联系电话
	 */
	@Column(name ="PHONE",nullable=true,precision=10,scale=0)
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  联系电话
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系地址
	 */
	@Column(name ="ADDRESS",nullable=true,length=255)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系地址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮箱
	 */
	@Column(name ="EMAIL",nullable=true,length=50)
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
	 *方法: 取得java.lang.Object
	 *@return: java.lang.Object  留言内容
	 */
	@Column(name ="CONTENT",nullable=true)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.Object
	 *@param: java.lang.Object  留言内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	
	/**
	 *方法: 取得java.lang.Object
	 *@return: java.lang.Object  留言内容
	 */
	@Column(name ="REPLY",nullable=true)
	public java.lang.String getReply(){
		return this.reply;
	}

	/**
	 *方法: 设置java.lang.Object
	 *@param: java.lang.Object  留言内容
	 */
	public void setReply(java.lang.String reply){
		this.reply = reply;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户ID
	 */
	@Column(name ="MEMBERID",nullable=true,length=32)
	public java.lang.String getMemberid(){
		return this.memberid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户ID
	 */
	public void setMemberid(java.lang.String memberid){
		this.memberid = memberid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  站点id
	 */
	@Column(name ="SITE_ID",nullable=true,length=32)
	public java.lang.String getSiteId(){
		return this.siteId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点id
	 */
	public void setSiteId(java.lang.String siteId){
		this.siteId = siteId;
	}
	


	/**
	 * 方法：取得java.util.Date
	 * @return
	 */
	@Column(name ="ADD_TIME",nullable=true)
	public java.util.Date getAddTime() {
		return addTime;
	}

	/**
	 * 方法：设置java.util.Date
	 * @param addTime
	 */
	public void setAddTime(java.util.Date addTime) {
		this.addTime = addTime;
	}

	/**
	 * 方法：取得java.util.date
	 * @return
	 */
	@Column(name ="REPLY_TIME",nullable=true)
	public java.util.Date getReplyTime() {
		return replyTime;
	}

	/**
	 * 方法：设置java.util.Date
	 * @param replyTime
	 */
	public void setReplyTime(java.util.Date replyTime) {
		this.replyTime = replyTime;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核状态
	 */
	@Column(name ="ISCHECK",nullable=true,length=10)
	public java.lang.String getIscheck() {
		return ischeck;
	}

	/**
	 * 方法：设置java.lang.String
	 * @param ischeck
	 */
	public void setIscheck(java.lang.String ischeck) {
		this.ischeck = ischeck;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  回复状态
	 */
	@Column(name ="reply_status",nullable=true,length=10)
	public java.lang.String getReplyStatus() {
		return replyStatus;
	}
	/**
	 * 方法：设置java.lang.String
	 * @param replyStatus
	 */
	public void setReplyStatus(java.lang.String replyStatus) {
		this.replyStatus = replyStatus;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  回复部门
	 */
	@Column(name ="departid",nullable=true,length=10)
	public java.lang.String getDepartId() {
		return departId;
	}

	public void setDepartId(java.lang.String departId) {
		this.departId = departId;
	}
	
	
}
