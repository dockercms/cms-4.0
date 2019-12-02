package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 业务咨询
 * @author 
 * @date 2016-03-31 14:27:11
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_business_consulting", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class BusinessConsultingEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1653353337160578417L;
	/** 业务类别 */
	private java.lang.String businessClass;
	/** 回复部门id */
	private java.lang.String departid;
	/** 咨询人 */
	private java.lang.String name;
	/** 联系方式 */
	private java.lang.String phone;
	/** 留言内容 */
	private java.lang.String message;
	/** 留言时间 */
	private java.util.Date messageTime;
	/** 回复内容 */
	private java.lang.String replyCount;
	/** 回复时间 */
	private java.util.Date replyTime;
	/** 创建时间 */
	private java.util.Date createtime;
	/** 回复状态(0未回复1已回复) */
	private java.lang.String replyStatus;
	/** 审核状态(0未审核1已审核) */
	private java.lang.String ischeck;
	/** 站点Id */
	private java.lang.String siteid;
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 业务类别
	 */
	@Column(name = "BUSINESS_CLASS", nullable = true, length = 32)
	public java.lang.String getBusinessClass() {
		return this.businessClass;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 业务类别
	 */
	public void setBusinessClass(java.lang.String businessClass) {
		this.businessClass = businessClass;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 回复部门id
	 */
	@Column(name = "DEPARTID", nullable = true, length = 32)
	public java.lang.String getDepartid() {
		return this.departid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 回复部门id
	 */
	public void setDepartid(java.lang.String departid) {
		this.departid = departid;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 咨询人
	 */
	@Column(name = "NAME", nullable = true, length = 20)
	public java.lang.String getName() {
		return this.name;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 咨询人
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 联系方式
	 */
	@Column(name = "PHONE", nullable = true, length = 18)
	public java.lang.String getPhone() {
		return this.phone;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 联系方式
	 */
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}
	
	/**
	 * 方法: 取得java.lang.Object
	 *
	 * @return: java.lang.Object 留言内容
	 */
	@Column(name = "MESSAGE", nullable = true)
	public java.lang.String getMessage() {
		return this.message;
	}

	/**
	 * 方法: 设置java.lang.Object
	 *
	 * @param: java.lang.Object 留言内容
	 */
	public void setMessage(java.lang.String message) {
		this.message = message;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 留言时间
	 */
	@Column(name = "MESSAGE_TIME", nullable = true)
	public java.util.Date getMessageTime() {
		return this.messageTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 留言时间
	 */
	public void setMessageTime(java.util.Date messageTime) {
		this.messageTime = messageTime;
	}
	
	/**
	 * 方法: 取得java.lang.Object
	 *
	 * @return: java.lang.Object 回复内容
	 */
	@Column(name = "REPLY_COUNT", nullable = true)
	public java.lang.String getReplyCount() {
		return this.replyCount;
	}

	/**
	 * 方法: 设置java.lang.Object
	 *
	 * @param: java.lang.Object 回复内容
	 */
	public void setReplyCount(java.lang.String replyCount) {
		this.replyCount = replyCount;
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
	@Column(name = "CREATETIME", nullable = true)
	public java.util.Date getCreatetime() {
		return this.createtime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 创建时间
	 */
	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
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
	 * @return: java.lang.String 审核状态(0未审核1已审核)
	 */
	@Column(name = "ISCHECK", nullable = true, length = 10)
	public java.lang.String getIscheck() {
		return this.ischeck;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 审核状态(0未审核1已审核)
	 */
	public void setIscheck(java.lang.String ischeck) {
		this.ischeck = ischeck;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 站点Id
	 */
	@Column(name = "SITEID", nullable = true, length = 32)
	public java.lang.String getSiteid() {
		return this.siteid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 站点Id
	 */
	public void setSiteid(java.lang.String siteid) {
		this.siteid = siteid;
	}
}
