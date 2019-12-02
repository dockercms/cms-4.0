package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 移动内容跟帖表
 * @author 
 * @date 2014-07-25 14:38:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cm_invitation", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")

public class InvitationMobileEntity extends IdEntity implements java.io.Serializable {


	/**内容ID*/
	private java.lang.String contentsId;
	/**跟帖类型*/
	private java.lang.String invitationType;
	/**作者ID*/
	private java.lang.String userId;
	/**作者*/
	private java.lang.String userName;
	/**联系方式*/
	private java.lang.String contact;
	/**创建时间*/
	private java.util.Date createtime;
	/**最后回复时间*/
	private java.util.Date lastreply;
	/**回复者名称*/
	private java.lang.String replyName;
	/**标题*/
	private java.lang.String title;
	/**内容*/
	private java.lang.String content;
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
	/**内容标题*/
	private java.lang.String contentsTitle;
	/**内容链接*/
	private java.lang.String contentsUrl;
	


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容ID
	 */
	@Column(name ="CONTENTS_ID",nullable=true,length=32)
	public java.lang.String getContentsId(){

		return this.contentsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容ID
	 */
	public void setContentsId(java.lang.String contentsId){
		this.contentsId = contentsId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  跟帖类型
	 */
	@Column(name ="INVITATION_TYPE",nullable=true,length=50)
	public java.lang.String getInvitationType(){
		return this.invitationType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  跟帖类型
	 */
	public void setInvitationType(java.lang.String invitationType){
		this.invitationType = invitationType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  作者ID
	 */

	@Column(name ="USER_ID",nullable=true,length=32)
	public java.lang.String getUserId(){

		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  作者ID
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
	@Column(name ="CREATETIME",nullable=true)
	public java.util.Date getCreatetime(){
		return this.createtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreatetime(java.util.Date createtime){
		this.createtime = createtime;
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
	@Column(name ="CONTENT",nullable=true)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容标题
	 */
	@Column(name ="CONTENTS_TITLE",nullable=true,length=255)
	public java.lang.String getContentsTitle() {
		return contentsTitle;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容标题
	 */
	public void setContentsTitle(java.lang.String contentsTitle) {
		this.contentsTitle = contentsTitle;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容链接
	 */
	@Column(name ="CONTENTS_URL",nullable=true,length=255)
	public java.lang.String getContentsUrl() {
		return contentsUrl;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容链接
	 */
	public void setContentsUrl(java.lang.String contentsUrl) {
		this.contentsUrl = contentsUrl;
	}
	
}
