package com.leimingtech.core.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 评论
 * @author zhangdaihao
 * @date 2014-05-06 15:26:26
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_commentary", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CommentaryFrontEntity extends IdEntity implements java.io.Serializable {

	/**用户名*/
	private java.lang.String username;

	/**文章主题*/
	private java.lang.String title;
	/**内容*/
	private java.lang.String content;
	/**评论人*/
	private java.lang.String commentaryperson;
	/**评论人ID*/
	private java.lang.String personid;
	/**评论时间*/
	private java.util.Date commentarytime;


	/**文章id*/

	private java.lang.String contentid;

	/**评价类型*/
	private java.lang.String commentType;
	/**审核状态*/
	private java.lang.String ischeck;
	
	/**创建时间*/
	private java.util.Date createdtime;

	/** 审核人*/
	private java.lang.String auditorName;

	/**审核人ID*/
	private java.lang.String auditorId;

	/**审核时间*/
	private java.util.Date auditorCreateTime;

	/**点赞数*/
	private java.lang.Integer supportcount;

	/**贬赞数*/
	private java.lang.Integer opposecount;

    /**跟帖数量*/
    private Integer replycount;

    /**站点Id*/
    private String siteId;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Integer getReplycount() {
        return replycount;
    }

    public void setReplycount(Integer replycount) {
        this.replycount = replycount;
    }

    public String getAuditorName() {
		return auditorName;
	}

	public String getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}

	public Date getAuditorCreateTime() {
		return auditorCreateTime;
	}

	public void setAuditorCreateTime(Date auditorCreateTime) {
		this.auditorCreateTime = auditorCreateTime;
	}

	/**
	 *方法: 取得 赞同数
	 *@return: java.lang.Integer  赞同数
	 */
	@Column(name ="supportcount",nullable=true,length=11)
	public Integer getSupportcount() {
		return supportcount;
	}

	public void setSupportcount(Integer supportcount) {
		this.supportcount = supportcount;
	}

	/**
	 *方法: 取得 反对数
	 *@return: java.lang.Integer  赞同数
	 */
	@Column(name ="opposecount",nullable=true,length=11)
	public Integer getOpposecount() {
		return opposecount;
	}


	public void setOpposecount(Integer opposecount) {
		this.opposecount = opposecount;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户名
	 */
	@Column(name ="USERNAME",nullable=true,length=255)
	public java.lang.String getUsername(){
		return this.username;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户名
	 */
	public void setUsername(java.lang.String username){
		this.username = username;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文章主题
	 */
	@Column(name ="TITLE",nullable=true,length=255)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文章主题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容
	 */
	@Column(name ="CONTENT",nullable=true,length=255)
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
	 *@return: java.lang.String  评论人
	 */
	@Column(name ="COMMENTARYPERSON",nullable=true,length=255)
	public java.lang.String getCommentaryperson(){
		return this.commentaryperson;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  评论人
	 */
	public void setCommentaryperson(java.lang.String commentaryperson){
		this.commentaryperson = commentaryperson;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  评论人ID
	 */
	@Column(name ="PERSONID",nullable=true,length=32)
	public java.lang.String getPersonid(){
		return this.personid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  评论人ID
	 */
	public void setPersonid(java.lang.String personid){
		this.personid = personid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  评论时间
	 */
	@Column(name ="COMMENTARYTIME",nullable=true)
	public java.util.Date getCommentarytime(){
		return this.commentarytime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  评论时间
	 */
	public void setCommentarytime(java.util.Date commentarytime){
		this.commentarytime = commentarytime;
	}




	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文章id
	 */

	@Column(name ="CONTENTID",nullable=true,length=32)
	public java.lang.String getContentid(){

		return this.contentid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文章id
	 */
	public void setContentid(java.lang.String contentid){
		this.contentid = contentid;
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
	 * 方法: 取得java.lang.String
	 * @return java.lang.String 审核状态
	 */
	@Column(name ="ISCHECK",nullable=true,length=10)
	public java.lang.String getIscheck() {
		return ischeck;
	}

	/**
	 * 方法：设置java.lang.String
	 * @param ischeck   审核状态
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
	
}
