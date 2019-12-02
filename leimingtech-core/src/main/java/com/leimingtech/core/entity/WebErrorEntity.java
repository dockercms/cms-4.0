package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 网站纠错
 * @author 
 * @date 2016-04-01 15:02:21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_web_error", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class WebErrorEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 2237663607348209097L;
	/** 姓名 */
	private java.lang.String name;
	/** 电子邮箱 */
	private java.lang.String email;
	/** 联系电话 */
	private java.lang.String telephone;
	/** 工作单位 */
	private java.lang.String workUnit;
	/** 内容 */
	private java.lang.String content;
	/** 站点Id */
	private java.lang.String siteid;
	/** 创建时间 */
	private java.util.Date createtime;
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 姓名
	 */
	@Column(name = "NAME", nullable = true, length = 32)
	public java.lang.String getName() {
		return this.name;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 姓名
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 电子邮箱
	 */
	@Column(name = "EMAIL", nullable = true, length = 50)
	public java.lang.String getEmail() {
		return this.email;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 电子邮箱
	 */
	public void setEmail(java.lang.String email) {
		this.email = email;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 联系电话
	 */
	@Column(name = "TELEPHONE", nullable = true, length = 18)
	public java.lang.String getTelephone() {
		return this.telephone;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 联系电话
	 */
	public void setTelephone(java.lang.String telephone) {
		this.telephone = telephone;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 工作单位
	 */
	@Column(name = "WORK_UNIT", nullable = true, length = 100)
	public java.lang.String getWorkUnit() {
		return this.workUnit;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 工作单位
	 */
	public void setWorkUnit(java.lang.String workUnit) {
		this.workUnit = workUnit;
	}
	
	/**
	 * 方法: 取得java.lang.Object
	 *
	 * @return: java.lang.Object 内容
	 */
	@Column(name = "CONTENT", nullable = true)
	public java.lang.String getContent() {
		return this.content;
	}

	/**
	 * 方法: 设置java.lang.Object
	 *
	 * @param: java.lang.Object 内容
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
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
