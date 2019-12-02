package com.leimingtech.cms.entity.guestbook;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 留言板
 * @author zhangdaihao
 * @date 2014-04-21 13:38:22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_guestbook", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class GuestBookEntity extends IdEntity implements java.io.Serializable {

	/**guestbookCtg*/
	private java.lang.Integer guestbookCtg;
	/**siteId*/
	private java.lang.String siteId;
	/**留言会员*/
	private java.lang.String memberId;
	/**回复管理员*/
	private java.lang.String adminId;
	/**留言IP*/
	private java.lang.String ip;
	/**留言时间*/
	private java.util.Date createTime;
	/**回复时间*/
	private java.util.Date replayTime;
	/**是否审核*/
	private java.lang.Integer isChecked;
	/**是否推荐*/
	private java.lang.Integer isRecommend;
	/**留言标题*/
	private java.lang.String title;
	/**留言内容*/
	private java.lang.String content;
	/**回复内容*/
	private java.lang.String reply;
	/**电子邮件*/
	private java.lang.String email;
	/**电话*/
	private java.lang.String phone;
	/**QQ*/
	private java.lang.String qq;
	
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  guestbookCtg
	 */
	@Column(name ="GUESTBOOK_CTG",nullable=false,precision=10,scale=0)
	public java.lang.Integer getGuestbookCtg(){
		return this.guestbookCtg;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  guestbookCtg
	 */
	public void setGuestbookCtg(java.lang.Integer guestbookCtg){
		this.guestbookCtg = guestbookCtg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  siteId
	 */
	@Column(name ="SITE_ID",nullable=false,length=32)
	public java.lang.String getSiteId(){
		return this.siteId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  siteId
	 */
	public void setSiteId(java.lang.String siteId){
		this.siteId = siteId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  留言会员
	 */
	@Column(name ="MEMBER_ID",nullable=true,length=32)
	public java.lang.String getMemberId(){
		return this.memberId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  留言会员
	 */
	public void setMemberId(java.lang.String memberId){
		this.memberId = memberId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  回复管理员
	 */
	@Column(name ="ADMIN_ID",nullable=true,length=32)
	public java.lang.String getAdminId(){
		return this.adminId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  回复管理员
	 */
	public void setAdminId(java.lang.String adminId){
		this.adminId = adminId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  留言IP
	 */
	@Column(name ="IP",nullable=false,length=50)
	public java.lang.String getIp(){
		return this.ip;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  留言IP
	 */
	public void setIp(java.lang.String ip){
		this.ip = ip;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  留言时间
	 */
	@Column(name ="CREATE_TIME",nullable=false)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  留言时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  回复时间
	 */
	@Column(name ="REPLAY_TIME",nullable=true)
	public java.util.Date getReplayTime(){
		return this.replayTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  回复时间
	 */
	public void setReplayTime(java.util.Date replayTime){
		this.replayTime = replayTime;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否审核
	 */
	@Column(name ="IS_CHECKED",nullable=false,precision=3,scale=0)
	public java.lang.Integer getIsChecked(){
		return this.isChecked;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否审核
	 */
	public void setIsChecked(java.lang.Integer isChecked){
		this.isChecked = isChecked;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否推荐
	 */
	@Column(name ="IS_RECOMMEND",nullable=false,precision=3,scale=0)
	public java.lang.Integer getIsRecommend(){
		return this.isRecommend;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否推荐
	 */
	public void setIsRecommend(java.lang.Integer isRecommend){
		this.isRecommend = isRecommend;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  留言标题
	 */
	@Column(name ="TITLE",nullable=true,length=255)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  留言标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  留言内容
	 */
	@Column(name ="CONTENT",nullable=true,length=255)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  留言内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  回复内容
	 */
	@Column(name ="REPLY",nullable=true,length=255)
	public java.lang.String getReply(){
		return this.reply;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  回复内容
	 */
	public void setReply(java.lang.String reply){
		this.reply = reply;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电子邮件
	 */
	@Column(name ="EMAIL",nullable=true,length=100)
	public java.lang.String getEmail(){
		return this.email;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电子邮件
	 */
	public void setEmail(java.lang.String email){
		this.email = email;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
	@Column(name ="PHONE",nullable=true,length=100)
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  QQ
	 */
	@Column(name ="QQ",nullable=true,length=50)
	public java.lang.String getQq(){
		return this.qq;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  QQ
	 */
	public void setQq(java.lang.String qq){
		this.qq = qq;
	}
}
