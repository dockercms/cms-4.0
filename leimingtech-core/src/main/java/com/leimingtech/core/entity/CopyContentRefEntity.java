package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 稿件复制或引用关联
 * @author 
 * @date 2015-10-26 17:52:44
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_copy_content_ref", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class CopyContentRefEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -195053738011239905L;
	/** 锁定标记（0：正常；1：锁定） */
	private java.lang.Integer lockFlag;
	/** 主稿件 */
	private java.lang.String mainContentId;
	/** 子稿件 */
	private java.lang.String subContentId;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 创建人 */
	private java.lang.String createBy;
	/** 修改时间 */
	private java.util.Date updateTime;
	/** 修改人 */
	private java.lang.String updateBy;
	/** 备注 */
	private java.lang.String remarks;
	/** 删除标记（0：正常；1：删除） */
	private java.lang.Integer delFlag;
	
	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 锁定标记（0：正常；1：锁定）
	 */
	@Column(name = "LOCK_FLAG", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getLockFlag() {
		return this.lockFlag;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 锁定标记（0：正常；1：锁定）
	 */
	public void setLockFlag(java.lang.Integer lockFlag) {
		this.lockFlag = lockFlag;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 主稿件
	 */
	@Column(name = "MAIN_CONTENT_ID", nullable = true, length = 32)
	public java.lang.String getMainContentId() {
		return this.mainContentId;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 主稿件
	 */
	public void setMainContentId(java.lang.String mainContentId) {
		this.mainContentId = mainContentId;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 子稿件
	 */
	@Column(name = "SUB_CONTENT_ID", nullable = true, length = 32)
	public java.lang.String getSubContentId() {
		return this.subContentId;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 子稿件
	 */
	public void setSubContentId(java.lang.String subContentId) {
		this.subContentId = subContentId;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "CREATE_TIME", nullable = true)
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 创建时间
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 创建人
	 */
	@Column(name = "CREATE_BY", nullable = true, length = 32)
	public java.lang.String getCreateBy() {
		return this.createBy;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 创建人
	 */
	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 修改时间
	 */
	@Column(name = "UPDATE_TIME", nullable = true)
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 修改时间
	 */
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 修改人
	 */
	@Column(name = "UPDATE_BY", nullable = true, length = 32)
	public java.lang.String getUpdateBy() {
		return this.updateBy;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 修改人
	 */
	public void setUpdateBy(java.lang.String updateBy) {
		this.updateBy = updateBy;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 备注
	 */
	@Column(name = "REMARKS", nullable = true, length = 255)
	public java.lang.String getRemarks() {
		return this.remarks;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 备注
	 */
	public void setRemarks(java.lang.String remarks) {
		this.remarks = remarks;
	}
	
	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 删除标记（0：正常；1：删除）
	 */
	@Column(name = "DEL_FLAG", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getDelFlag() {
		return this.delFlag;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 删除标记（0：正常；1：删除）
	 */
	public void setDelFlag(java.lang.Integer delFlag) {
		this.delFlag = delFlag;
	}
}
