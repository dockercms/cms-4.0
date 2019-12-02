package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 留言管理
 * @author 
 * @date 2016-04-26 15:41:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_message_management", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class MessageManagementEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 672706592235335160L;
	/** 留言人 */
	private java.lang.String name;
	/** 留言内容 */
	private java.lang.String content;
	/** 回复内容 */
	private java.lang.String reply;
	/** 创建时间 */
	private java.util.Date addTime;
	/** 审核状态 */
	private java.lang.String ischeck;
	/** 用户ID */
	private java.lang.String memberid;
	/** 站点id */
	private java.lang.String siteId;
	/** 回复时间 */
	private java.util.Date replyTime;
	/** 创建时间 */
	private java.util.Date createdtime;
	/** 回复人 */
	private java.lang.String replyUser;
	/** 回复状态(0未回复1已回复) */
	private java.lang.String replyStatus;
	/** 留言板Id */
	private java.lang.String boardId;
	/**排序*/
	private java.lang.String sort;
	
	@Column(name = "SORT", nullable = true, length = 10)
	public java.lang.String getSort() {
		return sort;
	}

	public void setSort(java.lang.String sort) {
		this.sort = sort;
	}
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 留言人
	 */
	@Column(name = "NAME", nullable = true, length = 50)
	public java.lang.String getName() {
		return this.name;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 留言人
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 留言内容
	 */
	@Column(name = "CONTENT", nullable = true, length = 65535)
	public java.lang.String getContent() {
		return this.content;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 留言内容
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 回复内容
	 */
	@Column(name = "REPLY", nullable = true, length = 65535)
	public java.lang.String getReply() {
		return this.reply;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 回复内容
	 */
	public void setReply(java.lang.String reply) {
		this.reply = reply;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "ADD_TIME", nullable = true)
	public java.util.Date getAddTime() {
		return this.addTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 创建时间
	 */
	public void setAddTime(java.util.Date addTime) {
		this.addTime = addTime;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 审核状态
	 */
	@Column(name = "ISCHECK", nullable = true, length = 10)
	public java.lang.String getIscheck() {
		return this.ischeck;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 审核状态
	 */
	public void setIscheck(java.lang.String ischeck) {
		this.ischeck = ischeck;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 用户ID
	 */
	@Column(name = "MEMBERID", nullable = true, length = 32)
	public java.lang.String getMemberid() {
		return this.memberid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 用户ID
	 */
	public void setMemberid(java.lang.String memberid) {
		this.memberid = memberid;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 站点id
	 */
	@Column(name = "SITE_ID", nullable = true, length = 32)
	public java.lang.String getSiteId() {
		return this.siteId;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 站点id
	 */
	public void setSiteId(java.lang.String siteId) {
		this.siteId = siteId;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 回复时间
	 */
	@Column(name = "REPLY_TIME", nullable = true)
	public java.util.Date getReplyTime() {
		return this.replyTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 回复时间
	 */
	public void setReplyTime(java.util.Date replyTime) {
		this.replyTime = replyTime;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "CREATEDTIME", nullable = true)
	public java.util.Date getCreatedtime() {
		return this.createdtime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 创建时间
	 */
	public void setCreatedtime(java.util.Date createdtime) {
		this.createdtime = createdtime;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 回复人
	 */
	@Column(name = "REPLY_USER", nullable = true, length = 50)
	public java.lang.String getReplyUser() {
		return this.replyUser;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 回复人
	 */
	public void setReplyUser(java.lang.String replyUser) {
		this.replyUser = replyUser;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 回复状态(0未回复1已回复)
	 */
	@Column(name = "REPLY_STATUS", nullable = true, length = 10)
	public java.lang.String getReplyStatus() {
		return this.replyStatus;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 回复状态(0未回复1已回复)
	 */
	public void setReplyStatus(java.lang.String replyStatus) {
		this.replyStatus = replyStatus;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 留言板Id
	 */
	@Column(name = "BOARD_ID", nullable = true, length = 32)
	public java.lang.String getBoardId() {
		return this.boardId;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 留言板Id
	 */
	public void setBoardId(java.lang.String boardId) {
		this.boardId = boardId;
	}
}
