package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 活动报名人表
 * @author 
 * @date 2015-08-28 17:57:42
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_activity_people", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class ActivityPeopleEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 6880869899747203351L;
	/** 活动id */
	private java.lang.String activityids;
	/** IP */
	private java.lang.String ip;
	/** 创建时间 */
	private java.util.Date createtime;
	/** 创建人 */
	private java.lang.String createby;
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 活动id
	 */
	@Column(name = "ACTIVITYIDS", nullable = true, length = 32)
	public java.lang.String getActivityids() {
		return this.activityids;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 活动id
	 */
	public void setActivityids(java.lang.String activityids) {
		this.activityids = activityids;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String IP
	 */
	@Column(name = "IP", nullable = true, length = 255)
	public java.lang.String getIp() {
		return this.ip;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String IP
	 */
	public void setIp(java.lang.String ip) {
		this.ip = ip;
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
	 * @return: java.lang.String 创建人
	 */
	@Column(name = "CREATEBY", nullable = true, length = 255)
	public java.lang.String getCreateby() {
		return this.createby;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 创建人
	 */
	public void setCreateby(java.lang.String createby) {
		this.createby = createby;
	}
}
