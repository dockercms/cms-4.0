package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 表单选项
 * @author 
 * @date 2015-08-07 17:55:15
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_activity_optioncontent", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class ActivityOptionContentEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -2206892065887665718L;
	/** 是否启用 */
	private java.lang.Integer isEnableds;
	/** 是否必填 */
	private java.lang.Integer isRequired;
	/** 是否前台显示 */
	private java.lang.Integer isReceptionshow;
	/** 选项id */
	private java.lang.String optionids;
	/** 活动id */
	private java.lang.String activityids;
	/** 创建时间 */
	private java.util.Date createtime;
	
	

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 是否启用
	 */
	@Column(name = "IS_ENABLEDS", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getIsEnableds() {
		return this.isEnableds;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 是否启用
	 */
	public void setIsEnableds(java.lang.Integer isEnableds) {
		this.isEnableds = isEnableds;
	}
	
	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 是否前台显示
	 */
	@Column(name = "IS_REQUIRED", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getIsRequired() {
		return this.isRequired;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 是否前台显示
	 */
	public void setIsRequired(java.lang.Integer isRequired) {
		this.isRequired = isRequired;
	}
	
	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer isReceptionshow
	 */
	@Column(name = "IS_RECEPTIONSHOW", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getIsReceptionshow() {
		return this.isReceptionshow;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer isReceptionshow
	 */
	public void setIsReceptionshow(java.lang.Integer isReceptionshow) {
		this.isReceptionshow = isReceptionshow;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 选项id
	 */
	@Column(name = "OPTIONIDS", nullable = true, length = 32)
	public java.lang.String getOptionids() {
		return this.optionids;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 选项id
	 */
	public void setOptionids(java.lang.String optionids) {
		this.optionids = optionids;
	}
	
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
}
